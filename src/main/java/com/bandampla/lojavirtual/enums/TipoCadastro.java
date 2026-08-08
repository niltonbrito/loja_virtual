package com.bandampla.lojavirtual.enums;

public enum TipoCadastro {

	ADMIN_PLATAFORMA("ADMIN_PLATAFORMA"), COLABORADOR("COLABORADOR"), CLIENTE("CLIENTE"), ADMIN_EMPRESA("ADMIN_EMPRESA"), EMPRESA("EMPRESA"),
	FORNECEDOR("FORNECEDOR"), FUNCIONARIO("FUNCIONARIO"), TRANSPORTADORA("TRANSPORTADORA"),;

	private String descricao;

	private TipoCadastro(String descricao) {
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
