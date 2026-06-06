package com.bandampla.lojavirtual;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.bandampla.lojavirtual.controller.CategoriaProdutoController;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.CategoriaProduto;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.util.ValidaCNPJ;

import junit.framework.TestCase;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
class TesteCategoriaProduto extends TestCase {

	@Autowired
	private CategoriaProdutoController categoriaProdutoController;

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;
	@Test
	public void testCadastroCategoriaProduto() throws ExceptionCustom {

		CategoriaProduto categoriaProduto = new CategoriaProduto();
		
		//Optional<PessoaJuridica> pessoaJuridicaOpt = pessoaJuridicaRepository.findByCnpj(ValidaCNPJ.cnpjSemMascara("64.916.306/0001-75"));


        // Buscar empresa
        PessoaJuridica empresaId = pessoaJuridicaRepository.findByCnpj(ValidaCNPJ.cnpjSemMascara("64.916.306/0001-75"))
                .orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));
        
		categoriaProduto.setNomeDescricao("Casa");
		//categoriaProduto.setEmpresa(pessoaJuridicaOpt.get());
		categoriaProduto.setEmpresa(empresaId);
		
		categoriaProduto = categoriaProdutoController.salvarCategoriaProduto(categoriaProduto).getBody();

		assertEquals(true, categoriaProduto.getId() > 0);
					
	}
	
}
