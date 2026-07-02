package com.bandampla.lojavirtual.exception;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.UUID;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bandampla.lojavirtual.dto.response.ErrorResponseDTO;
import com.bandampla.lojavirtual.service.SendMailService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class ControllerExceptionAdvertise extends ResponseEntityExceptionHandler {

	@Autowired
	private SendMailService sendMailService;

	// =========================
	// UTILITÁRIOS
	// =========================
	private String generateTraceId() {
		return UUID.randomUUID().toString();
	}

	private String extractPath(WebRequest request) {
		return request.getDescription(false).replace("uri=", "");
	}

	private void enviarEmailErro(Exception ex) {
		StringBuilder mensagemHtml = new StringBuilder();
		mensagemHtml.append("<b>Olá Desenvolvedor!</b><br/>")
				.append("<b>Alerta:</b> Foi detectado um erro na plataforma Loja Virtual Bandampla.<br/>")
				.append("<b>Erro:</b><br/>").append(ExceptionUtils.getStackTrace(ex));

		try {
			sendMailService.enviarEmailHtml("Erro detectado na plataforma Loja Virtual Bandampla!",
					mensagemHtml.toString(), "nilton.brito@outlook.com");
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
	}

	// =========================
	// EXCEPTION DE REGRA DE NEGÓCIO
	// =========================
	@ExceptionHandler(ExceptionCustom.class)
	protected ResponseEntity<Object> handleExceptionCustom(ExceptionCustom ex, WebRequest request) {
		ex.printStackTrace();
		// enviarEmailErro(ex);

		return new ResponseEntity<>(new ErrorResponseDTO(HttpStatus.BAD_REQUEST.toString(), "Erro de negócio",
				ex.getMessage(), extractPath(request), generateTraceId()), HttpStatus.BAD_REQUEST);
	}

	// =========================
	// ERROS DE JSON / ENUM / CORPO MAL FORMADO
	// =========================
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String traceId = generateTraceId();
		String path = extractPath(request);
		ErrorResponseDTO errorResponseDTO;

		// -------------------------
		// ERRO DE VALIDAÇÃO (Bean Validation)
		// -------------------------
		if (ex instanceof MethodArgumentNotValidException) {

			StringBuilder sb = new StringBuilder();
			((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors().forEach(
					err -> sb.append(err.getField()).append(": ").append(err.getDefaultMessage()).append(" | "));

			errorResponseDTO = new ErrorResponseDTO("400", "Erro de validação", sb.toString().trim(), path, traceId);

			ex.printStackTrace();
			// enviarEmailErro(ex);
			return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
		}

		// -------------------------
		// ERRO DE JSON MAL FORMADO
		// -------------------------
		if (ex instanceof HttpMessageNotReadableException) {

			Throwable rootCause = ExceptionUtils.getRootCause(ex);

			if (rootCause instanceof InvalidFormatException) {

				InvalidFormatException ife = (InvalidFormatException) rootCause;

				String field = ife.getPath() != null && !ife.getPath().isEmpty() ? ife.getPath().get(0).getFieldName()
						: "campo";
				String enumClass = ife.getTargetType() != null ? ife.getTargetType().getSimpleName() : "";

				String field1 = ife.getPath().get(0).getFieldName();
				String targetType = ife.getTargetType().getSimpleName();

				if ("TipoPessoa".equals(enumClass)) {
					errorResponseDTO = new ErrorResponseDTO("400", "Valor inválido para '" + field + "'",
							"Use apenas: FISICA ou JURIDICA", path, traceId);
				} else if (targetType.equals("Boolean")) {
					return new ResponseEntity<>(new ErrorResponseDTO("400", "Valor inválido para '" + field1 + "'",
							"Use apenas true ou false", path, traceId), HttpStatus.BAD_REQUEST);
				} else {
					errorResponseDTO = new ErrorResponseDTO("400", "Valor inválido para o campo '" + field + "'",
							"Tipo de dado incompatível", path, traceId);
				}
			} else {
				errorResponseDTO = new ErrorResponseDTO("400", "Erro ao ler o JSON enviado",
						"Verifique o corpo da requisição", path, traceId);
			}
			ex.printStackTrace();
			// enviarEmailErro(ex);
			return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
		}

		// -------------------------
		// ERRO DE PARÂMETRO INVÁLIDO
		// -------------------------
		if (ex instanceof IllegalArgumentException) {
			ex.printStackTrace();
			// enviarEmailErro(ex);
			return new ResponseEntity<>(
					new ErrorResponseDTO("400", "Parâmetro inválido", ex.getMessage(), path, traceId),
					HttpStatus.BAD_REQUEST);
		}

		// -------------------------
		// ERRO DE MÉTODO HTTP NÃO SUPORTADO
		// -------------------------
		if (ex instanceof HttpRequestMethodNotSupportedException) {
			ex.printStackTrace();
			// enviarEmailErro(ex);
			return new ResponseEntity<>(new ErrorResponseDTO("405", "Método HTTP não suportado",
					"Use o método correto para este endpoint", path, traceId), HttpStatus.METHOD_NOT_ALLOWED);
		}

		// -------------------------
		// ERRO DE PERMISSÃO NEGADA
		// -------------------------
		if (ex instanceof AccessDeniedException) {
			ex.printStackTrace();
			// enviarEmailErro(ex);
			return new ResponseEntity<>(new ErrorResponseDTO("403", "Acesso negado",
					"Você não possui permissão para acessar este recurso", path, traceId), HttpStatus.FORBIDDEN);
		}

		// -------------------------
		// ERRO GENÉRICO
		// -------------------------
		errorResponseDTO = new ErrorResponseDTO(String.valueOf(status.value()), status.getReasonPhrase(),
				ex.getMessage(), path, traceId);

		ex.printStackTrace();
		// enviarEmailErro(ex);

		return new ResponseEntity<>(errorResponseDTO, status);
	}

	// =========================
	// ERROS DE BANCO (FK, INTEGRIDADE, SQL)
	// =========================
	@ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class })
	protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex, WebRequest request) {

		String detalhes;

		if (ex instanceof SQLException) {
			detalhes = ((SQLException) ex).getCause() != null ? ((SQLException) ex).getCause().getMessage()
					: ex.getMessage();
		} else if (ex instanceof DataIntegrityViolationException) {
			detalhes = ((DataIntegrityViolationException) ex).getCause() != null
					? ((DataIntegrityViolationException) ex).getCause().getMessage()
					: ex.getMessage();
		} else if (ex instanceof ConstraintViolationException) {
			detalhes = ((ConstraintViolationException) ex).getCause() != null
					? ((ConstraintViolationException) ex).getCause().getMessage()
					: ex.getMessage();
		} else {
			detalhes = ex.getMessage();
		}

		ex.printStackTrace();
		// enviarEmailErro(ex);

		return new ResponseEntity<>(new ErrorResponseDTO("500", "Erro de integridade no banco de dados", detalhes,
				extractPath(request), generateTraceId()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
