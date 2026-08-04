package com.bandampla.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.model.VendaLojaVirtual;

@Repository
public interface VendaLojaVirtualRepository
		extends JpaRepository<VendaLojaVirtual, Long>, JpaSpecificationExecutor<VendaLojaVirtual> {

		@Query("SELECT COUNT(v) FROM VendaLojaVirtual v WHERE v.pessoa.id = :clienteId AND v.cupomDesconto.id = :cupomId")
		int countVendasPorClienteECupom(@Param("clienteId") Long clienteId, @Param("cupomId") Long cupomId);


}
