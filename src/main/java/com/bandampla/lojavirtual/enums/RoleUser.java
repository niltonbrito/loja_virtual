package com.bandampla.lojavirtual.enums;

public enum RoleUser {

	ROLE_USER("USER"), ROLE_FINANCEIRO("FINANCEIRO"), ROLE_ESTOQUE("ESTOQUE"), ROLE_GERENTE("GERENTE"), ROLE_ADMIN("ADMIN"),
	ROLE_SUPER_ADMIN("SUPER_ADMIN");

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
