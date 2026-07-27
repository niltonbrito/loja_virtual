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

import com.bandampla.lojavirtual.controller.api.MarcaProdutoControllerAPI;
import com.bandampla.lojavirtual.dto.MarcaProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.MarcaProdutoService;

@RestController
@RequestMapping("/marca")
public class MarcaProdutoController implements MarcaProdutoControllerAPI {

	private final MarcaProdutoService marcaProdutoService;
	private final HttpServletRequest request;

	public MarcaProdutoController(MarcaProdutoService marcaProdutoService, HttpServletRequest request) {
		this.marcaProdutoService = marcaProdutoService;
		this.request = request;
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<MarcaProdutoDTO>> cadastrar(@Valid MarcaProdutoDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		MarcaProdutoDTO retorno = marcaProdutoService.cadastrar(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),
				"Marca de produto criada com sucesso", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<MarcaProdutoDTO>> atualizar(Long id, @Valid MarcaProdutoDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		MarcaProdutoDTO retorno = marcaProdutoService.atualizar(id, dto);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Marca de produto atualizada com sucesso", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(Long id, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		marcaProdutoService.deletar(id, usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Marca de produto deletada com sucesso", null, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<MarcaProdutoDTO>>> buscarPorDescricao(String descricao,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		List<MarcaProdutoDTO> retorno = marcaProdutoService.buscarPorDescricao(descricao, usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Marcas recuperadas por descrição",
				retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<MarcaProdutoDTO>>> buscarTodosPorEmpresa(
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		List<MarcaProdutoDTO> retorno = marcaProdutoService.buscarTodosPorEmpresa(usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Todas as marcas listadas por empresa", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Page<MarcaProdutoDTO>>> buscarAvancado(String descricao, int page,
			int size, UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		Page<MarcaProdutoDTO> retorno = marcaProdutoService.buscarAvancado(descricao, page, size,
				usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Busca avançada de marcas concluída", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Page<MarcaProdutoDTO>>> buscarPaginado(int page, int size, String sort,
			String direction, UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		Page<MarcaProdutoDTO> retorno = marcaProdutoService.buscarPaginado(page, size, sort, direction,
				usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Listagem paginada de marcas concluída", retorno, request.getRequestURI(), traceId));
	}
}
