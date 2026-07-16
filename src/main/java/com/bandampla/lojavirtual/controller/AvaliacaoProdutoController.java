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

import com.bandampla.lojavirtual.controller.api.AvaliacaoProdutoControllerAPI;
import com.bandampla.lojavirtual.dto.AvaliacaoProdutoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.AvaliacaoProdutoService;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoProdutoController implements AvaliacaoProdutoControllerAPI {

	@Autowired
	private AvaliacaoProdutoService avaliacaoProdutoService; // 🔥 Injeção corrigida

	@Autowired
	private HttpServletRequest request;

	@Override
	public ResponseEntity<ResponseDefaultDTO<AvaliacaoProdutoDTO>> cadastrar(AvaliacaoProdutoDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom, MessagingException, IOException {

		String traceId = UUID.randomUUID().toString();

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),
				"Avaliação do produto registrada com sucesso", avaliacaoProdutoService.cadastrar(dto, usuarioLogado), request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<AvaliacaoProdutoDTO>> atualizar(Long id, AvaliacaoProdutoDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom, IOException {

		String traceId = UUID.randomUUID().toString();
		dto.setEmpresaId(usuarioLogado.getEmpresaId());

		// Exemplo de rota delegada corretamente para o serviço de avaliação
		// AvaliacaoProdutoDTO retorno = avaliacaoProdutoService.atualizar(id, dto,
		// usuarioLogado);

		ResponseDefaultDTO<AvaliacaoProdutoDTO> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Avaliação do produto atualizada com sucesso", null, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(Long id, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();
		// avaliacaoProdutoService.deletar(id, usuarioLogado);

		ResponseDefaultDTO<Void> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Avaliação do produto deletada com sucesso", null, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<AvaliacaoProdutoDTO>> buscarPorId(Long id,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();
		// AvaliacaoProdutoDTO retorno = avaliacaoProdutoService.buscarPorId(id,
		// usuarioLogado);

		ResponseDefaultDTO<AvaliacaoProdutoDTO> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Avaliação do produto recuperada com sucesso", null, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<List<AvaliacaoProdutoDTO>> buscarPorDescricao(String descricao,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		// return
		// ResponseEntity.ok(avaliacaoProdutoService.buscarPorDescricao(descricao,
		// usuarioLogado));
		return ResponseEntity.ok(null);
	}

	@Override
	public ResponseEntity<List<AvaliacaoProdutoDTO>> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		// return
		// ResponseEntity.ok(avaliacaoProdutoService.buscarTodosPorEmpresa(usuarioLogado));
		return ResponseEntity.ok(null);
	}

	@Override
	public ResponseEntity<Page<AvaliacaoProdutoDTO>> buscarAvancado(String descricao, Boolean ativo, int page, int size,
			UsuarioLogadoPrincipal usuarioLogado) {
		// return ResponseEntity.ok(avaliacaoProdutoService.buscarAvancado(descricao,
		// ativo, page, size, usuarioLogado));
		return ResponseEntity.ok(null);
	}

	@Override
	public ResponseEntity<Page<AvaliacaoProdutoDTO>> buscarPaginado(int page, int size, String sort, String direction,
			UsuarioLogadoPrincipal usuarioLogado) {
		// return ResponseEntity.ok(avaliacaoProdutoService.buscarPaginado(page, size,
		// sort, direction, usuarioLogado));
		return ResponseEntity.ok(null);
	}
}
