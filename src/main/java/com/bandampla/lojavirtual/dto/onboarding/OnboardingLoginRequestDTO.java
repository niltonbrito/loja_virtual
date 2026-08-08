package com.bandampla.lojavirtual.dto.onboarding;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class OnboardingLoginRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "E-mail é obrigatório.")
	@Email(message = "E-mail inválido.")
	@Size(max = 180, message = "E-mail deve possuir no máximo 180 caracteres.")
	private String email;

	@NotBlank(message = "Senha é obrigatória.")
	@Size(min = 8, max = 72, message = "Senha deve possuir entre 8 e 72 caracteres.")
	private String senha;

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
}