/**
 * 
 */
package com.bandampla.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.bandampla.lojavirtual.model.Acesso;
import com.bandampla.lojavirtual.service.AcessoService;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   27 de abr. de 2026
 */

@Controller
public class AcessoController {

	@Autowired
	private AcessoService acessoService;
	
	@PostMapping("/salvaAcesso")
	public Acesso salvarAcesso(Acesso acesso) {
		return acessoService.save(acesso);
	}
}
