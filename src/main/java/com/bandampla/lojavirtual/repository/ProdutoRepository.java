/**
 * 
 */
package com.bandampla.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.model.Produto;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Repository
public interface ProdutoRepository
		extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {
		
	List<Produto> findByNomeIgnoreCase(String nomeDescricao, Long empresaId);

	List<Produto> findByDescricao(String nomeDescricao);
	
	List<Produto> findByNomeContainingIgnoreCase(String nome);
	
	List<Produto> findByEmpresaId(Long empresaId);
}
