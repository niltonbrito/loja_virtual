package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bandampla.lojavirtual.dto.StatusRastreioDTO;
import com.bandampla.lojavirtual.model.StatusRastreio;

@Mapper(componentModel = "spring")
public interface StatusRastreioMapper {

	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "vendaLojaVirtual.id", target = "vendaLojaVirtualId")
	StatusRastreioDTO toDTO(StatusRastreio model);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "vendaLojaVirtualId", target = "vendaLojaVirtual.id")
	@Mapping(target = "compraLojaVirtual", ignore = true)
	StatusRastreio toModel(StatusRastreioDTO dto);
}