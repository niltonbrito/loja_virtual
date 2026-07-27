/**
 * 
 */
package com.bandampla.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bandampla.lojavirtual.model.FormaPagamento;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 26 de jul. de 2026
 */
public interface FormaPagamentoRepository
		extends JpaRepository<FormaPagamento, Long>, JpaSpecificationExecutor<FormaPagamento> {

	@Query("SELECT f FROM FormaPagamento f WHERE f.empresa.id = :empresaId ORDER BY f.descricao ASC")
	List<FormaPagamento> buscarFormasPagamentoPorEmpresa(Long empresaId);

	@Query("SELECT f FROM FormaPagamento f WHERE LOWER(f.descricao) LIKE LOWER(CONCAT('%', :descricao, '%')) AND f.empresa.id = :empresaId")
	List<FormaPagamento> buscarPorDescricaoEEmpresa(String descricao, Long empresaId);
}