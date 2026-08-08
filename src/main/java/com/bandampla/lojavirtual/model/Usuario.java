package com.bandampla.lojavirtual.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.enums.StatusUsuario;

@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 1)
public class Usuario extends EntidadeAuditavel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
	private Long id;

	@Column(nullable = false, unique = true, length = 180)
	private String login;

	@Column(nullable = false, length = 100)
	private String senha;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private StatusUsuario status = StatusUsuario.ATIVO;

	@Column(name = "troca_senha_obrigatoria", nullable = false)
	private Boolean trocaSenhaObrigatoria = false;

	/*
	 * SUPER_ADMIN pode não possuir empresa. Usuários normais serão validados no
	 * service.
	 */
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_usuario_empresa"))
	private PessoaJuridica empresa;

	@OneToOne
	@JoinColumn(name = "pessoa_id", nullable = false, unique = true, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "fk_usuario_pessoa"))
	private Pessoa pessoa;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_acesso", joinColumns = @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_usuario_acesso_usuario")), inverseJoinColumns = @JoinColumn(name = "acesso_id", foreignKey = @ForeignKey(name = "fk_usuario_acesso_acesso")))
	private Set<Acesso> acessos = new HashSet<>();

	public void adicionarAcesso(Acesso acesso) {
		if (acesso == null) {
			throw new IllegalArgumentException("O acesso não pode ser nulo.");
		}

		this.acessos.add(acesso);
	}

	public boolean possuiRole(RoleUser role) {
		if (role == null || acessos == null) {
			return false;
		}

		return acessos.stream().anyMatch(acesso -> role.equals(acesso.getRoleUser()));
	}

	public boolean isSuperAdmin() {
		return possuiRole(RoleUser.ROLE_SUPER_ADMIN);
	}

	// Getters e Setters de banco padrão
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public StatusUsuario getStatus() {
		return status;
	}

	public void setStatus(StatusUsuario status) {
		this.status = status;
	}

	public Boolean getTrocaSenhaObrigatoria() {
		return trocaSenhaObrigatoria;
	}

	public void setTrocaSenhaObrigatoria(Boolean trocaSenhaObrigatoria) {
		this.trocaSenhaObrigatoria = trocaSenhaObrigatoria;
	}

	public Set<Acesso> getAcessos() {
		return acessos;
	}

	public void setAcessos(Set<Acesso> acessos) {
		this.acessos = acessos == null ? new HashSet<>() : acessos;
	}

	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}
}