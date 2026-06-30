/**
 * 
 */
package com.bandampla.lojavirtual.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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

import com.bandampla.lojavirtual.enums.StatusSolicitacaoCompra;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 17 de jun. de 2026
 */
@Entity
@Table(name = "solicitacao_compra")
@SequenceGenerator(name = "seq_solicitacao_compra", sequenceName = "seq_solicitacao_compra", allocationSize = 1, initialValue = 1)
public class SolicitacaoCompra implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_solicitacao_compra")
	private Long id;

	@Column(nullable = false)
	private String descricao;

	@Column(nullable = false)
	private LocalDate dataSolicitacao;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusSolicitacaoCompra status; // ABERTA, APROVADA, REJEITADA, CANCELADA

	@ManyToOne
	@JoinColumn(name = "solicitante_id", nullable = false)
	private Pessoa solicitante;

	@ManyToOne
	@JoinColumn(name = "setor_solicitante_id")
	private Setor setorSolicitante;

	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private PessoaJuridica empresa;

	@OneToMany(mappedBy = "solicitacaoCompra", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<SolicitacaoCompraItem> itens = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(LocalDate dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public StatusSolicitacaoCompra getStatus() {
		return status;
	}

	public void setStatus(StatusSolicitacaoCompra status) {
		this.status = status;
	}

	public Setor getSetorSolicitante() {
		return setorSolicitante;
	}

	public void setSetorSolicitante(Setor setorSolicitante) {
		this.setorSolicitante = setorSolicitante;
	}

	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

	public List<SolicitacaoCompraItem> getItens() {
		return itens;
	}

	public void setItens(List<SolicitacaoCompraItem> itens) {
		this.itens = itens;
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
		SolicitacaoCompra other = (SolicitacaoCompra) obj;
		return Objects.equals(id, other.id);
	}
}
