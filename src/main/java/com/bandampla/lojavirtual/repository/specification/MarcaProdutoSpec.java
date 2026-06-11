package com.bandampla.lojavirtual.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.bandampla.lojavirtual.model.MarcaProduto;

public class MarcaProdutoSpec {

	// Filtro por ID da Empresa (Obrigatório na maioria dos cenários)
	public static Specification<MarcaProduto> empresaIgual(Long empresaId) {
		return (root, query, cb) -> {
			if (empresaId == null)
				return null;
			return cb.equal(root.get("empresa").get("id"), empresaId);
		};
	}

	// Filtro por Descricao exato (IgnoreCase) para validação de duplicidade
	public static Specification<MarcaProduto> descricaoExata(String nomeDescricao) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(nomeDescricao))
				return null;
			return cb.equal(cb.lower(root.get("nomeDescricao")), nomeDescricao.trim().toLowerCase());
		};
	}

	// Filtro por Descrição parcial (LIKE)
	public static Specification<MarcaProduto> descricaoContem(String nomeDescricao) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(nomeDescricao))
				return null;
			return cb.like(cb.lower(root.get("nomeDescricao")), "%" + nomeDescricao.toLowerCase() + "%");
		};
	}

}