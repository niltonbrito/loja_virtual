package com.bandampla.lojavirtual.dto.onboarding;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class ConfirmarEmailOnboardingRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Token de confirmação é obrigatório.")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}