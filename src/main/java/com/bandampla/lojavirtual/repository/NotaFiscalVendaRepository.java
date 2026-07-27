package com.bandampla.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.model.NotaFiscalVenda;

@Repository
public interface NotaFiscalVendaRepository extends JpaRepository<NotaFiscalVenda, Long> {

	@Query("SELECT n FROM NotaFiscalVenda n WHERE n.vendaLojaVirtual.id = :vendaId")
	NotaFiscalVenda buscarNotaPorVendaId(Long vendaId);
}