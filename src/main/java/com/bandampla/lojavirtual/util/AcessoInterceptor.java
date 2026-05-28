package com.bandampla.lojavirtual.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AcessoInterceptor implements HandlerInterceptor {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		String rota = request.getRequestURI();

		String sql = "INSERT INTO tabela_acesso_end_point (nome_end_point, qtd_acesso) VALUES (?, 1) ON CONFLICT (nome_end_point) DO UPDATE SET qtd_acesso = tabela_acesso_end_point.qtd_acesso + 1";

		jdbcTemplate.update(sql, rota);

		return true;
	}
}
