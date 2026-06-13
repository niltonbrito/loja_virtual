package com.bandampla.lojavirtual.enums;

public enum TipoCadastro {
	
    CLIENTE("CLIENTE"),
    FORNECEDOR("FORNECEDOR"),
    FUNCIONARIO("FUNCIONARIO"),
    TRANSPORTADORA("TRANSPORTADORA"),
    EMPRESA("EMPRESA")	;
	
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
