package com.bandampla.lojavirtual.enums;

public enum StatusPreCadastroCliente {

	EMAIL_PENDENTE("EMAIL_PENDENTE"),

	CONFIRMADO("CONFIRMADO"),

	CONCLUIDO("CONCLUIDO"),

	EXPIRADO("EXPIRADO"),

	CANCELADO("CANCELADO");

	private String descricao;

	private StatusPreCadastroCliente(String descricao) {
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