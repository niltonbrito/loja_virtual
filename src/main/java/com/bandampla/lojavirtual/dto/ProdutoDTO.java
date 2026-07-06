package com.bandampla.lojavirtual.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.bandampla.lojavirtual.enums.TipoUnidadeMedida;
import com.bandampla.lojavirtual.validation.NotEmptyList;
import com.bandampla.lojavirtual.validation.ValidBoolean;
import com.bandampla.lojavirtual.validation.ValidYoutubeUrl;

public class ProdutoDTO {

	private Long id;

	@NotNull(message = "O tipo da unidade de medida deve ser informado.")
	private TipoUnidadeMedida tipoUnidadeMedida;

	@Size(min = 10, message = "O nome do produto deve ter mais de 10 caracteres.")
	@NotBlank(message = "O Nome do produto deve ser informado.")
	private String nome;

	@NotNull(message = "O campo Ativo é obrigatório e deve ser true ou false.")
	@ValidBoolean(message = "O campo Ativo é obrigatório e deve ser true ou false.")
	private Boolean ativo;

	@Length(max = 2000)
	@NotBlank(message = "Informe uma descrição para o produto.")
	private String descricao;

	@NotNull(message = "Informe o peso do produto")
	@Positive(message = "O peso deve ser maior que zero.")
	private BigDecimal peso;

	@NotNull(message = "Informe a largura do produto")
	@Positive(message = "O largura deve ser maior que zero.")
	private BigDecimal largura;

	@NotNull(message = "Informe a altura do produto")
	@Positive(message = "O altura deve ser maior que zero.")
	private BigDecimal altura;

	@NotNull(message = "Informe a profundidade do produto")
	@Positive(message = "O profundidade deve ser maior que zero.")
	private BigDecimal profundidade;

	@NotNull(message = "Informe o valor de venda do produto")
	@Positive(message = "O valor de venda deve ser maior que zero.")
	private BigDecimal valorVenda;

	@Positive(message = "A quantidade de deve ser maior que zero.")
	private BigDecimal qtdEstoque;

	@NotNull(message = "Informe o valor de estoque minimo do produto")
	@Positive(message = "O estoque minimo de deve ser maior que zero.")
	private BigDecimal qtdEstoqueMinimo;

	@NotNull(message = "O campo Alerta Estoque é obrigatório e deve ser true ou false.")
	private Boolean alertaEstoque;

	@ValidYoutubeUrl(message = "Informe uma URL válida do YouTube.")
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

	@NotNull(message = "O fornecedor do produto deve ser informado.")
	@Positive(message = "O ID do fornecedor deve ser maior que zero.")
	private Long fornecedorId;

	@NotBlank(message = "O código do produto do fornecedor deve ser informado.")
	private String codigoProdutoFornecedor;

	@NotEmptyList(message = "Informe entre 3 e 6 imagens.")
	@Size(min = 1, max = 6, message = "Informe entre 3 e 6 imagens.")
	private List<ImagemProdutoDTO> imagens;

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

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public BigDecimal getLargura() {
		return largura;
	}

	public void setLargura(BigDecimal largura) {
		this.largura = largura;
	}

	public BigDecimal getAltura() {
		return altura;
	}

	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}

	public BigDecimal getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(BigDecimal profundidade) {
		this.profundidade = profundidade;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public BigDecimal getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(BigDecimal qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public BigDecimal getQtdEstoqueMinimo() {
		return qtdEstoqueMinimo;
	}

	public void setQtdEstoqueMinimo(BigDecimal qtdEstoqueMinimo) {
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

	public Long getFornecedorId() {
		return fornecedorId;
	}

	public void setFornecedorId(Long fornecedorId) {
		this.fornecedorId = fornecedorId;
	}

	public String getCodigoProdutoFornecedor() {
		return codigoProdutoFornecedor;
	}

	public void setCodigoProdutoFornecedor(String codigoProdutoFornecedor) {
		this.codigoProdutoFornecedor = codigoProdutoFornecedor;
	}

	public List<ImagemProdutoDTO> getImagens() {
		return imagens;
	}

	public void setImagens(List<ImagemProdutoDTO> imagens) {
		this.imagens = imagens;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoDTO other = (ProdutoDTO) obj;
		return Objects.equals(id, other.id);
	}
}
