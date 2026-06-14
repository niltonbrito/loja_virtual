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

import com.bandampla.lojavirtual.dto.ContaPagarDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.ContaPagarService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/contapagar")
@Tag(name = "Contas a Pagar", description = "Operações de gestão de contas a pagar da empresa")
public class ContaPagarController {

	@Autowired
	private ContaPagarService contaPagarService;

	// -----------------------------
	// CADASTRAR
	// -----------------------------
	@PostMapping
	@Operation(summary = "Cadastrar Conta a Pagar", description = "Cria uma nova conta a pagar vinculada à empresa do usuário logado.")
	public ResponseEntity<ResponseDefaultDTO<ContaPagarDTO>> cadastrar(@Valid @RequestBody ContaPagarDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		dto.setEmpresaId(usuarioLogado.getEmpresaId());

		ContaPagarDTO criado = contaPagarService.cadastrar(dto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDefaultDTO<>("Conta Pagar criada com sucesso", criado));
	}

	// -----------------------------
	// ATUALIZAR
	// -----------------------------

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Conta a Pagar", description = "Atualiza uma conta a pagar existente, validando empresa e duplicidade.")
    public ResponseEntity<ResponseDefaultDTO<ContaPagarDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody ContaPagarDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		dto.setEmpresaId(usuarioLogado.getEmpresaId());

		ContaPagarDTO atualizado = contaPagarService.atualizar(id, dto);

		return ResponseEntity.ok(new ResponseDefaultDTO<>("Conta Pagar atualizada com sucesso", atualizado));
	}

	// -----------------------------
	// DELETAR
	// -----------------------------
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Conta a Pagar", description = "Remove uma conta a pagar da empresa do usuário logado.")
    public ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		contaPagarService.deletar(id, usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(new ResponseDefaultDTO<>("Conta Pagar deletada com sucesso", null));
	}

	// -----------------------------
	// BUSCAR POR DESCRIÇÃO
	// -----------------------------
    @GetMapping("/buscar")
    @Operation(summary = "Buscar por descrição", description = "Retorna contas a pagar filtradas pela descrição e empresa.")
    public ResponseEntity<List<ContaPagarDTO>> buscarPorDescricao(@RequestParam String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {

		List<ContaPagarDTO> lista = contaPagarService.buscarPorDescricao(descricao, usuarioLogado.getEmpresaId());
		return ResponseEntity.ok(lista);
	}

	// -----------------------------
	// BUSCAR TODOS
	// -----------------------------
    @GetMapping
    @Operation(summary = "Listar todas as contas a pagar", description = "Lista todas as contas a pagar da empresa do usuário.")
    public ResponseEntity<List<ContaPagarDTO>> buscarTodos(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {

		List<ContaPagarDTO> lista = contaPagarService.buscarTodosPorEmpresa(usuarioLogado.getEmpresaId());
		return ResponseEntity.ok(lista);
	}

	// -----------------------------
	// BUSCA AVANÇADA
	// -----------------------------
    @GetMapping("/busca-avancada")
    @Operation(summary = "Busca avançada", description = "Busca contas a pagar com filtros e paginação.")
    public ResponseEntity<Page<ContaPagarDTO>> buscarAvancado(@RequestParam(required = false) String descricao,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {

		Page<ContaPagarDTO> resultado = contaPagarService.buscarAvancado(descricao, page, size,
				usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(resultado);
	}

	// -----------------------------
	// PAGINADO
	// -----------------------------
    @GetMapping("/paginado")
    @Operation(summary = "Listar paginado", description = "Lista contas a pagar com paginação e ordenação.")
    public ResponseEntity<Page<ContaPagarDTO>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {

		Page<ContaPagarDTO> resultado = contaPagarService.buscarPaginado(page, size, sort, direction,
				usuarioLogado.getEmpresaId());

		return ResponseEntity.ok(resultado);
	}
}
