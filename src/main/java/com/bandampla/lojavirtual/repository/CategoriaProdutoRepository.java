/**
 * 
 */
package com.bandampla.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.model.Acesso;
import com.bandampla.lojavirtual.model.CategoriaProduto;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   27 de abr. de 2026
 */

@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long>{
	

	List<CategoriaProduto> findByNomeDescricao(String nomeDescricao);
}
