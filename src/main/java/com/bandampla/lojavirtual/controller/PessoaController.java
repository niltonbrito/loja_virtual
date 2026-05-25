/**
 * 
 */
package com.bandampla.lojavirtual.controller;

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

	@Autowired
	private PessoaUserService pessoaUserService;

    /* ============================
       SALVAR PESSOA JURÍDICA
       ============================ */
	@ResponseBody // Pode dar um retorno da API
	@PostMapping(value = "/pessoa/juridica") // Mapeandoa url para receber um JSON
	public ResponseEntity<PessoaJuridica> salvarPessoaJuridica(@Valid @RequestBody PessoaJuridica pessoaJuridica)
			throws ExceptionCustom {// Recebe o JSON e converte para objeto
		return new ResponseEntity<PessoaJuridica>(pessoaUserService.salvarPessoaJuridica(pessoaJuridica),
				HttpStatus.OK);
	}

    /* ============================
       SALVAR PESSOA FÍSICA
       ============================ */
	@ResponseBody // Pode dar um retorno da API
	@PostMapping(value = "/pessoa/fisica") // Mapeandoa url para receber um JSON
	public ResponseEntity<PessoaFisica> salvarPessoaFisica(@Valid @RequestBody PessoaFisica pessoaFisica)
			throws ExceptionCustom {// Recebe o JSON e converte para objeto
		return new ResponseEntity<PessoaFisica>(pessoaUserService.salvarPessoaFisica(pessoaFisica), HttpStatus.OK);
	}

    /* ============================
       CONSULTAR CEP
       ============================ */
	@ResponseBody // Pode dar um retorno da API
	@GetMapping(value = "/consulta/cep/{cep}") // Mapeandoa url para receber um JSON
	public ResponseEntity<CepDTO> consultaCep(@Valid @PathVariable String cep) throws ExceptionCustom {
		return new ResponseEntity<CepDTO>(pessoaUserService.consultaCep(cep), HttpStatus.OK);
	}

}
