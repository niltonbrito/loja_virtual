package com.bandampla.lojavirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bandampla.lojavirtual.dto.request.UsuarioRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final JWTTokenAutenticacaoService jwtTokenAutenticacaoService;

    public JWTLoginFilter(String url,
                          AuthenticationManager authenticationManager,
                          JWTTokenAutenticacaoService jwtTokenAutenticacaoService) {

        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
        this.jwtTokenAutenticacaoService = jwtTokenAutenticacaoService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        UsuarioRequestDTO loginDTO = new ObjectMapper()
                .readValue(request.getInputStream(), UsuarioRequestDTO.class);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getSenha());

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        try {
            jwtTokenAutenticacaoService.addAuthentication(response, authResult.getName());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Erro ao gerar token: " + e.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {

        if (failed instanceof BadCredentialsException) {
            response.getWriter().write("Usuário ou senha inválidos.");
        } else if (failed instanceof InternalAuthenticationServiceException) {
            response.getWriter().write("Usuário ou senha não encontrados.");
        } else {
            response.getWriter().write("Falha ao logar: " + failed.getMessage());
        }
    }
}
