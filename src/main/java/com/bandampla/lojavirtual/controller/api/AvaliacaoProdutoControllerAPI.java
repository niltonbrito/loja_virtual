/**
 * 
 */
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

import com.bandampla.lojavirtual.dto.AvaliacaoProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 12 de jul. de 2026
 */

@Tag(name = "Avaliação do Produto", description = "Operações de gestão da Avaliação doProduto da empresa")
public interface AvaliacaoProdutoControllerAPI {

	@Operation(summary = "Cadastrar Avaliação do Produto", description = "Cria uma avaliação do produto vinculado à empresa do usuário logado.")
	@PostMapping
	ResponseEntity<ResponseDefaultDTO<AvaliacaoProdutoDTO>> cadastrar(@Valid @RequestBody AvaliacaoProdutoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, MessagingException, IOException;

	@Operation(summary = "Atualizar Avaliação do Produto", description = "Atualiza os dados cadastrais de uma avaliação do produto no banco de dados.")
	@PutMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<AvaliacaoProdutoDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody AvaliacaoProdutoDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, IOException;

	@Operation(summary = "Deletar Avaliação do Produto", description = "Exclui uma avaliação do produto.")
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar Avaliação do Produto por ID", description = "Recupera os detalhes de uma avaliação do produto.")
	@GetMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<AvaliacaoProdutoDTO>> buscarPorId(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar Avaliação do Produto por Descrição", description = "Filtra e retorna uma lista de avaliação do produto baseada no nome ou descrição.")
	@GetMapping("/buscar")
	ResponseEntity<List<AvaliacaoProdutoDTO>> buscarPorDescricao(@RequestParam String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Listar todos por Empresa", description = "Recupera a lista completa de uma avaliação do produto associados à empresa logada.")
	@GetMapping
	ResponseEntity<List<AvaliacaoProdutoDTO>> buscarTodosPorEmpresa(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Busca Avançada Paginada", description = "Realiza consultas complexas aplicando paginação e filtros dinâmicos opcionais de status e descrição.")
	@GetMapping("/busca-avancada")
	ResponseEntity<Page<AvaliacaoProdutoDTO>> buscarAvancado(@RequestParam(required = false) String descricao,
			@RequestParam(required = false) Boolean ativo, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Listagem Paginada Dinâmica", description = "Retorna uma página de uma avaliação do produto permitindo ordenação e direção customizada de colunas via query string.")
	@GetMapping("/paginado")
	ResponseEntity<Page<AvaliacaoProdutoDTO>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);
}