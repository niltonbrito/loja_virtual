package com.bandampla.lojavirtual.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.UsuarioRepository;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Usuario> optUsuario = usuarioRepository.findByLogin(username);

		if (!optUsuario.isPresent()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}

		// 🔄 Transforma a Entidade em Objeto de Sessão isolado
		return new UsuarioLogadoPrincipal(optUsuario.get());
		// return new User(usuario.getUsername(), usuario.getPassword(),
		// usuario.getAuthorities());
	}
}