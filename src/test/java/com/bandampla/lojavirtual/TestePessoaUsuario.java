package com.bandampla.lojavirtual;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.bandampla.lojavirtual.controller.PessoaController;
import com.bandampla.lojavirtual.enums.TipoCadastro;
import com.bandampla.lojavirtual.enums.TipoEndereco;
import com.bandampla.lojavirtual.enums.TipoPessoa;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.Endereco;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.util.ValidaCNPJ;
import com.bandampla.lojavirtual.util.ValidaCPF;

import junit.framework.TestCase;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
class TestePessoaUsuario extends TestCase {

	@Autowired
	private PessoaController pessoaController;

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;

	@Test
	public void testCadastroPessoaJuridica() throws ExceptionCustom {

		PessoaJuridica pessoaJuridica = new PessoaJuridica();

		pessoaJuridica.setCnpj(ValidaCNPJ.cnpjSemMascara("64.916.306/0001-75"));
		pessoaJuridica.setNome("Nilton Brito");
		pessoaJuridica.setEmail("nilton.brito@rbdimagooem.com.br");
		pessoaJuridica.setTelefone("719920456500");
		pessoaJuridica.setInscricaoEstadual("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setInscricaoMunicipal("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNomeFantasia("Bandampla");
		pessoaJuridica.setRazaoSocial("BAndampla Sistemas");
		pessoaJuridica.setTipoCadastro(TipoCadastro.EMPRESA);
		pessoaJuridica.setTipoPessoa(TipoPessoa.JURIDICA);

		Endereco endereco = new Endereco();
		endereco.setRua("Rua do Ceu");
		endereco.setNumero("35");
		endereco.setBairro("VAle dos Lagos");
		endereco.setComplemento("AP 02");
		endereco.setCidade("Salvador");
		endereco.setCep("41256255");
		endereco.setUf("BA");
		endereco.setTipoEndereco(TipoEndereco.ENTREGA);
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
		endereco1.setPessoa(pessoaJuridica);

		pessoaJuridica.getEnderecos().add(endereco);
		pessoaJuridica.getEnderecos().add(endereco1);

		pessoaJuridica = pessoaController.salvarPessoaJuridica(pessoaJuridica).getBody();

		assertEquals(true, pessoaJuridica.getId() > 0);

		for (Endereco enderecos : pessoaJuridica.getEnderecos()) {
			assertEquals(true, enderecos.getId() > 0);
		}

		assertEquals(2, pessoaJuridica.getEnderecos().size());
	}

	@Test
	public void testCadastroPessoFisica() throws ExceptionCustom, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Optional<PessoaJuridica> pessoaJuridica = pessoaJuridicaRepository
				.findByCnpj(ValidaCNPJ.cnpjSemMascara("64.916.306/0001-75"));

		PessoaFisica pessoaFisica = new PessoaFisica();

		pessoaFisica.setCpf(ValidaCPF.cpfSemMascara("279.281.650-31"));
		pessoaFisica.setNome("Nilton Brito");
		pessoaFisica.setEmail("nilton.brito@outlook.com");
		pessoaFisica.setDataNascimento(LocalDate.parse("1985-10-07"));

		pessoaFisica.setTelefone("719920456500");
		pessoaFisica.setEmpresa(pessoaJuridica.get().getMatriz());
		pessoaFisica.setTipoCadastro(TipoCadastro.CLIENTE);
		pessoaFisica.setTipoPessoa(TipoPessoa.FISICA);

		Endereco endereco = new Endereco();
		endereco.setRua("Rua do Ceu");
		endereco.setNumero("35");
		endereco.setBairro("VAle dos Lagos");
		endereco.setComplemento("AP 02");
		endereco.setCidade("Salvador");
		endereco.setCep("41256255");
		endereco.setUf("BA");
		endereco.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco.setPessoa(pessoaFisica);

		Endereco endereco1 = new Endereco();
		endereco1.setRua("Rua do Capim");
		endereco1.setNumero("95");
		endereco1.setBairro("VAle dos Lagos");
		endereco1.setComplemento("AP 99");
		endereco1.setCidade("Salvador");
		endereco1.setCep("41256275");
		endereco1.setUf("BA");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setPessoa(pessoaFisica);

		pessoaFisica.getEnderecos().add(endereco);
		pessoaFisica.getEnderecos().add(endereco1);

		pessoaFisica = pessoaController.salvarPessoaFisica(pessoaFisica).getBody();

		assertEquals(true, pessoaFisica.getId() > 0);

		for (Endereco enderecos : pessoaFisica.getEnderecos()) {
			assertEquals(true, enderecos.getId() > 0);
		}

		assertEquals(2, pessoaFisica.getEnderecos().size());

	}

}
