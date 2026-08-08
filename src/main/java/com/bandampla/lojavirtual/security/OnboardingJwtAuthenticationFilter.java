package com.bandampla.lojavirtual.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bandampla.lojavirtual.enums.StatusOnboarding;
import com.bandampla.lojavirtual.model.PreCadastroEmpresa;
import com.bandampla.lojavirtual.repository.PreCadastroEmpresaRepository;

import io.jsonwebtoken.Claims;

@Component
public class OnboardingJwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtOnboardingService jwtOnboardingService;

	private final PreCadastroEmpresaRepository preCadastroEmpresaRepository;

	public OnboardingJwtAuthenticationFilter(JwtOnboardingService jwtOnboardingService,
			PreCadastroEmpresaRepository preCadastroEmpresaRepository) {

		this.jwtOnboardingService = jwtOnboardingService;

		this.preCadastroEmpresaRepository = preCadastroEmpresaRepository;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {

		String servletPath = request.getServletPath();

		return !servletPath.startsWith("/onboarding/");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (header == null || !header.startsWith("Bearer ")) {

			filterChain.doFilter(request, response);

			return;
		}

		try {
			String token = header.substring(7).trim();

			Claims claims = jwtOnboardingService.validarEObterClaims(token);

			Long onboardingId = jwtOnboardingService.obterOnboardingId(claims);

			String email = jwtOnboardingService.obterEmail(claims);

			PreCadastroEmpresa preCadastro = preCadastroEmpresaRepository.findById(onboardingId)
					.orElseThrow(() -> new IllegalStateException("Pré-cadastro não encontrado."));

			validarPreCadastro(preCadastro, email);

			OnboardingPrincipal principal = new OnboardingPrincipal(preCadastro.getId(), preCadastro.getEmail());

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,
					null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_ONBOARDING")));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			filterChain.doFilter(request, response);

		} catch (Exception e) {
			SecurityContextHolder.clearContext();

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			response.setContentType("application/json");

			response.setCharacterEncoding("UTF-8");

			response.getWriter().write("{\"erro\":\"Token de onboarding " + "inválido ou expirado.\"}");
		}
	}

	private void validarPreCadastro(PreCadastroEmpresa preCadastro, String emailToken) {

		if (!preCadastro.getEmail().equalsIgnoreCase(emailToken)) {

			throw new IllegalStateException("E-mail do token não corresponde " + "ao pré-cadastro.");
		}

		StatusOnboarding status = preCadastro.getStatus();

		boolean permitido = status == StatusOnboarding.DADOS_EMPRESA_PENDENTES
				|| status == StatusOnboarding.PRONTO_PARA_FINALIZAR;

		if (!permitido) {
			throw new IllegalStateException("Onboarding não está disponível.");
		}
	}
}