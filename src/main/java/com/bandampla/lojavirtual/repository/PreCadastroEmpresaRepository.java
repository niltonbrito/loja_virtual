package com.bandampla.lojavirtual.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.enums.StatusOnboarding;
import com.bandampla.lojavirtual.model.PreCadastroEmpresa;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 26 de jul. de 2026
 */
@Repository
@Transactional
public interface PreCadastroEmpresaRepository
		extends JpaRepository<PreCadastroEmpresa, Long>, JpaSpecificationExecutor<PreCadastroEmpresa> {

	Optional<PreCadastroEmpresa> findByEmailIgnoreCase(String email);

	Optional<PreCadastroEmpresa> findByTokenConfirmacaoHash(String tokenConfirmacaoHash);

	boolean existsByEmailIgnoreCase(String email);

	boolean existsByCnpj(String cnpj);

	boolean existsByCpfResponsavel(String cpfResponsavel);

	boolean existsByEmailIgnoreCaseAndStatus(String email, StatusOnboarding status);

	@Modifying
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Query("UPDATE PreCadastroEmpresa p " + "SET p.status = :status, " + "p.tokenConfirmacaoHash = null, "
			+ "p.tokenExpiraEm = null, " + "p.updatedAt = :updatedAt " + "WHERE p.id = :id")
	int marcarTokenComoExpirado(@Param("id") Long id, @Param("status") StatusOnboarding status,
			@Param("updatedAt") LocalDateTime updatedAt);

	boolean existsByCnpjAndIdNot(String cnpj, Long id);

	boolean existsByCpfResponsavelAndIdNot(String cpfResponsavel, Long id);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT p " + "FROM PreCadastroEmpresa p " + "WHERE p.id = :id")
	Optional<PreCadastroEmpresa> buscarPorIdParaFinalizacao(@Param("id") Long id);
}