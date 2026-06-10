package com.bandampla.lojavirtual.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsuarioRequestDTO {

	@NotBlank(message = "O e-mail de login deve ser informado.")
	@Email(message = "Formato de e-mail inválido.")
	private String login;

	@NotBlank(message = "A senha deve ser informada.")
	@Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
	private String senha;

	@NotNull(message = "O ID da pessoa vinculada deve ser informado.")
	private Long pessoaId;

	@NotNull(message = "O ID da empresa vinculada deve ser informado.")
	private Long empresaId;
		
	// Getters e Setters comuns do Java 11
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
}