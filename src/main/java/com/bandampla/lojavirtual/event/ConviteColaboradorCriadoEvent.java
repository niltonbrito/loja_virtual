package com.bandampla.lojavirtual.event;

import java.time.LocalDateTime;

public class ConviteColaboradorCriadoEvent {

	private final String nome;
	private final String email;
	private final String nomeEmpresa;
	private final String token;
	private final LocalDateTime expiraEm;

	public ConviteColaboradorCriadoEvent(String nome, String email, String nomeEmpresa, String token,
			LocalDateTime expiraEm) {

		this.nome = nome;
		this.email = email;
		this.nomeEmpresa = nomeEmpresa;
		this.token = token;
		this.expiraEm = expiraEm;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public String getToken() {
		return token;
	}

	public LocalDateTime getExpiraEm() {
		return expiraEm;
	}
}