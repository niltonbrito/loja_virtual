/**
 * 
 */
package com.bandampla.lojavirtual.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.dto.CategoriaProdutoDTO;
import com.bandampla.lojavirtual.dto.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.service.CategoriaProdutoService;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

//@CrossOrigin(value = "http://bandampla.com") //Somente requisições a partir desta origem http://bandampla.com podem utilizar este controler ou end-point
@RestController
public class CategoriaProdutoController {

	@Autowired
	private CategoriaProdutoService categoriaProdutoService;

	@ResponseBody // Pode dar um retorno da API
	@PostMapping(value = "/categoria/salvar") // Mapeandoa url para receber um JSON
	public ResponseEntity<ResponseDefaultDTO<CategoriaProdutoDTO>> salvar(@Valid @RequestBody CategoriaProdutoDTO dto)
			throws ExceptionCustom {
		return ResponseEntity.ok(new ResponseDefaultDTO<>("Categoria salva com sucesso", categoriaProdutoService.salvar(dto)));
	}

	@PutMapping("/categoria/{id}")
	public ResponseEntity<CategoriaProdutoDTO> atualizar(@PathVariable Long id,
			@Valid @RequestBody CategoriaProdutoDTO dto) throws ExceptionCustom {
		return ResponseEntity.ok(categoriaProdutoService.atualizar(id, dto));
	}

	@DeleteMapping("/categoria/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) throws ExceptionCustom {
		categoriaProdutoService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@ResponseBody
	@GetMapping("/categoria/consulta")
	public ResponseEntity<List<CategoriaProdutoDTO>> buscar(@RequestParam String descricao) throws ExceptionCustom {
		return ResponseEntity.ok(categoriaProdutoService.buscarPorDescricao(descricao));
	}
	
	@ResponseBody
	@GetMapping("/categoria/{id}")
	public ResponseEntity<CategoriaProdutoDTO> buscarPorId(@Valid @PathVariable Long id
			) throws ExceptionCustom {
		return ResponseEntity.ok(categoriaProdutoService.buscarPorId(id));
	}
	
	@ResponseBody
	@GetMapping("/categoria/consulta/empresa/{id}")
	public ResponseEntity<List<CategoriaProdutoDTO>> buscarPorEmpresa(@Valid @PathVariable Long id
			) throws ExceptionCustom {
		return ResponseEntity.ok(categoriaProdutoService.buscarPorEmpresa(id));
	}
	
	@GetMapping("/categoria/listar")
	public ResponseEntity<List<CategoriaProdutoDTO>> listarTodos() {
		return ResponseEntity.ok(categoriaProdutoService.listarTodos());
	}

	@GetMapping("/categoria/paginado")
	public ResponseEntity<Page<CategoriaProdutoDTO>> listarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction) {
		return ResponseEntity.ok(categoriaProdutoService.listarPaginado(page, size, sort, direction));
	}

	@GetMapping("/categoria/busca-avancada")
	public ResponseEntity<Page<CategoriaProdutoDTO>> buscarAvancado(@RequestParam(required = false) String descricao,
			@RequestParam(required = false) Long empresaId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(categoriaProdutoService.buscarAvancado(descricao, empresaId, page, size));
	}
}
