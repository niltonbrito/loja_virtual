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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.bandampla.lojavirtual.dto.AvaliacaoProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Avaliação do Produto", description = "Operações de gestão de Avaliação de Produtos da empresa")
public interface AvaliacaoProdutoControllerAPI {

	@Operation(summary = "Cadastrar Avaliação do Produto", description = "Cria uma avaliação única para o produto vinculado à empresa do usuário logado.")
	@PostMapping
	ResponseEntity<ResponseDefaultDTO<AvaliacaoProdutoDTO>> cadastrar(@Valid @RequestBody AvaliacaoProdutoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, MessagingException, IOException;

	@Operation(summary = "Deletar Avaliação do Produto", description = "Exclui fisicamente uma avaliação do produto (Moderação do Administrador).")
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar Avaliação por ID", description = "Recupera os detalhes individuais de uma determinada avaliação.")
	@GetMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<AvaliacaoProdutoDTO>> buscarPorId(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar Avaliações por Descrição", description = "Filtra e retorna as avaliações baseadas em trechos do comentário/descrição.")
	@GetMapping("/buscar")
	ResponseEntity<List<AvaliacaoProdutoDTO>> buscarPorDescricao(@RequestParam String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Listar todas por Empresa", description = "Recupera todas as avaliações de produtos associadas à empresa logada.")
	@GetMapping
	ResponseEntity<List<AvaliacaoProdutoDTO>> buscarTodosPorEmpresa(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Busca Avançada Paginada", description = "Realiza consultas complexas aplicando filtros dinâmicos de nota e descrição por página.")
	@GetMapping("/busca-avancada")
	ResponseEntity<Page<AvaliacaoProdutoDTO>> buscarAvancado(@RequestParam(required = false) String descricao,
			@RequestParam(required = false) Integer nota, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Listagem Paginada Dinâmica", description = "Retorna uma página de avaliações permitindo ordenação customizada de colunas via query string.")
	@GetMapping("/paginado")
	ResponseEntity<Page<AvaliacaoProdutoDTO>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);
}
