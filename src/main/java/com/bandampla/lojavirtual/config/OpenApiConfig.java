/**
 * 
 */
package com.bandampla.lojavirtual.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 13 de jun. de 2026
 */

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI lojaVirtualOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Loja Virtual - API REST").description("API para gestão de Loja Virtual")
						.version("v1.0.0")
						.contact(new Contact().name("Nilton Brito").email("nilton.brito@outlook.com")
								.url("https://github.com/niltonbrito")))
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				.components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("bearerAuth",
						new SecurityScheme().name("Authorization").type(SecurityScheme.Type.HTTP).scheme("bearer")
								.bearerFormat("JWT")));
	}
}