package com.bandampla.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.dto.CategoriaProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.CategoriaProdutoService;

@RestController
@RequestMapping("/categoria")
public class CategoriaProdutoController {

	@Autowired
	private CategoriaProdutoService categoriaProdutoService;

	// ============================
	// 🔵 CRIAR
	// ============================
	@PostMapping
	public ResponseEntity<ResponseDefaultDTO<CategoriaProdutoDTO>> salvar(@Valid @RequestBody CategoriaProdutoDTO dto,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDefaultDTO<>("Categoria criada com sucesso", categoriaProdutoService.salvar(dto)));
	}

	// ============================
	// 🟢 BUSCAR POR ID
	// ============================
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaProdutoDTO> buscarPorId(@PathVariable Long id) throws ExceptionCustom {
		return ResponseEntity.ok(categoriaProdutoService.buscarPorId(id));
	}

	// ============================
	// 🟡 LISTAR TODAS
	// ============================
	@GetMapping
	public ResponseEntity<List<CategoriaProdutoDTO>> listarTodos() {
		return ResponseEntity.ok(categoriaProdutoService.listarTodos());
	}

	// ============================
	// 🟣 LISTAR PAGINADO
	// ============================
	@GetMapping("/paginado")
	public ResponseEntity<Page<CategoriaProdutoDTO>> listarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction) {
		return ResponseEntity.ok(categoriaProdutoService.listarPaginado(page, size, sort, direction));
	}

	// ============================
	// 🔍 BUSCA POR DESCRIÇÃO
	// ============================
	@GetMapping("/buscar")
	public ResponseEntity<List<CategoriaProdutoDTO>> buscarPorDescricao(@RequestParam String descricao)
			throws ExceptionCustom {
		return ResponseEntity.ok(categoriaProdutoService.buscarPorDescricao(descricao));
	}

	// ============================
	// 🏢 BUSCAR POR EMPRESA
	// ============================
	@GetMapping("/empresa/{id}")
	public ResponseEntity<List<CategoriaProdutoDTO>> buscarPorEmpresa(@PathVariable Long id) throws ExceptionCustom {
		return ResponseEntity.ok(categoriaProdutoService.buscarPorEmpresa(id));
	}

	// ============================
	// 🔎 BUSCA AVANÇADA
	// ============================
	@GetMapping("/busca-avancada")
	public ResponseEntity<Page<CategoriaProdutoDTO>> buscarAvancado(@RequestParam(required = false) String descricao,
			@RequestParam(required = false) Long empresaId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(categoriaProdutoService.buscarAvancado(descricao, empresaId, page, size));
	}

	// ============================
	// 🟠 ATUALIZAR
	// ============================
	@PutMapping("/{id}")
	public ResponseEntity<CategoriaProdutoDTO> atualizar(@PathVariable Long id,
			@Valid @RequestBody CategoriaProdutoDTO dto) throws ExceptionCustom {
		return ResponseEntity.ok(categoriaProdutoService.atualizar(id, dto));
	}

	// ============================
	// 🔴 DELETAR
	// ============================
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) throws ExceptionCustom {
		categoriaProdutoService.deletar(id);
		return ResponseEntity.noContent().build(); // 204
	}
}
