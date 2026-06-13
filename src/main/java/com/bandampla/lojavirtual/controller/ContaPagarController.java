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

import com.bandampla.lojavirtual.dto.ContaPagarDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.ContaPagarService;

@RestController
@RequestMapping("/contapagar")
public class ContaPagarController {

	@Autowired
	private ContaPagarService contaPagarService;

	@PostMapping
	public ResponseEntity<ResponseDefaultDTO<ContaPagarDTO>> cadastrar(@Valid @RequestBody ContaPagarDTO dto,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDefaultDTO<>("Conta Pagar criada com sucesso", contaPagarService.cadastrar(dto)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<ContaPagarDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody ContaPagarDTO dto,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseDefaultDTO<>("Conta Pagar atualizada com sucesso", contaPagarService.atualizar(id, dto)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		contaPagarService.deletar(id, usuarioLogado.getEmpresaId());

		// Retorna HTTP 200 OK com o JSON estruturado para o front-end ler a string
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDefaultDTO<>("Conta Pagar deletada com sucesso", null));
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<ContaPagarDTO>> buscarPorDescricao(@RequestParam String descricao,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		return ResponseEntity.ok(contaPagarService.buscarPorDescricao(descricao, usuarioLogado.getEmpresaId()));
	}

	@GetMapping
	public ResponseEntity<List<ContaPagarDTO>> buscarTodosPorEmpresa(
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		return ResponseEntity.ok(contaPagarService.buscarTodosPorEmpresa(usuarioLogado.getEmpresaId()));
	}

	@GetMapping("/busca-avancada")
	public ResponseEntity<Page<ContaPagarDTO>> buscarAvancado(@RequestParam(required = false) String descricao,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity.ok(contaPagarService.buscarAvancado(descricao, page, size, usuarioLogado.getEmpresaId()));
	}
	
	@GetMapping("/paginado")
	public ResponseEntity<Page<ContaPagarDTO>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@org.springframework.security.core.annotation.AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity
				.ok(contaPagarService.buscarPaginado(page, size, sort, direction, usuarioLogado.getEmpresaId()));
	}
}