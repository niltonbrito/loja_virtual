/**
 * 
 */
package com.bandampla.lojavirtual.dto;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.bandampla.lojavirtual.enums.TipoUnidadeMedida;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 18 de mai. de 2026
 */
public class ProdutoDTO {

	private Long id;

	@NotNull(message = "O tipo da unidade de medida deve ser informado.")
	private TipoUnidadeMedida tipoUnidadeMedida;

	@Size(min = 10, message = "O nome do produto deve ter mais de 10 caracteres.")
	@NotBlank(message = "O Nome do produto deve ser informado.")
	private String nome;
	private Boolean ativo;

	@Length(max = 2000)
	@NotBlank(message = "Informe uma descrição para o produto.")
	private String descricao;

	@NotNull(message = "Informe o peso do produto")
	@Positive(message = "O peso deve ser maior que zero.")
	private Double peso;

	@NotNull(message = "Informe a largura do produto")
	@Positive(message = "O largura deve ser maior que zero.")
	private Double largura;

	@NotNull(message = "Informe a altura do produto")
	@Positive(message = "O altura deve ser maior que zero.")
	private Double altura;

	@NotNull(message = "Informe a profundidade do produto")
	@Positive(message = "O profundidade deve ser maior que zero.")
	private Double profundidade;

	@NotNull(message = "Informe o valor de venda do produto")
	@Positive(message = "O valor de venda deve ser maior que zero.")
	private BigDecimal valorVenda;

	private Integer qtdEstoque;
	private Integer qtdEstoqueMinimo;
	private Boolean alertaEstoque;
	private String linkYoutube;

	@PositiveOrZero(message = "A quantidade de cliques deve ser maior que zero.")
	private Integer qtdClickProduto;

	private Long empresaId;

	@NotNull(message = "A Categoria do Produto deve ser informada")
	@Positive(message = "O ID da Categoria do Produto deve ser maior que zero.")
	private Long categoriaId;

	@NotNull(message = "A Marca do Produto deve ser informada")
	@Positive(message = "O ID da Marca do Produto deve ser maior que zero.")
	private Long marcaId;

	@NotNull(message = "A Nota Item do Produto deve ser informada")
	@Positive(message = "O ID da Nota Item do Produto deve ser maior que zero.")
	private Long notaItemId;

	// =========================================================================
	// 🔄 MÉTODOS EQUALS E HASHCODE ADICIONADOS (PADRÃO JAVA 11)
	// =========================================================================
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		ProdutoDTO other = (ProdutoDTO) obj;

		// Tratamento especial para BigDecimal (compara o valor matemático ignorando
		// zeros na escala)
		boolean precoIgual = (this.valorVenda == null && other.valorVenda == null) || (this.valorVenda != null
				&& other.valorVenda != null && this.valorVenda.compareTo(other.valorVenda) == 0);

		return Objects.equals(id, other.id) && Objects.equals(tipoUnidadeMedida, other.tipoUnidadeMedida)
				&& Objects.equals(nome, other.nome) && Objects.equals(ativo, other.ativo)
				&& Objects.equals(descricao, other.descricao) && Objects.equals(peso, other.peso)
				&& Objects.equals(largura, other.largura) && Objects.equals(altura, other.altura)
				&& Objects.equals(profundidade, other.profundidade) && precoIgual
				&& Objects.equals(qtdEstoque, other.qtdEstoque)
				&& Objects.equals(qtdEstoqueMinimo, other.qtdEstoqueMinimo)
				&& Objects.equals(alertaEstoque, other.alertaEstoque) && Objects.equals(linkYoutube, other.linkYoutube)
				&& Objects.equals(qtdClickProduto, other.qtdClickProduto) && Objects.equals(empresaId, other.empresaId)
				&& Objects.equals(categoriaId, other.categoriaId) && Objects.equals(marcaId, other.marcaId)
				&& Objects.equals(notaItemId, other.notaItemId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tipoUnidadeMedida, nome, ativo, descricao, peso, largura, altura, profundidade,
				valorVenda, qtdEstoque, qtdEstoqueMinimo, alertaEstoque, linkYoutube, qtdClickProduto, empresaId,
				categoriaId, marcaId, notaItemId);
	}

	// =========================================================================
	// 🟢 GETTERS AND SETTERS TRADICIONAIS DO SEU ARQUIVO ORIGINAL
	// =========================================================================
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoUnidadeMedida getTipoUnidadeMedida() {
		return tipoUnidadeMedida;
	}

	public void setTipoUnidadeMedida(TipoUnidadeMedida tipoUnidadeMedida) {
		this.tipoUnidadeMedida = tipoUnidadeMedida;
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

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Long getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(Long marcaId) {
		this.marcaId = marcaId;
	}

	public Long getNotaItemId() {
		return notaItemId;
	}

	public void setNotaItemId(Long notaItemId) {
		this.notaItemId = notaItemId;
	}
}