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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.model.Usuario;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByLogin(String login);

	@Query(value = "select constraint_name from information_schema.constraint_column_usage where table_name ='usuario_acesso'\r\n"
			+ "and column_name = 'acesso_id' and constraint_name <> 'unique_acesso_user'", nativeQuery = true)
	String consultaConstraintAcesso();

    @Query(value = "select * from usuario u where u.pessoa_id = :pessoaId or u.login = :login", nativeQuery = true)
    Optional<Usuario> findByPessoaOuLogin(@Param("pessoaId") Long pessoaId, @Param("login") String login);
    
	//@Query(value = "INSERT INTO usuario_acesso(usuario_id, acesso_id)VALUES (?1, (SELECT id FROM acesso WHERE role_user = ?2))", nativeQuery = true)
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "insert into usuario_acesso(usuario_id, acesso_id)  select :usuarioId, a.id from acesso a where a.role_user = :roleUser  on conflict do nothing", nativeQuery = true)
	int insereAcessoUser(@Param("usuarioId") Long usuarioId, @Param("roleUser") String roleUser);

	@Query(value = "select u.* from usuario u where u.update_at is not null and u.update_at <= current_date - interval '90 days'", nativeQuery = true)
	List<Usuario> usuarioSenhaVencida();
}
