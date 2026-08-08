package com.bandampla.lojavirtual.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.dto.onboarding.ConfirmarEmailOnboardingRequestDTO;
import com.bandampla.lojavirtual.dto.onboarding.ConfirmarEmailOnboardingResponseDTO;
import com.bandampla.lojavirtual.enums.StatusOnboarding;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.PreCadastroEmpresa;
import com.bandampla.lojavirtual.repository.PreCadastroEmpresaRepository;

@Service
public class ConfirmacaoEmailOnboardingService {

	private final PreCadastroEmpresaRepository preCadastroEmpresaRepository;

	private final TokenSeguroService tokenSeguroService;

	public ConfirmacaoEmailOnboardingService(PreCadastroEmpresaRepository preCadastroEmpresaRepository,
			TokenSeguroService tokenSeguroService) {

		this.preCadastroEmpresaRepository = preCadastroEmpresaRepository;

		this.tokenSeguroService = tokenSeguroService;
	}

	@Transactional(rollbackFor = Exception.class)
	public ConfirmarEmailOnboardingResponseDTO confirmar(ConfirmarEmailOnboardingRequestDTO dto) {

		String token = normalizarToken(dto.getToken());
		String tokenHash = tokenSeguroService.gerarHash(token);

		PreCadastroEmpresa preCadastro = preCadastroEmpresaRepository.findByTokenConfirmacaoHash(tokenHash)
				.orElseThrow(() -> new ExceptionCustom("Token de confirmação inválido."));

		validarStatus(preCadastro);
		LocalDateTime agora = LocalDateTime.now();

		validarExpiracao(preCadastro, agora);

		preCadastro.setStatus(StatusOnboarding.DADOS_EMPRESA_PENDENTES);
		preCadastro.setEmailConfirmadoEm(agora);
		preCadastro.setUpdatedAt(agora);

		/*
		 * O token é de uso único.
		 */
		preCadastro.setTokenConfirmacaoHash(null);
		preCadastro.setTokenExpiraEm(null);

		PreCadastroEmpresa salvo = preCadastroEmpresaRepository.save(preCadastro);

		return new ConfirmarEmailOnboardingResponseDTO(salvo.getId(), salvo.getEmail(), salvo.getStatus(),
				salvo.getEmailConfirmadoEm(),
				"E-mail confirmado. Continue o cadastro " + "informando os dados da empresa.");
	}

	private void validarStatus(PreCadastroEmpresa preCadastro) {

		if (preCadastro.getStatus() == StatusOnboarding.EMAIL_PENDENTE) {

			return;
		}

		if (preCadastro.getStatus() == StatusOnboarding.DADOS_EMPRESA_PENDENTES
				|| preCadastro.getStatus() == StatusOnboarding.PRONTO_PARA_FINALIZAR) {
			throw new ExceptionCustom("Este e-mail já foi confirmado.");
		}

		if (preCadastro.getStatus() == StatusOnboarding.CONCLUIDO) {
			throw new ExceptionCustom("Este cadastro já foi concluído.");
		}

		if (preCadastro.getStatus() == StatusOnboarding.EXPIRADO) {
			throw new ExceptionCustom("Este token está expirado. " + "Inicie o cadastro novamente.");
		}

		if (preCadastro.getStatus() == StatusOnboarding.CANCELADO) {
			throw new ExceptionCustom("Este cadastro foi cancelado.");
		}

		throw new ExceptionCustom("Status de onboarding inválido " + "para confirmação de e-mail.");
	}

	private void validarExpiracao(PreCadastroEmpresa preCadastro, LocalDateTime agora) {

		if (preCadastro.getTokenExpiraEm() == null) {
			throw new ExceptionCustom("O token não possui data de expiração.");
		}

		boolean expirado = !preCadastro.getTokenExpiraEm().isAfter(agora);

		if (!expirado) {
			return;
		}

		preCadastroEmpresaRepository.marcarTokenComoExpirado(preCadastro.getId(), StatusOnboarding.EXPIRADO, agora);

		throw new ExceptionCustom("O token de confirmação expirou. " + "Inicie o cadastro novamente.");
	}

	private String normalizarToken(String token) {

		if (token == null || token.trim().isEmpty()) {
			throw new ExceptionCustom("Token de confirmação não informado.");
		}

		return token.trim();
	}
}