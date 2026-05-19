/**
 * 
 */
package com.bandampla.lojavirtual.controller;

import com.bandampla.lojavirtual.mapper.PessoaMapper;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.dto.request.CepDTO;
import com.bandampla.lojavirtual.dto.request.PessoaFisicaRequestDTO;
import com.bandampla.lojavirtual.dto.request.PessoaJuridicaRequestDTO;
import com.bandampla.lojavirtual.dto.response.PessoaFisicaResponseDTO;
import com.bandampla.lojavirtual.dto.response.PessoaJuridicaResponseDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.service.PessoaUserService;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

//@CrossOrigin(value = "http://bandampla.com") //Somente requisições a partir desta origem http://bandampla.com podem utilizar este controler ou end-point

@RestController
public class PessoaController {

	private final PessoaMapper pessoaMapper;

	@Autowired
	private PessoaUserService pessoaUserService;

	PessoaController(PessoaMapper pessoaMapper) {
		this.pessoaMapper = pessoaMapper;
	}

	@ResponseBody
	@PostMapping(value = "/pessoa/juridica")
	public ResponseEntity<PessoaJuridicaResponseDTO> salvarPessoaJuridica(
			@Valid @RequestBody PessoaJuridicaRequestDTO dto) throws ExceptionCustom {

		PessoaJuridica pj = pessoaUserService.salvarPessoaJuridica(dto);
		return ResponseEntity.ok(pessoaMapper.toResponse(pj));
	}

	@ResponseBody
	@PostMapping(value = "/pessoa/fisica")
	public ResponseEntity<PessoaFisicaResponseDTO> salvarPessoaFisica(@Valid @RequestBody PessoaFisicaRequestDTO dto)
			throws ExceptionCustom {

		PessoaFisica pf = pessoaUserService.salvarPessoaFisica(dto);
		return ResponseEntity.ok(pessoaMapper.toResponse(pf));
	}

	@ResponseBody
	@GetMapping(value = "/consulta/cep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep) throws ExceptionCustom {
		return ResponseEntity.ok(pessoaUserService.consultaCep(cep));
	}

}
