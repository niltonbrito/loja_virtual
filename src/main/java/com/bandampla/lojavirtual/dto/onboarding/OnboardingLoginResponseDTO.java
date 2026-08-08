package com.bandampla.lojavirtual.dto.onboarding;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.bandampla.lojavirtual.enums.StatusOnboarding;
import com.bandampla.lojavirtual.enums.TipoTokenJwt;

public class OnboardingLoginResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String token;
	private String tokenType;
	private TipoTokenJwt tipoToken;
	private Long onboardingId;
	private String email;
	private StatusOnboarding status;
	private LocalDateTime expiraEm;

	public OnboardingLoginResponseDTO() {
	}

	public OnboardingLoginResponseDTO(String token, String tokenType, TipoTokenJwt tipoToken, Long onboardingId,
			String email, StatusOnboarding status, LocalDateTime expiraEm) {

		this.token = token;
		this.tokenType = tokenType;
		this.tipoToken = tipoToken;
		this.onboardingId = onboardingId;
		this.email = email;
		this.status = status;
		this.expiraEm = expiraEm;
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

	public Long getOnboardingId() {
		return onboardingId;
	}

	public void setOnboardingId(Long onboardingId) {
		this.onboardingId = onboardingId;
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

	public LocalDateTime getExpiraEm() {
		return expiraEm;
	}

	public void setExpiraEm(LocalDateTime expiraEm) {
		this.expiraEm = expiraEm;
	}
}