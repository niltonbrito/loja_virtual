package com.bandampla.lojavirtual.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bandampla.lojavirtual.service.ImplementacaoUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {

	private final ImplementacaoUserDetailsService userDetailsService;
	private final JWTTokenAutenticacaoService tokenService;
	private final PasswordEncoder passwordEncoder;
	private final ObjectMapper objectMapper;
	private static final String[] SWAGGER_WHITELIST = { "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
			"/swagger-resources/**", "/webjars/**" };

	public WebConfigSecurity(ImplementacaoUserDetailsService userDetailsService,
			JWTTokenAutenticacaoService tokenService, PasswordEncoder passwordEncoder, ObjectMapper objectMapper) {
		this.userDetailsService = userDetailsService;
		this.tokenService = tokenService;
		this.passwordEncoder = passwordEncoder;
		this.objectMapper = objectMapper;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		JWTLoginFilter loginFilter = new JWTLoginFilter("/auth/login", authenticationManager(), tokenService,
				objectMapper);

		http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeRequests()
				.antMatchers("/", "/index", "/auth/login", " /auth/register", "/auth/confirm-email",
						"/auth/onboarding/login")
				.permitAll().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().antMatchers(SWAGGER_WHITELIST)
				.permitAll().anyRequest().authenticated().and()
				.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class).addFilterBefore(
						new JwtApiAuntenticacaoFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new
		// BCryptPasswordEncoder());
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
}
