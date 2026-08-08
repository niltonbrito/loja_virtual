package com.bandampla.lojavirtual.enums;

public enum StatusOnboarding {

	EMAIL_PENDENTE("EMAIL_PENDENTE"),

	DADOS_EMPRESA_PENDENTES("DADOS_EMPRESA_PENDENTES"),

	PRONTO_PARA_FINALIZAR("PRONTO_PARA_FINALIZAR"),

	CONCLUIDO("CONCLUIDO"),

	EXPIRADO("EXPIRADO"),

	CANCELADO("CANCELADO");

	private String descricao;

	private StatusOnboarding(String descricao) {
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