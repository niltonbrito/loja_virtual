package com.bandampla.lojavirtual.event;

import java.time.LocalDateTime;

public class EmailConfirmacaoOnboardingEvent {

	private final String nomeResponsavel;
	private final String email;
	private final String token;
	private final LocalDateTime expiraEm;

	public EmailConfirmacaoOnboardingEvent(String nomeResponsavel, String email, String token, LocalDateTime expiraEm) {

		this.nomeResponsavel = nomeResponsavel;
		this.email = email;
		this.token = token;
		this.expiraEm = expiraEm;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public String getEmail() {
		return email;
	}

	public String getToken() {
		return token;
	}

	public LocalDateTime getExpiraEm() {
		return expiraEm;
	}
}