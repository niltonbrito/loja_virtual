/**
 * 
 */
package com.bandampla.lojavirtual.enums;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 17 de jun. de 2026
 */
public enum StatusPedidoCompra {
	AGUARDANDO_ENTREGA("AGUARDANDO_ENTREGA"), PARCIALMENTE_ENTREGUE("PARCIALMENTE_ENTREGUE"), CONCLUIDO("CONCLUIDO"),
	CANCELADO("CANCELADO");

	private String descricao;

	private StatusPedidoCompra(String descricao) {
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