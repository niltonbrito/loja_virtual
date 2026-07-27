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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.bandampla.lojavirtual.dto.VendaLojaVirtualDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Venda Loja Virtual", description = "Operações do Checkout e Vendas da empresa")
public interface VendaLojaVirtualControllerAPI {

	@Operation(summary = "Cadastra Venda na Loja Virtual", description = "Inicia o checkout e persiste um pedido de venda pendente vinculado à empresa logada.")
	@PostMapping
	ResponseEntity<ResponseDefaultDTO<VendaLojaVirtualDTO>> cadastrar(@Valid @RequestBody VendaLojaVirtualDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, MessagingException, IOException;

	@Operation(summary = "Deletar/Cancelar Venda", description = "Remove ou estorna fisicamente a venda e limpa seus registros relatórios.")
	@DeleteMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar Venda por ID", description = "Recupera todos os detalhes operacionais e financeiros de uma determinada venda.")
	@GetMapping("/{id}")
	ResponseEntity<ResponseDefaultDTO<VendaLojaVirtualDTO>> buscarPorId(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Buscar Vendas por Número de Pedido", description = "Filtra as vendas cadastradas utilizando a query string do número legível do pedido.")
	@GetMapping("/buscar")
	ResponseEntity<List<VendaLojaVirtualDTO>> buscarPorNumeroPedido(@RequestParam String numeroPedido,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Listar todas por Empresa", description = "Retorna a relação histórica completa de faturamento da empresa logada.")
	@GetMapping
	ResponseEntity<List<VendaLojaVirtualDTO>> buscarTodosPorEmpresa(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Busca Avançada Paginada", description = "Consulta as vendas com filtros opcionais de paginação de dados dinâmicos.")
	@GetMapping("/busca-avancada")
	ResponseEntity<Page<VendaLojaVirtualDTO>> buscarAvancado(@RequestParam(required = false) String numeroPedido,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);

	@Operation(summary = "Listagem Paginada Dinâmica", description = "Gera páginas de registros permitindo definir direção e colunas para ordenação.")
	@GetMapping("/paginado")
	ResponseEntity<Page<VendaLojaVirtualDTO>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado);
}
