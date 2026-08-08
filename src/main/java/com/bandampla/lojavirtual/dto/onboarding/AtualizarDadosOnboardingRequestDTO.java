package com.bandampla.lojavirtual.dto.onboarding;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AtualizarDadosOnboardingRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Valid
	@NotNull(message = "Dados do responsável são obrigatórios.")
	private DadosResponsavelOnboardingRequestDTO responsavel;

	@Valid
	@NotNull(message = "Dados da empresa são obrigatórios.")
	private DadosEmpresaOnboardingRequestDTO empresa;

	public DadosResponsavelOnboardingRequestDTO getResponsavel() {

		return responsavel;
	}

	public void setResponsavel(DadosResponsavelOnboardingRequestDTO responsavel) {

		this.responsavel = responsavel;
	}

	public DadosEmpresaOnboardingRequestDTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(DadosEmpresaOnboardingRequestDTO empresa) {

		this.empresa = empresa;
	}
}