package com.bandampla.lojavirtual.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import com.bandampla.lojavirtual.model.Produto;
import org.springframework.util.StringUtils;

public class ProdutoSpec {

	// Filtro por ID da Empresa (Obrigatório na maioria dos cenários)
	public static Specification<Produto> empresaIgual(Long empresaId) {
		return (root, query, cb) -> {
			if (empresaId == null)
				return null;
			return cb.equal(root.get("empresa").get("id"), empresaId);
		};
	}

	// Filtro por Nome exato (IgnoreCase) para validação de duplicidade
	public static Specification<Produto> nomeExato(String nome) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(nome))
				return null;
			return cb.equal(cb.lower(root.get("nome")), nome.trim().toLowerCase());
		};
	}

	// Filtro por Nome parcial (LIKE)
	public static Specification<Produto> nomeContem(String nome) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(nome))
				return null;
			return cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
		};
	}

	// Filtro por Descrição parcial (LIKE)
	public static Specification<Produto> descricaoContem(String descricao) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(descricao))
				return null;
			return cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
		};
	}

	// Filtro por produto Ativo
	public static Specification<Produto> statusAtivoEquivalente(Boolean ativo) {
		return (root, query, cb) -> {
			// Se a tela não enviar o filtro (null), ignora a regra e traz ativos e inativos
			if (ativo == null)
				return null;

			// Aplica o filtro de forma dinâmica de acordo com o booleano recebido
			return cb.equal(root.get("ativo"), ativo);
		};
	}
}