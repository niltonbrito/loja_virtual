package com.bandampla.lojavirtual.dto;

import javax.validation.constraints.NotNull;

import com.bandampla.lojavirtual.enums.RoleUser;

public class AcessoDTO {

	private Long id;

	@NotNull(message = "O papel de usuário (RoleUser) deve ser informado.")
	private RoleUser roleUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleUser getRoleUser() {
		return roleUser;
	}

	public void setRoleUser(RoleUser roleUser) {
		this.roleUser = roleUser;
	}
}