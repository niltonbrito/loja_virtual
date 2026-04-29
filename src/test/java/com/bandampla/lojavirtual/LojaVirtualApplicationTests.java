package com.bandampla.lojavirtual;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bandampla.lojavirtual.controller.AcessoController;
import com.bandampla.lojavirtual.model.Acesso;
import com.bandampla.lojavirtual.repository.AcessoRepository;

import junit.framework.TestCase;

@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests extends TestCase {

	@Autowired
	private AcessoController acessoController;

	@Autowired
	private AcessoRepository acessoRepository;

	@Test
	public void testCadastrarAcesso() {
		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_ADMIN");

		assertEquals(true, acesso.getId() == null);

		/* Teste de cadastro */
		// Gravou no banco de dados
		acessoController.salvarAcesso(acesso);

		/* Teste de Validação */
		// Valida dados da forma correta
		assertEquals("ROLE_ADMIN", acesso.getDescricao());

		assertEquals(true, acesso.getId() > 0);
		/* Teste de carregamento */
		Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();

		assertEquals(acesso.getId(), acesso2.getId());

		/* Teste de delete */

		acessoRepository.deleteById(acesso2.getId());
		acessoRepository.flush(); // Roda esse SQL de delete no banco
		Acesso acesso3 = acessoRepository.findById(acesso.getId()).orElse(null);
		assertEquals(true, acesso3 == null);

		/*Teste de Query*/		
		acesso = new Acesso(); //Objeto recebe uma nova instância
		acesso.setDescricao("ROLE_ALUNO"); //seta o valor no objeto
		acesso = acessoController.salvarAcesso(acesso).getBody(); // Chama o metodo salvar do controller passando o objeto
		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase()); //cria uma lista de Acessos buscando os acessos no banco
		assertEquals(1, acessos.size()); // valida a quantidade de acessos com o valor passado, travando somente em um unico resultado
		acessoRepository.deleteById(acesso.getId()); // deletea o valor no banco
	}

}
