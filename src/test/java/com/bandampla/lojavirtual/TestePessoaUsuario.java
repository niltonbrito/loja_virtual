package com.bandampla.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.bandampla.lojavirtual.controller.PessoaController;
import com.bandampla.lojavirtual.enums.TipoEndereco;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.Endereco;
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
		pessoaJuridica.setEmail("nilton.brito@rbdimagem.com.br");
		pessoaJuridica.setTelefone("719920456500");
		pessoaJuridica.setInscricaoEstadual("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setInscricaoMunicipal("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNomeFantasia("Bandampla");
		pessoaJuridica.setRazaoSocial("BAndampla Sistemas");
		pessoaJuridica.setTipoPessoa("JURIDICA");
		
		Endereco endereco = new Endereco();
		endereco.setRua("Rua do Ceu");
		endereco.setNumero("35");
		endereco.setBairro("VAle dos Lagos");
		endereco.setComplemento("AP 02");
		endereco.setCidade("Salvador");
		endereco.setCep("41256255");
		endereco.setUf("BA");
		endereco.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco.setEmpresa(pessoaJuridica);
		endereco.setPessoa(pessoaJuridica);

		Endereco endereco1 = new Endereco();
		endereco1.setRua("Rua do Capim");
		endereco1.setNumero("95");
		endereco1.setBairro("VAle dos Lagos");
		endereco1.setComplemento("AP 99");
		endereco1.setCidade("Salvador");
		endereco1.setCep("41256275");
		endereco1.setUf("BA");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setPessoa(pessoaJuridica);

		pessoaJuridica.getEnderecos().add(endereco);
		pessoaJuridica.getEnderecos().add(endereco1);
		
		pessoaJuridica = pessoaController.salvarPessoaJuridica(pessoaJuridica).getBody();

		assertEquals(true, pessoaJuridica.getId() > 0);
		
		for (Endereco enderecos : pessoaJuridica.getEnderecos()) {
			assertEquals(true, enderecos.getId() > 0);
		}
		
		assertEquals(2, pessoaJuridica.getEnderecos().size());
		
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
