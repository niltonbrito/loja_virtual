package com.bandampla.lojavirtual.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.bandampla.lojavirtual.model.ContaPagar;

public class ContaPagarSpec {

	// Filtro por ID da Empresa (Obrigatório na maioria dos cenários)
	public static Specification<ContaPagar> empresaIgual(Long empresaId) {
		return (root, query, cb) -> {
			if (empresaId == null)
				return null;
			return cb.equal(root.get("empresa").get("id"), empresaId);
		};
	}

	// Filtro por Descricao exato (IgnoreCase) para validação de duplicidade
	public static Specification<ContaPagar> descricaoExata(String descricao) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(descricao))
				return null;
			return cb.equal(cb.lower(root.get("descricao")), descricao.trim().toLowerCase());
		};
	}

	// Filtro por Descrição parcial (LIKE)
	public static Specification<ContaPagar> descricaoContem(String descricao) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(descricao))
				return null;
			return cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
		};
	}

}