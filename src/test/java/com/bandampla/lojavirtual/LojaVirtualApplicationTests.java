package com.bandampla.lojavirtual;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bandampla.lojavirtual.controller.AcessoController;
import com.bandampla.lojavirtual.model.Acesso;
import com.bandampla.lojavirtual.repository.AcessoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;

@SpringBootTest(classes = LojaVirtualApplication.class)
class LojaVirtualApplicationTests extends TestCase {

	@Autowired
	private AcessoController acessoController;

	@Autowired
	private AcessoRepository acessoRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	/* Teste Mokito para o endPoint de cadastrar */
	@Test
	public void testRestApiCadstroAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_COMPRADOR");

		ObjectMapper objectMapperAcesso = new ObjectMapper();

		ResultActions resultApiAcesso = mockMvc.perform(
				MockMvcRequestBuilders.post("/salvarAcesso").content(objectMapperAcesso.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		/* Checagem do retorno */
		System.out.println("Retorno da API: " + resultApiAcesso.andReturn().getResponse().getContentAsString());
		/* Converter o retorno da API para um objeto de acesso */
		Acesso objetResultApiAcesso = objectMapperAcesso
				.readValue(resultApiAcesso.andReturn().getResponse().getContentAsString(), Acesso.class);
		/* Validação */
		assertEquals(acesso.getDescricao(), objetResultApiAcesso.getDescricao());
	}

	/* Teste Mokito para o endPoint de cadastrar */
	@Test
	public void testRestApiUpdateAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_COMPRADOR");

		ObjectMapper objectMapperAcesso = new ObjectMapper();

		ResultActions resultApiAcesso = mockMvc.perform(
				MockMvcRequestBuilders.post("/salvarAcesso").content(objectMapperAcesso.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		/* Checagem do retorno */
		System.out.println("Retorno da API: " + resultApiAcesso.andReturn().getResponse().getContentAsString());
		/* Converter o retorno da API para um objeto de acesso */
		Acesso objetResultApiAcesso = objectMapperAcesso
				.readValue(resultApiAcesso.andReturn().getResponse().getContentAsString(), Acesso.class);
		/* Validação */
		assertEquals(acesso.getDescricao(), objetResultApiAcesso.getDescricao());
	}
	/* Teste Mokito para o endPoint de cadastrar */
	@Test
	public void testRestApiDeletarAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_DELETE");
		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapperAcesso = new ObjectMapper();

		ResultActions resultApiAcesso = mockMvc.perform(
				MockMvcRequestBuilders.post("/deletarAcesso").content(objectMapperAcesso.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
		/* Checagem do retorno */
		System.out.println("Retorno da API: " + resultApiAcesso.andReturn().getResponse().getContentAsString());
		System.out.println("Status de retorno da API: " + resultApiAcesso.andReturn().getResponse().getStatus());
		/* Validação */
		assertEquals("Acesso Removido", resultApiAcesso.andReturn().getResponse().getContentAsString());
		assertEquals(200, resultApiAcesso.andReturn().getResponse().getStatus());

	}

	/* Teste Mokito para o endPoint de cadastrar */
	@Test
	public void testRestApiDeletarPorIdAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_DELETE_ID");
		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapperAcesso = new ObjectMapper();

		ResultActions resultApiAcesso = mockMvc.perform(MockMvcRequestBuilders
				.delete("/deletarAcessoPorId/" + acesso.getId()).content(objectMapperAcesso.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
		/* Checagem do retorno */
		System.out.println("Retorno da API: " + resultApiAcesso.andReturn().getResponse().getContentAsString());
		System.out.println("Status de retorno da API: " + resultApiAcesso.andReturn().getResponse().getStatus());
		/* Validação */
		assertEquals("Acesso Removido", resultApiAcesso.andReturn().getResponse().getContentAsString());
		assertEquals(200, resultApiAcesso.andReturn().getResponse().getStatus());

	}

	/* Teste Mokito para o endPoint de cadastrar */
	@Test
	public void testRestApiBuscarPorIdAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_BUSCAR_ID");
		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapperAcesso = new ObjectMapper();

		ResultActions resultApiAcesso = mockMvc.perform(MockMvcRequestBuilders
				.get("/buscarAcessoPorId/" + acesso.getId()).content(objectMapperAcesso.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));
		/* Checagem do retorno */
		System.out.println("Retorno da API: " + resultApiAcesso.andReturn().getResponse().getContentAsString());
		System.out.println("Status de retorno da API: " + resultApiAcesso.andReturn().getResponse().getStatus());
		/* Converter o retorno da API para um objeto de acesso */
		Acesso objetResultApiAcesso = objectMapperAcesso
				.readValue(resultApiAcesso.andReturn().getResponse().getContentAsString(), Acesso.class);
		/* Validação */
		assertEquals(acesso.getDescricao(), objetResultApiAcesso.getDescricao());
		assertEquals(acesso.getId(), objetResultApiAcesso.getId());
		assertEquals(200, resultApiAcesso.andReturn().getResponse().getStatus());
	}

	/* Teste Mokito para o endPoint de cadastrar */
	@Test
	public void testRestApiBuscarPorDescricaoAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_TESTE_BUSCAR_DESCRICAO");
		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapperAcesso = new ObjectMapper();

		ResultActions resultApiAcesso = mockMvc.perform(MockMvcRequestBuilders
				.get("/buscarPorDescricao/TE_BUSCAR_DESCRICAO").content(objectMapperAcesso.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		/* Checagem do retorno */
		System.out.println("Retorno da API: " + resultApiAcesso.andReturn().getResponse().getContentAsString());
		System.out.println("Status de retorno da API: " + resultApiAcesso.andReturn().getResponse().getStatus());

		/* Converter o retorno da API para um objeto de Lista Acesso */
		List<Acesso> objetResultApiAcessoList = objectMapperAcesso.readValue(
				resultApiAcesso.andReturn().getResponse().getContentAsString(), new TypeReference<List<Acesso>>() {
				});

		/* Validação */
		assertEquals(1, objetResultApiAcessoList.size());
		assertEquals(acesso.getDescricao(), objetResultApiAcessoList.get(0).getDescricao());
		assertEquals(200, resultApiAcesso.andReturn().getResponse().getStatus());

		acessoRepository.deleteById(acesso.getId());
	}

	/* Teste Junit unitario */
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

		/* Teste de Query */
		acesso = new Acesso(); // Objeto recebe uma nova instância
		acesso.setDescricao("ROLE_ALUNO"); // seta o valor no objeto
		acesso = acessoController.salvarAcesso(acesso).getBody(); // Chama o metodo salvar do controller passando o objeto
		
		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase()); // cria uma lista de Acessos buscando os acessos no banco
		assertEquals(1, acessos.size()); // valida a quantidade de acessos com o valor passado, travando somente em um unico resultado
		acessoRepository.deleteById(acesso.getId()); // deletea o valor no banco
	}

}
