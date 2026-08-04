package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.bandampla.lojavirtual.dto.StatusRastreioDTO;
import com.bandampla.lojavirtual.model.StatusRastreio;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StatusRastreioMapper {

	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "vendaLojaVirtual.id", target = "vendaLojaVirtualId")
	@Mapping(source = "compraLojaVirtual.id", target = "compraLojaVirtualId")
	StatusRastreioDTO toDTO(StatusRastreio model);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "vendaLojaVirtualId", target = "vendaLojaVirtual.id")
	@Mapping(source = "compraLojaVirtualId", target = "compraLojaVirtual.id")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	StatusRastreio toModel(StatusRastreioDTO dto);
}
