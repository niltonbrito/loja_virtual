package com.bandampla.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bandampla.lojavirtual.controller.AcessoController;
import com.bandampla.lojavirtual.model.Acesso;

import junit.framework.TestCase;

@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests extends TestCase{

	@Autowired
	private AcessoController acessoController;

	// @Autowired
	// private AcessoRepository acessoRepository;

	@Test
	public void testCadastrarAcesso() {
		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_ADMIN");

		acessoController.salvarAcesso(acesso);
	}

}
