/**
 * 
 */
package com.bandampla.lojavirtual.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.model.Usuario;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	//@Query(value = "select u from Usuario u where u.login = ?1")
	//Usuario findUserByLogin(String id);

	Optional<Usuario> findByLogin(String login);

	@Query(nativeQuery = true, value = "select * from usuario u where u.pessoa_id = ?1 or u.login = ?2")
	Usuario finUserByPessoa(Long id, String email);

	@Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name ='usuario_acesso'\r\n"
			+ "and column_name = 'acesso_id' and constraint_name <> 'unique_acesso_user'", nativeQuery = true)
	String consultaConstraintAcesso();


	@Transactional
	@Modifying
	@Query(value = "insert into usuario_acesso(usuario_id, acesso_id) values(?1, (select id from acesso where descricao = 'ROLE_USER'))", nativeQuery = true)
	void insereAcessoPj(Long idUser);

	@Transactional
	@Modifying
	@Query(value = "insert into usuario_acesso(usuario_id, acesso_id) values(?1, (select id from acesso where descricao = ?2))", nativeQuery = true)
	void insereAcessoUser(Long idUser, String role);

	@Query(value = "SELECT u.* FROM usuario u WHERE u.update_at <= current_date - 90", nativeQuery = true)
	List<Usuario> usuarioSenhaVencida();
}
