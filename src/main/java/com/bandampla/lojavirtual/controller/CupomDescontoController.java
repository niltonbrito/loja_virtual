package com.bandampla.lojavirtual.controller;

import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.controller.api.CupomDescontoControllerAPI;
import com.bandampla.lojavirtual.dto.CupomDescontoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.CupomDescontoService;

@RestController
@RequestMapping("/cupom_desconto")
public class CupomDescontoController implements CupomDescontoControllerAPI {

	private final CupomDescontoService cupomDescontoService;
	private final HttpServletRequest request;

	// Injeção por construtor nativo puro imutável
	public CupomDescontoController(CupomDescontoService cupomDescontoService, HttpServletRequest request) {
		this.cupomDescontoService = cupomDescontoService;
		this.request = request;
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<CupomDescontoDTO>> cadastrar(@Valid CupomDescontoDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		CupomDescontoDTO retorno = cupomDescontoService.cadastrar(dto, usuarioLogado);

		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),
				"Cupom de Desconto cadastrado com sucesso", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<CupomDescontoDTO>> atualizar(Long id, @Valid CupomDescontoDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		CupomDescontoDTO retorno = cupomDescontoService.atualizar(id, dto, usuarioLogado);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Cupom de Desconto atualizado com sucesso", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(Long id, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		cupomDescontoService.deletar(id, usuarioLogado);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Cupom de Desconto excluído com sucesso", null, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<CupomDescontoDTO>>> buscarPorDescricao(String codigoDescricao, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		List<CupomDescontoDTO> retorno = cupomDescontoService.buscarPorDescricao(codigoDescricao, usuarioLogado);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Cupons promocionais localizados com sucesso", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<CupomDescontoDTO>>> buscarTodos(@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		List<CupomDescontoDTO> retorno = cupomDescontoService.buscarTodosPorEmpresa(usuarioLogado);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), 
				"Todos os cupons cadastrados recuperados", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Page<CupomDescontoDTO>>> buscarAvancado(String codigoDescricao, int page, int size, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		Page<CupomDescontoDTO> retorno = cupomDescontoService.buscarAvancado(codigoDescricao, page, size, usuarioLogado);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), 
				"Busca avançada promocional concluída", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Page<CupomDescontoDTO>>> buscarPaginado(int page, int size, String sort, String direction, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		Page<CupomDescontoDTO> retorno = cupomDescontoService.buscarPaginado(page, size, sort, direction, usuarioLogado);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Listagem paginada promocional concluída", retorno, request.getRequestURI(), traceId));
	}
}