package com.bandampla.lojavirtual.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.bandampla.lojavirtual.model.VendaLojaVirtual;

public class VendaLojaVirtualSpec {

	public static Specification<VendaLojaVirtual> empresaIgual(Long empresaId) {
		return (root, query, cb) -> {
			if (empresaId == null)
				return null;
			return cb.equal(root.get("empresa").get("id"), empresaId);
		};
	}

	public static Specification<VendaLojaVirtual> pessoaIgual(Long pessoaId) {
		return (root, query, cb) -> {
			if (pessoaId == null)
				return null;
			return cb.equal(root.get("pessoa").get("id"), pessoaId);
		};
	}
	
	public static Specification<VendaLojaVirtual> numeroPedidoExato(String numeroPedido) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(numeroPedido))
				return null;
			return cb.equal(cb.lower(root.get("numeroPedido")), numeroPedido.trim().toLowerCase());
		};
	}
	
	public static Specification<VendaLojaVirtual> numeroPedidoContem(String numeroPedido) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(numeroPedido))
				return null;
			return cb.like(cb.lower(root.get("numeroPedido")), "%" + numeroPedido.trim().toLowerCase() + "%");
		};
	}
}