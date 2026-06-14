package com.bandampla.lojavirtual.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.bandampla.lojavirtual.model.ContaPagar;

public class ContaPagarSpec {

	public static Specification<ContaPagar> idDiferente(Long id) {
		return (root, query, cb) -> id == null ? null : cb.notEqual(root.get("id"), id);
	}

	public static Specification<ContaPagar> empresaIgual(Long empresaId) {
		return (root, query, cb) -> empresaId == null ? null : cb.equal(root.get("empresa").get("id"), empresaId);
	}

	public static Specification<ContaPagar> descricaoExata(String descricao) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(descricao))
				return null;
			return cb.equal(cb.lower(root.get("descricao")), descricao.trim().toLowerCase());
		};
	}

	public static Specification<ContaPagar> descricaoContem(String descricao) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(descricao))
				return null;
			return cb.like(cb.lower(root.get("descricao")), "%" + descricao.trim().toLowerCase() + "%");
		};
	}
}