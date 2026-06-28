package com.bandampla.lojavirtual.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.bandampla.lojavirtual.enums.TipoPessoa;

public class NotaFiscalCompraDTO {

	private Long id;

    @NotBlank(message = "O número da nota fiscal deve ser informado.")
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

    @NotNull(message = "Informe a data da compra.")
    private LocalDate dataCompra;

    @NotNull(message = "Informe a pessoa (fornecedor) da nota.")
    @Positive(message = "O ID da pessoa deve ser maior que zero.")
    private Long pessoaId; // fornecedor

    private Long empresaId;

    @NotNull(message = "Tipo de pessoa do fornecedor deve ser informado (FISICA ou JURIDICA).")
    private TipoPessoa tipoPessoaFornecedor;
    
	private Long contaPagarId;

    @NotNull(message = "Informe ao menos um item na nota fiscal.")
    private List<NotaItemProdutoDTO> itens;
    
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

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public List<NotaItemProdutoDTO> getItens() {
		return itens;
	}

	public void setItens(List<NotaItemProdutoDTO> itens) {
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

	public TipoPessoa getTipoPessoaFornecedor() {
		return tipoPessoaFornecedor;
	}

	public void setTipoPessoaFornecedor(TipoPessoa tipoPessoaFornecedor) {
		this.tipoPessoaFornecedor = tipoPessoaFornecedor;
	}

	public Long getContaPagarId() {
		return contaPagarId;
	}

	public void setContaPagarId(Long contaPagarId) {
		this.contaPagarId = contaPagarId;
	}
}
