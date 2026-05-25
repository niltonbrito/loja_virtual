/**
 * 
 */
package com.bandampla.lojavirtual.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Usuario;
import java.util.List;


/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Repository
@Transactional
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

	Optional<PessoaFisica> findByCpf(String cpf);

	List<PessoaFisica> findByNome(String nome);
}
