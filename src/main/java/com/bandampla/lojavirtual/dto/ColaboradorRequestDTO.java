package com.bandampla.lojavirtual.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bandampla.lojavirtual.enums.RoleUser;

public class ColaboradorRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Nome do colaborador é obrigatório.")
	@Size(min = 3, max = 150, message = "Nome deve possuir entre 3 e 150 caracteres.")
	private String nome;

	@NotBlank(message = "E-mail do colaborador é obrigatório.")
	@Email(message = "E-mail inválido.")
	@Size(max = 180, message = "E-mail deve possuir no máximo 180 caracteres.")
	private String email;

	@NotNull(message = "Perfil do colaborador é obrigatório.")
	private RoleUser perfil;

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
}