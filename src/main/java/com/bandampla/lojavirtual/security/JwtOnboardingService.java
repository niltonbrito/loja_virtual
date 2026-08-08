package com.bandampla.lojavirtual.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.enums.TipoTokenJwt;
import com.bandampla.lojavirtual.model.PreCadastroEmpresa;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtOnboardingService {

	private final String jwtSecret;
	private final long expirationMs;

	public JwtOnboardingService(@Value("${app.jwt.secret}") String jwtSecret,
			@Value("${app.jwt.onboarding-expiration-ms:1800000}") long expirationMs) {

		this.jwtSecret = jwtSecret;
		this.expirationMs = expirationMs;
	}

	public String gerarToken(PreCadastroEmpresa preCadastro) {

		Date agora = new Date();

		Date expiracao = new Date(agora.getTime() + expirationMs);

		return Jwts.builder().setSubject(preCadastro.getEmail()).claim("tipoToken", TipoTokenJwt.ONBOARDING.name())
				.claim("onboardingId", preCadastro.getId()).claim("scope", "ONBOARDING").setIssuedAt(agora)
				.setExpiration(expiracao).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public Claims validarEObterClaims(String token) {

		try {
			Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

			validarTipoToken(claims);

			return claims;

		} catch (JwtException | IllegalArgumentException e) {

			throw new BadCredentialsException("Token de onboarding inválido.", e);
		}
	}

	public Long obterOnboardingId(Claims claims) {

		Object valor = claims.get("onboardingId");

		if (!(valor instanceof Number)) {
			throw new BadCredentialsException("Token não possui onboardingId válido.");
		}

		return ((Number) valor).longValue();
	}

	public String obterEmail(Claims claims) {
		return claims.getSubject();
	}

	public long getExpirationMs() {
		return expirationMs;
	}

	private void validarTipoToken(Claims claims) {

		String tipoToken = claims.get("tipoToken", String.class);

		if (!TipoTokenJwt.ONBOARDING.name().equals(tipoToken)) {

			throw new BadCredentialsException("O token informado não é " + "um token de onboarding.");
		}

		String scope = claims.get("scope", String.class);

		if (!"ONBOARDING".equals(scope)) {
			throw new BadCredentialsException("Escopo do token inválido.");
		}
	}
}