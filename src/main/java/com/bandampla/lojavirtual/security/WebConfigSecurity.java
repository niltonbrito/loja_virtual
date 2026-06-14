package com.bandampla.lojavirtual.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bandampla.lojavirtual.service.ImplementacaoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;

	@Autowired
	private JWTTokenAutenticacaoService jwtTokenAutenticacaoService;

	private static final String[] SWAGGER_WHITELIST = { "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
			"/swagger-resources/**", "/webjars/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable()

				.authorizeRequests().antMatchers("/", "/index").permitAll().antMatchers(HttpMethod.OPTIONS, "/**")
				.permitAll()

				// Swagger liberado
				.antMatchers(SWAGGER_WHITELIST).permitAll()

				// Login liberado
				.antMatchers("/auth/login").permitAll()

				// Qualquer outra requisição precisa de autenticação
				.anyRequest().authenticated()

				.and().logout().logoutSuccessUrl("/index").logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

				.and()

				// Filtro de login
				.addFilterBefore(
						new JWTLoginFilter("/auth/login", authenticationManager(), jwtTokenAutenticacaoService),
						UsernamePasswordAuthenticationFilter.class)

				// Filtro que valida o token
				.addFilterBefore(new JwtApiAuntenticacaoFilter(jwtTokenAutenticacaoService),
						UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Se quiser liberar arquivos estáticos
		// web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
