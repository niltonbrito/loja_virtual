package com.bandampla.lojavirtual.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.bandampla.lojavirtual.model.ProdutoFornecedor;

public class ProdutoFornecedorSpec {

	// Filtro por ID da Empresa (Obrigatório na maioria dos cenários)
	public static Specification<ProdutoFornecedor> empresaIgual(Long empresaId) {
		return (root, query, cb) -> {
			if (empresaId == null)
				return null;
			return cb.equal(root.get("empresa").get("id"), empresaId);
		};
	}

	// Filtro por ID da Empresa (Obrigatório na maioria dos cenários)
	public static Specification<ProdutoFornecedor> fornecedorIgual(Long fornecedorId) {
		return (root, query, cb) -> {
			if (fornecedorId == null)
				return null;
			return cb.equal(root.get("fornecedor").get("id"), fornecedorId);
		};
	}

	// Filtro por ID da Empresa (Obrigatório na maioria dos cenários)
	public static Specification<ProdutoFornecedor> produtoIgual(Long produtoId) {
		return (root, query, cb) -> {
			if (produtoId == null)
				return null;
			return cb.equal(root.get("produto").get("id"), produtoId);
		};
	}
	
	// Filtro por Nome exato (IgnoreCase) para validação de duplicidade
	public static Specification<ProdutoFornecedor> codigoProdutoFornecedorExato(String codigoProdutoFornecedor) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(codigoProdutoFornecedor))
				return null;
			return cb.equal(cb.lower(root.get("codigoProdutoFornecedor")), codigoProdutoFornecedor.trim().toLowerCase());
		};
	}

	
	// Filtro por Descrição parcial (LIKE)
	public static Specification<ProdutoFornecedor> codigoProdutoFornecedorContem(String codigoProdutoFornecedor) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(codigoProdutoFornecedor))
				return null;
			return cb.like(cb.lower(root.get("codigoProdutoFornecedor")), "%" + codigoProdutoFornecedor.toLowerCase() + "%");
		};
	}

	// Filtro por produto Ativo
	public static Specification<ProdutoFornecedor> statusAtivoEquivalente(Boolean ativo) {
		return (root, query, cb) -> {
			// Se a tela não enviar o filtro (null), ignora a regra e traz ativos e inativos
			if (ativo == null)
				return null;

			// Aplica o filtro de forma dinâmica de acordo com o booleano recebido
			return cb.equal(root.get("ativo"), ativo);
		};
	}
}