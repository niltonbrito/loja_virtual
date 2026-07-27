package com.bandampla.lojavirtual.controller.api;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.bandampla.lojavirtual.dto.FormaPagamentoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Forma de Pagamento", description = "Gerenciamento dos Meios e Opções de faturamento da empresa")
public interface FormaPagamentoControllerAPI {

	@Operation(summary = "Cadastrar Forma de Pagamento", description = "Adiciona uma nova opção financeira (Pix, Crédito, Boleto) ao catálogo de recebimentos da empresa.")
	@PostMapping
	ResponseEntity<ResponseDefaultDTO<FormaPagamentoDTO>> cadastrar(
			@Valid @RequestBody FormaPagamentoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, MessagingException, IOException;

	@Operation(summary = "Atualizar Forma de Pagamento", description = "Modifica os dados de descrição ou enums de uma determinada opção financeira ativa.")
	@PutMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<FormaPagamentoDTO>> atualizar(
			@PathVariable Long id, 
			@Valid @RequestBody FormaPagamentoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom, IOException;
	
	@Operation(summary = "Deletar Forma de Pagamento", description = "Remove fisicamente a opção financeira de recebimento do catálogo da empresa.")
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletar(
			@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar por ID", description = "Recupera os detalhes completos de configuração de uma determinada forma de pagamento.")
	@GetMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<FormaPagamentoDTO>> buscarPorId(
			@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar por Descrição", description = "Filtra os meios de faturamento ativos da empresa através de trechos da descrição.")
	@GetMapping("/buscar")
	ResponseEntity<List<FormaPagamentoDTO>> buscarPorDescricao(
			@RequestParam String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Listar todas por Empresa", description = "Retorna o leque completo de opções financeiras ativas configuradas para a empresa logada.")
	@GetMapping
	ResponseEntity<List<FormaPagamentoDTO>> buscarTodosPorEmpresa(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;
}