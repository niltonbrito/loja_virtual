package com.bandampla.lojavirtual.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ItemVendaLojaDTO {

	private Long id;

	@NotNull(message = "Informe a quantidade do produto.")
	@Positive(message = "A quantidade do produto deve ser maior que zero.")
	private Double quantidade;

	@NotNull(message = "O ID do produto deve ser informado.")
	@Positive(message = "O ID do produto deve ser um número positivo válido.")
	private Long produtoId;

	@NotNull(message = "O ID da venda correspondente deve ser informado.")
	@Positive(message = "O ID da venda deve ser um número positivo válido.")
	private Long vendaLojaVirtualId;

	private Long empresaId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public Long getVendaLojaVirtualId() {
		return vendaLojaVirtualId;
	}

	public void setVendaLojaVirtualId(Long vendaLojaVirtualId) {
		this.vendaLojaVirtualId = vendaLojaVirtualId;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
}