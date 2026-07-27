package com.bandampla.lojavirtual.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.bandampla.lojavirtual.dto.VendaLojaVirtualDTO;
import com.bandampla.lojavirtual.model.VendaLojaVirtual;

@Mapper(componentModel = "spring")
public interface VendaLojaVirtualMapper {

	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "notaFiscalVenda.id", target = "notaFiscalVendaId")
	@Mapping(source = "cupomDesconto.id", target = "cupomDescontoId")
	@Mapping(source = "formaPagamento.id", target = "formaPagamentoId")
	@Mapping(source = "pessoa.id", target = "pessoaId")
	@Mapping(source = "enderecoEntrega.id", target = "enderecoEntregaId")
	@Mapping(source = "enderecoCobranca.id", target = "enderecoCobrancaId")
	VendaLojaVirtualDTO toDTO(VendaLojaVirtual model);

	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "notaFiscalVendaId", target = "notaFiscalVenda.id")
	@Mapping(source = "cupomDescontoId", target = "cupomDesconto.id")
	@Mapping(source = "formaPagamentoId", target = "formaPagamento.id")
	@Mapping(source = "pessoaId", target = "pessoa.id")
	@Mapping(source = "enderecoEntregaId", target = "enderecoEntrega.id")
	@Mapping(source = "enderecoCobrancaId", target = "enderecoCobranca.id")
	VendaLojaVirtual toModel(VendaLojaVirtualDTO dto);

	@Named("limparTexto")
	default String limparTexto(String texto) {
		return texto == null ? null : texto.trim();
	}
}