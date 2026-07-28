package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.bandampla.lojavirtual.dto.FormaPagamentoDTO;
import com.bandampla.lojavirtual.model.FormaPagamento;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FormaPagamentoMapper {

	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	FormaPagamentoDTO toDTO(FormaPagamento model);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	FormaPagamento toModel(FormaPagamentoDTO dto);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "descricao", target = "descricao", qualifiedByName = "limparTexto")
	void atualizarCamposDaFormaPagamento(FormaPagamentoDTO dto, @MappingTarget FormaPagamento existente);

	@Named("limparTexto")
	default String limparTexto(String texto) {
		return texto == null ? null : texto.trim();
	}
}