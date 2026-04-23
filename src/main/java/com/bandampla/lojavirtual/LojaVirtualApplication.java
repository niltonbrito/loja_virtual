package com.bandampla.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "com.bandampla.lojavirtual.model") //Deixa explicito para o spring que este e o pacote de Model do projeto
@ComponentScan(basePackages = {"com.*"})  //Informa ao spring para varrer todo o projeto a procura de anotações e recusos ativado
@EnableJpaRepositories(basePackages = {"com.bandampla.lojavirtual.repository"}) //Deixa explicito para o spring que este e o pacote de Repository do projeto
@EnableTransactionManagement  //Anotação para gerenciar as transações com o banco de dados.
public class LojaVirtualApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualApplication.class, args);
	}

}
