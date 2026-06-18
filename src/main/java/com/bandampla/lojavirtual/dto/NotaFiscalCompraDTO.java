package com.bandampla.lojavirtual.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.bandampla.lojavirtual.model.NotaItemProduto;

public class NotaFiscalCompraDTO {

	private Long id;

	@NotBlank(message = "O número da Nota Fiscal deve ser informado.")
	private String numeroNota;

	@NotBlank(message = "A série da Nota Fiscal deve ser informada.")
	private String serieNota;

	private String descricaoObservacao;

	@NotNull(message = "Informe o valor total da Nota Fiscal.")
	@Positive(message = "O valor total deve ser maior que zero.")
	private BigDecimal valorTotal;

	private BigDecimal valorDesconto;

	@NotNull(message = "Informe o valor do ICMS da Nota Fiscal.")
	@Positive(message = "O valor do ICMS deve ser maior que zero.")
	private BigDecimal valorIcms;

	@NotNull(message = "Informe a data da compra da Nota Fiscal.")
	private Date dataCompra;

	private List<NotaItemProduto> itens;
	
	@NotNull(message = "O ID da pessoa (comprador/responsável) deve ser informado.")
	@Positive(message = "O ID da pessoa deve ser maior que zero.")
	private Long pessoaId;

	private Long empresaId;

	//@NotNull(message = "O ID da Conta a Pagar vinculada deve ser informado.")
	//@Positive(message = "O ID da conta a pagar deve ser maior que zero.")
	private Long contaPagarId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}

	public String getSerieNota() {
		return serieNota;
	}

	public void setSerieNota(String serieNota) {
		this.serieNota = serieNota;
	}

	public String getDescricaoObservacao() {
		return descricaoObservacao;
	}

	public void setDescricaoObservacao(String descricaoObservacao) {
		this.descricaoObservacao = descricaoObservacao;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorIcms() {
		return valorIcms;
	}

	public void setValorIcms(BigDecimal valorIcms) {
		this.valorIcms = valorIcms;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	
	public List<NotaItemProduto> getItens() {
		return itens;
	}

	public void setItens(List<NotaItemProduto> itens) {
		this.itens = itens;
	}

	public Long getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Long getContaPagarId() {
		return contaPagarId;
	}

	public void setContaPagarId(Long contaPagarId) {
		this.contaPagarId = contaPagarId;
	}
}
