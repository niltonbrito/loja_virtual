package com.bandampla.lojavirtual.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.controller.api.PalavraProibidaControllerAPI;
import com.bandampla.lojavirtual.dto.PalavraProibidaDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.service.PalavraProibidaService;

@RestController
@RequestMapping("/palavra_proibida")
public class PalavraProibidaController implements PalavraProibidaControllerAPI {

	private final PalavraProibidaService palavraProibidaService;
	private final HttpServletRequest request;

	public PalavraProibidaController(PalavraProibidaService palavraProibidaService, HttpServletRequest request) {
		this.palavraProibidaService = palavraProibidaService;
		this.request = request;
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<PalavraProibidaDTO>> cadastrar(PalavraProibidaDTO dto)
			throws ExceptionCustom, MessagingException, IOException {

		String traceId = UUID.randomUUID().toString();
		PalavraProibidaDTO retorno = palavraProibidaService.cadastrar(dto);

		ResponseDefaultDTO<PalavraProibidaDTO> response = new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),
				"Palavra Proibida registrada com sucesso", retorno, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<PalavraProibidaDTO>> atualizar(@PathVariable Long id,
			@Valid @RequestBody PalavraProibidaDTO dto) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		PalavraProibidaDTO retorno = palavraProibidaService.atualizar(id, dto);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Palavra Proibida atualizada com sucesso", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Void>> deletar(Long id) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		palavraProibidaService.deletar(id);

		ResponseDefaultDTO<Void> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Palavra Proibida deletada com sucesso", null, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<PalavraProibidaDTO>>> buscarPorDescricao(String termo)
			throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		List<PalavraProibidaDTO> retorno = palavraProibidaService.buscarPorDescricao(termo);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Termos proibidos recuperados",
				retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<PalavraProibidaDTO>>> buscarTodos() throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();
		List<PalavraProibidaDTO> retorno = palavraProibidaService.buscarTodos();

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Catálogo de palavras proibidas listado", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Page<PalavraProibidaDTO>>> buscarAvancado(String termo, int page,
			int size) {
		String traceId = UUID.randomUUID().toString();
		Page<PalavraProibidaDTO> retorno = palavraProibidaService.buscarAvancado(termo, page, size);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Busca avançada global de termos concluída", retorno, request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Page<PalavraProibidaDTO>>> buscarPaginado(int page, int size, String sort,
			String direction) {
		String traceId = UUID.randomUUID().toString();
		Page<PalavraProibidaDTO> retorno = palavraProibidaService.buscarPaginado(page, size, sort, direction);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Listagem paginada global de termos concluída", retorno, request.getRequestURI(), traceId));
	}
}