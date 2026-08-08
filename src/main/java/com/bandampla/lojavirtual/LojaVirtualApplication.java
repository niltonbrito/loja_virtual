package com.bandampla.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableTransactionManagement // Anotação para gerenciar as transações com o banco de dados.
public class LojaVirtualApplication implements AsyncConfigurer {

	public static void main(String[] args) {

		// System.out.println(new BCryptPasswordEncoder().encode("admin123"));

		SpringApplication.run(LojaVirtualApplication.class, args);
	}
}
