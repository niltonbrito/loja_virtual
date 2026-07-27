package com.bandampla.lojavirtual.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bandampla.lojavirtual.dto.AcessoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.exception.ExceptionCustom;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Acesso e Segurança", description = "Operações de gestão dos papéis e permissões de acesso do sistema")
public interface AcessoControllerAPI {

	@Operation(summary = "Cadastrar Acesso", description = "Cria um novo nível de permissão ou papel de segurança no banco de dados.")
	@PostMapping
	ResponseEntity<ResponseDefaultDTO<AcessoDTO>> cadastrar(@RequestBody AcessoDTO dto) throws ExceptionCustom;

	@Operation(summary = "Deletar Acesso por ID", description = "Remove cirurgicamente uma permissão de acesso a partir do seu ID numérico único.")
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletarPorId(@PathVariable Long id);

	@Operation(summary = "Buscar Acesso por ID", description = "Recupera os detalhes operacionais de uma determinada regra de acesso pelo ID.")
	@GetMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<AcessoDTO>> buscarPorId(@PathVariable Long id) throws ExceptionCustom;

	@Operation(summary = "Buscar por Role", description = "Filtra e retorna todas as permissões associadas a um determinado papel do Enum de segurança.")
	@GetMapping("/role/{role}")
	ResponseEntity<ResponseDefaultDTO<List<AcessoDTO>>> buscarPorRole(@PathVariable RoleUser role);
}