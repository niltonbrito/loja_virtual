package com.bandampla.lojavirtual;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.bandampla.lojavirtual.dto.CategoriaProdutoDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.service.CategoriaProdutoService;
import com.bandampla.lojavirtual.util.ValidaCNPJ;

import junit.framework.TestCase;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
class TesteCategoriaProduto extends TestCase {

	@Autowired
	private CategoriaProdutoService categoriaProdutoService;

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;

	@Test
	public void testCadastroCategoriaProduto() throws ExceptionCustom {

		CategoriaProdutoDTO dto = new CategoriaProdutoDTO();

		Optional<PessoaJuridica> pessoaJuridicaOpt = pessoaJuridicaRepository
				.findByCnpj(ValidaCNPJ.cnpjSemMascara("64.916.306/0001-75"));

		dto.setNomeDescricao("Casa");
		dto.setEmpresaId(pessoaJuridicaOpt.get().getId());

		dto = categoriaProdutoService.salvar(dto);

		assertEquals(true, dto.getId() > 0);

	}

}
