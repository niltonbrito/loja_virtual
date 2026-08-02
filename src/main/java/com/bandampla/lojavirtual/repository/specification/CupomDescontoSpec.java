package com.bandampla.lojavirtual.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import com.bandampla.lojavirtual.model.CupomDesconto;

public class CupomDescontoSpec {

	public static Specification<CupomDesconto> idIgual(Long id) {
		return (root, query, cb) -> id == null ? null : cb.equal(root.get("id"), id);
	}

	public static Specification<CupomDesconto> idDiferente(Long id) {
		return (root, query, cb) -> id == null ? null : cb.notEqual(root.get("id"), id);
	}

	public static Specification<CupomDesconto> empresaIgual(Long empresaId) {
		return (root, query, cb) -> empresaId == null ? null : cb.equal(root.get("empresa").get("id"), empresaId);
	}

	public static Specification<CupomDesconto> codigoDescricaoExata(String codigoDescricao) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(codigoDescricao)) return null;
			return cb.equal(cb.lower(root.get("codigoDescricao")), codigoDescricao.trim().toLowerCase());
		};
	}

	public static Specification<CupomDesconto> codigoDescricaoContem(String codigoDescricao) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(codigoDescricao)) return null;
			return cb.like(cb.lower(root.get("codigoDescricao")), "%" + codigoDescricao.trim().toLowerCase() + "%");
		};
	}
}