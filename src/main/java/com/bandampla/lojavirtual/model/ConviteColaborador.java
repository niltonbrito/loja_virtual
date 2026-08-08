package com.bandampla.lojavirtual.model;

import java.time.LocalDateTime;
import java.util.Objects;

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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.enums.StatusConvite;

@Entity
@Table(name = "convite_colaborador", indexes = {
		@Index(name = "idx_convite_colaborador_empresa_email", columnList = "empresa_id,email"),
		@Index(name = "idx_convite_colaborador_status", columnList = "status"),
		@Index(name = "idx_convite_colaborador_expira_em", columnList = "expira_em") })
@SequenceGenerator(name = "seq_convite_colaborador", sequenceName = "seq_convite_colaborador", allocationSize = 1, initialValue = 1)
public class ConviteColaborador extends EntidadeAuditavel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_convite_colaborador")
	private Long id;

	@Column(nullable = false, length = 150)
	private String nome;

	@Column(nullable = false, length = 180)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private RoleUser perfil;

	@Column(name = "token_hash", nullable = false, unique = true, length = 64)
	private String tokenHash;

	@Column(name = "expira_em", nullable = false)
	private LocalDateTime expiraEm;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private StatusConvite status;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_convite_colaborador_empresa"))
	private PessoaJuridica empresa;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "criado_por_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_convite_colaborador_criado_por"))
	private Usuario criadoPor;

	@Column(name = "aceito_em")
	private LocalDateTime aceitoEm;

	@Column(name = "cancelado_em")
	private LocalDateTime canceladoEm;

	public boolean estaExpirado(LocalDateTime agora) {
		return expiraEm != null && !expiraEm.isAfter(agora);
	}

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

	public RoleUser getPerfil() {
		return perfil;
	}

	public void setPerfil(RoleUser perfil) {
		this.perfil = perfil;
	}

	public String getTokenHash() {
		return tokenHash;
	}

	public void setTokenHash(String tokenHash) {
		this.tokenHash = tokenHash;
	}

	public LocalDateTime getExpiraEm() {
		return expiraEm;
	}

	public void setExpiraEm(LocalDateTime expiraEm) {
		this.expiraEm = expiraEm;
	}

	public StatusConvite getStatus() {
		return status;
	}

	public void setStatus(StatusConvite status) {
		this.status = status;
	}

	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

	public Usuario getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(Usuario criadoPor) {
		this.criadoPor = criadoPor;
	}

	public LocalDateTime getAceitoEm() {
		return aceitoEm;
	}

	public void setAceitoEm(LocalDateTime aceitoEm) {
		this.aceitoEm = aceitoEm;
	}

	public LocalDateTime getCanceladoEm() {
		return canceladoEm;
	}

	public void setCanceladoEm(LocalDateTime canceladoEm) {
		this.canceladoEm = canceladoEm;
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

		ConviteColaborador other = (ConviteColaborador) obj;

		return Objects.equals(id, other.id);
	}
}