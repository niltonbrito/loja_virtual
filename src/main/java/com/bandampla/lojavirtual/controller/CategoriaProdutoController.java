package com.bandampla.lojavirtual.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.dto.CategoriaProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.CategoriaProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/categoria")
@Tag(name = "Categoria de Produto", description = "Operações de gestão de Nota Fiscal de Compra da empresa")
public class CategoriaProdutoController {

	@Autowired
	private CategoriaProdutoService categoriaProdutoService;

	@Autowired
	private HttpServletRequest request;

	@PostMapping
	@Operation(summary = "Cadastrar Categoria de Produto", description = "Cria uma nova Categoria de Produto vinculada à empresa do usuário logado.")
	public ResponseEntity<ResponseDefaultDTO<CategoriaProdutoDTO>> cadastrar(
			@Valid @RequestBody CategoriaProdutoDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(), "Categoria criada com sucesso",
				categoriaProdutoService.cadastrar(dto), request.getRequestURI(), traceId));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<CategoriaProdutoDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody CategoriaProdutoDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();

		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Categoria atualizada com sucesso",
				categoriaProdutoService.atualizar(id, dto), request.getRequestURI(), traceId));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();
		categoriaProdutoService.deletar(id, usuarioLogado.getEmpresaId());

		// Retorna HTTP 200 OK com o JSON estruturado para o front-end ler a string
		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.NO_CONTENT.toString(),
				"Categoria deletada com sucesso", null, request.getRequestURI(), traceId));
	}

	@GetMapping("/buscar")
	public ResponseEntity<ResponseDefaultDTO<List<CategoriaProdutoDTO>>> buscarPorDescricao(
			@RequestParam String descricao, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Buscar por descrição",
				categoriaProdutoService.buscarPorDescricao(descricao, usuarioLogado.getEmpresaId()),
				request.getRequestURI(), traceId));
	}

	@GetMapping
	public ResponseEntity<ResponseDefaultDTO<List<CategoriaProdutoDTO>>> buscarTodosPorEmpresa(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		return ResponseEntity
				.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Listar todas as Categoria de Produto",
						categoriaProdutoService.buscarTodosPorEmpresa(usuarioLogado.getEmpresaId()),
						request.getRequestURI(), traceId));
	}

	@GetMapping("/busca-avancada")
	public ResponseEntity<ResponseDefaultDTO<Page<CategoriaProdutoDTO>>> buscarAvancado(
			@RequestParam(required = false) String descricao,
			@RequestParam(required = false) @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		String traceId = UUID.randomUUID().toString();
		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Busca avançada",
				categoriaProdutoService.buscarAvancado(descricao, usuarioLogado.getEmpresaId(), page, size),
				request.getRequestURI(), traceId));
	}

	@GetMapping("/paginado")
	public ResponseEntity<ResponseDefaultDTO<Page<CategoriaProdutoDTO>>> buscarPaginado(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		String traceId = UUID.randomUUID().toString();
		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Listar paginado",
				categoriaProdutoService.buscarPaginado(page, size, sort, direction, usuarioLogado.getEmpresaId()),
				request.getRequestURI(), traceId));
	}
}
