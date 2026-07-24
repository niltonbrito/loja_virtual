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

	@Autowired
	private PalavraProibidaService palavraProibidaService;

	@Autowired
	private HttpServletRequest request;

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

		return ResponseEntity
				.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Palavra Proibida atualizada com sucesso",
						palavraProibidaService.atualizar(id, dto), request.getRequestURI(), traceId));
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
	public ResponseEntity<List<PalavraProibidaDTO>> buscarPorDescricao(String termo) throws ExceptionCustom {
		return ResponseEntity.ok(palavraProibidaService.buscarPorDescricao(termo));
	}

	@Override
	public ResponseEntity<List<PalavraProibidaDTO>> buscarTodos() throws ExceptionCustom {
		return ResponseEntity.ok(palavraProibidaService.buscarTodos());
	}

	@Override
	public ResponseEntity<Page<PalavraProibidaDTO>> buscarAvancado(String termo, int page, int size) {
		return ResponseEntity.ok(palavraProibidaService.buscarAvancado(termo, page, size));
	}

	@Override
	public ResponseEntity<Page<PalavraProibidaDTO>> buscarPaginado(int page, int size,String sort,
			String direction) {
		return ResponseEntity.ok(palavraProibidaService.buscarPaginado(page, size, sort, direction));
	}
}