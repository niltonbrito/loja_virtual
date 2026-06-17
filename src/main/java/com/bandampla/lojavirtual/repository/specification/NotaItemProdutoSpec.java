package com.bandampla.lojavirtual.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bandampla.lojavirtual.model.NotaItemProduto;

public class NotaItemProdutoSpec {

	// Filtro por ID exato da própria Nota Item Produto
	public static Specification<NotaItemProduto> idIgual(Long id) {
		return (root, query, cb) -> id == null ? null : cb.equal(root.get("id"), id);
	}

	public static Specification<NotaItemProduto> idDiferente(Long id) {
		return (root, query, cb) -> id == null ? null : cb.notEqual(root.get("id"), id);
	}

	// Filtro Multi-Tenant Obrigatório
	public static Specification<NotaItemProduto> empresaIgual(Long empresaId) {
		return (root, query, cb) -> empresaId == null ? null : cb.equal(root.get("empresa").get("id"), empresaId);
	}

}