/**
 * 
 */
package com.bandampla.lojavirtual.enums;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 17 de jun. de 2026
 */
public enum StatusSolicitacaoCompra {
	ABERTA("ABERTA"), APROVADA("APROVADA"), REJEITADA("REJEITADA"), CANCELADA("CANCELADA");

	private String descricao;

	private StatusSolicitacaoCompra(String descricao) {
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