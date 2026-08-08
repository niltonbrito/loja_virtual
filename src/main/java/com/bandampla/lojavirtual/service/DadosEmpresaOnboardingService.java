package com.bandampla.lojavirtual.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.dto.onboarding.AtualizarDadosOnboardingRequestDTO;
import com.bandampla.lojavirtual.dto.onboarding.AtualizarDadosOnboardingResponseDTO;
import com.bandampla.lojavirtual.enums.StatusOnboarding;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.PreCadastroEmpresa;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.PreCadastroEmpresaRepository;
import com.bandampla.lojavirtual.security.OnboardingPrincipal;

@Service
public class DadosEmpresaOnboardingService {

	private final PreCadastroEmpresaRepository preCadastroEmpresaRepository;

	private final PessoaFisicaRepository pessoaFisicaRepository;

	private final PessoaJuridicaRepository pessoaJuridicaRepository;

	public DadosEmpresaOnboardingService(PreCadastroEmpresaRepository preCadastroEmpresaRepository,
			PessoaFisicaRepository pessoaFisicaRepository, PessoaJuridicaRepository pessoaJuridicaRepository) {

		this.preCadastroEmpresaRepository = preCadastroEmpresaRepository;

		this.pessoaFisicaRepository = pessoaFisicaRepository;

		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
	}

	@Transactional(rollbackFor = Exception.class)
	public AtualizarDadosOnboardingResponseDTO atualizar(OnboardingPrincipal principal,
			AtualizarDadosOnboardingRequestDTO dto) {

		validarPrincipal(principal);

		PreCadastroEmpresa preCadastro = preCadastroEmpresaRepository.findById(principal.getOnboardingId())
				.orElseThrow(() -> new ExceptionCustom("Pré-cadastro não encontrado."));

		validarIdentidadeDoToken(preCadastro, principal);

		validarStatus(preCadastro);

		String cpf = somenteNumeros(dto.getResponsavel().getCpf());

		String cnpj = somenteNumeros(dto.getEmpresa().getCnpj());

		String telefoneResponsavel = somenteNumeros(dto.getResponsavel().getTelefone());

		String telefoneEmpresa = somenteNumeros(dto.getEmpresa().getTelefone());

		validarDocumentos(preCadastro.getId(), cpf, cnpj);

		LocalDateTime agora = LocalDateTime.now();

		preCadastro.setNomeResponsavel(dto.getResponsavel().getNome().trim());

		preCadastro.setCpfResponsavel(cpf);

		preCadastro.setTelefoneResponsavel(telefoneResponsavel);

		preCadastro.setRazaoSocial(dto.getEmpresa().getRazaoSocial().trim());

		preCadastro.setNomeFantasia(dto.getEmpresa().getNomeFantasia().trim());

		preCadastro.setCnpj(cnpj);

		preCadastro.setInscricaoEstadual(normalizarTextoOpcional(dto.getEmpresa().getInscricaoEstadual()));

		preCadastro.setTelefoneEmpresa(telefoneEmpresa);

		preCadastro.setStatus(StatusOnboarding.PRONTO_PARA_FINALIZAR);

		preCadastro.setUpdatedAt(agora);

		PreCadastroEmpresa salvo = preCadastroEmpresaRepository.save(preCadastro);

		return new AtualizarDadosOnboardingResponseDTO(salvo.getId(), salvo.getEmail(), salvo.getNomeResponsavel(),
				salvo.getRazaoSocial(), salvo.getNomeFantasia(), salvo.getStatus(), salvo.getUpdatedAt(),
				"Dados salvos. O onboarding está pronto " + "para ser finalizado.");
	}

	private void validarPrincipal(OnboardingPrincipal principal) {

		if (principal == null) {
			throw new ExceptionCustom("Token de onboarding não informado.");
		}

		if (principal.getOnboardingId() == null) {
			throw new ExceptionCustom("Token não possui o identificador " + "do onboarding.");
		}

		if (principal.getEmail() == null || principal.getEmail().trim().isEmpty()) {

			throw new ExceptionCustom("Token não possui o e-mail " + "do onboarding.");
		}
	}

	private void validarIdentidadeDoToken(PreCadastroEmpresa preCadastro, OnboardingPrincipal principal) {

		if (!preCadastro.getEmail().equalsIgnoreCase(principal.getEmail())) {

			throw new ExceptionCustom("O token não pertence a este " + "pré-cadastro.");
		}
	}

	private void validarStatus(PreCadastroEmpresa preCadastro) {

		StatusOnboarding status = preCadastro.getStatus();

		if (status == StatusOnboarding.DADOS_EMPRESA_PENDENTES || status == StatusOnboarding.PRONTO_PARA_FINALIZAR) {

			return;
		}

		if (status == StatusOnboarding.EMAIL_PENDENTE) {
			throw new ExceptionCustom("Confirme o e-mail antes de informar " + "os dados da empresa.");
		}

		if (status == StatusOnboarding.CONCLUIDO) {
			throw new ExceptionCustom("Este onboarding já foi concluído.");
		}

		if (status == StatusOnboarding.EXPIRADO) {
			throw new ExceptionCustom("Este pré-cadastro está expirado.");
		}

		if (status == StatusOnboarding.CANCELADO) {
			throw new ExceptionCustom("Este pré-cadastro foi cancelado.");
		}

		throw new ExceptionCustom("Status do onboarding inválido.");
	}

	private void validarDocumentos(Long preCadastroId, String cpf, String cnpj) {

		if (cpf.length() != 11) {
			throw new ExceptionCustom("CPF deve possuir 11 dígitos.");
		}

		if (cnpj.length() != 14) {
			throw new ExceptionCustom("CNPJ deve possuir 14 dígitos.");
		}

		boolean cpfOutroPreCadastro = preCadastroEmpresaRepository.existsByCpfResponsavelAndIdNot(cpf, preCadastroId);

		if (cpfOutroPreCadastro) {
			throw new ExceptionCustom("CPF já utilizado em outro " + "pré-cadastro.");
		}

		boolean cnpjOutroPreCadastro = preCadastroEmpresaRepository.existsByCnpjAndIdNot(cnpj, preCadastroId);

		if (cnpjOutroPreCadastro) {
			throw new ExceptionCustom("CNPJ já utilizado em outro " + "pré-cadastro.");
		}

		if (pessoaFisicaRepository.existsByCpf(cpf)) {
			throw new ExceptionCustom("CPF já cadastrado no sistema.");
		}

		if (pessoaJuridicaRepository.existsByCnpj(cnpj)) {
			throw new ExceptionCustom("CNPJ já cadastrado no sistema.");
		}
	}

	private String somenteNumeros(String valor) {

		if (valor == null) {
			return "";
		}

		return valor.replaceAll("\\D", "");
	}

	private String normalizarTextoOpcional(String valor) {

		if (valor == null) {
			return null;
		}

		String normalizado = valor.trim();

		return normalizado.isEmpty() ? null : normalizado;
	}
}