package com.bandampla.lojavirtual.enums;

public enum TipoTokenJwt {
	ACCESS("ACCESS"), ONBOARDING("ONBOARDING");

	private String descricao;

	private TipoTokenJwt(String descricao) {
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