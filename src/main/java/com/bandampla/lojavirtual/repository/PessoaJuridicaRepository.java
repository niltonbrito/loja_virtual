package com.bandampla.lojavirtual.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.model.PessoaJuridica;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Repository
@Transactional
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

	@Query("select pj from PessoaJuridica pj where upper(pj.nome) like upper(concat('%', :nome, '%'))")
	List<PessoaJuridica> findAllByNome(@Param("nome") String nome);

	@Query("select pj from PessoaJuridica pj where pj.cnpj like %?1%")
	List<PessoaJuridica> findAllByCnpj(String cnpj);

	Optional<PessoaJuridica> findByCnpj(String cnpj);

	Optional<PessoaJuridica> findByInscricaoEstadual(String inscricaoEstadual);
}
