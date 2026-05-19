package com.bandampla.lojavirtual;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EntityScan(basePackages = "com.bandampla.lojavirtual.model") //Deixa explicito para o spring que este e o pacote de Model do projeto
@ComponentScan(basePackages = {"com.*"})  //Informa ao spring para varrer todo o projeto a procura de anotações e recusos ativado
@EnableJpaRepositories(basePackages = {"com.bandampla.lojavirtual.repository"}) //Deixa explicito para o spring que este e o pacote de Repository do projeto
@EnableTransactionManagement  //Anotação para gerenciar as transações com o banco de dados.
public class LojaVirtualApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		
		//System.out.println(new BCryptPasswordEncoder().encode("admin123"));
		
		SpringApplication.run(LojaVirtualApplication.class, args);
	}
	
	@Override
	@Bean
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Assyncrono Thread");
		executor.initialize();
		return executor;
	}

}
