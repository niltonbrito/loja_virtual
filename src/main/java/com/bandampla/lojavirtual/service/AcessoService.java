/**
 * 
 */
package com.bandampla.lojavirtual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.model.Acesso;
import com.bandampla.lojavirtual.repository.AcessoRepository;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Service
public class AcessoService {

	@Autowired
	private AcessoRepository acessoRepository;

	public Acesso save(Acesso acesso) {
		return acessoRepository.save(acesso);
	}

	public void delete(Acesso acesso) {
		acessoRepository.deleteById(acesso.getId());
	}

	public void deleteById(Long id) {
		acessoRepository.deleteById(id);
	}

	public Acesso buscarById(Long id) {
		return acessoRepository.findById(id).get();
	}

	public List<Acesso> buscarPorDescricao(String desc) {
		return acessoRepository.buscarAcessoDesc(desc);
	}
}
