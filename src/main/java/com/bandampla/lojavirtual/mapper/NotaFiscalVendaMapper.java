package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.bandampla.lojavirtual.dto.NotaFiscalVendaDTO;
import com.bandampla.lojavirtual.model.NotaFiscalVenda;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotaFiscalVendaMapper {

	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "vendaLojaVirtual.id", target = "vendaLojaVirtualId")
	NotaFiscalVendaDTO toDTO(NotaFiscalVenda model);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "vendaLojaVirtualId", target = "vendaLojaVirtual.id")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	NotaFiscalVenda toModel(NotaFiscalVendaDTO dto);
}