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

import com.bandampla.lojavirtual.dto.ContaPagarDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Contas a Pagar", description = "Operações do financeiro corporativo B2B para gestão de contas a pagar da empresa")
public interface ContaPagarControllerAPI {

	@Operation(summary = "Cadastrar Conta a Pagar", description = "Registra um novo título ou obrigação financeira vinculada à empresa do usuário logado.")
	@PostMapping
	ResponseEntity<ResponseDefaultDTO<ContaPagarDTO>> cadastrar(@Valid @RequestBody ContaPagarDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Atualizar Título", description = "Altera os valores ou vencimentos de uma conta a pagar ativa após validação por ID.")
	@PutMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<ContaPagarDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody ContaPagarDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom;

	@Operation(summary = "Deletar Título", description = "Exclui a obrigação financeira cadastrada respeitando as alçadas de isolamento da empresa.")
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar por Descrição", description = "Filtra e retorna as contas a pagar que possuam trechos da descrição ou nome do fornecedor.")
	@GetMapping("/buscar")
	ResponseEntity<ResponseDefaultDTO<List<ContaPagarDTO>>> buscarPorDescricao(@RequestParam String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Listar todas as Contas", description = "Recupera o livro completo de contas a faturar da empresa logada.")
	@GetMapping
	ResponseEntity<ResponseDefaultDTO<List<ContaPagarDTO>>> buscarTodos(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Busca Avançada Paginada", description = "Aplica filtros dinâmicos de data ou descrição organizados em paginação.")
	@GetMapping("/busca-avancada")
	ResponseEntity<ResponseDefaultDTO<Page<ContaPagarDTO>>> buscarAvancado(
			@RequestParam(required = false) String descricao, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Listagem Paginada Dinâmica", description = "Retorna uma página de contas permitindo ordenação parametrizada via query string.")
	@GetMapping("/paginado")
	ResponseEntity<ResponseDefaultDTO<Page<ContaPagarDTO>>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);
}