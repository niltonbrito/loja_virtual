package com.bandampla.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.model.NotaFiscalCompra;

@Repository
public interface NotaFiscalCompraRepository
		extends JpaRepository<NotaFiscalCompra, Long>, JpaSpecificationExecutor<NotaFiscalCompra> {

	@Query("SELECT n FROM NotaFiscalCompra n WHERE n.pessoa.id = :pessoaId AND n.empresa.id = :empresaId")
	List<NotaFiscalCompra> buscarPorPessoa(@Param("pessoaId") Long pessoaId, @Param("empresaId") Long empresaId);

	@Query("SELECT n FROM NotaFiscalCompra n WHERE n.contaPagar.id = :contaPagarId AND n.empresa.id = :empresaId")
	List<NotaFiscalCompra> buscarPorContaPagar(@Param("contaPagarId") Long contaPagarId, @Param("empresaId") Long empresaId);

	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(nativeQuery = true, value = "delete from nota_item_produto where nota_fiscal_compra_id = ?1")
	void deleteItemNotaFiscal(Long notaFiscalCompraId);
}
