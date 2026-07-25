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

import com.bandampla.lojavirtual.dto.ProdutoDTO;
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

@Tag(name = "Venda e Compra Loja Virtual", description = "Operações de gestão de venda e compra loja virtual da empresa")
public interface VendaCompraLojaVirtualControllerAPI {

	@Operation(summary = "Cadastrar Produto", description = "Cria uma venda e compra na loja virtual vinculado à empresa do usuário logado.")
	@PostMapping
	ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> cadastrar(@Valid @RequestBody ProdutoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, MessagingException, IOException;

	@Operation(summary = "Atualizar venda e compra na loja virtual", description = "Atualiza os dados cadastrais de uma venda e compra na loja virtual de forma física no banco de dados.")
	@PutMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom, IOException;

	@Operation(summary = "Deletar venda e compra na loja virtual", description = "Exclui fisicamente a venda e compra na loja virtual vinculadas em cascata na tabela do PostgreSQL.")
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar venda e compra na loja virtual por ID", description = "Recupera os detalhes de uma venda e compra na loja virtual.")
	@GetMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> buscarPorId(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar venda e compra na loja virtual por Descrição", description = "Filtra e retorna uma lista de venda e compra na loja virtual baseada no nome ou descrição.")
	@GetMapping("/buscar")
	ResponseEntity<List<ProdutoDTO>> buscarPorDescricao(@RequestParam String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Listar todos por Empresa", description = "Recupera a lista completa de venda e compra na loja virtual associados à empresa logada.")
	@GetMapping
	ResponseEntity<List<ProdutoDTO>> buscarTodosPorEmpresa(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Busca Avançada Paginada", description = "Realiza consultas complexas aplicando paginação e filtros dinâmicos opcionais de status e descrição.")
	@GetMapping("/busca-avancada")
	ResponseEntity<Page<ProdutoDTO>> buscarAvancado(@RequestParam(required = false) String descricao,
			@RequestParam(required = false) Boolean ativo, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Listagem Paginada Dinâmica", description = "Retorna uma página de venda e compra na loja virtual permitindo ordenação e direção customizada de colunas via query string.")
	@GetMapping("/paginado")
	ResponseEntity<Page<ProdutoDTO>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);
}