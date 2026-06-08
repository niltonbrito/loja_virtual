/**
 * 
 */
package com.bandampla.lojavirtual.dto;

import java.math.BigDecimal;

import com.bandampla.lojavirtual.model.NotaItemProduto;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 18 de mai. de 2026
 */
public class ProdutoDTO {

	private Long id;
	private String tipoUnidade;
	private String nome;
	private Boolean ativo;
	private String descricao;
	private NotaItemProduto notaItemProduto;
	private Double peso;
	private Double largura;
	private Double altura;
	private Double profundidade;
	private BigDecimal valorVenda;
	private Integer qtdEstoque;
	private Integer qtdEstoqueMinimo;
	private Boolean alertaEstoque;
	private String linkYoutube;
	private Integer qtdClickProduto;
	private Long empresaId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoUnidade() {
		return tipoUnidade;
	}

	public void setTipoUnidade(String tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public NotaItemProduto getNotaItemProduto() {
		return notaItemProduto;
	}

	public void setNotaItemProduto(NotaItemProduto notaItemProduto) {
		this.notaItemProduto = notaItemProduto;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getLargura() {
		return largura;
	}

	public void setLargura(Double largura) {
		this.largura = largura;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(Double profundidade) {
		this.profundidade = profundidade;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public Integer getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(Integer qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public Integer getQtdEstoqueMinimo() {
		return qtdEstoqueMinimo;
	}

	public void setQtdEstoqueMinimo(Integer qtdEstoqueMinimo) {
		this.qtdEstoqueMinimo = qtdEstoqueMinimo;
	}

	public Boolean getAlertaEstoque() {
		return alertaEstoque;
	}

	public void setAlertaEstoque(Boolean alertaEstoque) {
		this.alertaEstoque = alertaEstoque;
	}

	public String getLinkYoutube() {
		return linkYoutube;
	}

	public void setLinkYoutube(String linkYoutube) {
		this.linkYoutube = linkYoutube;
	}

	public Integer getQtdClickProduto() {
		return qtdClickProduto;
	}

	public void setQtdClickProduto(Integer qtdClickProduto) {
		this.qtdClickProduto = qtdClickProduto;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
}
