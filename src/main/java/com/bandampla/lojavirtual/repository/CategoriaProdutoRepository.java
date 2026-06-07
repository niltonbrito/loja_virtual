/**
 * 
 */
package com.bandampla.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.model.CategoriaProduto;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Repository
public interface CategoriaProdutoRepository
		extends JpaRepository<CategoriaProduto, Long>, JpaSpecificationExecutor<CategoriaProduto> {
		
	List<CategoriaProduto> findByNomeDescricaoIgnoreCaseAndEmpresaId(String nomeDescricao, Long empresaId);

	List<CategoriaProduto> findByNomeDescricao(String nomeDescricao);
	
	List<CategoriaProduto> findByNomeDescricaoContainingIgnoreCase(String nome);
	
	List<CategoriaProduto> findByEmpresaId(Long empresaId);
}
