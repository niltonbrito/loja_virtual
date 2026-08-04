package com.bandampla.lojavirtual.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class CupomDescontoDTO {

	private Long id;

	@NotBlank(message = "O codigo de descriçao do cupom de desconto deve ser informado.")
	private String codigoDescricao;

	@NotNull(message = "Informe o valor real do desconto.")
	@PositiveOrZero(message = "O valor real do desconto deve ser igual ou maior que zero.")
	private BigDecimal valorRealDesconto;

	@PositiveOrZero(message = "A porcentagem do desconto deve ser igual ou maior que zero.")
	private BigDecimal valorPorcentagemDesconto;

	@NotNull(message = "Informe a data de validade do cupom de desconto.")
	@FutureOrPresent(message = "A data do cupom de desconto deve ser hoje ou uma data futura.")
	private LocalDate dataValidade;
	
	private Integer limiteUsoTotal;
	private Integer quantidadeUsado;

	private List<Long> categoriasIds;
	private List<Long> marcasIds;
	private List<Long> produtosIds;

	private Long empresaId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigoDescricao() {
		return codigoDescricao;
	}

	public void setCodigoDescricao(String codigoDescricao) {
		this.codigoDescricao = codigoDescricao;
	}

	public BigDecimal getValorRealDesconto() {
		return valorRealDesconto;
	}

	public void setValorRealDesconto(BigDecimal valorRealDesconto) {
		this.valorRealDesconto = valorRealDesconto;
	}

	public BigDecimal getValorPorcentagemDesconto() {
		return valorPorcentagemDesconto;
	}

	public void setValorPorcentagemDesconto(BigDecimal valorPorcentagemDesconto) {
		this.valorPorcentagemDesconto = valorPorcentagemDesconto;
	}

	public LocalDate getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(LocalDate dataValidade) {
		this.dataValidade = dataValidade;
	}

	public Integer getLimiteUsoTotal() {
		return limiteUsoTotal;
	}

	public void setLimiteUsoTotal(Integer limiteUsoTotal) {
		this.limiteUsoTotal = limiteUsoTotal;
	}

	public Integer getQuantidadeUsado() {
		return quantidadeUsado;
	}

	public void setQuantidadeUsado(Integer quantidadeUsado) {
		this.quantidadeUsado = quantidadeUsado;
	}

	public List<Long> getCategoriasIds() {
		return categoriasIds;
	}

	public void setCategoriasIds(List<Long> categoriasIds) {
		this.categoriasIds = categoriasIds;
	}

	public List<Long> getMarcasIds() {
		return marcasIds;
	}

	public void setMarcasIds(List<Long> marcasIds) {
		this.marcasIds = marcasIds;
	}

	public List<Long> getProdutosIds() {
		return produtosIds;
	}

	public void setProdutosIds(List<Long> produtosIds) {
		this.produtosIds = produtosIds;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
}