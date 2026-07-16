package com.bandampla.lojavirtual.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.bandampla.lojavirtual.model.AvaliacaoProduto;

public class AvaliacaoProdutoSpec {

	public static Specification<AvaliacaoProduto> empresaIgual(Long empresaId) {
		return (root, query, cb) -> {
			if (empresaId == null)
				return null;
			return cb.equal(root.get("empresa").get("id"), empresaId);
		};
	}

	public static Specification<AvaliacaoProduto> produtoIgual(Long produtoId) {
		return (root, query, cb) -> {
			if (produtoId == null)
				return null;
			return cb.equal(root.get("produto").get("id"), produtoId);
		};
	}

	public static Specification<AvaliacaoProduto> descricaoContem(String descricao) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(descricao))
				return null;
			return cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
		};
	}

	public static Specification<AvaliacaoProduto> nota(Integer nota) {
		return (root, query, cb) -> {
			if (nota == null || nota < 0)
				return null;

			return cb.equal(root.get("nota"), nota);
		};
	}
}