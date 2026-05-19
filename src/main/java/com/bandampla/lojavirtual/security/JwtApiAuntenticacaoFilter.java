package com.bandampla.lojavirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtApiAuntenticacaoFilter extends GenericFilterBean {

    private final JWTTokenAutenticacaoService jwtTokenAutenticacaoService;

    public JwtApiAuntenticacaoFilter(JWTTokenAutenticacaoService jwtTokenAutenticacaoService) {
        this.jwtTokenAutenticacaoService = jwtTokenAutenticacaoService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            Authentication authentication = jwtTokenAutenticacaoService
                    .getAuthentication((HttpServletRequest) request, (HttpServletResponse) response);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Ocorreu um erro no sistema, contacte o administrador: \n" + e.getMessage());
        }
    }
}
