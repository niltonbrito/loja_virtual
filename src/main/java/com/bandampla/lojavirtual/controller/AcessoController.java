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

import com.bandampla.lojavirtual.model.Acesso;
import com.bandampla.lojavirtual.service.AcessoService;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Controller
@RestController(value = "acesso")
public class AcessoController {

	@Autowired
	private AcessoService acessoService;

	@ResponseBody // Pode dar um retorno da API
	@PostMapping(value = "/salvarAcesso") // Mapeandoa url para receber um JSON
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) {// Recebe o JSON e converte para objeto
		Acesso acessoSalvoAcesso = acessoService.save(acesso);
		return new ResponseEntity<Acesso>(acessoSalvoAcesso, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "/deletarAcesso")
	public ResponseEntity<?> deletarAcesso(@RequestBody Acesso acesso) {
		acessoService.delete(acesso);
		return new ResponseEntity<>("Acesso Removido", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "/deletarAcessoPorId/{id}")
	public ResponseEntity<?> deletarAcessoPorId(@PathVariable Long id) {
		acessoService.deleteById(id);
		return new ResponseEntity<>("Acesso Removido", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/buscarAcessoPorId/{id}")
	public ResponseEntity<Acesso> buscarAcessoPorId(@PathVariable Long id) {
		Acesso acesso = acessoService.buscarById(id);
		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "/buscarPorDescricao/{desc}")
	public ResponseEntity<List<Acesso>> buscarPorDescricao(@PathVariable String desc) {
		List<Acesso> acesso = acessoService.buscarPorDescricao(desc);
		return new ResponseEntity<List<Acesso>>(acesso, HttpStatus.OK);
	}
}
