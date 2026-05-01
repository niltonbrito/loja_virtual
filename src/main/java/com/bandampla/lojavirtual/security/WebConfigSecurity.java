/**
 * 
 */
package com.bandampla.lojavirtual.security;

import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bandampla.lojavirtual.service.ImplementacaoUserDetailsService;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   27 de abr. de 2026
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener{
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	/*Ignora algumas URL livres de autenticação*/
	@Override
	public void configure(WebSecurity web) throws Exception {

		//Ignorando as URL na Validação
		web.ignoring()
		.antMatchers(HttpMethod.GET, "/salvarAcesso", "/deletarAcesso")
		.antMatchers(HttpMethod.POST, "/salvarAcesso", "/deletarAcesso");
	}

	/*Ira consultar o user no banco Spring Security*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
