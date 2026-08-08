package com.bandampla.lojavirtual.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.bandampla.lojavirtual.enums.StatusOnboarding;

@Entity
@Table(name = "pre_cadastro_empresa", uniqueConstraints = {
		@UniqueConstraint(name = "uk_pre_cadastro_empresa_email", columnNames = "email") })
@SequenceGenerator(name = "seq_pre_cadastro_empresa", sequenceName = "seq_pre_cadastro_empresa", allocationSize = 1, initialValue = 1)
public class PreCadastroEmpresa extends EntidadeAuditavel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pre_cadastro_empresa")
	private Long id;

	@Column(name = "nome_responsavel", nullable = false, length = 150)
	private String nomeResponsavel;

	@Column(nullable = false, length = 180)
	private String email;

	@Column(name = "senha_hash", length = 100)
	private String senhaHash;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 40)
	private StatusOnboarding status;

	@Column(name = "token_confirmacao_hash", length = 128)
	private String tokenConfirmacaoHash;

	@Column(name = "token_expira_em")
	private LocalDateTime tokenExpiraEm;

	@Column(name = "cpf_responsavel", length = 11)
	private String cpfResponsavel;

	@Column(name = "telefone_responsavel", length = 20)
	private String telefoneResponsavel;

	@Column(name = "razao_social", length = 200)
	private String razaoSocial;

	@Column(name = "nome_fantasia", length = 200)
	private String nomeFantasia;

	@Column(length = 14)
	private String cnpj;

	@Column(name = "inscricao_estadual", length = 30)
	private String inscricaoEstadual;

	@Column(name = "telefone_empresa", length = 20)
	private String telefoneEmpresa;

	@Column(name = "email_confirmado_em")
	private LocalDateTime emailConfirmadoEm;

	@Column(name = "concluido_em")
	private LocalDateTime concluidoEm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenhaHash() {
		return senhaHash;
	}

	public void setSenhaHash(String senhaHash) {
		this.senhaHash = senhaHash;
	}

	public StatusOnboarding getStatus() {
		return status;
	}

	public void setStatus(StatusOnboarding status) {
		this.status = status;
	}

	public String getTokenConfirmacaoHash() {
		return tokenConfirmacaoHash;
	}

	public void setTokenConfirmacaoHash(String tokenConfirmacaoHash) {
		this.tokenConfirmacaoHash = tokenConfirmacaoHash;
	}

	public LocalDateTime getTokenExpiraEm() {
		return tokenExpiraEm;
	}

	public void setTokenExpiraEm(LocalDateTime tokenExpiraEm) {
		this.tokenExpiraEm = tokenExpiraEm;
	}

	public String getCpfResponsavel() {
		return cpfResponsavel;
	}

	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}

	public String getTelefoneResponsavel() {
		return telefoneResponsavel;
	}

	public void setTelefoneResponsavel(String telefoneResponsavel) {
		this.telefoneResponsavel = telefoneResponsavel;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

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

	public String getTelefoneEmpresa() {
		return telefoneEmpresa;
	}

	public void setTelefoneEmpresa(String telefoneEmpresa) {
		this.telefoneEmpresa = telefoneEmpresa;
	}

	public LocalDateTime getEmailConfirmadoEm() {
		return emailConfirmadoEm;
	}

	public void setEmailConfirmadoEm(LocalDateTime emailConfirmadoEm) {
		this.emailConfirmadoEm = emailConfirmadoEm;
	}

	public LocalDateTime getConcluidoEm() {
		return concluidoEm;
	}

	public void setConcluidoEm(LocalDateTime concluidoEm) {
		this.concluidoEm = concluidoEm;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		PreCadastroEmpresa other = (PreCadastroEmpresa) obj;
		return Objects.equals(id, other.id);
	}
}