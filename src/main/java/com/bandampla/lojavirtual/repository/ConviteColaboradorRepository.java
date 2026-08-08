package com.bandampla.lojavirtual.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.enums.StatusConvite;
import com.bandampla.lojavirtual.model.ConviteColaborador;

@Repository
public interface ConviteColaboradorRepository extends JpaRepository<ConviteColaborador, Long> {

	Optional<ConviteColaborador> findByTokenHash(String tokenHash);

	Optional<ConviteColaborador> findByEmpresaIdAndEmailIgnoreCaseAndStatus(Long empresaId, String email,
			StatusConvite status);

	boolean existsByEmpresaIdAndEmailIgnoreCaseAndStatus(Long empresaId, String email, StatusConvite status);
}