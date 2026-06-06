/**
 * 
 */
package com.bandampla.lojavirtual.exception;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bandampla.lojavirtual.dto.ObjectErrorDTO;
import com.bandampla.lojavirtual.service.SendMailService;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 3 de mai. de 2026
 */

@RestControllerAdvice
public class ControllerExceptionAdvertise extends ResponseEntityExceptionHandler {

	@Autowired
	private SendMailService sendMailService;
	
    @ExceptionHandler(ExceptionCustom.class)
    protected ResponseEntity<Object> handleExceptionCustom(ExceptionCustom ex) {
        ObjectErrorDTO objectErrorDTO = new ObjectErrorDTO();
        objectErrorDTO.setError(ex.getMessage());
        objectErrorDTO.setCode(HttpStatus.BAD_REQUEST.toString());
        ex.printStackTrace();
        return new ResponseEntity<>(objectErrorDTO, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        ObjectErrorDTO objectErrorDTO = new ObjectErrorDTO();
        String msg;

        if (ex instanceof MethodArgumentNotValidException) {
            StringBuilder sb = new StringBuilder();
            ((MethodArgumentNotValidException) ex).getBindingResult()
                    .getAllErrors()
                    .forEach(e -> sb.append(e.getDefaultMessage()).append("\n"));
            msg = sb.toString();
        } else if (ex instanceof HttpMessageNotReadableException) {
            msg = "Não está sendo enviado dados para o BODY corpo da requisição";
        } else if (ex instanceof IllegalArgumentException) {
            msg = "?";
        } else {
            msg = ex.getMessage();
        }

        objectErrorDTO.setError(msg);
        objectErrorDTO.setCode(status.value() + " ==> " + status.getReasonPhrase());

        ex.printStackTrace();

		StringBuilder mensagemHtml = new StringBuilder();
		mensagemHtml.append("<b>Olá Desenvolvedor!</b>").append("<br/>");
		mensagemHtml.append("<b>Alerta: </b>"+"Foi detectado um erro na loja virtual que precisa ser analisado e reparado.").append("<br/>");
		mensagemHtml.append("<b>Erro: </b>"+ ExceptionUtils.getStackTrace(ex)).append("<br/>");
		
		try {
			sendMailService.enviarEmailHtml("Erro detectado na plataforma Loja Virtual Bandampla!" , mensagemHtml.toString(), "nilton.brito@outlook.com");
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ResponseEntity<>(objectErrorDTO, status);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class })
    protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {
        ObjectErrorDTO objectErrorDTO = new ObjectErrorDTO();
        String msg;

        if (ex instanceof SQLException) {
            msg = "Erro de SQL do banco de dados: " + ((SQLException) ex).getCause().getMessage();
        } else if (ex instanceof DataIntegrityViolationException) {
            msg = "Erro de integridade no banco de dados: "
                    + ((DataIntegrityViolationException) ex).getCause().getMessage();
        } else if (ex instanceof ConstraintViolationException) {
            msg = "Erro de chave estrangeira no banco de dados: "
                    + ((ConstraintViolationException) ex).getCause().getMessage();
        } else {
            msg = ex.getMessage();
        }

        objectErrorDTO.setError(msg);
        objectErrorDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());

        ex.printStackTrace();

		StringBuilder mensagemHtml = new StringBuilder();
		mensagemHtml.append("<b>Olá Desenvolvedor!</b>").append("<br/>");
		mensagemHtml.append("<b>Alerta: </b>"+"Foi detectado um erro na loja virtual que precisa ser analisado e reparado.").append("<br/>");
		mensagemHtml.append("<b>Erro: </b>"+ ExceptionUtils.getStackTrace(ex)).append("<br/>");
		
		try {
			sendMailService.enviarEmailHtml("Erro detectado na plataforma Loja Virtual Bandampla!" , mensagemHtml.toString(), "nilton.brito@outlook.com");
		} catch (UnsupportedEncodingException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ResponseEntity<>(objectErrorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
