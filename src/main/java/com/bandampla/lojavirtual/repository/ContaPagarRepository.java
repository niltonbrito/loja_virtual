package com.bandampla.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.model.ContaPagar;

@Repository
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long>, JpaSpecificationExecutor<ContaPagar> {

	@Query("select a from ContaPagar a where a.pessoa.id = ?1 and a.empresa.id = ?2")
	List<ContaPagar> buscarByPessoaId(Long id, Long empresaId);

	@Query("select a from ContaPagar a where a.pessoaFornecedor.id = ?1 and a.empresa.id = ?2")
	List<ContaPagar> buscarByFornecedorId(Long id, Long empresaId);
}
