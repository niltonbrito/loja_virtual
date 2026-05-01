/**
 * 
 */
package com.bandampla.lojavirtual.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.UsuarioRepository;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 1 de mai. de 2026
 */

public class ImplementacaoUserDetailsService implements UserDetailsService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findUserByLogin(username);
		

		Optional<Usuario> optUsuario = usuarioRepository.findByLogin(username);
		Usuario usuarioOpt = optUsuario.get();
		if (optUsuario.isEmpty()) {
			throw new UsernameNotFoundException("Não achei este usuario Usuário"+usuarioOpt.getUsername()+" "+usuarioOpt.getPassword()+" "+usuarioOpt.getAuthorities());				
		}
		
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");			
		}

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getAuthorities());
	}

}
