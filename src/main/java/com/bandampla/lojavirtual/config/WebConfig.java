package com.bandampla.lojavirtual.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bandampla.lojavirtual.util.AcessoInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final AcessoInterceptor acessoInterceptor;

	public WebConfig(AcessoInterceptor acessoInterceptor) {
		this.acessoInterceptor = acessoInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(acessoInterceptor).addPathPatterns("/**").excludePathPatterns("/auth/login",
				"/swagger-ui/**", "/v3/api-docs/**");
	}
}