package com.bandampla.lojavirtual.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "produto_fornecedor", uniqueConstraints = @UniqueConstraint(name = "uk_produto_fornecedor", columnNames = {
		"produto_id", "fornecedor_id" }))
@SequenceGenerator(name = "seq_produto_fornecedor", sequenceName = "seq_produto_fornecedor", allocationSize = 1, initialValue = 1)
public class ProdutoFornecedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto_fornecedor")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	private Produto produto;

	@ManyToOne
	@JoinColumn(name = "fornecedor_id", nullable = false)
	private PessoaJuridica fornecedor;

	@Column(nullable = false, unique = true)
	private String codigoProdutoFornecedor;

	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)
	private PessoaJuridica empresa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public PessoaJuridica getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(PessoaJuridica fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getCodigoProdutoFornecedor() {
		return codigoProdutoFornecedor;
	}

	public void setCodigoProdutoFornecedor(String codigoProdutoFornecedor) {
		this.codigoProdutoFornecedor = codigoProdutoFornecedor;
	}

	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
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
		ProdutoFornecedor other = (ProdutoFornecedor) obj;
		return Objects.equals(id, other.id);
	}
}
