package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.bandampla.lojavirtual.dto.PalavraProibidaDTO;
import com.bandampla.lojavirtual.model.PalavraProibida;

@Mapper(componentModel = "spring")
public interface PalavraProibidaMapper {

	@Mapping(source = "termo", target = "termo", qualifiedByName = "limparTexto")
	PalavraProibidaDTO toDTO(PalavraProibida model);

	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(source = "termo", target = "termo", qualifiedByName = "limparTexto")
	PalavraProibida toModel(PalavraProibidaDTO dto);

	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(source = "termo", target = "termo", qualifiedByName = "limparTexto")
	void atualizarTermoPalavraProibida(PalavraProibidaDTO dto, @MappingTarget PalavraProibida existente);

	@Named("limparTexto")
	default String limparTexto(String texto) {
		return texto == null ? null : texto.trim();
	}
}