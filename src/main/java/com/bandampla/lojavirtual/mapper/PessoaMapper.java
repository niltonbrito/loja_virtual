package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.bandampla.lojavirtual.dto.ProdutoDTO;
import com.bandampla.lojavirtual.model.Produto;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "categoriaProduto.id", target = "categoriaId") // 🛠️ CORREÇÃO DA ROTA DO ATRIBUTO
	@Mapping(source = "marcaProduto.id", target = "marcaId") // 🛠️ CORREÇÃO DA ROTA DO ATRIBUTO
	@Mapping(source = "nome", target = "nome", qualifiedByName = "limparTexto")
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	ProdutoDTO toDTO(Produto model);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "categoriaId", target = "categoriaProduto.id") // 🛠️ CORREÇÃO DA ROTA INVERSA
	@Mapping(source = "marcaId", target = "marcaProduto.id") // 🛠️ CORREÇÃO DA ROTA INVERSA
	@Mapping(source = "nome", target = "nome", qualifiedByName = "limparTexto")
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	Produto toModel(ProdutoDTO dto);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "categoriaId", target = "categoriaProduto.id")
	@Mapping(source = "marcaId", target = "marcaProduto.id")
	@Mapping(source = "nome", target = "nome", qualifiedByName = "limparTexto")
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	void atualizarCamposDoProduto(ProdutoDTO dto, @MappingTarget Produto existente);

	@Named("limparTexto")
	default String limparTexto(String texto) {
		return texto == null ? null : texto.trim();
	}
}
