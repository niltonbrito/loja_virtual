package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bandampla.lojavirtual.dto.NotaFiscalVendaDTO;
import com.bandampla.lojavirtual.model.NotaFiscalVenda;

@Mapper(componentModel = "spring")
public interface NotaFiscalVendaMapper {

	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "vendaCompraLojaVirtual.id", target = "vendaLojaVirtualId")
	NotaFiscalVendaDTO toDTO(NotaFiscalVenda model);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "vendaLojaVirtualId", target = "vendaCompraLojaVirtual.id")
	NotaFiscalVenda toModel(NotaFiscalVendaDTO dto);
}