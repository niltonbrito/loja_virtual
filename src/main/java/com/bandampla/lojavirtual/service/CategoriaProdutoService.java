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
import com.bandampla.lojavirtual.model.CategoriaProduto;
import com.bandampla.lojavirtual.repository.AcessoRepository;
import com.bandampla.lojavirtual.repository.CategoriaProdutoRepository;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Service
public class CategoriaProdutoService {

	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;

	public CategoriaProduto save(CategoriaProduto categoriaProduto ) throws ExceptionCustom {

		if (categoriaProduto.getId() == null) {
			List<CategoriaProduto> categoriaProdutos = categoriaProdutoRepository.findByNomeDescricao(categoriaProduto.getNomeDescricao());
			if (!categoriaProdutos.isEmpty()) {
				throw new ExceptionCustom("Já existe Categoria de Produto com a descrição: " + categoriaProduto.getNomeDescricao());
			}
		}
		return categoriaProdutoRepository.save(categoriaProduto);
	}
	/*
	public void delete(Acesso acesso) {
		acessoRepository.deleteById(acesso.getId());
	}

	public void deleteById(Long id) {
		acessoRepository.deleteById(id);
	}

	public Acesso buscarById(Long id) throws ExceptionCustom {

		Acesso acesso = acessoRepository.findById(id).orElse(null);
		if (acesso == null) {
			throw new ExceptionCustom("Não encontrou Acesso com o código: " + id);
		}
		return acesso;
	}

	public List<Acesso> buscarPorRole(RoleUser roleUser) {
	    return acessoRepository.findByRoleUser(roleUser);
	}*/

}
