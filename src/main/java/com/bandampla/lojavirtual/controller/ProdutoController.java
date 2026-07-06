package com.bandampla.lojavirtual.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
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

import com.bandampla.lojavirtual.dto.ImagemProdutoDTO;
import com.bandampla.lojavirtual.dto.ProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/produto")
@Tag(name = "Produto", description = "Operações de gestão de produto da empresa")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private HttpServletRequest request;

	@Operation(summary = "Cadastrar Produto", description = "Cria um produto vinculada à empresa do usuário logado.")
	@PostMapping
	public ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> cadastrar(@Valid @RequestBody ProdutoDTO dto,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, MessagingException, IOException {

		String traceId = UUID.randomUUID().toString();

		ProdutoDTO retorno = produtoService.cadastrar(dto, usuarioLogado);

		ResponseDefaultDTO<ProdutoDTO> response = new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),
				"Produto criada com sucesso", retorno, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody ProdutoDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		dto.setEmpresaId(usuarioLogado.getEmpresaId());
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDefaultDTO<>("Produto atualizada com sucesso",
				produtoService.atualizar(id, dto, usuarioLogado)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(@PathVariable Long id,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		produtoService.deletar(id, usuarioLogado);

		// Retorna HTTP 200 OK com o JSON estruturado para o front-end ler a string
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseDefaultDTO<>("Produto deletada com sucesso", null));
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<ProdutoDTO>> buscarPorDescricao(@RequestParam String descricao,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		return ResponseEntity.ok(produtoService.buscarPorDescricao(descricao, usuarioLogado));
	}

	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> buscarTodosPorEmpresa(
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		return ResponseEntity.ok(produtoService.buscarTodosPorEmpresa(usuarioLogado));
	}

	@GetMapping("/busca-avancada")
	public ResponseEntity<Page<ProdutoDTO>> buscarAvancado(@RequestParam(required = false) String descricao,
			@RequestParam(required = false) Boolean ativo, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity.ok(produtoService.buscarAvancado(descricao, ativo, page, size, usuarioLogado));
	}

	@GetMapping("/paginado")
	public ResponseEntity<Page<ProdutoDTO>> buscarPaginado(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "ASC") String direction,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity.ok(produtoService.buscarPaginado(page, size, sort, direction, usuarioLogado));
	}

	@Operation(summary = "Atualizar Imagens do Produto", description = "Atualiza as imagens de um produto.")
	@PutMapping("/{id}/imagens")
	public ResponseEntity<ResponseDefaultDTO<Void>> atualizarImagens(@PathVariable Long id,
			@Valid @RequestBody List<ImagemProdutoDTO> imagens,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();

		produtoService.atualizarImagens(id, imagens, usuarioLogado);

		ResponseDefaultDTO<Void> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Imagens atualizadas com sucesso", null, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
