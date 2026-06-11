package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.bandampla.lojavirtual.dto.MarcaProdutoDTO;
import com.bandampla.lojavirtual.model.MarcaProduto;

@Mapper(componentModel = "spring")
public interface MarcaProdutoMapper {

	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "nomeDescricao", target = "nomeDescricao", qualifiedByName = "limparTexto")
	MarcaProdutoDTO toDTO(MarcaProduto model);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "nomeDescricao", target = "nomeDescricao", qualifiedByName = "limparTexto")
	MarcaProduto toModel(MarcaProdutoDTO dto);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "nomeDescricao", target = "nomeDescricao", qualifiedByName = "limparTexto")
	void atualizarCamposDoProduto(MarcaProdutoDTO dto, @MappingTarget MarcaProduto existente);

	@Named("limparTexto")
	default String limparTexto(String texto) {
		return texto == null ? null : texto.trim();
	}
}
