/**
 * 
 */
package com.bandampla.lojavirtual.dto.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   18 de mai. de 2026
 */
public class UsuarioRequestDTO {

    @NotBlank(message = "Login deve ser informado.")
    @Email(message = "E-mail inválido")
    private String login;

    @Size(min = 8, message = "A senha deve ter no minimo 8 digitos")
    @NotBlank(message = "Senha deve ser informada.")
    private String senha;

    @NotNull(message = "Pessoa deve ser informada.")
    private Long pessoaId;

    @NotNull(message = "Empresa deve ser informada.")
    private Long empresaId;

    private List<String> acessos;

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

	public List<String> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<String> acessos) {
		this.acessos = acessos;
	}
}

