package com.bandampla.lojavirtual.enums;

public enum StatusContaPagar {

	COBRANCA("Pagar"), VENCIDA("Vencida"), ABERTA("Aberta"), QUITADA("Quitada"), RENEGOCIADA("Renegociada"), ALUGUEL("Aluguel"), FUNCIONARIO("Funcionário"), DESPESA("Despesa");

	private String descricao;

	private StatusContaPagar(String descricao) {
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
