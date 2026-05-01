/**
 * 
 */
package com.bandampla.lojavirtual.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.model.Usuario;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query(value = "select u from Usuario u where u.login = ?1")
	Usuario findUserByLogin(String id);

	Optional<Usuario> findByLogin(String login);

}
