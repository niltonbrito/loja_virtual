/**
 * 
 */
package com.bandampla.lojavirtual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
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

	public Acesso cadastrar(Acesso acesso) throws ExceptionCustom {
		
		RoleUser roleUser = acesso.getRoleUser();
		
		if (acesso.getId() == null) {
			List<Acesso> acessos = acessoRepository.findByRoleUser(roleUser);
			if (!acessos.isEmpty()) {
				throw new ExceptionCustom("Já existe Acesso com a descrição: " + roleUser.getDescricao());
			}
		}
		return acessoRepository.save(acesso);
	}

	public void deletar(Acesso acesso) {
		acessoRepository.deleteById(acesso.getId());
	}

	public void deletePorId(Long id) {
		acessoRepository.deleteById(id);
	}

	public Acesso buscarPorId(Long id) throws ExceptionCustom {

		Acesso acesso = acessoRepository.findById(id).orElse(null);
		if (acesso == null) {
			throw new ExceptionCustom("Não encontrou Acesso com o código: " + id);
		}
		return acesso;
	}

	public List<Acesso> buscarPorRole(RoleUser roleUser) {
	    return acessoRepository.findByRoleUser(roleUser);
	}

}
