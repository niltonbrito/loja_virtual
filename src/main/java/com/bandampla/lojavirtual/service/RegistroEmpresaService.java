package com.bandampla.lojavirtual.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.config.OnboardingProperties;
import com.bandampla.lojavirtual.dto.onboarding.RegistroEmpresaRequestDTO;
import com.bandampla.lojavirtual.dto.onboarding.RegistroEmpresaResponseDTO;
import com.bandampla.lojavirtual.enums.StatusOnboarding;
import com.bandampla.lojavirtual.event.EmailConfirmacaoOnboardingEvent;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.PreCadastroEmpresa;
import com.bandampla.lojavirtual.repository.PreCadastroEmpresaRepository;
import com.bandampla.lojavirtual.repository.UsuarioRepository;

@Service
public class RegistroEmpresaService {

	private final PreCadastroEmpresaRepository preCadastroEmpresaRepository;

	private final UsuarioRepository usuarioRepository;

	private final PasswordEncoder passwordEncoder;

	private final TokenSeguroService tokenSeguroService;

	private final OnboardingProperties onboardingProperties;

	private final ApplicationEventPublisher eventPublisher;

	public RegistroEmpresaService(PreCadastroEmpresaRepository preCadastroEmpresaRepository,
			UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, TokenSeguroService tokenSeguroService,
			OnboardingProperties onboardingProperties, ApplicationEventPublisher eventPublisher) {

		this.preCadastroEmpresaRepository = preCadastroEmpresaRepository;

		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenSeguroService = tokenSeguroService;
		this.onboardingProperties = onboardingProperties;
		this.eventPublisher = eventPublisher;
	}

	@Transactional(rollbackFor = Exception.class)
	public RegistroEmpresaResponseDTO registrar(RegistroEmpresaRequestDTO dto) {

		validarSenhas(dto);

		String email = normalizarEmail(dto.getEmail());

		validarUsuarioExistente(email);

		LocalDateTime agora = LocalDateTime.now();

		PreCadastroEmpresa preCadastro = obterPreCadastroDisponivel(email, agora);

		String tokenPuro = tokenSeguroService.gerarToken();

		String tokenHash = tokenSeguroService.gerarHash(tokenPuro);

		LocalDateTime tokenExpiraEm = agora.plusMinutes(onboardingProperties.getEmailTokenExpirationMinutes());

		prepararPreCadastro(preCadastro, dto, email, tokenHash, tokenExpiraEm, agora);

		PreCadastroEmpresa salvo = preCadastroEmpresaRepository.save(preCadastro);

		eventPublisher.publishEvent(new EmailConfirmacaoOnboardingEvent(salvo.getNomeResponsavel(), salvo.getEmail(),
				tokenPuro, tokenExpiraEm));

		return new RegistroEmpresaResponseDTO(salvo.getId(), salvo.getEmail(), salvo.getStatus(),
				salvo.getTokenExpiraEm(), "Pré-cadastro realizado. " + "Verifique seu e-mail para continuar.");
	}

	private void validarSenhas(RegistroEmpresaRequestDTO dto) {

		if (!dto.getSenha().equals(dto.getConfirmacaoSenha())) {

			throw new ExceptionCustom("Senha e confirmação de senha " + "não correspondem.");
		}

		String senha = dto.getSenha();

		boolean possuiMaiuscula = senha.matches(".*[A-Z].*");

		boolean possuiMinuscula = senha.matches(".*[a-z].*");

		boolean possuiNumero = senha.matches(".*\\d.*");

		if (!possuiMaiuscula || !possuiMinuscula || !possuiNumero) {

			throw new ExceptionCustom("A senha deve possuir letra maiúscula, " + "letra minúscula e número.");
		}
	}

	private void validarUsuarioExistente(String email) {

		if (usuarioRepository.existsByLoginIgnoreCase(email)) {

			throw new ExceptionCustom("Já existe uma conta cadastrada " + "com este e-mail.");
		}
	}

	private PreCadastroEmpresa obterPreCadastroDisponivel(String email, LocalDateTime agora) {

		Optional<PreCadastroEmpresa> existente = preCadastroEmpresaRepository.findByEmailIgnoreCase(email);

		if (!existente.isPresent()) {
			return new PreCadastroEmpresa();
		}

		PreCadastroEmpresa preCadastro = existente.get();

		StatusOnboarding status = preCadastro.getStatus();

		if (status == StatusOnboarding.CONCLUIDO) {
			throw new ExceptionCustom("Este cadastro já foi concluído.");
		}

		if (status == StatusOnboarding.DADOS_EMPRESA_PENDENTES || status == StatusOnboarding.PRONTO_PARA_FINALIZAR) {

			throw new ExceptionCustom("O e-mail já foi confirmado. " + "Continue pelo login de onboarding.");
		}

		if (status == StatusOnboarding.EMAIL_PENDENTE && preCadastro.getTokenExpiraEm() != null
				&& preCadastro.getTokenExpiraEm().isAfter(agora)) {

			throw new ExceptionCustom("Já existe um pré-cadastro pendente " + "para este e-mail.");
		}

		/*
		 * Permite reiniciar um cadastro expirado, cancelado ou com token já vencido.
		 */
		limparDadosAnteriores(preCadastro);

		return preCadastro;
	}

	private void prepararPreCadastro(PreCadastroEmpresa preCadastro, RegistroEmpresaRequestDTO dto, String email,
			String tokenHash, LocalDateTime tokenExpiraEm, LocalDateTime agora) {

		preCadastro.setNomeResponsavel(dto.getNomeResponsavel().trim());

		preCadastro.setEmail(email);

		preCadastro.setSenhaHash(passwordEncoder.encode(dto.getSenha()));

		preCadastro.setStatus(StatusOnboarding.EMAIL_PENDENTE);

		preCadastro.setTokenConfirmacaoHash(tokenHash);

		preCadastro.setTokenExpiraEm(tokenExpiraEm);

		if (preCadastro.getCreatedAt() == null) {
			preCadastro.setCreatedAt(agora);
		}

		preCadastro.setUpdatedAt(agora);
	}

	private void limparDadosAnteriores(PreCadastroEmpresa preCadastro) {

		preCadastro.setCpfResponsavel(null);
		preCadastro.setTelefoneResponsavel(null);
		preCadastro.setRazaoSocial(null);
		preCadastro.setNomeFantasia(null);
		preCadastro.setCnpj(null);
		preCadastro.setInscricaoEstadual(null);
		preCadastro.setTelefoneEmpresa(null);
		preCadastro.setEmailConfirmadoEm(null);
		preCadastro.setConcluidoEm(null);
		preCadastro.setTokenConfirmacaoHash(null);
		preCadastro.setTokenExpiraEm(null);
	}

	private String normalizarEmail(String email) {
		return email.trim().toLowerCase();
	}
}