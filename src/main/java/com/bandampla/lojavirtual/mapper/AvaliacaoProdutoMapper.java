package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.bandampla.lojavirtual.dto.AvaliacaoProdutoDTO;
import com.bandampla.lojavirtual.model.AvaliacaoProduto;

@Mapper(componentModel = "spring")
public interface AvaliacaoProdutoMapper {

	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "produto.id", target = "produtoId")
	@Mapping(source = "pessoa.id", target = "pessoaId") // 🔥 Corrigido o mapeamento reverso
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	AvaliacaoProdutoDTO toDTO(AvaliacaoProduto model);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "produtoId", target = "produto.id")
	@Mapping(source = "pessoaId", target = "pessoa.id") // 🔥 Corrigido o mapeamento direto
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	AvaliacaoProduto toModel(AvaliacaoProdutoDTO dto);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "produtoId", target = "produto.id")
	@Mapping(source = "pessoaId", target = "pessoa.id")
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	void atualizarCamposDaAvaliacao(AvaliacaoProdutoDTO dto, @MappingTarget AvaliacaoProduto existente); // 🔥 Corrigida
																											// a
																											// assinatura
																											// do tipo
																											// de model

	@Named("limparTexto")
	default String limparTexto(String texto) {
		return texto == null ? null : texto.trim();
	}
}
