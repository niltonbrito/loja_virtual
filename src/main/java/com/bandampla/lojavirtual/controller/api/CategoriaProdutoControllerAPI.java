package com.bandampla.lojavirtual.controller.api;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.bandampla.lojavirtual.dto.CategoriaProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Categoria de Produto", description = "Operações de gestão e árvores de Categorias de Produtos da empresa")
public interface CategoriaProdutoControllerAPI {

	@PostMapping
	ResponseEntity<ResponseDefaultDTO<CategoriaProdutoDTO>> cadastrar(@Valid @RequestBody CategoriaProdutoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, MessagingException, IOException;

	@PutMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<CategoriaProdutoDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody CategoriaProdutoDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, IOException;

	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@GetMapping("/buscar")
	ResponseEntity<ResponseDefaultDTO<List<CategoriaProdutoDTO>>> buscarPorDescricao(@RequestParam String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@GetMapping
	ResponseEntity<ResponseDefaultDTO<List<CategoriaProdutoDTO>>> buscarTodosPorEmpresa(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@GetMapping("/busca-avancada")
	ResponseEntity<ResponseDefaultDTO<Page<CategoriaProdutoDTO>>> buscarAvancado(
			@RequestParam(required = false) String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size);

	@GetMapping("/paginado")
	ResponseEntity<ResponseDefaultDTO<Page<CategoriaProdutoDTO>>> buscarPaginado(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);
}