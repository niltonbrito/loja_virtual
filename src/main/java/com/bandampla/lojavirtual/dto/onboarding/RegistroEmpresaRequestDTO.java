package com.bandampla.lojavirtual.dto.onboarding;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegistroEmpresaRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Nome do responsável é obrigatório.")
	@Size(min = 3, max = 150, message = "Nome do responsável deve possuir entre 3 e 150 caracteres.")
	private String nomeResponsavel;

	@NotBlank(message = "E-mail é obrigatório.")
	@Email(message = "E-mail inválido.")
	@Size(max = 180, message = "E-mail deve possuir no máximo 180 caracteres.")
	private String email;

	@NotBlank(message = "Senha é obrigatória.")
	@Size(min = 8, max = 72, message = "Senha deve possuir entre 8 e 72 caracteres.")
	private String senha;

	@NotBlank(message = "Confirmação da senha é obrigatória.")
	private String confirmacaoSenha;

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {

		this.confirmacaoSenha = confirmacaoSenha;
	}
}