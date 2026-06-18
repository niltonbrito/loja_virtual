/**
 * 
 */
package com.bandampla.lojavirtual.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bandampla.lojavirtual.enums.StatusPedidoCompra;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 17 de jun. de 2026
 */
@Entity
@Table(name = "pedido_compra")
@SequenceGenerator(name = "seq_pedido_compra", sequenceName = "seq_pedido_compra", allocationSize = 1, initialValue = 1)
public class PedidoCompra implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pedido_compra")
	private Long id;

	@Column(nullable = false)
	private String numeroPedido;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataPedido;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusPedidoCompra status; // AGUARDANDO_ENTREGA, PARCIAL, CONCLUIDO, CANCELADO

	@ManyToOne
	@JoinColumn(name = "fornecedor_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fornecedor_fk"))
	private PessoaJuridica fornecedor;

	@ManyToOne
	@JoinColumn(name = "solicitacao_compra_id", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "solicitacao_compra_fk"))
	private SolicitacaoCompra solicitacaoCompra;

	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private PessoaJuridica empresa;

	@OneToMany(mappedBy = "pedidoCompra", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PedidoCompraItem> itens = new ArrayList<>();

	@OneToMany(mappedBy = "pedidoCompra", fetch = FetchType.LAZY)
	private List<NotaFiscalCompra> notasFiscais = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public StatusPedidoCompra getStatus() {
		return status;
	}

	public void setStatus(StatusPedidoCompra status) {
		this.status = status;
	}

	public PessoaJuridica getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(PessoaJuridica fornecedor) {
		this.fornecedor = fornecedor;
	}

	public SolicitacaoCompra getSolicitacaoCompra() {
		return solicitacaoCompra;
	}

	public void setSolicitacaoCompra(SolicitacaoCompra solicitacaoCompra) {
		this.solicitacaoCompra = solicitacaoCompra;
	}

	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

	public List<PedidoCompraItem> getItens() {
		return itens;
	}

	public void setItens(List<PedidoCompraItem> itens) {
		this.itens = itens;
	}

	public List<NotaFiscalCompra> getNotasFiscais() {
		return notasFiscais;
	}

	public void setNotasFiscais(List<NotaFiscalCompra> notasFiscais) {
		this.notasFiscais = notasFiscais;
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
		PedidoCompra other = (PedidoCompra) obj;
		return Objects.equals(id, other.id);
	}
}
