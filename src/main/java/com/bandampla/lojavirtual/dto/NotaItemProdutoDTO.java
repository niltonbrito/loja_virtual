/**
 * 
 */
package com.bandampla.lojavirtual.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de jun. de 2026
 */
public class NotaItemProdutoDTO {

	private Long id;

    @NotNull(message = "Informe a quantidade do produto.")
    @Positive(message = "A quantidade do produto deve ser maior que zero.")
    private BigDecimal quantidade;

    @NotNull(message = "Informe o valor unitário do produto.")
    @Positive(message = "O valor unitário do produto deve ser maior que zero.")
    private BigDecimal valorUnitarioCusto;

	@NotNull(message = "O numero da nota fiscal de compra deve ser informado.")
	@Positive(message = "O ID da nota fiscal de compra deve ser maior que zero.")
	private Long notaFiscalCompraId;

    @NotNull(message = "O Produto deve ser informado.")
    @Positive(message = "O ID do Produto deve ser maior que zero.")
    private Long produtoId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitarioCusto() {
		return valorUnitarioCusto;
	}

	public void setValorUnitarioCusto(BigDecimal valorUnitarioCusto) {
		this.valorUnitarioCusto = valorUnitarioCusto;
	}

	public Long getNotaFiscalCompraId() {
		return notaFiscalCompraId;
	}

	public void setNotaFiscalCompraId(Long notaFiscalCompraId) {
		this.notaFiscalCompraId = notaFiscalCompraId;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}
}
