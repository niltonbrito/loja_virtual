package com.bandampla.lojavirtual.dto.onboarding;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.bandampla.lojavirtual.enums.StatusOnboarding;

public class ConfirmarEmailOnboardingResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long preCadastroId;
	private String email;
	private StatusOnboarding status;
	private LocalDateTime emailConfirmadoEm;
	private String mensagem;

	public ConfirmarEmailOnboardingResponseDTO() {
	}

	public ConfirmarEmailOnboardingResponseDTO(Long preCadastroId, String email, StatusOnboarding status,
			LocalDateTime emailConfirmadoEm, String mensagem) {

		this.preCadastroId = preCadastroId;
		this.email = email;
		this.status = status;
		this.emailConfirmadoEm = emailConfirmadoEm;
		this.mensagem = mensagem;
	}

	public Long getPreCadastroId() {
		return preCadastroId;
	}

	public void setPreCadastroId(Long preCadastroId) {
		this.preCadastroId = preCadastroId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public StatusOnboarding getStatus() {
		return status;
	}

	public void setStatus(StatusOnboarding status) {
		this.status = status;
	}

	public LocalDateTime getEmailConfirmadoEm() {
		return emailConfirmadoEm;
	}

	public void setEmailConfirmadoEm(LocalDateTime emailConfirmadoEm) {

		this.emailConfirmadoEm = emailConfirmadoEm;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}