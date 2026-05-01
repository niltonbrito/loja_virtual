/**
 * 
 */
package com.bandampla.lojavirtual.security;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
		String jWTString = Jwts.builder() /* Chama o gerador de Token */
				.setSubject(username) /* Adiciona o Usuário */
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))/* Tempo de Expiração */
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();

		/*Exemplo: Bearer *-/*bfgbfbhythu76uuj67j67ju67j76j6.67jg67jg67jg67jg67.6gj6gj67j67.jhytjyh*/
		String token = TOKEN_PREFIX + " "+jWTString;
		
		/*Dá a resposta para a tela e para o cliente, outra API, navegador, aplicativo, javascript, outras chamadas java*/
		httpServletResponse.addHeader(HEADER_STRING, token);
		
		/*Usado para ver no Postman para teste*/
		httpServletResponse.getWriter().write("{\"Authorization\": \""+ token+"\"}");
	}
}
