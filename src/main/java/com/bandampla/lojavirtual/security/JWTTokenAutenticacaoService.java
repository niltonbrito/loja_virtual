package com.bandampla.lojavirtual.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.enums.TipoTokenJwt;
import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.UsuarioRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTTokenAutenticacaoService {

	private static final String TOKEN_PREFIX = "Bearer";
	private static final String HEADER_STRING = "Authorization";

	private final UsuarioRepository usuarioRepository;
	private final String secret;
	private final long expirationMillis;

	public JWTTokenAutenticacaoService(UsuarioRepository usuarioRepository, @Value("${app.jwt.secret}") String secret,
			@Value("${app.jwt.expiration-millis:1296000000}") long expirationMillis) {
		this.usuarioRepository = usuarioRepository;
		this.secret = secret;
		this.expirationMillis = expirationMillis;
	}

	public String createToken(String username) {
		Usuario usuario = usuarioRepository.findByLoginIgnoreCase(username)
				.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
		Long empresaId = usuario.getEmpresa() == null ? null : usuario.getEmpresa().getId();
		return Jwts.builder().setSubject(username).claim("empresaId", empresaId).claim("usuarioId", usuario.getId())
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public void addAuthentication(HttpServletResponse response, String username) {
		response.setHeader(HEADER_STRING, TOKEN_PREFIX + createToken(username));
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		String header = request.getHeader(HEADER_STRING);
		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			return null;
		}
		String token = header.substring(TOKEN_PREFIX.length()).trim();
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		String login = claims.getSubject();
		Usuario usuario = usuarioRepository.findByLoginIgnoreCase(login)
				.orElseThrow(() -> new IllegalArgumentException("Usuário do token não encontrado."));
		UsuarioLogadoPrincipal principal = new UsuarioLogadoPrincipal(usuario);
		return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
	}

	public String gerarTokenAcesso(Usuario usuario) {

		Date agora = new Date();

		Date expiracao = new Date(agora.getTime() + expirationMillis);

		List<String> roles = usuario.getAcessos().stream().map(acesso -> acesso.getRoleUser().name())
				.collect(Collectors.toList());

		return Jwts.builder().setSubject(usuario.getLogin()).claim("tipoToken", TipoTokenJwt.ACCESS.name())
				.claim("usuarioId", usuario.getId())
				.claim("empresaId", usuario.getEmpresa() != null ? usuario.getEmpresa().getId() : null)
				.claim("roles", roles).setIssuedAt(agora).setExpiration(expiracao)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}