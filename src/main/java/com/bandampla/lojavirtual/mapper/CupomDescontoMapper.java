package com.bandampla.lojavirtual.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.bandampla.lojavirtual.dto.CupomDescontoDTO;
import com.bandampla.lojavirtual.model.CategoriaProduto;
import com.bandampla.lojavirtual.model.CupomDesconto;
import com.bandampla.lojavirtual.model.MarcaProduto;
import com.bandampla.lojavirtual.model.Produto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CupomDescontoMapper {

	// 🔥 Mapeamento Limpo: Ignora as coleções complexas no mapeamento automático e deixa o Service tratá-las de forma segura
	@Mapping(source = "empresaId", target = "empresa.id")
	@Mapping(source = "codigo", target = "codigo", qualifiedByName = "limparTexto")
	CupomDesconto toModel(CupomDescontoDTO dto);
	
	@Mapping(source = "empresa.id", target = "empresaId")
	@Mapping(source = "codigo", target = "codigo", qualifiedByName = "limparTexto")
	// 🔥 CORREÇÃO: Alinhado o target para o plural exato das propriedades do DTO
	@Mapping(source = "categorias", target = "categoriasIds", qualifiedByName = "mapCategoriasToIds")
	@Mapping(source = "marcas", target = "marcasIds", qualifiedByName = "mapMarcasToIds")
	@Mapping(source = "produtos", target = "produtosIds", qualifiedByName = "mapProdutosToIds")
	CupomDescontoDTO toDTO(CupomDesconto model);


	@Mapping(target = "empresa", ignore = true)
	@Mapping(target = "categorias", ignore = true)
	@Mapping(target = "marcas", ignore = true)
	@Mapping(target = "produtos", ignore = true)
	@Mapping(source = "codigo", target = "codigo", qualifiedByName = "limparTexto")
	void atualizarCamposDoCupomDesconto(CupomDescontoDTO dto, @MappingTarget CupomDesconto cupom);

	@Named("limparTexto")
	default String limparTexto(String texto) {
		return texto == null ? null : texto.trim();
	}

	// 🔥 MÉTODOS CUSTOMIZADOS: Convertem listas de entidades para listas de IDs para o JSON do cliente
	@Named("mapCategoriasToIds")
	default List<Long> mapCategoriasToIds(List<CategoriaProduto> categorias) {
		if (categorias == null) return null;
		return categorias.stream().map(CategoriaProduto::getId).collect(Collectors.toList());
	}

	@Named("mapMarcasToIds")
	default List<Long> mapMarcasToIds(List<MarcaProduto> marcas) {
		if (marcas == null) return null;
		return marcas.stream().map(MarcaProduto::getId).collect(Collectors.toList());
	}

	@Named("mapProdutosToIds")
	default List<Long> mapProdutosToIds(List<Produto> produtos) {
		if (produtos == null) return null;
		return produtos.stream().map(Produto::getId).collect(Collectors.toList());
	}
}