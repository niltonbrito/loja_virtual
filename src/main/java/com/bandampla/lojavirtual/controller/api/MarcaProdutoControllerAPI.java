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

import com.bandampla.lojavirtual.dto.MarcaProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Marca do Produto", description = "Operações de gestão e cadastros de Marcas de Produtos da empresa")
public interface MarcaProdutoControllerAPI {

	@Operation(summary = "Cadastrar Marca de Produto", description = "Cria uma nova marca de produto vinculada de forma exclusiva à empresa logada.")
	@PostMapping
	ResponseEntity<ResponseDefaultDTO<MarcaProdutoDTO>> cadastrar(@Valid @RequestBody MarcaProdutoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Atualizar Marca", description = "Modifica os dados cadastrais da marca informada após validação de concorrência por ID.")
	@PutMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<MarcaProdutoDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody MarcaProdutoDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom;

	@Operation(summary = "Deletar Marca", description = "Remove a marca informada do banco garantindo o isolamento da empresa logada.")
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar Marcas por Descrição", description = "Retorna uma lista filtrada de marcas contendo trechos do texto informado.")
	@GetMapping("/buscar")
	ResponseEntity<ResponseDefaultDTO<List<MarcaProdutoDTO>>> buscarPorDescricao(@RequestParam String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Listar todas por Empresa", description = "Recupera todas as marcas associadas à empresa inquilina logada.")
	@GetMapping
	ResponseEntity<ResponseDefaultDTO<List<MarcaProdutoDTO>>> buscarTodosPorEmpresa(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Busca Avançada Paginada", description = "Realiza consultas dinâmicas e complexas utilizando filtros opcionais por página.")
	@GetMapping("/busca-avancada")
	ResponseEntity<ResponseDefaultDTO<Page<MarcaProdutoDTO>>> buscarAvancado(
			@RequestParam(required = false) String descricao, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Listagem Paginada Dinâmica", description = "Gera uma página de registros de marcas com ordenação e direção customizada via query string.")
	@GetMapping("/paginado")
	ResponseEntity<ResponseDefaultDTO<Page<MarcaProdutoDTO>>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);
}