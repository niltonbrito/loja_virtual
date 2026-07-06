package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.bandampla.lojavirtual.dto.ProdutoDTO;
import com.bandampla.lojavirtual.model.Produto;


@Mapper(componentModel = "spring")
public interface ProdutoMapper {

	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "categoriaProduto.id", target = "categoriaId")
	@Mapping(source = "marcaProduto.id", target = "marcaId")
	@Mapping(source = "nome", target = "nome", qualifiedByName = "limparTexto")
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	@Mapping(target = "imagens", ignore = true) // ✔ CORREÇÃO CRÍTICA
	@Mapping(target = "fornecedorId", ignore = true) // ✔ CORREÇÃO CRÍTICAcodigo
	@Mapping(target = "codigoProdutoFornecedor", ignore = true) // ✔ CORREÇÃO CRÍTICA
	ProdutoDTO toDTO(Produto model);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "categoriaId", target = "categoriaProduto.id")
	@Mapping(source = "marcaId", target = "marcaProduto.id")
	@Mapping(source = "nome", target = "nome", qualifiedByName = "limparTexto")
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	@Mapping(target = "imagens", ignore = true) // ✔ CORREÇÃO CRÍTICA
	Produto toModel(ProdutoDTO dto);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "categoriaId", target = "categoriaProduto.id")
	@Mapping(source = "marcaId", target = "marcaProduto.id")
	@Mapping(source = "nome", target = "nome", qualifiedByName = "limparTexto")
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	@Mapping(target = "imagens", ignore = true) // ✔ CORREÇÃO CRÍTICA
	void atualizarCamposDoProduto(ProdutoDTO dto, @MappingTarget Produto existente);

	@Named("limparTexto")
	default String limparTexto(String texto) {
		return texto == null ? null : texto.trim();
	}
}
