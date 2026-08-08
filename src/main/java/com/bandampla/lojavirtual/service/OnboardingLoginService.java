package com.bandampla.lojavirtual.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.dto.onboarding.OnboardingLoginRequestDTO;
import com.bandampla.lojavirtual.dto.onboarding.OnboardingLoginResponseDTO;
import com.bandampla.lojavirtual.enums.StatusOnboarding;
import com.bandampla.lojavirtual.enums.TipoTokenJwt;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.PreCadastroEmpresa;
import com.bandampla.lojavirtual.repository.PreCadastroEmpresaRepository;
import com.bandampla.lojavirtual.security.JwtOnboardingService;

@Service
public class OnboardingLoginService {

	private final PreCadastroEmpresaRepository preCadastroEmpresaRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtOnboardingService jwtOnboardingService;

	public OnboardingLoginService(PreCadastroEmpresaRepository preCadastroEmpresaRepository,
			PasswordEncoder passwordEncoder, JwtOnboardingService jwtOnboardingService) {

		this.preCadastroEmpresaRepository = preCadastroEmpresaRepository;

		this.passwordEncoder = passwordEncoder;
		this.jwtOnboardingService = jwtOnboardingService;
	}

	@Transactional(readOnly = true)
	public OnboardingLoginResponseDTO autenticar(OnboardingLoginRequestDTO dto) {

		String email = normalizarEmail(dto.getEmail());

		PreCadastroEmpresa preCadastro = preCadastroEmpresaRepository.findByEmailIgnoreCase(email)
				.orElseThrow(() -> credenciaisInvalidas());

		if (!passwordEncoder.matches(dto.getSenha(), preCadastro.getSenhaHash())) {

			throw credenciaisInvalidas();
		}

		validarStatus(preCadastro);

		String token = jwtOnboardingService.gerarToken(preCadastro);

		LocalDateTime expiraEm = LocalDateTime.now().plusNanos(jwtOnboardingService.getExpirationMs() * 1_000_000);

		return new OnboardingLoginResponseDTO(token, "Bearer", TipoTokenJwt.ONBOARDING, preCadastro.getId(),
				preCadastro.getEmail(), preCadastro.getStatus(), expiraEm);
	}

	private void validarStatus(PreCadastroEmpresa preCadastro) {

		StatusOnboarding status = preCadastro.getStatus();

		if (status == StatusOnboarding.DADOS_EMPRESA_PENDENTES || status == StatusOnboarding.PRONTO_PARA_FINALIZAR) {

			return;
		}

		if (status == StatusOnboarding.EMAIL_PENDENTE) {

			throw new ExceptionCustom("Confirme seu e-mail antes " + "de continuar o cadastro.");
		}

		if (status == StatusOnboarding.CONCLUIDO) {
			throw new ExceptionCustom("Este onboarding já foi concluído. " + "Utilize o login normal.");
		}

		if (status == StatusOnboarding.EXPIRADO) {
			throw new ExceptionCustom("Este pré-cadastro expirou.");
		}

		if (status == StatusOnboarding.CANCELADO) {
			throw new ExceptionCustom("Este pré-cadastro foi cancelado.");
		}

		throw new ExceptionCustom("O cadastro não está disponível " + "para autenticação.");
	}

	private ExceptionCustom credenciaisInvalidas() {
		return new ExceptionCustom("E-mail ou senha inválidos.");
	}

	private String normalizarEmail(String email) {
		return email.trim().toLowerCase();
	}
}