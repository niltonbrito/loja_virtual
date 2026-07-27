package com.bandampla.lojavirtual.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.controller.api.FormaPagamentoControllerAPI;
import com.bandampla.lojavirtual.dto.FormaPagamentoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.FormaPagamentoService;

@RestController
@RequestMapping("/formapagamento")
public class FormaPagamentoController implements FormaPagamentoControllerAPI {

	private final FormaPagamentoService formaPagamentoService;
	private final HttpServletRequest request;

	public FormaPagamentoController(FormaPagamentoService formaPagamentoService, HttpServletRequest request) {
		this.formaPagamentoService = formaPagamentoService;
		this.request = request;
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<FormaPagamentoDTO>> cadastrar(FormaPagamentoDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom, MessagingException, IOException {

		String traceId = UUID.randomUUID().toString();
		FormaPagamentoDTO retorno = formaPagamentoService.cadastrar(dto, usuarioLogado);

		ResponseDefaultDTO<FormaPagamentoDTO> response = new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),
				"Forma de pagamento registrada com sucesso", retorno, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<FormaPagamentoDTO>> atualizar(Long id, FormaPagamentoDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom, IOException {

		String traceId = UUID.randomUUID().toString();
		dto.setEmpresaId(usuarioLogado.getEmpresaId());

		FormaPagamentoDTO retorno = formaPagamentoService.atualizar(id, dto, usuarioLogado);

		ResponseDefaultDTO<FormaPagamentoDTO> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Forma de pagamento atualizada com sucesso", retorno, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(Long id, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();
		formaPagamentoService.deletar(id, usuarioLogado);

		ResponseDefaultDTO<Void> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Forma de pagamento deletada com sucesso", null, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<FormaPagamentoDTO>> buscarPorId(Long id,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();
		FormaPagamentoDTO retorno = formaPagamentoService.buscarPorId(id, usuarioLogado);

		ResponseDefaultDTO<FormaPagamentoDTO> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Forma de pagamento recuperada com sucesso", retorno, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<List<FormaPagamentoDTO>> buscarPorDescricao(String descricao,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		return ResponseEntity.ok(formaPagamentoService.buscarPorDescricao(descricao, usuarioLogado));
	}

	@Override
	public ResponseEntity<List<FormaPagamentoDTO>> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		return ResponseEntity.ok(formaPagamentoService.buscarTodosPorEmpresa(usuarioLogado));
	}
}