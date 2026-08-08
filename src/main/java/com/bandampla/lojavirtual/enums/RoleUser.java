package com.bandampla.lojavirtual.enums;

public enum RoleUser {

	ROLE_USER("Usuário"), ROLE_CLIENTE("Cliente"), ROLE_FINANCEIRO("Financeiro"), ROLE_ESTOQUE("Estoque"),
	ROLE_GERENTE("Gerente"), ROLE_ADMIN("Administrador"), ROLE_SUPER_ADMIN("Superadministrador");

	private final String descricao;

	RoleUser(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}
}