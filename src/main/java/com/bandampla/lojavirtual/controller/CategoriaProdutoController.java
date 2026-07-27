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

import com.bandampla.lojavirtual.controller.api.CategoriaProdutoControllerAPI;
import com.bandampla.lojavirtual.dto.CategoriaProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.CategoriaProdutoService;

@RestController
@RequestMapping("/categoria")
public class CategoriaProdutoController implements CategoriaProdutoControllerAPI {

	private final CategoriaProdutoService categoriaProdutoService;
	private final HttpServletRequest request;

	public CategoriaProdutoController(CategoriaProdutoService categoriaProdutoService, HttpServletRequest request) {
		this.categoriaProdutoService = categoriaProdutoService;
		this.request = request;
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<CategoriaProdutoDTO>> cadastrar(@Valid CategoriaProdutoDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		dto.setEmpresaId(usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(), "Categoria criada com sucesso",
				categoriaProdutoService.cadastrar(dto), request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<CategoriaProdutoDTO>> atualizar(Long id, @Valid CategoriaProdutoDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		dto.setEmpresaId(usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Categoria atualizada com sucesso",
				categoriaProdutoService.atualizar(id, dto), request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(Long id, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		categoriaProdutoService.deletar(id, usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.NO_CONTENT.toString(),
				"Categoria deletada com sucesso", null, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<CategoriaProdutoDTO>>> buscarPorDescricao(String descricao,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Buscar por descrição",
				categoriaProdutoService.buscarPorDescricao(descricao, usuarioLogado.getEmpresaId()),
				request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<CategoriaProdutoDTO>>> buscarTodosPorEmpresa(
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();

		return ResponseEntity
				.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Listar todas as Categorias de Produto",
						categoriaProdutoService.buscarTodosPorEmpresa(usuarioLogado.getEmpresaId()),
						request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Page<CategoriaProdutoDTO>>> buscarAvancado(String descricao,
			UsuarioLogadoPrincipal usuarioLogado, int page, int size) {
		String traceId = UUID.randomUUID().toString();

		return ResponseEntity
				.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Busca avançada de categorias concluída",
						categoriaProdutoService.buscarAvancado(descricao, usuarioLogado.getEmpresaId(), page, size),
						request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Page<CategoriaProdutoDTO>>> buscarPaginado(int page, int size, String sort,
			String direction, UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Listar paginado",
				categoriaProdutoService.buscarPaginado(page, size, sort, direction, usuarioLogado.getEmpresaId()),
				request.getRequestURI(), traceId));
	}
}