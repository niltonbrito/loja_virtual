package com.bandampla.lojavirtual.security;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bandampla.lojavirtual.dto.request.LoginRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final JWTTokenAutenticacaoService tokenService;
	private final ObjectMapper objectMapper;
	private final Validator validator;

	public JWTLoginFilter(String url, AuthenticationManager authenticationManager,
			JWTTokenAutenticacaoService tokenService, ObjectMapper objectMapper, Validator validator) {

		super(new AntPathRequestMatcher(url, "POST"));

		setAuthenticationManager(authenticationManager);

		this.tokenService = tokenService;
		this.objectMapper = objectMapper;
		this.validator = validator;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		LoginRequestDTO dto;

		try {
			dto = objectMapper.readValue(request.getInputStream(), LoginRequestDTO.class);
		} catch (IOException ex) {
			throw new AuthenticationServiceException("JSON de autenticação inválido.", ex);
		}

		Set<ConstraintViolation<LoginRequestDTO>> violations = validator.validate(dto);

		if (!violations.isEmpty()) {

			String mensagem = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(" "));

			throw new AuthenticationServiceException(mensagem);
		}

		String login = dto.getLogin().trim().toLowerCase();

		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(login, dto.getSenha()));
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
			AuthenticationException failed) throws IOException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		response.setCharacterEncoding("UTF-8");

		response.getWriter().write(objectMapper.writeValueAsString(java.util.Map.of("status", 401, "erro",
				failed.getMessage() == null ? "Usuário ou senha inválidos" : failed.getMessage())));
	}
}