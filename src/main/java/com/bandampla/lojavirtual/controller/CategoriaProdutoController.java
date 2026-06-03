/**
 * 
 */
package com.bandampla.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.Acesso;
import com.bandampla.lojavirtual.model.CategoriaProduto;
import com.bandampla.lojavirtual.service.AcessoService;
import com.bandampla.lojavirtual.service.CategoriaProdutoService;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

//@CrossOrigin(value = "http://bandampla.com") //Somente requisições a partir desta origem http://bandampla.com podem utilizar este controler ou end-point
@RestController
public class CategoriaProdutoController {

	@Autowired
	private CategoriaProdutoService categoriaProdutoService;

	@ResponseBody // Pode dar um retorno da API
	@PostMapping(value = "/salvarCategoriaProduto") // Mapeandoa url para receber um JSON
	public ResponseEntity<CategoriaProduto> salvarCategoriaProduto(@RequestBody CategoriaProduto categoriaProduto) throws ExceptionCustom {// Recebe o JSON e converte para objeto		
		return new ResponseEntity<CategoriaProduto>(categoriaProdutoService.save(categoriaProduto), HttpStatus.OK);
	}
	/*
	@ResponseBody
	@PostMapping(value = "/deletarAcesso")
	public ResponseEntity<?> deletarAcesso(@RequestBody Acesso acesso) {
		acessoService.delete(acesso);
		return new ResponseEntity<>("Acesso Removido", HttpStatus.OK);
	}

	//@Secured(value = {"ROLE_ADMIN", "ROLE_GERENTE"}) //Somente usuario ou requisições com o Role permitido podem utilizar este controler ou end-point
	@ResponseBody
	@DeleteMapping(value = "/deletarAcessoPorId/{id}")
	public ResponseEntity<?> deletarAcessoPorId(@PathVariable Long id) {
		acessoService.deleteById(id);
		return new ResponseEntity<>("Acesso Removido", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/buscarAcessoPorId/{id}")
	public ResponseEntity<Acesso> buscarAcessoPorId(@PathVariable Long id) throws ExceptionCustom {		
		return new ResponseEntity<Acesso>(acessoService.buscarById(id), HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/buscarPorRole/{role}")
	public ResponseEntity<List<Acesso>> buscarPorRole(@PathVariable("role") RoleUser role) {
	    return new ResponseEntity<>(acessoService.buscarPorRole(role), HttpStatus.OK);
	}*/

}
