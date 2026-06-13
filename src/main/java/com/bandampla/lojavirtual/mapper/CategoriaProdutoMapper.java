package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.bandampla.lojavirtual.dto.CategoriaProdutoDTO;
import com.bandampla.lojavirtual.model.CategoriaProduto;

@Mapper(componentModel = "spring")
public interface CategoriaProdutoMapper {

	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "nomeDescricao", target = "nomeDescricao", qualifiedByName = "limparTexto")
	CategoriaProdutoDTO toDTO(CategoriaProduto model);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "nomeDescricao", target = "nomeDescricao", qualifiedByName = "limparTexto")
	CategoriaProduto toModel(CategoriaProdutoDTO dto);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "nomeDescricao", target = "nomeDescricao", qualifiedByName = "limparTexto")
	void atualizarCamposCategoriaProduto(CategoriaProdutoDTO dto, @MappingTarget CategoriaProduto existente);

	@Named("limparTexto")
	default String limparTexto(String texto) {
		return texto == null ? null : texto.trim();
	}
}