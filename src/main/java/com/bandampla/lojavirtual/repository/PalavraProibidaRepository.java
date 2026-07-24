/**
 * 
 */
package com.bandampla.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.model.PalavraProibida;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Repository
public interface PalavraProibidaRepository
		extends JpaRepository<PalavraProibida, Long>, JpaSpecificationExecutor<PalavraProibida> {

	/**
	 * Busca apenas as strings textuais dos termos otimizando o consumo de memória RAM do cache.
	 */
	@Query("SELECT LOWER(p.termo) FROM PalavraProibida p")
	List<String> buscarTodosOsTermosLimpas();
}
