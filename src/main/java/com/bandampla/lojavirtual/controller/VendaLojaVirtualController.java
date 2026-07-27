package com.bandampla.lojavirtual.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.controller.api.VendaLojaVirtualControllerAPI;
import com.bandampla.lojavirtual.dto.VendaLojaVirtualDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.VendaLojaVirtualService;

@RestController
@RequestMapping("/vendalojavirtual")
public class VendaLojaVirtualController implements VendaLojaVirtualControllerAPI {

	private final VendaLojaVirtualService vendaLojaVirtualService;
	private final HttpServletRequest request;

	public VendaLojaVirtualController(VendaLojaVirtualService vendaLojaVirtualService, HttpServletRequest request) {
		this.vendaLojaVirtualService = vendaLojaVirtualService;
		this.request = request;
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<VendaLojaVirtualDTO>> cadastrar(VendaLojaVirtualDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom, MessagingException, IOException {

		var traceId = UUID.randomUUID().toString();

		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),
				"Venda registrada com sucesso no sistema", vendaLojaVirtualService.cadastrar(dto, usuarioLogado), request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(Long id, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		var traceId = UUID.randomUUID().toString();
		vendaLojaVirtualService.deletar(id, usuarioLogado);

		ResponseDefaultDTO<Void> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Pedido de venda cancelado e removido com sucesso", null, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<VendaLojaVirtualDTO>> buscarPorId(Long id,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		var traceId = UUID.randomUUID().toString();
		VendaLojaVirtualDTO retorno = vendaLojaVirtualService.buscarPorId(id, usuarioLogado);

		ResponseDefaultDTO<VendaLojaVirtualDTO> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Venda recuperada com sucesso", retorno, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<List<VendaLojaVirtualDTO>> buscarPorNumeroPedido(String numeroPedido,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		return ResponseEntity.ok(vendaLojaVirtualService.buscarPorNumeroPedido(numeroPedido, usuarioLogado));
	}

	@Override
	public ResponseEntity<List<VendaLojaVirtualDTO>> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		return ResponseEntity.ok(vendaLojaVirtualService.buscarTodosPorEmpresa(usuarioLogado));
	}

	@Override
	public ResponseEntity<Page<VendaLojaVirtualDTO>> buscarAvancado(String numeroPedido, int page, int size,
			UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity.ok(vendaLojaVirtualService.buscarAvancado(numeroPedido, page, size, usuarioLogado));
	}

	@Override
	public ResponseEntity<Page<VendaLojaVirtualDTO>> buscarPaginado(int page, int size, String sort, String direction,
			UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity.ok(vendaLojaVirtualService.buscarPaginado(page, size, sort, direction, usuarioLogado));
	}
}
