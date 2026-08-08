package com.bandampla.lojavirtual.security;

import javax.servlet.http.HttpSessionListener;
import javax.validation.Validator;

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
	private final OnboardingJwtAuthenticationFilter onboardingJwtAuthenticationFilter;
	private final Validator validator;

	private static final String[] SWAGGER_WHITELIST = { "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
			"/swagger-resources/**", "/webjars/**" };

	public WebConfigSecurity(ImplementacaoUserDetailsService userDetailsService,
			JWTTokenAutenticacaoService tokenService, PasswordEncoder passwordEncoder, ObjectMapper objectMapper,
			OnboardingJwtAuthenticationFilter onboardingJwtAuthenticationFilter, Validator validator) {

		this.userDetailsService = userDetailsService;
		this.tokenService = tokenService;
		this.passwordEncoder = passwordEncoder;
		this.objectMapper = objectMapper;
		this.onboardingJwtAuthenticationFilter = onboardingJwtAuthenticationFilter;
		this.validator = validator;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		JWTLoginFilter loginFilter = new JWTLoginFilter("/auth/login", authenticationManager(), tokenService,
				objectMapper, validator);

		http.cors().and().csrf().disable()

				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and().authorizeRequests()

				.antMatchers("/", "/index", "/auth/login", "/auth/register", "/auth/confirm-email",
						"/auth/register/empresa", "/auth/onboarding/confirmar-email", "/auth/onboarding/login")
				.permitAll()

				.antMatchers(HttpMethod.POST, "/public/lojas/*/clientes/register", "/public/clientes/confirm-email",
						"/public/lojas/*/auth/login", "/public/lojas/*/auth/forgot-password")
				.permitAll()

				.antMatchers("/onboarding/**").hasRole("ONBOARDING")

				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

				.antMatchers(SWAGGER_WHITELIST).permitAll()

				.anyRequest().authenticated()

				.and().addFilterBefore(onboardingJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class).addFilterBefore(
						new JwtApiAuntenticacaoFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
}