package com.bandampla.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cupom_desconto")
@SequenceGenerator(name = "seq_cupom_desconto", sequenceName = "seq_cupom_desconto", allocationSize = 1, initialValue = 1)
public class CupomDesconto extends EntidadeAuditavel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cupom_desconto")
	private Long id;

	@Column(nullable = false)
	private String codigoDescricao;

	private BigDecimal valorRealDesconto;

	private BigDecimal valorPorcentagemDesconto;

	@Column(nullable = false)
	private LocalDate dataValidade;

	private Integer limiteUsoTotal;
	
	// 🔥 Manda o Hibernate NUNCA atualizar essa coluna em comandos de UPDATE do SQL
	@Column(name = "quantidade_usado", updatable = false)
	private Integer quantidadeUsado;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "cupom_categoria", // Nome da tabela intermediária pura
			joinColumns = @JoinColumn(name = "cupom_id"), // Chave que aponta para o Cupom (que tem o empresaId)
			inverseJoinColumns = @JoinColumn(name = "categoria_id") // Chave que aponta para a Categoria do catálogo
	)
	private List<CategoriaProduto> categorias;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "cupom_marca", joinColumns = @JoinColumn(name = "cupom_id"), inverseJoinColumns = @JoinColumn(name = "marca_id"))
	private List<MarcaProduto> marcas;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "cupom_produto", joinColumns = @JoinColumn(name = "cupom_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
	private List<Produto> produtos;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private PessoaJuridica empresa;

	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

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

	public List<CategoriaProduto> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaProduto> categorias) {
		this.categorias = categorias;
	}

	public List<MarcaProduto> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<MarcaProduto> marcas) {
		this.marcas = marcas;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
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
		CupomDesconto other = (CupomDesconto) obj;
		return Objects.equals(id, other.id);
	}

}