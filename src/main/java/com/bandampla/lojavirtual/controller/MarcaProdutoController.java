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

import com.bandampla.lojavirtual.dto.MarcaProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.MarcaProdutoService;

@RestController
@RequestMapping("/marca")
public class MarcaProdutoController {

	@Autowired
	private MarcaProdutoService marcaProdutoService;

	@PostMapping
	public ResponseEntity<ResponseDefaultDTO<MarcaProdutoDTO>> cadastrar(@Valid @RequestBody MarcaProdutoDTO dto,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDefaultDTO<>("Marca Produto criada com sucesso", marcaProdutoService.cadastrar(dto)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<MarcaProdutoDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody MarcaProdutoDTO dto,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDefaultDTO<>(
				"Marca Produto atualizada com sucesso", marcaProdutoService.atualizar(id, dto)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		marcaProdutoService.deletar(id, usuarioLogado.getEmpresaId());

		// Retorna HTTP 200 OK com o JSON estruturado para o front-end ler a string
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDefaultDTO<>("Marca Produto deletada com sucesso", null));
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<MarcaProdutoDTO>> buscarPorDescricao(@RequestParam String descricao,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		return ResponseEntity.ok(marcaProdutoService.buscarPorDescricao(descricao, usuarioLogado.getEmpresaId()));
	}

	@GetMapping
	public ResponseEntity<List<MarcaProdutoDTO>> buscarTodosPorEmpresa(
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		return ResponseEntity.ok(marcaProdutoService.buscarTodosPorEmpresa(usuarioLogado.getEmpresaId()));
	}

	@GetMapping("/busca-avancada")
	public ResponseEntity<Page<MarcaProdutoDTO>> buscarAvancado(@RequestParam(required = false) String descricao,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity
				.ok(marcaProdutoService.buscarAvancado(descricao, page, size, usuarioLogado.getEmpresaId()));
	}

	@GetMapping("/paginado")
	public ResponseEntity<Page<MarcaProdutoDTO>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity
				.ok(marcaProdutoService.buscarPaginado(page, size, sort, direction, usuarioLogado.getEmpresaId()));
	}

}