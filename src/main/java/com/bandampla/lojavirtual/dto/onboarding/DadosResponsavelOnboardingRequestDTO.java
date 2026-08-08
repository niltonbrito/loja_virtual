package com.bandampla.lojavirtual.dto.onboarding;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

public class DadosResponsavelOnboardingRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Nome do responsável é obrigatório.")
	@Size(min = 3, max = 150, message = "Nome do responsável deve possuir entre 3 e 150 caracteres.")
	private String nome;

	@NotBlank(message = "CPF do responsável é obrigatório.")
	@CPF(message = "CPF do responsável inválido.")
	private String cpf;

	@NotBlank(message = "Telefone do responsável é obrigatório.")
	@Size(min = 10, max = 20, message = "Telefone do responsável inválido.")
	private String telefone;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}