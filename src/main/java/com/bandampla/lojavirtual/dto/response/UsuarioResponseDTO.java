/**
 * 
 */
package com.bandampla.lojavirtual.dto.response;

import java.util.Date;
import java.util.List;

import com.bandampla.lojavirtual.enums.RoleUser;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   18 de mai. de 2026
 */
public class UsuarioResponseDTO {

    private Long id;
    private String login;
    private Date createAt;
    private Date updateAt;

    private String nomePessoa;
    private String tipoPessoa;

    private Long empresaId;
    private String empresaNomeFantasia;

    private List<RoleUser> acessos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public String getEmpresaNomeFantasia() {
		return empresaNomeFantasia;
	}

	public void setEmpresaNomeFantasia(String empresaNomeFantasia) {
		this.empresaNomeFantasia = empresaNomeFantasia;
	}

	public List<RoleUser> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<RoleUser> acessos) {
		this.acessos = acessos;
	}
}
