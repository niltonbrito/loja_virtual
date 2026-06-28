package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.bandampla.lojavirtual.dto.NotaFiscalCompraDTO;
import com.bandampla.lojavirtual.model.NotaFiscalCompra;

@Mapper(componentModel = "spring")
public interface NotaFiscalCompraMapper {

    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "contaPagar.id", target = "contaPagarId")
    @Mapping(target = "itens", ignore = true) // itens tratados no service
    NotaFiscalCompraDTO toDTO(NotaFiscalCompra model);

    @Mapping(source = "empresaId", target = "empresa.id")
    @Mapping(target = "pessoa", ignore = true)
    @Mapping(target = "contaPagar", ignore = true)
    @Mapping(target = "itens", ignore = true)
    NotaFiscalCompra toModel(NotaFiscalCompraDTO dto);

    @Mapping(source = "empresaId", target = "empresa.id")
    @Mapping(target = "pessoa", ignore = true)
    @Mapping(target = "contaPagar", ignore = true)
    @Mapping(target = "itens", ignore = true)
    void atualizarCamposDaConta(NotaFiscalCompraDTO dto, @MappingTarget NotaFiscalCompra existente);
}
