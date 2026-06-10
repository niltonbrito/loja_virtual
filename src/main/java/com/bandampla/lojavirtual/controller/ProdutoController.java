package com.bandampla.lojavirtual.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.bandampla.lojavirtual.dto.ProdutoDTO;
import com.bandampla.lojavirtual.dto.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	// ============================
	// 🔵 CRIAR (CORRETO)
	// ============================
	@PostMapping
	public ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> salvar(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado,
			@Valid @RequestBody ProdutoDTO dto) throws ExceptionCustom {

		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDefaultDTO<>("Produto criado com sucesso", produtoService.salvar(dto)));
	}

	// ============================
	// 🟠 ATUALIZAR (CORRETO)
	// ============================
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> atualizar(
			@PathVariable Long id, 
			@Valid @RequestBody ProdutoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// Força a empresa dona do registro a ser a mesma do token do usuário
		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDefaultDTO<>("Produto criado com sucesso", produtoService.atualizar(id, dto)));
	}

	// ============================
	// 🔴 DELETAR (AJUSTADO)
	// ============================
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(
			@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// 🛠️ SEGURANÇA: O service deve receber o empresaId para garantir que o usuário não delete o produto de outra empresa
		produtoService.deletar(id, usuarioLogado.getEmpresaId());
		return ResponseEntity.noContent().build(); 
	}

	// ============================
	// 🟢 BUSCAR POR ID (AJUSTADO)
	// ============================
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDTO> buscarPorId(
			@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// 🛠️ SEGURANÇA: O service deve filtrar o ID do produto E o ID da empresa do token
		return ResponseEntity.ok(produtoService.buscarPorId(id, usuarioLogado.getEmpresaId()));
	}

	// ============================
	// 🟡 LISTAR TODOS (AJUSTADO - Substitui o vazamento global)
	// ============================
	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> listarTodos(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// 🛠️ SEGURANÇA: Nunca chame .findAll() puro. Liste apenas os produtos da empresa logada
		return ResponseEntity.ok(produtoService.buscarPorEmpresa(usuarioLogado.getEmpresaId()));
	}

	// ============================
	// 🟣 LISTAR PAGINADO (AJUSTADO)
	// ============================
	@GetMapping("/paginado")
	public ResponseEntity<Page<ProdutoDTO>> listarPaginado(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, 
			@RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// 🛠️ SEGURANÇA: Passa o empresaId para que o SQL da paginação aplique o WHERE empresa_id = ?
		return ResponseEntity.ok(produtoService.listarPaginado(page, size, sort, direction, usuarioLogado.getEmpresaId()));
	}

	// ============================
	// 🔍 BUSCA POR DESCRIÇÃO (AJUSTADO)
	// ============================
	@GetMapping("/buscar")
	public ResponseEntity<List<ProdutoDTO>> buscarPorDescricao(
			@RequestParam String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom { 
		
		// 🛠️ REMOVIDO o @RequestParam Long empresaId. O ID da empresa vem estritamente do token por segurança!
		return ResponseEntity.ok(produtoService.buscarPorDescricao(descricao, usuarioLogado.getEmpresaId()));
	}

	// ============================
	// 🏢 BUSCAR POR EMPRESA (PODE SER REMOVIDO)
	// ============================
	@GetMapping("/empresa")
	public ResponseEntity<List<ProdutoDTO>> buscarPorEmpresa(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// 🛠️ REMOVIDO o /{id} da URL. O usuário só pode ver a empresa dele mesma
		return ResponseEntity.ok(produtoService.buscarPorEmpresa(usuarioLogado.getEmpresaId()));
	}

	// ============================
	// 🔎 BUSCA AVANÇADA (AJUSTADO)
	// ============================
	@GetMapping("/busca-avancada")
	public ResponseEntity<Page<ProdutoDTO>> buscarAvancado(
			@RequestParam(required = false) String descricao,
			@RequestParam(required = false) Boolean ativo, 
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// 🛠️ REMOVIDO o @RequestParam Long empresaId do front. Usamos o do token de forma fixa
		return ResponseEntity.ok(produtoService.buscarAvancado(descricao, usuarioLogado.getEmpresaId(), ativo, page, size));
	}
}
