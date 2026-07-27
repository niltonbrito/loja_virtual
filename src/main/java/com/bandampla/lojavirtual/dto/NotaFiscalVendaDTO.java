package com.bandampla.lojavirtual.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NotaFiscalVendaDTO {

	private Long id;

	@NotBlank(message = "O número da nota fiscal deve ser informado.")
	private String numeroNota;

	@NotBlank(message = "A série da nota fiscal deve ser informada.")
	private String serieNota;

	@NotBlank(message = "O tipo da nota (Entrada/Saída) deve ser informado.")
	private String tipo;

	@NotBlank(message = "A descrição da nota deve ser informada.")
	private String descricao;

	@NotNull(message = "O valor total da nota deve ser informado.")
	@Positive(message = "O valor total deve ser maior que zero.")
	private BigDecimal valorTotal;

	private BigDecimal valorDesconto;

	@NotNull(message = "O valor do ICMS deve ser informado.")
	private BigDecimal valorIcms;

	@NotBlank(message = "O conteúdo XML da nota é obrigatório.")
	private String xml;

	@NotBlank(message = "O link ou conteúdo do PDF (DANFE) é obrigatório.")
	private String pdf;

	@NotNull(message = "O ID da venda correspondente deve ser informado.")
	private Long vendaLojaVirtualId;

	private Long empresaId;

	// Getters e Setters
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
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