package com.bandampla.lojavirtual.repository.specification;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   5 de jun. de 2026
 */

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.bandampla.lojavirtual.model.PalavraProibida;

public class PalavraProibidaSpec {

	// Filtro por Descricao exato (IgnoreCase) para validação de duplicidade
	public static Specification<PalavraProibida> termoExata(String descricao) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(descricao))
				return null;
			return cb.equal(cb.lower(root.get("termo")), descricao.trim().toLowerCase());
		};
	}

	public static Specification<PalavraProibida> termoContem(String descricao) {
		return (root, query, cb) -> descricao == null ? null
				: cb.like(cb.lower(root.get("termo")), "%" + descricao.trim().toLowerCase() + "%");
	}
}