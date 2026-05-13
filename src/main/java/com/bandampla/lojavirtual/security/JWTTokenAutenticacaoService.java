package com.bandampla.lojavirtual.security;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.config.ApplicationContextLoad;
import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 1 de mai. de 2026
 */
/* Criar a autenticação e retornar também a autenticação JWT */

@Service
@Component
public class JWTTokenAutenticacaoService {

	/* Token de Validade de 15 dias */
	private static final long EXPIRATION_TIME = 1296000000;

	/* Chave de senha para juntar com o JWT */
	private static final String SECRET = "8@nd@Mp1@2026";

	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";

	/* Gera o token e da resposta para o cliente */
	public void addAuthentication(HttpServletResponse httpServletResponse, String username) throws Exception {

		/* Montagem do Token */
		String JWT = Jwts.builder() /* Chama o gerador de Token */
				.setSubject(username) /* Adiciona o Usuário */
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))/* Tempo de Expiração */
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();

		/*
		 * Exemplo: Bearer
		 * *-/*bfgbfbhythu76uuj67j67ju67j76j6.67jg67jg67jg67jg67.6gj6gj67j67.jhytjyh
		 */
		String token = TOKEN_PREFIX + " " + JWT;

		/*
		 * Dá a resposta para a tela e para o cliente, outra API, navegador, aplicativo,
		 * javascript, outras chamadas java
		 */
		httpServletResponse.addHeader(HEADER_STRING, token);

		liberacaoCors(httpServletResponse);

		/* Usado para ver no Postman para teste */
		httpServletResponse.getWriter().write("{\"Authorization\": \"" + token + "\"}");
	}

	/* Retorna o usuario válido com tojen ou caso não seja válido retorna null */
	public Authentication getAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws IOException {

		String tokenString = httpServletRequest.getHeader(HEADER_STRING);

		if (tokenString != null && tokenString.startsWith("Bearer ")) {

			String token = tokenString.replace(TOKEN_PREFIX, "").trim();
			// String token = tokenString.substring(7).trim(); // remove "Bearer "

			try {
				/* Faz a validação do token do usuario na requisição e obtem o USER */
				String usuario = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody()
						.getSubject();/* ADMIn ou Nilton */

				if (usuario != null) {
					// Usuario usuario2 =
					// ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class).findUserByLogin(usuario);

					Optional<Usuario> usuario2 = ApplicationContextLoad.getApplicationContext()
							.getBean(UsuarioRepository.class).findByLogin(usuario);
					Usuario usuarioOpt = usuario2.get();

					if (usuario2 != null) {
						return new UsernamePasswordAuthenticationToken(usuarioOpt.getUsername(),
								usuarioOpt.getPassword(), usuarioOpt.getAuthorities());
					}
				}

			} catch (io.jsonwebtoken.SignatureException e) {
				response.getWriter().write("Token está inválido: " + e.getMessage());
				e.printStackTrace();
			} catch (io.jsonwebtoken.ExpiredJwtException e) {
				response.getWriter().write("Token está Expirado efetue o login novamente: " + e.getMessage());
				e.printStackTrace();
			} finally {
				liberacaoCors(response);
			}
		}
		return null;
	}

	/* Fazendo liberação contra erro de COrs no navegador */
	private void liberacaoCors(HttpServletResponse httpServletResponse) {
		if (httpServletResponse.getHeader("Access-Control-Allow-Origin") == null) {
			httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
		}
		if (httpServletResponse.getHeader("Access-Control-Allow-Header") == null) {
			httpServletResponse.addHeader("Access-Control-Allow-Header", "*");
		}
		if (httpServletResponse.getHeader("Access-Control-Request-Headers") == null) {
			httpServletResponse.addHeader("Access-Control-Request-Headers", "*");
		}
		if (httpServletResponse.getHeader("Access-Control-Allow-Methods") == null) {
			httpServletResponse.addHeader("Access-Control-Allow-Methods", "*");
		}
	}
}
