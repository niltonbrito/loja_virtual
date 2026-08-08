package com.bandampla.lojavirtual.dto.onboarding;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

public class DadosEmpresaOnboardingRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Razão social é obrigatória.")
	@Size(min = 3, max = 200, message = "Razão social deve possuir entre 3 e 200 caracteres.")
	private String razaoSocial;

	@NotBlank(message = "Nome fantasia é obrigatório.")
	@Size(min = 2, max = 200, message = "Nome fantasia deve possuir entre 2 e 200 caracteres.")
	private String nomeFantasia;

	@NotBlank(message = "CNPJ é obrigatório.")
	@CNPJ(message = "CNPJ inválido.")
	private String cnpj;

	@Size(max = 30, message = "Inscrição estadual deve possuir no máximo 30 caracteres.")
	private String inscricaoEstadual;

	@NotBlank(message = "Telefone da empresa é obrigatório.")
	@Size(min = 10, max = 20, message = "Telefone da empresa inválido.")
	private String telefone;

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {

		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}