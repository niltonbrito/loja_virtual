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

import com.bandampla.lojavirtual.dto.ImagemProdutoDTO;
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

@Tag(name = "Produto", description = "Operações de gestão de produto da empresa")
public interface ProdutoControllerAPI {

	@Operation(summary = "Cadastrar Produto", description = "Cria um produto vinculado à empresa do usuário logado.")
	@PostMapping
	ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> cadastrar(@Valid @RequestBody ProdutoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, MessagingException, IOException;

	@Operation(summary = "Atualizar Produto", description = "Atualiza os dados cadastrais de um produto e substitui sua galeria de imagens de forma física no banco de dados.")
	@PutMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom, IOException;

	@Operation(summary = "Atualizar Imagens do Produto", description = "Atualiza exclusivamente o lote de imagens de um produto, limpando o acervo antigo via orphanRemoval.")
	@PutMapping("/{id}/imagens")
	ResponseEntity<ResponseDefaultDTO<Void>> atualizarImagens(@PathVariable Long id,
			@Valid @RequestBody List<ImagemProdutoDTO> imagens,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Deletar Produto", description = "Exclui fisicamente o produto e todas as suas imagens vinculadas em cascata na tabela do PostgreSQL.")
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Deletar uma imagem específica", description = "Remove cirurgicamente uma única imagem do produto utilizando o ID da imagem filha.")
	@DeleteMapping("/imagem/{imagemId}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletarImagemEspecifica(@PathVariable Long imagemId,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar Produto por ID", description = "Recupera os detalhes de um produto e reconverte as fotos binárias BYTEA em strings Base64 legíveis no JSON.")
	@GetMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> buscarPorId(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar Produtos por Descrição", description = "Filtra e retorna uma lista de produtos baseada no nome ou descrição.")
	@GetMapping("/buscar")
	ResponseEntity<List<ProdutoDTO>> buscarPorDescricao(@RequestParam String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Listar todos por Empresa", description = "Recupera a lista completa de produtos associados à empresa logada.")
	@GetMapping
	ResponseEntity<List<ProdutoDTO>> buscarTodosPorEmpresa(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Busca Avançada Paginada", description = "Realiza consultas complexas aplicando paginação e filtros dinâmicos opcionais de status e descrição.")
	@GetMapping("/busca-avancada")
	ResponseEntity<Page<ProdutoDTO>> buscarAvancado(@RequestParam(required = false) String descricao,
			@RequestParam(required = false) Boolean ativo, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Listagem Paginada Dinâmica", description = "Retorna uma página de produtos permitindo ordenação e direção customizada de colunas via query string.")
	@GetMapping("/paginado")
	ResponseEntity<Page<ProdutoDTO>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);
}