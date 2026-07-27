package com.bandampla.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.model.CupomDesconto;

@Repository
public interface CupomDescontoRepository
		extends JpaRepository<CupomDesconto, Long>, JpaSpecificationExecutor<CupomDesconto> {

}
