package com.bandampla.lojavirtual.enums;

public enum RoleUser {

	ROLE_USER("ROLE_USER"), ROLE_FINANCEIRO("ROLE_FINANCEIRO"), ROLE_ESTOQUE("ROLE_ESTOQUE"), ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN");

	private String descricao;

	private RoleUser(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}
