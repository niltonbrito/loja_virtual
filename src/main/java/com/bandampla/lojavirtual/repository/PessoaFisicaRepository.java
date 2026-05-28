/**
 * 
 */
package com.bandampla.lojavirtual.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.model.PessoaFisica;


/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Repository
@Transactional
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

	Optional<PessoaFisica> findByCpf(String cpf);

	@Query("select pf from PessoaFisica pf where upper(pf.nome) like upper(concat('%', :nome, '%'))")
	List<PessoaFisica> findByNome(@Param("nome") String nome);
}
