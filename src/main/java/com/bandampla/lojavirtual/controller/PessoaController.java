package com.bandampla.lojavirtual.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.controller.api.PessoaControllerAPI;
import com.bandampla.lojavirtual.dto.CepDTO;
import com.bandampla.lojavirtual.dto.CnpjDTO;
import com.bandampla.lojavirtual.dto.PessoaFisicaDTO;
import com.bandampla.lojavirtual.dto.PessoaJuridicaDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.service.PessoaUserService;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

//@CrossOrigin(value = "http://bandampla.com") //Somente requisições a partir desta origem http://bandampla.com podem utilizar este controler ou end-point

@RestController
@RequestMapping("/person")
public class PessoaController implements PessoaControllerAPI {

	private final PessoaUserService pessoaUserService;
	private final HttpServletRequest request;

	public PessoaController(PessoaUserService pessoaUserService, HttpServletRequest request) {
		this.pessoaUserService = pessoaUserService;
		this.request = request;
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<PessoaJuridicaDTO>> salvarPessoaJuridica(@Valid PessoaJuridicaDTO dto)
			throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(), "Pessoa Jurídica cadastrada com sucesso",
						pessoaUserService.salvarPessoaJuridica(dto), request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<PessoaJuridicaDTO>>> consultaPessoaJuridicaPorNome(String nome)
			throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Consulta por nome realizada",
				pessoaUserService.consultaPessoaJuridicaPorNome(nome.trim().toUpperCase()), request.getRequestURI(),
				traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<PessoaJuridicaDTO>>> consultaPessoaJuridicaPorCnpj(String cnpj)
			throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "Consulta por CNPJ realizada",
				pessoaUserService.consultaPessoaJuridicaPorCnpj(cnpj.trim()), request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<PessoaFisicaDTO>> salvarPessoaFisica(@Valid PessoaFisicaDTO dto)
			throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(), "Pessoa Física cadastrada com sucesso",
						pessoaUserService.salvarPessoaFisica(dto), request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<CepDTO>> consultaCep(String cep) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "CEP consultado com sucesso",
				pessoaUserService.consultaCep(cep), request.getRequestURI(), traceId));
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<CnpjDTO>> consultaCnpj(String cnpj) throws ExceptionCustom {
		String traceId = UUID.randomUUID().toString();

		return ResponseEntity.ok(new ResponseDefaultDTO<>(HttpStatus.OK.toString(), "CNPJ verificado com sucesso",
				pessoaUserService.consultaCnpj(cnpj), request.getRequestURI(), traceId));
	}
}