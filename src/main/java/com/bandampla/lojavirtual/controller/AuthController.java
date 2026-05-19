/**
 * 
 */
package com.bandampla.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.dto.response.UsuarioResponseDTO;
import com.bandampla.lojavirtual.mapper.UsuarioMapper;
import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.UsuarioRepository;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   18 de mai. de 2026
 */
@RestController
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping("/auth/me")
    public UsuarioResponseDTO getUsuarioLogado() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();

        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return usuarioMapper.toResponse(usuario);
    }
}