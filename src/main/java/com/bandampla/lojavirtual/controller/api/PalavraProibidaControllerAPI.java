package com.bandampla.lojavirtual.controller.api;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.bandampla.lojavirtual.dto.PalavraProibidaDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Palavra Proibida", description = "Operações de gestão de Palavras Proibidas da empresa")
public interface PalavraProibidaControllerAPI {

	@Operation(summary = "Cadastrar Palavra Proibida", description = "Cria um único cadastro para palavra proibida.")
	@PostMapping
	ResponseEntity<ResponseDefaultDTO<PalavraProibidaDTO>> cadastrar(@Valid @RequestBody PalavraProibidaDTO dto)
			throws ExceptionCustom, MessagingException, IOException;

	@Operation(summary = "Atualizar Palavra Proibida", description = "Atualiza os dados cadastrais de um produto e substitui sua galeria de imagens de forma física no banco de dados.")
	@PutMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<PalavraProibidaDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody PalavraProibidaDTO dto) throws ExceptionCustom, IOException;

	@Operation(summary = "Deletar Palavra Proibida", description = "Exclui fisicamente uma palavra proibida (Moderação do Administrador).")
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id) throws ExceptionCustom;

	@Operation(summary = "Buscar palavras proibidas por Descrição", description = "Filtra e retorna as palavra proibida baseadas em trechos do comentário/descrição.")
	@GetMapping("/buscar")
	ResponseEntity<List<PalavraProibidaDTO>> buscarPorDescricao(@RequestParam String termo) throws ExceptionCustom;

	@Operation(summary = "Listar todas por Empresa", description = "Recupera todas as palavras proibidas de produtos associadas à empresa logada.")
	@GetMapping
	ResponseEntity<List<PalavraProibidaDTO>> buscarTodos() throws ExceptionCustom;

	@Operation(summary = "Busca Avançada Paginada", description = "Realiza consultas complexas aplicando filtros dinâmicos de palavra proibida e descrição por página.")
	@GetMapping("/busca-avancada")
	ResponseEntity<Page<PalavraProibidaDTO>> buscarAvancado(@RequestParam(required = false) String termo,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);

	@Operation(summary = "Listagem Paginada Dinâmica", description = "Retorna uma página de palavras proibidas permitindo ordenação customizada de colunas via query string.")
	@GetMapping("/paginado")
	ResponseEntity<Page<PalavraProibidaDTO>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, 
			@RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "ASC") String direction);
}
