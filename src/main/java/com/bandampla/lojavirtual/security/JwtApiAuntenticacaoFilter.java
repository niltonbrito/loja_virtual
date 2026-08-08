package com.bandampla.lojavirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;

public class JwtApiAuntenticacaoFilter extends OncePerRequestFilter {

	private final JWTTokenAutenticacaoService tokenService;

	public JwtApiAuntenticacaoFilter(JWTTokenAutenticacaoService tokenService) {

		this.tokenService = tokenService;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {

		String path = request.getServletPath();

		return path.equals("/auth/login") || path.equals("/auth/register") || path.equals("/auth/confirm-email")
				|| path.equals("/auth/register/empresa") || path.equals("/auth/onboarding/confirmar-email")
				|| path.equals("/auth/onboarding/login") || path.startsWith("/onboarding/")
				|| path.startsWith("/public/") || path.startsWith("/swagger-ui/") || path.startsWith("/v3/api-docs/");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		if (SecurityContextHolder.getContext().getAuthentication() != null) {

			filterChain.doFilter(request, response);
			return;
		}

		String authorization = request.getHeader("Authorization");

		/*
		 * Requisição sem JWT: segue normalmente para o Spring Security. A autorização
		 * do endpoint será decidida depois.
		 */
		if (authorization == null || authorization.trim().isEmpty() || !authorization.startsWith("Bearer ")) {

			filterChain.doFilter(request, response);
			return;
		}

		try {

			Authentication authentication = tokenService.getAuthentication(request);

			if (authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

			filterChain.doFilter(request, response);

		} catch (JwtException | IllegalArgumentException ex) {

			SecurityContextHolder.clearContext();

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			response.setContentType(MediaType.APPLICATION_JSON_VALUE);

			response.setCharacterEncoding("UTF-8");

			response.getWriter().write("{\"status\":401," + "\"erro\":\"Token inválido ou expirado\"}");
		}
	}
}