package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.bandampla.lojavirtual.dto.NotaFiscalCompraDTO;
import com.bandampla.lojavirtual.model.NotaFiscalCompra;

@Mapper(componentModel = "spring")
public interface NotaFiscalCompraMapper {

	@Mapping(target = "itens", ignore = true)
	@Mapping(target = "empresa", ignore = true)
	@Mapping(target = "pessoa", ignore = true)
	@Mapping(target = "contaPagar", ignore = true)
	@Mapping(target = "pedidoCompra", ignore = true)
	NotaFiscalCompra toModel(NotaFiscalCompraDTO dto);

	@Mapping(target = "pessoaId", source = "pessoa.id")
	@Mapping(target = "empresaId", source = "empresa.id")
	@Mapping(target = "contaPagarId", source = "contaPagar.id")
	@Mapping(target = "itens", ignore = true)
	@Mapping(target = "tipoPessoaFornecedor", source = "pessoa.tipoPessoa")
	NotaFiscalCompraDTO toDTO(NotaFiscalCompra model);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "empresa", ignore = true)
	@Mapping(target = "pessoa", ignore = true)
	@Mapping(target = "contaPagar", ignore = true)
	@Mapping(target = "itens", ignore = true)
	@Mapping(target = "pedidoCompra", ignore = true)
	void atualizarCamposDaConta(NotaFiscalCompraDTO dto, @MappingTarget NotaFiscalCompra nf);
}
