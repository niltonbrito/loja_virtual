package com.bandampla.lojavirtual.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.enums.StatusConvite;

public class ColaboradorResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long conviteId;
	private String nome;
	private String email;
	private RoleUser perfil;
	private StatusConvite status;
	private LocalDateTime expiraEm;
	private String mensagem;

	public ColaboradorResponseDTO() {
	}

	public ColaboradorResponseDTO(Long conviteId, String nome, String email, RoleUser perfil, StatusConvite status,
			LocalDateTime expiraEm, String mensagem) {

		this.conviteId = conviteId;
		this.nome = nome;
		this.email = email;
		this.perfil = perfil;
		this.status = status;
		this.expiraEm = expiraEm;
		this.mensagem = mensagem;
	}

	public Long getConviteId() {
		return conviteId;
	}

	public void setConviteId(Long conviteId) {
		this.conviteId = conviteId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RoleUser getPerfil() {
		return perfil;
	}

	public void setPerfil(RoleUser perfil) {
		this.perfil = perfil;
	}

	public StatusConvite getStatus() {
		return status;
	}

	public void setStatus(StatusConvite status) {
		this.status = status;
	}

	public LocalDateTime getExpiraEm() {
		return expiraEm;
	}

	public void setExpiraEm(LocalDateTime expiraEm) {
		this.expiraEm = expiraEm;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}