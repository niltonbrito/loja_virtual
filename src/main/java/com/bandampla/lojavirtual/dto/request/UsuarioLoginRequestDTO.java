/**
 * 
 */
package com.bandampla.lojavirtual.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 18 de mai. de 2026
 */
public class UsuarioLoginRequestDTO {
	@NotBlank(message = "Login deve ser informado.")
	@Email(message = "E-mail inválido")
	private String login;

	@Size(min = 8, message = "A senha deve ter no minimo 8 digitos")
	@NotBlank(message = "Senha deve ser informada.")
	private String senha;

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
}
