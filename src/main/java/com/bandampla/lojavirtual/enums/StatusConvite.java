package com.bandampla.lojavirtual.enums;

public enum StatusConvite {

	PENDENTE("PENDENTE"), ACEITO("ACEITO"), EXPIRADO("EXPIRADO"), CANCELADO("CANCELADO");

	private String descricao;

	private StatusConvite(String descricao) {
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