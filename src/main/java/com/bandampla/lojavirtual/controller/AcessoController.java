/**
 * 
 */
package com.bandampla.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.model.Acesso;
import com.bandampla.lojavirtual.service.AcessoService;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   27 de abr. de 2026
 */

@Controller
@RestController
public class AcessoController {

	@Autowired
	private AcessoService acessoService;
	
	@ResponseBody
	@PostMapping(value = "/salvarAcesso")
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) {
		Acesso acessoSalvoAcesso = acessoService.save(acesso);
		
		return new ResponseEntity<Acesso>(acessoSalvoAcesso, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "/deletarAcesso")
	public ResponseEntity<?> deletarAcesso(@RequestBody Acesso acesso) {
		acessoService.delete(acesso);
		
		return new ResponseEntity("Acesso Removido",HttpStatus.OK);
	}
}
