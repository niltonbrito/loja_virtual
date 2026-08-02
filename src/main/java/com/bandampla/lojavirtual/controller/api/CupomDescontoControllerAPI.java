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

import com.bandampla.lojavirtual.dto.CupomDescontoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cupom Promocional", description = "Operações de gestão e parametrização de Cupons de Desconto da empresa")
public interface CupomDescontoControllerAPI {

	@Operation(summary = "Cadastrar Cupom Promocional", description = "Registra um novo cupom promocional atômico vinculado à empresa do usuário logado.")
	@PostMapping
	ResponseEntity<ResponseDefaultDTO<CupomDescontoDTO>> cadastrar(@Valid @RequestBody CupomDescontoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Atualizar Cupom", description = "Atualiza os valores de abatimento ou vigência de um cupom promocional ativo no banco de dados.")
	@PutMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<CupomDescontoDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody CupomDescontoDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom;

	@Operation(summary = "Deletar Cupom", description = "Exclui fisicamente o cupom promocional com base nas regras de isolamento multitenant.")
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar Cupons por Código", description = "Retorna uma lista de cupons promocionais filtrados pelo código textual.")
	@GetMapping("/buscar")
	ResponseEntity<ResponseDefaultDTO<List<CupomDescontoDTO>>> buscarPorDescricao(@RequestParam String codigoDescricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Listar todos por Empresa", description = "Recupera todos os cupons ativos cadastrados para a empresa logada.")
	@GetMapping
	ResponseEntity<ResponseDefaultDTO<List<CupomDescontoDTO>>> buscarTodos(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Busca Avançada Paginada", description = "Filtra os cupons cadastrados aplicando paginação e ordenação de dados.")
	@GetMapping("/busca-avancada")
	ResponseEntity<ResponseDefaultDTO<Page<CupomDescontoDTO>>> buscarAvancado(
			@RequestParam(required = false) String codigoDescricao, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Listagem Paginada Dinâmica", description = "Retorna uma página de cupons permitindo definir direções de ordenação via query string.")
	@GetMapping("/paginado")
	ResponseEntity<ResponseDefaultDTO<Page<CupomDescontoDTO>>> buscarPaginado(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);
}