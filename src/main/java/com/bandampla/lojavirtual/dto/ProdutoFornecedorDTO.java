package com.bandampla.lojavirtual.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ProdutoFornecedorDTO {

	private Long id;

	@NotBlank(message = "O código do produto no fornecedor deve ser informado.")
	@JsonAlias("codigoProdutoNoFornecedor")
	private String codigoProdutoNoFornecedor;

	private Long empresaId;

	@NotNull(message = "O Produto deve ser informada")
	@Positive(message = "O ID do Produto deve ser maior que zero.")
	private Long produtoId;

	@NotNull(message = "O fornecedor do produto deve ser informado.")
	@Positive(message = "O ID do fornecedor deve ser maior que zero.")
	private Long fornecedorId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Long getFornecedorId() {
		return fornecedorId;
	}

	public void setFornecedorId(Long fornecedorId) {
		this.fornecedorId = fornecedorId;
	}

	public String getCodigoProdutoNoFornecedor() {
		return codigoProdutoNoFornecedor;
	}

	public void setCodigoProdutoNoFornecedor(String codigoProdutoNoFornecedor) {
		this.codigoProdutoNoFornecedor = codigoProdutoNoFornecedor;
	}
}