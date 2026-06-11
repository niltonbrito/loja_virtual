package com.bandampla.lojavirtual.controller;

import java.util.List;

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

import com.bandampla.lojavirtual.dto.MarcaProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.MarcaProdutoService;

@RestController
@RequestMapping("/marca")
public class MarcaProdutoController {

	@Autowired
	private MarcaProdutoService marcaProdutoService;

	@PostMapping
	public ResponseEntity<ResponseDefaultDTO<MarcaProdutoDTO>> cadastrar(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado,
			@Valid @RequestBody MarcaProdutoDTO dto) throws ExceptionCustom {

		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDefaultDTO<>("Marca do Produto criado com sucesso", marcaProdutoService.cadastrar(dto)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<MarcaProdutoDTO>> atualizar(
			@PathVariable Long id, 
			@Valid @RequestBody MarcaProdutoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// Força a empresa dona do registro a ser a mesma do token do usuário
		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDefaultDTO<>("Marca atualizada com sucesso", marcaProdutoService.atualizar(id, dto)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(
			@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// SEGURANÇA: O service deve receber o empresaId para garantir que o usuário não delete o produto de outra empresa
		marcaProdutoService.deletar(id, usuarioLogado.getEmpresaId());
		return ResponseEntity.noContent().build(); 
	}

	@GetMapping("/{id}")
	public ResponseEntity<MarcaProdutoDTO> buscarPorId(
			@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// SEGURANÇA: O service deve filtrar o ID do produto E o ID da empresa do token
		return ResponseEntity.ok(marcaProdutoService.buscarPorId(id, usuarioLogado.getEmpresaId()));
	}
	
	@GetMapping
	public ResponseEntity<List<MarcaProdutoDTO>> listarTodos(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// SEGURANÇA: Nunca chame .findAll() puro. Liste apenas os produtos da empresa logada
		return ResponseEntity.ok(marcaProdutoService.buscarPorEmpresa(usuarioLogado.getEmpresaId()));
	}

	@GetMapping("/paginado")
	public ResponseEntity<Page<MarcaProdutoDTO>> listarPaginado(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, 
			@RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// SEGURANÇA: Passa o empresaId para que o SQL da paginação aplique o WHERE empresa_id = ?
		return ResponseEntity.ok(marcaProdutoService.listarPaginado(page, size, sort, direction, usuarioLogado.getEmpresaId()));
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<MarcaProdutoDTO>> buscarPorDescricao(
			@RequestParam String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom { 
		
		// Removido o @RequestParam Long empresaId. O ID da empresa vem estritamente do token por segurança!
		return ResponseEntity.ok(marcaProdutoService.buscarPorDescricao(descricao, usuarioLogado.getEmpresaId()));
	}

	@GetMapping("/empresa")
	public ResponseEntity<List<MarcaProdutoDTO>> buscarPorEmpresa(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// Removido o /{id} da URL. O usuário só pode ver a empresa dele mesma
		return ResponseEntity.ok(marcaProdutoService.buscarPorEmpresa(usuarioLogado.getEmpresaId()));
	}

	@GetMapping("/busca-avancada")
	public ResponseEntity<Page<MarcaProdutoDTO>> buscarAvancado(
			@RequestParam(required = false) String descricao,
			@RequestParam(required = false) Boolean ativo, 
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		
		// Removido o @RequestParam Long empresaId do front. Usamos o do token de forma fixa
		return ResponseEntity.ok(marcaProdutoService.buscarAvancado(descricao, usuarioLogado.getEmpresaId(), ativo, page, size));
	}
}