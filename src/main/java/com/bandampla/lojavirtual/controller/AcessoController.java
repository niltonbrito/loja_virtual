package com.bandampla.lojavirtual.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.controller.api.AcessoControllerAPI;
import com.bandampla.lojavirtual.dto.AcessoDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.service.AcessoService;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

//@CrossOrigin(value = "http://bandampla.com") //Somente requisições a partir desta origem http://bandampla.com podem utilizar este controler ou end-point
@RestController
@RequestMapping("/acesso")
public class AcessoController implements AcessoControllerAPI {

	private final AcessoService acessoService;
	private final HttpServletRequest request;

	public AcessoController(AcessoService acessoService, HttpServletRequest request) {
		this.acessoService = acessoService;
		this.request = request;
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<AcessoDTO>> cadastrar(AcessoDTO dto) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();

		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),
				"Acesso cadastrado com sucesso", acessoService.cadastrar(dto), request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<Void>> deletarPorId(Long id) {
		String traceId = UUID.randomUUID().toString();
		acessoService.deletePorId(id);

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Acesso removido com sucesso", null,
				request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<AcessoDTO>> buscarPorId(Long id) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Acesso recuperado com sucesso",
				acessoService.buscarPorId(id), request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<AcessoDTO>>> buscarPorRole(RoleUser role) {
		String traceId = UUID.randomUUID().toString();

		return ResponseEntity
				.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Acessos listados por papel com sucesso",
						acessoService.buscarPorRole(role), request.getRequestURI(), traceId));
	}
}