package com.bandampla.lojavirtual.controller;

import java.util.List;

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

@RestController
@RequestMapping("/categoria")
public class CategoriaProdutoController {

	@Autowired
	private CategoriaProdutoService categoriaProdutoService;

	@PostMapping
	public ResponseEntity<ResponseDefaultDTO<CategoriaProdutoDTO>> cadastrar(
			@Valid @RequestBody CategoriaProdutoDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDefaultDTO<>("Categoria criada com sucesso", categoriaProdutoService.cadastrar(dto)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<CategoriaProdutoDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody CategoriaProdutoDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDefaultDTO<>("Categoria atualizada com sucesso",
				categoriaProdutoService.atualizar(id, dto)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		categoriaProdutoService.deletar(id, usuarioLogado.getEmpresaId());

		// Retorna HTTP 200 OK com o JSON estruturado para o front-end ler a string
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDefaultDTO<>("Categoria deletada com sucesso", null));
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<CategoriaProdutoDTO>> buscarPorDescricao(@RequestParam String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		return ResponseEntity.ok(categoriaProdutoService.buscarPorDescricao(descricao, usuarioLogado.getEmpresaId()));
	}

	@GetMapping
	public ResponseEntity<List<CategoriaProdutoDTO>> buscarTodosPorEmpresa(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		return ResponseEntity.ok(categoriaProdutoService.buscarTodosPorEmpresa(usuarioLogado.getEmpresaId()));
	}

	@GetMapping("/busca-avancada")
	public ResponseEntity<Page<CategoriaProdutoDTO>> buscarAvancado(@RequestParam(required = false) String descricao,
			@RequestParam(required = false) @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return ResponseEntity
				.ok(categoriaProdutoService.buscarAvancado(descricao, usuarioLogado.getEmpresaId(), page, size));
	}

	@GetMapping("/paginado")
	public ResponseEntity<Page<CategoriaProdutoDTO>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity
				.ok(categoriaProdutoService.buscarPaginado(page, size, sort, direction, usuarioLogado.getEmpresaId()));
	}
}
