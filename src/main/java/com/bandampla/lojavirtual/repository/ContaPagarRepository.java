package com.bandampla.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.model.ContaPagar;

@Repository
public interface ContaPagarRepository 
        extends JpaRepository<ContaPagar, Long>, JpaSpecificationExecutor<ContaPagar> {

    @Query("SELECT c FROM ContaPagar c WHERE c.pessoa.id = :pessoaId AND c.empresa.id = :empresaId")
    List<ContaPagar> buscarPorPessoa(@Param("pessoaId") Long pessoaId, @Param("empresaId") Long empresaId);

    @Query("SELECT c FROM ContaPagar c WHERE c.pessoaFornecedor.id = :fornecedorId AND c.empresa.id = :empresaId")
    List<ContaPagar> buscarPorFornecedor(@Param("fornecedorId") Long fornecedorId, @Param("empresaId") Long empresaId);
}
