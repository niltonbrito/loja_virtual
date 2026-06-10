package com.bandampla.lojavirtual.enums;

public enum TipoUnidadeMedida {

	KG("Kilo"), G("Grama"), T("Tonelada"), UN("Unidade"), PC("Peça"), CX("Caixa"), PCT("Pacote"), DZ("Duzia"),
	FD("Fardo"), L("Litro"), ML("Mililitro"), M("Metro"), CM("Centrimetro");

	private String descricao;

	private TipoUnidadeMedida(String descricao) {
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
