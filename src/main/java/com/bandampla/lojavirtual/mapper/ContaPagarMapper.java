package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.bandampla.lojavirtual.dto.ContaPagarDTO;
import com.bandampla.lojavirtual.model.ContaPagar;

@Mapper(componentModel = "spring")
public interface ContaPagarMapper {

	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "pessoa.id", target = "pessoaId")
	@Mapping(source = "pessoaFornecedor.id", target = "pessoaFornecedorId")
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	ContaPagarDTO toDTO(ContaPagar model);

	// 🛠️ AJUSTE DE OURO: Ignora a instanciação automática das classes complexas/abstratas
	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(target = "pessoa", ignore = true)
	@Mapping(target = "pessoaFornecedor", ignore = true)
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	ContaPagar toModel(ContaPagarDTO dto);

	// 🛠️ AJUSTE DE OURO: Ignora a sobreposição automática das classes complexas/abstratas no update
	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(target = "pessoa", ignore = true)
	@Mapping(target = "pessoaFornecedor", ignore = true)
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	void atualizarCamposDaConta(ContaPagarDTO dto, @MappingTarget ContaPagar existente);

	@Named("limparTexto")
	default String limparTexto(String texto) {
		return texto == null ? null : texto.trim();
	}
}
