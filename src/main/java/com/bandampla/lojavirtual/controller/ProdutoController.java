package com.bandampla.lojavirtual.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.controller.api.ProdutoControllerAPI;
import com.bandampla.lojavirtual.dto.ImagemProdutoDTO;
import com.bandampla.lojavirtual.dto.ProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController implements ProdutoControllerAPI {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private HttpServletRequest request;

	@Override
	public ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> cadastrar(ProdutoDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom, MessagingException, IOException {

		String traceId = UUID.randomUUID().toString();
		ProdutoDTO retorno = produtoService.cadastrar(dto, usuarioLogado);

		ResponseDefaultDTO<ProdutoDTO> response = new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),
				"Produto criado com sucesso", retorno, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> atualizar(Long id, ProdutoDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom, IOException {

		String traceId = UUID.randomUUID().toString();
		dto.setEmpresaId(usuarioLogado.getEmpresaId());

		ProdutoDTO retorno = produtoService.atualizar(id, dto, usuarioLogado);

		ResponseDefaultDTO<ProdutoDTO> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Produto atualizado com sucesso", retorno, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Void>> atualizarImagens(Long id, List<ImagemProdutoDTO> imagens,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();
		produtoService.atualizarImagens(id, imagens, usuarioLogado);

		ResponseDefaultDTO<Void> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Imagens atualizadas com sucesso", null, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(Long id, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();
		produtoService.deletar(id, usuarioLogado);

		ResponseDefaultDTO<Void> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Produto deletado com sucesso", null, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Void>> deletarImagemEspecifica(Long imagemId,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();
		produtoService.deletarImagemEspecifica(imagemId, usuarioLogado);

		ResponseDefaultDTO<Void> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Imagem removida com sucesso", null, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<ProdutoDTO>> buscarPorId(Long id, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();
		ProdutoDTO retorno = produtoService.buscarPorId(id, usuarioLogado);

		ResponseDefaultDTO<ProdutoDTO> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Produto recuperado com sucesso", retorno, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<List<ProdutoDTO>> buscarPorDescricao(String descricao, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		return ResponseEntity.ok(produtoService.buscarPorDescricao(descricao, usuarioLogado));
	}

	@Override
	public ResponseEntity<List<ProdutoDTO>> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		return ResponseEntity.ok(produtoService.buscarTodosPorEmpresa(usuarioLogado));
	}

	@Override
	public ResponseEntity<Page<ProdutoDTO>> buscarAvancado(String descricao, Boolean ativo, int page, int size,
			UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity.ok(produtoService.buscarAvancado(descricao, ativo, page, size, usuarioLogado));
	}

	@Override
	public ResponseEntity<Page<ProdutoDTO>> buscarPaginado(int page, int size, String sort, String direction,
			UsuarioLogadoPrincipal usuarioLogado) {
		return ResponseEntity.ok(produtoService.buscarPaginado(page, size, sort, direction, usuarioLogado));
	}
}
