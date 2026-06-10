package com.bandampla.lojavirtual.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bandampla.lojavirtual.model.Usuario;

public class UsuarioLogadoPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final Long id;
	private final String login;
	private final String senha;
	private final Long empresaId;
	private final Collection<? extends GrantedAuthority> autoridades;

	// Construtor copia os dados cruciais para a sessão de forma segura
	public UsuarioLogadoPrincipal(Usuario usuario) {
		this.id = usuario.getId();
		this.login = usuario.getLogin();
		this.senha = usuario.getSenha();
		this.empresaId = usuario.getEmpresa() != null ? usuario.getEmpresa().getId() : null;
		this.autoridades = usuario.getAcessos();
	}

	public Long getId() {
		return id;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.autoridades;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}