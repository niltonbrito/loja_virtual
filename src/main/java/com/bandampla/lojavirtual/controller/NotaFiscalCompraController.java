package com.bandampla.lojavirtual.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.dto.NotaFiscalCompraDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.NotaFiscalCompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/notafiscalcompra")
@Tag(name = "Nota Fiscal de Compra", description = "Operações de gestão de Nota Fiscal de Compra da empresa")
public class NotaFiscalCompraController {

	@Autowired
	private NotaFiscalCompraService notaFiscalCompraService;

	@PostMapping
	@Operation(summary = "Cadastrar Nota Fiscal de Compra", description = "Cria uma nova Nota Fiscal de Compra vinculada à empresa do usuário logado.")
	public ResponseEntity<ResponseDefaultDTO<NotaFiscalCompraDTO>> cadastrar(
			@Valid @RequestBody NotaFiscalCompraDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado, HttpServletRequest request)
			throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();
		
		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),
				"Nota Fiscal de Compra criada com sucesso", notaFiscalCompraService.cadastrar(dto, usuarioLogado), request.getRequestURI(), traceId));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar Nota Fiscal de Compra", description = "Atualiza uma Nota Fiscal de Compra existente, validando empresa e duplicidade.")
	public ResponseEntity<ResponseDefaultDTO<NotaFiscalCompraDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody NotaFiscalCompraDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),"Nota Fiscal de Compra atualizada com sucesso",
				notaFiscalCompraService.atualizar(id, dto, usuarioLogado)));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar Nota Fiscal de Compra", description = "Remove uma Nota Fiscal de Compra da empresa do usuário logado.")
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		notaFiscalCompraService.deletar(id, usuarioLogado);
		return ResponseEntity.ok(new ResponseDefaultDTO<>("Nota Fiscal de Compra deletada com sucesso", null));
	}

	@GetMapping("/buscar")
	@Operation(summary = "Buscar por descrição", description = "Retorna Nota Fiscal de Compra filtradas pela descrição e empresa.")
	public ResponseEntity<List<NotaFiscalCompraDTO>> buscarPorDescricao(@RequestParam String numeroNota,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity.ok(notaFiscalCompraService.buscarPorDescricao(numeroNota, usuarioLogado));
	}

	@GetMapping
	@Operation(summary = "Listar todas as Nota Fiscal de Compra", description = "Lista todas as Nota Fiscal de Compra da empresa do usuário.")
	public ResponseEntity<List<NotaFiscalCompraDTO>> buscarTodos(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity.ok(notaFiscalCompraService.buscarTodosPorEmpresa(usuarioLogado));
	}

	@GetMapping("/busca-avancada")
	@Operation(summary = "Busca avançada", description = "Busca Nota Fiscal de Compra com filtros e paginação.")
	public ResponseEntity<Page<NotaFiscalCompraDTO>> buscarAvancado(@RequestParam(required = false) String numeroNota,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity.ok(notaFiscalCompraService.buscarAvancado(numeroNota, page, size, usuarioLogado));
	}

	@GetMapping("/paginado")
	@Operation(summary = "Listar paginado", description = "Lista Nota Fiscal de Compra com paginação e ordenação.")
	public ResponseEntity<Page<NotaFiscalCompraDTO>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity.ok(notaFiscalCompraService.buscarPaginado(page, size, sort, direction, usuarioLogado));
	}

	@GetMapping("/produto/{produtoId}")
	@Operation(summary = "Listar Notas Fiscais por Produto", description = "Retorna todas as notas fiscais de compra que possuem itens com o produto informado.")
	public ResponseEntity<ResponseDefaultDTO<List<NotaFiscalCompraDTO>>> buscarPorProduto(@PathVariable Long produtoId,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado, HttpServletRequest request)
			throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();

		List<NotaFiscalCompraDTO> notas = notaFiscalCompraService.buscarPorProduto(produtoId, usuarioLogado);

		ResponseDefaultDTO<List<NotaFiscalCompraDTO>> response = new ResponseDefaultDTO<>("200",
				"Notas fiscais encontradas para o produto " + produtoId, notas, request.getRequestURI(), traceId);

		/*
		 * MDC.put("traceId", traceId);
		 * log.info("Consulta de NF por produto {} realizada pela empresa {}",
		 * produtoId, usuarioLogado.getEmpresaId()); MDC.clear();
		 */

		return ResponseEntity.ok(response);
	}

}