package com.bandampla.lojavirtual.enums;

public enum TipoPessoa {

	FISICA("FISICA"),
	JURIDICA("JURIDICA"),
	FORNECEDOR("FORNECEDOR");
	
	private String descricao;
	
	private TipoPessoa(String descricao) {
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
