package com.bandampla.lojavirtual.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bandampla.lojavirtual.dto.CepDTO;
import com.bandampla.lojavirtual.dto.CnpjDTO;
import com.bandampla.lojavirtual.dto.PessoaFisicaDTO;
import com.bandampla.lojavirtual.dto.PessoaJuridicaDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pessoas e Clientes", description = "Operações de cadastro e consulta de clientes Compradores (Física e Jurídica) e APIs de apoio")
public interface PessoaControllerAPI {

	@Operation(summary = "Salvar Pessoa Jurídica", description = "Efetua o cadastro corporativo de uma nova empresa compradora no banco de dados.")
	@PostMapping("/pessoa/juridica")
	ResponseEntity<ResponseDefaultDTO<PessoaJuridicaDTO>> salvarPessoaJuridica(
			@Valid @RequestBody PessoaJuridicaDTO dto) throws ExceptionCustom;

	@Operation(summary = "Consulta Jurídica por Nome", description = "Filtra e retorna empresas compradoras a partir de trechos do nome ou razão social.")
	@GetMapping("/consulta/pessoa/juridicas/{nome}")
	ResponseEntity<ResponseDefaultDTO<List<PessoaJuridicaDTO>>> consultaPessoaJuridicaPorNome(@PathVariable String nome)
			throws ExceptionCustom;

	@Operation(summary = "Consulta Jurídica por CNPJ", description = "Localiza e retorna os registros da empresa compradora a partir do CNPJ informado.")
	@GetMapping("/consulta/pessoa/juridica/{cnpj}")
	ResponseEntity<ResponseDefaultDTO<List<PessoaJuridicaDTO>>> consultaPessoaJuridicaPorCnpj(@PathVariable String cnpj)
			throws ExceptionCustom;

	@Operation(summary = "Salvar Pessoa Física", description = "Registra um novo cliente comprador do tipo Pessoa Física no ecossistema da loja virtual.")
	@PostMapping("/pessoa/fisica")
	ResponseEntity<ResponseDefaultDTO<PessoaFisicaDTO>> salvarPessoaFisica(@Valid @RequestBody PessoaFisicaDTO dto)
			throws ExceptionCustom;

	@Operation(summary = "Consulta Externa de CEP", description = "Consome as APIs de apoio integradas para retornar informações de endereçamento com base no CEP.")
	@GetMapping("/consulta/cep/{cep}")
	ResponseEntity<ResponseDefaultDTO<CepDTO>> consultaCep(@PathVariable String cep) throws ExceptionCustom;

	@Operation(summary = "Consulta Cadastral de CNPJ", description = "Consome os serviços integrados para resgatar dados da Receita Federal com base no CNPJ.")
	@GetMapping("/consulta/cnpj/{cnpj}")
	ResponseEntity<ResponseDefaultDTO<CnpjDTO>> consultaCnpj(@PathVariable String cnpj) throws ExceptionCustom;
}