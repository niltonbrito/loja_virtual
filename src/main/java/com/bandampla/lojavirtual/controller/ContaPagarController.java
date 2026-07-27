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

import com.bandampla.lojavirtual.controller.api.ContaPagarControllerAPI;
import com.bandampla.lojavirtual.dto.ContaPagarDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.ContaPagarService;

@RestController
@RequestMapping("/contapagar")
public class ContaPagarController implements ContaPagarControllerAPI {

	private final ContaPagarService contaPagarService;
	private final HttpServletRequest request;

	public ContaPagarController(ContaPagarService contaPagarService, HttpServletRequest request) {
		this.contaPagarService = contaPagarService;
		this.request = request;
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<ContaPagarDTO>> cadastrar(@Valid ContaPagarDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		ContaPagarDTO criado = contaPagarService.cadastrar(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),
				"Conta a Pagar criada com sucesso", criado, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<ContaPagarDTO>> atualizar(Long id, @Valid ContaPagarDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		ContaPagarDTO atualizado = contaPagarService.atualizar(id, dto);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Conta a Pagar atualizada com sucesso", atualizado, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(Long id, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		contaPagarService.deletar(id, usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Conta a Pagar deletada com sucesso", null, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<ContaPagarDTO>>> buscarPorDescricao(String descricao,
			UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		List<ContaPagarDTO> lista = contaPagarService.buscarPorDescricao(descricao, usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Contas a pagar filtradas com sucesso", lista, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<ContaPagarDTO>>> buscarTodos(UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		List<ContaPagarDTO> lista = contaPagarService.buscarTodosPorEmpresa(usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Todas as contas a pagar listadas",
				lista, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Page<ContaPagarDTO>>> buscarAvancado(String descricao, int page, int size,
			UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		Page<ContaPagarDTO> resultado = contaPagarService.buscarAvancado(descricao, page, size,
				usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Busca avançada financeira concluída", resultado, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Page<ContaPagarDTO>>> buscarPaginado(int page, int size, String sort,
			String direction, UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		Page<ContaPagarDTO> resultado = contaPagarService.buscarPaginado(page, size, sort, direction,
				usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Listagem paginada financeira concluída", resultado, request.getRequestURI(), traceId));
	}
}