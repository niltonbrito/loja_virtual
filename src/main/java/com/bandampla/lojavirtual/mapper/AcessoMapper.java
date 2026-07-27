package com.bandampla.lojavirtual.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import com.bandampla.lojavirtual.dto.AcessoDTO;
import com.bandampla.lojavirtual.model.Acesso;

@Mapper(componentModel = "spring")
public interface AcessoMapper {
	AcessoDTO toDTO(Acesso model);

	Acesso toModel(AcessoDTO dto);

	List<AcessoDTO> toDTOList(List<Acesso> models);
}