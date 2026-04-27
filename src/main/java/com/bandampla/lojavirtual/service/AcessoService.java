/**
 * 
 */
package com.bandampla.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.model.Acesso;
import com.bandampla.lojavirtual.repository.AcessoRepository;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   27 de abr. de 2026
 */

@Service
public class AcessoService {

	@Autowired
	private AcessoRepository acessoRepository;
	
	public Acesso save(Acesso acesso) {
		return acessoRepository.save(acesso);
	}
}
