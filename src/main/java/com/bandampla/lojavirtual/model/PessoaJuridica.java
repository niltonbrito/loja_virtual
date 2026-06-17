package com.bandampla.lojavirtual.model;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "pessoa_juridica", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "cnpj", "empresa_id" }, name = "uk_cnpj_por_empresa") })
@PrimaryKeyJoinColumn(name = "id")
public class PessoaJuridica extends Pessoa {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true)
	private String cnpj;

	@Column(nullable = true, unique = true)
	private String inscricaoEstadual;

	@Column(nullable = true, unique = true)
	private String inscricaoMunicipal;

	@Column(nullable = false)
	private String nomeFantasia;

	@Column(nullable = false)
	private String razaoSocial;

	private String categoria;

	// MATRIZ / FILIAL
	@ManyToOne
	@JoinColumn(name = "matriz_id", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "matriz_fk"))
	private PessoaJuridica matriz;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private PessoaJuridica empresa; // O Tenant pai (Será nulo apenas se a PJ for a própria Matriz Suprema)

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public PessoaJuridica getMatriz() {
		return matriz;
	}

	public void setMatriz(PessoaJuridica matriz) {
		this.matriz = matriz;
	}
}
