package com.bandampla.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.dto.ProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@PostMapping
	public ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> cadastrar(@Valid @RequestBody ProdutoDTO dto,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDefaultDTO<>("Produto criada com sucesso", produtoService.cadastrar(dto)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody ProdutoDTO dto,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDefaultDTO<>("Produto atualizada com sucesso", produtoService.atualizar(id, dto)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		produtoService.deletar(id, usuarioLogado.getEmpresaId());

		// Retorna HTTP 200 OK com o JSON estruturado para o front-end ler a string
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDefaultDTO<>("Produto deletada com sucesso", null));
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<ProdutoDTO>> buscarPorDescricao(@RequestParam String descricao,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		return ResponseEntity.ok(produtoService.buscarPorDescricao(descricao, usuarioLogado.getEmpresaId()));
	}

	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> buscarTodosPorEmpresa(
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		return ResponseEntity.ok(produtoService.buscarTodosPorEmpresa(usuarioLogado.getEmpresaId()));
	}

	@GetMapping("/busca-avancada")
	public ResponseEntity<Page<ProdutoDTO>> buscarAvancado(@RequestParam(required = false) String descricao,
			@RequestParam(required = false) Boolean ativo, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity
				.ok(produtoService.buscarAvancado(descricao, ativo, page, size, usuarioLogado.getEmpresaId()));
	}

	@GetMapping("/paginado")
	public ResponseEntity<Page<ProdutoDTO>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity
				.ok(produtoService.buscarPaginado(page, size, sort, direction, usuarioLogado.getEmpresaId()));
	}
}
