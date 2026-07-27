package com.bandampla.lojavirtual.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bandampla.lojavirtual.dto.PessoaFisicaDTO;
import com.bandampla.lojavirtual.dto.PessoaJuridicaDTO;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

	@Mapping(source = "setor.id", target = "setorId")
	@Mapping(source = "empresa.id", target = "empresaId")
	PessoaFisicaDTO toDTO(PessoaFisica model);

	@Mapping(source = "setorId", target = "setor.id")
	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(target = "enderecos", ignore = true)
	PessoaFisica toModel(PessoaFisicaDTO dto);

	@Mapping(source = "setor.id", target = "setorId")
	@Mapping(source = "matriz.id", target = "matrizId")
	@Mapping(source = "empresa.id", target = "empresaId")
	PessoaJuridicaDTO toDTO(PessoaJuridica model);

	@Mapping(source = "setorId", target = "setor.id")
	@Mapping(source = "matrizId", target = "matriz.id")
	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(target = "enderecos", ignore = true)
	PessoaJuridica toModel(PessoaJuridicaDTO dto);

	List<PessoaJuridicaDTO> toJuridicaDTOList(List<PessoaJuridica> models);
}