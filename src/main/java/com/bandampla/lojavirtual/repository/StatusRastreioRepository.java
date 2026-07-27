package com.bandampla.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.model.StatusRastreio;

@Repository
public interface StatusRastreioRepository extends JpaRepository<StatusRastreio, Long> {

	// 🔥 Busca a linha do tempo da entrega ordenada cronologicamente (da mais
	// antiga para a mais recente)
	@Query("SELECT s FROM StatusRastreio s WHERE s.vendaLojaVirtual.id = :vendaId ORDER BY s.id ASC")
	List<StatusRastreio> buscarRastreioPorVendaId(Long vendaId);
}