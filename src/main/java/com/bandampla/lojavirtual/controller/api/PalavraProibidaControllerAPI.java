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

@Tag(name = "Palavra Proibida", description = "Operações de moderação e gestão do catálogo global de Palavras Proibidas do sistema")
public interface PalavraProibidaControllerAPI {

	@Operation(summary = "Cadastrar Palavra Proibida", description = "Cria um novo descrição banido global que protegerá as avaliações de todas as empresas.")
	@PostMapping
	ResponseEntity<ResponseDefaultDTO<PalavraProibidaDTO>> cadastrar(@Valid @RequestBody PalavraProibidaDTO dto)
			throws ExceptionCustom, MessagingException, IOException;

	@Operation(summary = "Atualizar descrição Banido", description = "Atualiza a string textual ou caracteres de uma palavra proibida existente.")
	@PutMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<PalavraProibidaDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody PalavraProibidaDTO dto) throws ExceptionCustom, IOException;

	@Operation(summary = "Deletar Palavra Proibida", description = "Exclui fisicamente um descrição banido da tabela global (Moderação do Administrador Geral).")
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id) throws ExceptionCustom;

	@Operation(summary = "Buscar por Descrição/Termo", description = "Retorna os termos proibidos que contenham trechos da string digitada.")
	@GetMapping("/buscar")
	ResponseEntity<ResponseDefaultDTO<List<PalavraProibidaDTO>>> buscarPorDescricao(@RequestParam @Valid String descricao)
			throws ExceptionCustom;

	@Operation(summary = "Listar todas as Palavras", description = "Recupera a lista completa e global de termos banidos do software.")
	@GetMapping
	ResponseEntity<ResponseDefaultDTO<List<PalavraProibidaDTO>>> buscarTodos() throws ExceptionCustom;

	@Operation(summary = "Busca Avançada Paginada", description = "Realiza consultas dinâmicas organizando as palavras banidas por página.")
	@GetMapping("/busca-avancada")
	ResponseEntity<ResponseDefaultDTO<Page<PalavraProibidaDTO>>> buscarAvancado(
			@RequestParam(required = false) String descricao, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size);

	@Operation(summary = "Listagem Paginada Dinâmica", description = "Gera páginas de registros permitindo ordenação customizada de colunas via query string.")
	@GetMapping("/paginado")
	ResponseEntity<ResponseDefaultDTO<Page<PalavraProibidaDTO>>> buscarPaginado(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "ASC") String direction);
}