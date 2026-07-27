/**
 * 
 */
package com.bandampla.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.model.ItemVendaLoja;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 26 de jul. de 2026
 */
@Repository
public interface ItemVendaLojaRepository extends JpaRepository<ItemVendaLoja, Long> {

	@Query("SELECT i FROM ItemVendaLoja i WHERE i.vendaLojaVirtual.id = :vendaId")
	List<ItemVendaLoja> buscarPorVendaId(Long vendaId);
}