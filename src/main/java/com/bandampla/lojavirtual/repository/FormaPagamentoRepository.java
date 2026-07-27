/**
 * 
 */
package com.bandampla.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bandampla.lojavirtual.model.FormaPagamento;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 26 de jul. de 2026
 */
public interface FormaPagamentoRepository
		extends JpaRepository<FormaPagamento, Long>, JpaSpecificationExecutor<FormaPagamento> {

}