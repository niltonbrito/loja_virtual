package com.bandampla.lojavirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bandampla.lojavirtual.dto.request.UsuarioRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final JWTTokenAutenticacaoService tokenService;
	private final ObjectMapper objectMapper;

	public JWTLoginFilter(String url, AuthenticationManager authenticationManager,
			JWTTokenAutenticacaoService tokenService, ObjectMapper objectMapper) {
		super(new AntPathRequestMatcher(url, "POST"));
		setAuthenticationManager(authenticationManager);
		this.tokenService = tokenService;
		this.objectMapper = objectMapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		UsuarioRequestDTO dto = objectMapper.readValue(request.getInputStream(), UsuarioRequestDTO.class);
		return getAuthenticationManager()
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException {
		String token = tokenService.createToken(authResult.getName());
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Authorization", "Bearer " + token);
		response.getWriter().write(objectMapper.writeValueAsString(java.util.Collections.singletonMap("token", token)));
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"status\":401,\"erro\":\"Usuário ou senha inválidos\"}");
	}
}
