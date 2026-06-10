package com.bandampla.lojavirtual.security;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTTokenAutenticacaoService {

    private static final long EXPIRATION_TIME = 1296000000L;
    private static final String SECRET = "8@nd@Mp1@2026";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    private final UsuarioRepository usuarioRepository;

    public JWTTokenAutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void addAuthentication(HttpServletResponse response, String username) throws Exception {
        Optional<Usuario> optUsuario = usuarioRepository.findByLogin(username);
        Long empresaId = null;
        if (optUsuario.isPresent() && optUsuario.get().getEmpresa() != null) {
            empresaId = optUsuario.get().getEmpresa().getId();
        }

        String jwt = Jwts.builder()
                .setSubject(username)
                .claim("empresaId", empresaId)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        String token = TOKEN_PREFIX + " " + jwt;
        response.addHeader(HEADER_STRING, token);
        liberacaoCors(response);
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tokenString = request.getHeader(HEADER_STRING);

        if (tokenString != null && tokenString.startsWith("Bearer ")) {
            String token = tokenString.replace(TOKEN_PREFIX, "").trim();
            try {
                String usuarioLogin = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

                if (usuarioLogin != null) {
                    Optional<Usuario> optUsuario = usuarioRepository.findByLogin(usuarioLogin);
                    if (optUsuario.isPresent()) {
                        
                        // 🔄 Transforma em Principal para o Controller ler os IDs
                        UsuarioLogadoPrincipal principal = new UsuarioLogadoPrincipal(optUsuario.get());
                        
                        return new UsernamePasswordAuthenticationToken(
                                principal, 
                                null, 
                                principal.getAuthorities());
                    }
                }
            } catch (io.jsonwebtoken.SignatureException e) {
				response.getWriter().write("Token está inválido: " + e.getMessage());
				e.printStackTrace();
			} catch (io.jsonwebtoken.ExpiredJwtException e) {
				response.getWriter().write("Token está expirado, efetue o login novamente: " + e.getMessage());
				e.printStackTrace();
			} finally {
				liberacaoCors(response);
			}
		}
		return null;
	}

	private void liberacaoCors(HttpServletResponse response) {
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		if (response.getHeader("Access-Control-Allow-Header") == null) {
			response.addHeader("Access-Control-Allow-Header", "*");
		}
		if (response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}
		if (response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}
}
