package com.bandampla.lojavirtual.enums;

public enum TipoFormaPagamento {

	PIX("Pix"), AVISTA("À Vista"), CARTAODEBITO("Cartão de Débito"), CARTAOCREDITO("Cartão de Credito"), BOLETO("Boleto"), BOLETO15("Boleto 15 dias"), BOLETO30("Boleto 30 Dias");

	private String descricao;

	private TipoFormaPagamento(String descricao) {
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
