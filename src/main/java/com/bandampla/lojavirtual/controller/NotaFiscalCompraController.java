package com.bandampla.lojavirtual.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.controller.api.NotaFiscalCompraControllerAPI;
import com.bandampla.lojavirtual.dto.NotaFiscalCompraDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.NotaFiscalCompraService;

@RestController
@RequestMapping("/notafiscalcompra")
public class NotaFiscalCompraController implements NotaFiscalCompraControllerAPI {

	private final NotaFiscalCompraService notaFiscalCompraService;
	private final HttpServletRequest request;

	public NotaFiscalCompraController(NotaFiscalCompraService notaFiscalCompraService, HttpServletRequest request) {
		this.notaFiscalCompraService = notaFiscalCompraService;
		this.request = request;
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<NotaFiscalCompraDTO>> cadastrar(@Valid NotaFiscalCompraDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		NotaFiscalCompraDTO retorno = notaFiscalCompraService.cadastrar(dto, usuarioLogado);

		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),
				"Nota Fiscal de Compra criada com sucesso", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<NotaFiscalCompraDTO>> atualizar(Long id, @Valid NotaFiscalCompraDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		NotaFiscalCompraDTO retorno = notaFiscalCompraService.atualizar(id, dto, usuarioLogado);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Nota Fiscal de Compra atualizada com sucesso", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(Long id, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		notaFiscalCompraService.deletar(id, usuarioLogado);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Nota Fiscal de Compra deletada com sucesso", null, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<NotaFiscalCompraDTO>>> buscarPorDescricao(String numeroNota,
			UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		List<NotaFiscalCompraDTO> retorno = notaFiscalCompraService.buscarPorDescricao(numeroNota, usuarioLogado);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Notas de compra localizadas com sucesso", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<NotaFiscalCompraDTO>>> buscarTodos(
			UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		List<NotaFiscalCompraDTO> retorno = notaFiscalCompraService.buscarTodosPorEmpresa(usuarioLogado);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Todas as notas de compra listadas",
				retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Page<NotaFiscalCompraDTO>>> buscarAvancado(String numeroNota, int page,
			int size, UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		Page<NotaFiscalCompraDTO> retorno = notaFiscalCompraService.buscarAvancado(numeroNota, page, size,
				usuarioLogado);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Busca avançada de notas concluída",
				retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Page<NotaFiscalCompraDTO>>> buscarPaginado(int page, int size, String sort,
			String direction, UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		Page<NotaFiscalCompraDTO> retorno = notaFiscalCompraService.buscarPaginado(page, size, sort, direction,
				usuarioLogado);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Listagem paginada de notas concluída", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<NotaFiscalCompraDTO>>> buscarPorProduto(Long produtoId,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		List<NotaFiscalCompraDTO> retorno = notaFiscalCompraService.buscarPorProduto(produtoId, usuarioLogado);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Notas encontradas para o produto informado", retorno, request.getRequestURI(), traceId));
	}
}