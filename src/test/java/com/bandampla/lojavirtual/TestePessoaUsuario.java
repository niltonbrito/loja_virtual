package com.bandampla.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.bandampla.lojavirtual.controller.PessoaController;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.PessoaJuridica;

import junit.framework.TestCase;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
class TestePessoaUsuario extends TestCase {

	@Autowired
	private PessoaController pessoaController;

	@Test
	public void testCadastroPessoa() throws ExceptionCustom {

		PessoaJuridica pessoaJuridica = new PessoaJuridica();

		pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNome("Nilton Brito");
		pessoaJuridica.setEmail("nilton.brdi8to@outlook.com");
		pessoaJuridica.setTelefone("719920456500");
		pessoaJuridica.setInscricaoEstadual("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setIncriscaoMunicipal("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNomeFantasia("Bandampla");
		pessoaJuridica.setRazaoSocial("BAndampla Sistemas");
		
		pessoaController.salvarPessoaJuridica(pessoaJuridica);

		
		/*
		 * PessoaFisica pessoaFisica = new PessoaFisica();
		 * 
		 * pessoaFisica.setCpf("02361173514"); pessoaFisica.setNome("Nilton Brito");
		 * pessoaFisica.setEmail("nilton.brito@outlook.com");
		 * pessoaFisica.setTelefone("719920456500");
		 * pessoaFisica.setEmpresa(pessoaFisica);
		 * 
		 * pessoaRepository.save(pessoaFisica);
		 */
		 
	}

}
