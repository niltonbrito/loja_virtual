package com.bandampla.lojavirtual.controller.api;

import java.util.List;

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

import com.bandampla.lojavirtual.dto.NotaFiscalCompraDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Nota Fiscal de Compra", description = "Operações de gerenciamento das Notas Fiscais de Compra (Entrada de Insumos/Estoque)")
public interface NotaFiscalCompraControllerAPI {

	@Operation(summary = "Cadastrar Nota Fiscal de Compra", description = "Registra uma nova nota fiscal de compra emitida pelo seu fornecedor para dar entrada de estoque.")
	@PostMapping
	ResponseEntity<ResponseDefaultDTO<NotaFiscalCompraDTO>> cadastrar(@Valid @RequestBody NotaFiscalCompraDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Atualizar Nota de Compra", description = "Atualiza os registros cadastrais da nota fiscal de compra no banco de dados.")
	@PutMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<NotaFiscalCompraDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody NotaFiscalCompraDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom;

	@Operation(summary = "Deletar Nota de Compra", description = "Exclui fisicamente do banco o registro fiscal com base nas regras de isolamento da empresa.")
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar Notas por Número", description = "Retorna uma lista de notas fiscais de compra filtradas pelo número e empresa.")
	@GetMapping("/buscar")
	ResponseEntity<ResponseDefaultDTO<List<NotaFiscalCompraDTO>>> buscarPorDescricao(@RequestParam String numeroNota,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Listar todas por Empresa", description = "Recupera todas as notas fiscais de entrada vinculadas à empresa logada.")
	@GetMapping
	ResponseEntity<ResponseDefaultDTO<List<NotaFiscalCompraDTO>>> buscarTodos(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Busca Avançada Paginada", description = "Filtra as notas de compra aplicando estruturas de dados dinâmicas e paginação.")
	@GetMapping("/busca-avancada")
	ResponseEntity<ResponseDefaultDTO<Page<NotaFiscalCompraDTO>>> buscarAvancado(
			@RequestParam(required = false) String numeroNota, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Listagem Paginada Dinâmica", description = "Retorna uma página de notas permitindo definir colunas de ordenação e direção via query string.")
	@GetMapping("/paginado")
	ResponseEntity<ResponseDefaultDTO<Page<NotaFiscalCompraDTO>>> buscarPaginado(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Listar Notas por Produto", description = "Retorna todas as notas fiscais de compra que possuem itens de entrada vinculados ao ID do produto informado.")
	@GetMapping("/produto/{produtoId}")
	ResponseEntity<ResponseDefaultDTO<List<NotaFiscalCompraDTO>>> buscarPorProduto(@PathVariable Long produtoId,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;
}