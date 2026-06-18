package com.bandampla.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.model.NotaFiscalCompra;

@Repository
public interface NotaFiscalCompraRepository
		extends JpaRepository<NotaFiscalCompra, Long>, JpaSpecificationExecutor<NotaFiscalCompra> {@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
		@Query(nativeQuery = true, value = "delete from nota_item_produto where nota_fiscal_compra_id = ?1")
		void deleteItemNotaFiscal(Long notaFiscalCompraId);

}
