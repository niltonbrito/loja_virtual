/**
 * 
 */
package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.bandampla.lojavirtual.dto.ProdutoFornecedorDTO;
import com.bandampla.lojavirtual.model.ProdutoFornecedor;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   27 de jun. de 2026
 */
@Mapper(componentModel = "spring",
unmappedTargetPolicy = ReportingPolicy.IGNORE,
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProdutoFornecedorMapper {

    @Mapping(source = "fornecedor.id", target = "fornecedorId")
    ProdutoFornecedorDTO toDTO(ProdutoFornecedor model);

    @Mapping(source = "fornecedorId", target = "fornecedor.id")
    ProdutoFornecedor toModel(ProdutoFornecedorDTO dto);
}