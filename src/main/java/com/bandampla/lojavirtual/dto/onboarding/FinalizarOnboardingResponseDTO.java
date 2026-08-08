package com.bandampla.lojavirtual.dto.onboarding;

import java.io.Serializable;

import com.bandampla.lojavirtual.enums.StatusOnboarding;
import com.bandampla.lojavirtual.enums.TipoTokenJwt;

public class FinalizarOnboardingResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String token;
	private String tokenType;
	private TipoTokenJwt tipoToken;

	private Long empresaId;
	private Long pessoaId;
	private Long usuarioId;

	private String login;
	private StatusOnboarding status;
	private String mensagem;

	public FinalizarOnboardingResponseDTO() {
	}

	public FinalizarOnboardingResponseDTO(String token, String tokenType, TipoTokenJwt tipoToken, Long empresaId,
			Long pessoaId, Long usuarioId, String login, StatusOnboarding status, String mensagem) {

		this.token = token;
		this.tokenType = tokenType;
		this.tipoToken = tipoToken;
		this.empresaId = empresaId;
		this.pessoaId = pessoaId;
		this.usuarioId = usuarioId;
		this.login = login;
		this.status = status;
		this.mensagem = mensagem;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public TipoTokenJwt getTipoToken() {
		return tipoToken;
	}

	public void setTipoToken(TipoTokenJwt tipoToken) {
		this.tipoToken = tipoToken;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Long getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public StatusOnboarding getStatus() {
		return status;
	}

	public void setStatus(StatusOnboarding status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}