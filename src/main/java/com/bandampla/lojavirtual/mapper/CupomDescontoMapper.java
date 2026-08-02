package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.bandampla.lojavirtual.dto.CupomDescontoDTO;
import com.bandampla.lojavirtual.model.CupomDesconto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CupomDescontoMapper {

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "codigoDescricao", target = "codigoDescricao", qualifiedByName = "limparTexto")
	CupomDesconto toModel(CupomDescontoDTO dto);

	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "codigoDescricao", target = "codigoDescricao", qualifiedByName = "limparTexto")
	CupomDescontoDTO toDTO(CupomDesconto model);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "codigoDescricao", target = "codigoDescricao", qualifiedByName = "limparTexto")
	void atualizarCamposDoCupomDesconto(CupomDescontoDTO dto, @MappingTarget CupomDesconto cupom);

	@Named("limparTexto")
	default String limparTexto(String texto) {
		return texto == null ? null : texto.trim();
	}
}