/**
 * 
 */
package com.bandampla.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.exception.ExceptionCustom;
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

	@ResponseBody // Pode dar um retorno da API
	@PostMapping(value = "/salvarPessoaJuridica") // Mapeandoa url para receber um JSON
	public ResponseEntity<PessoaJuridica> salvarPessoaJuridica(@RequestBody PessoaJuridica pessoaJuridica) throws ExceptionCustom  {// Recebe o JSON e converte para objeto
		
		return new ResponseEntity<PessoaJuridica>(pessoaUserService.salvarPessoaJuridica(pessoaJuridica), HttpStatus.OK);
	}
/*
	@ResponseBody
	@PostMapping(value = "/deletarAcesso")
	public ResponseEntity<?> deletarPessoaJuridica(@RequestBody Acesso acesso) {
		//acessoService.delete(acesso);
		/ new ResponseEntity<>("Acesso Removido", HttpStatus.OK);
	}

	//@Secured(value = {"ROLE_ADMIN", "ROLE_GERENTE"}) //Somente usuario ou requisições com o Role permitido podem utilizar este controler ou end-point
	@ResponseBody
	@DeleteMapping(value = "/deletarAcessoPorId/{id}")
	public ResponseEntity<?> deletarPessoaJuridicaPorId(@PathVariable Long id) {
		//acessoService.deleteById(id);
		//return new ResponseEntity<>("Acesso Removido", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/buscarAcessoPorId/{id}")
	public ResponseEntity<PessoaJuridica> buscarPessoaJuridicaPorId(@PathVariable Long id) throws ExceptionCustom {		
	//	return new ResponseEntity<Acesso>(acessoService.buscarById(id), HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/buscarPorDescricao/{desc}")
	public ResponseEntity<List<PessoaJuridica>> buscarPessoaJuridicaPorDescricao(@PathVariable String desc) {
		//List<Acesso> acesso = acessoService.buscarPorDescricao(desc.toUpperCase());
	//	return new ResponseEntity<List<Acesso>>(acesso, HttpStatus.OK);
	}*/
}
