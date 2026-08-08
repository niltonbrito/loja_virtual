package com.bandampla.lojavirtual.dto.onboarding;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.bandampla.lojavirtual.enums.StatusOnboarding;

public class RegistroEmpresaResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long preCadastroId;

	private String email;

	private StatusOnboarding status;

	private LocalDateTime tokenExpiraEm;

	private String mensagem;

	public RegistroEmpresaResponseDTO() {
	}

	public RegistroEmpresaResponseDTO(Long preCadastroId, String email, StatusOnboarding status,
			LocalDateTime tokenExpiraEm, String mensagem) {

		this.preCadastroId = preCadastroId;
		this.email = email;
		this.status = status;
		this.tokenExpiraEm = tokenExpiraEm;
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

	public LocalDateTime getTokenExpiraEm() {
		return tokenExpiraEm;
	}

	public void setTokenExpiraEm(LocalDateTime tokenExpiraEm) {

		this.tokenExpiraEm = tokenExpiraEm;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}