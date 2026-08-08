package com.bandampla.lojavirtual.dto.onboarding;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.bandampla.lojavirtual.enums.StatusOnboarding;

public class AtualizarDadosOnboardingResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long preCadastroId;
	private String email;
	private String nomeResponsavel;
	private String razaoSocial;
	private String nomeFantasia;
	private StatusOnboarding status;
	private LocalDateTime atualizadoEm;
	private String mensagem;

	public AtualizarDadosOnboardingResponseDTO() {
	}

	public AtualizarDadosOnboardingResponseDTO(Long preCadastroId, String email, String nomeResponsavel,
			String razaoSocial, String nomeFantasia, StatusOnboarding status, LocalDateTime atualizadoEm,
			String mensagem) {

		this.preCadastroId = preCadastroId;
		this.email = email;
		this.nomeResponsavel = nomeResponsavel;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.status = status;
		this.atualizadoEm = atualizadoEm;
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

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

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

	public StatusOnboarding getStatus() {
		return status;
	}

	public void setStatus(StatusOnboarding status) {
		this.status = status;
	}

	public LocalDateTime getAtualizadoEm() {
		return atualizadoEm;
	}

	public void setAtualizadoEm(LocalDateTime atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}