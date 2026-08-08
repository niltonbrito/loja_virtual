package com.bandampla.lojavirtual.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bandampla.lojavirtual.enums.StatusPreCadastroCliente;

@Entity
@Table(name = "pre_cadastro_cliente")
@SequenceGenerator(name = "seq_pre_cadastro_cliente", sequenceName = "seq_pre_cadastro_cliente", allocationSize = 1, initialValue = 1)
public class PreCadastroCliente extends EntidadeAuditavel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pre_cadastro_cliente")
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String email;

	private String cpf;

	private String telefone;

	@Column(nullable = false)
	private String senhaHash;

	@Column(nullable = false)
	private String tokenConfirmacaoHash;

	private LocalDateTime tokenExpiraEm;

	@Enumerated(EnumType.STRING)
	private StatusPreCadastroCliente status;

	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pre_cadastro_cliente_fk"))
	private PessoaJuridica empresa;

	private LocalDateTime confirmadoEm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenhaHash() {
		return senhaHash;
	}

	public void setSenhaHash(String senhaHash) {
		this.senhaHash = senhaHash;
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

	public StatusPreCadastroCliente getStatus() {
		return status;
	}

	public void setStatus(StatusPreCadastroCliente status) {
		this.status = status;
	}

	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

	public LocalDateTime getConfirmadoEm() {
		return confirmadoEm;
	}

	public void setConfirmadoEm(LocalDateTime confirmadoEm) {
		this.confirmadoEm = confirmadoEm;
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
		PreCadastroCliente other = (PreCadastroCliente) obj;
		return Objects.equals(id, other.id);
	}
}