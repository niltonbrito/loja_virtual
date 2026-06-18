package com.bandampla.lojavirtual.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.bandampla.lojavirtual.model.NotaFiscalCompra;

public class NotaFiscalCompraSpec {

	/*
	 * =========================== FILTROS POR ID DA NOTA
	 * ===========================
	 */

	public static Specification<NotaFiscalCompra> idIgual(Long id) {
		return (root, query, cb) -> id == null ? null : cb.equal(root.get("id"), id);
	}

	public static Specification<NotaFiscalCompra> idDiferente(Long id) {
		return (root, query, cb) -> id == null ? null : cb.notEqual(root.get("id"), id);
	}

	/*
	 * =========================== FILTROS POR NÚMERO DA NOTA
	 * ===========================
	 */

	public static Specification<NotaFiscalCompra> numeroNotaExato(String numeroNota) {
		return (root, query, cb) -> !StringUtils.hasText(numeroNota) ? null
				: cb.equal(root.get("numeroNota"), numeroNota.trim());
	}

	public static Specification<NotaFiscalCompra> numeroNotaContem(String numeroNota) {
		return (root, query, cb) -> !StringUtils.hasText(numeroNota) ? null
				: cb.like(root.get("numeroNota"), "%" + numeroNota.trim() + "%");
	}

	/*
	 * =========================== FILTROS POR RELACIONAMENTOS
	 * ===========================
	 */

	// Equivalente ao método buscarPorPessoa()
	public static Specification<NotaFiscalCompra> pessoaIgual(Long pessoaId) {
		return (root, query, cb) -> pessoaId == null ? null : cb.equal(root.get("pessoa").get("id"), pessoaId);
	}

	// Equivalente ao método buscarPorContaPagar()
	public static Specification<NotaFiscalCompra> contaPagarIgual(Long contaPagarId) {
		return (root, query, cb) -> contaPagarId == null ? null
				: cb.equal(root.get("contaPagar").get("id"), contaPagarId);
	}

	/*
	 * =========================== FILTRO MULTI-TENANT ===========================
	 */

	public static Specification<NotaFiscalCompra> empresaIgual(Long empresaId) {
		return (root, query, cb) -> empresaId == null ? null : cb.equal(root.get("empresa").get("id"), empresaId);
	}

	/*
	 * =========================== FILTROS POR DESCRIÇÃO ===========================
	 */

	public static Specification<NotaFiscalCompra> descricaoObservacaoExata(String descricao) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(descricao))
				return null;
			return cb.equal(cb.lower(root.get("descricaoObservacao")), descricao.trim().toLowerCase());
		};
	}

	public static Specification<NotaFiscalCompra> descricaoObservacaoContem(String descricao) {
		return (root, query, cb) -> {
			if (!StringUtils.hasText(descricao))
				return null;
			return cb.like(cb.lower(root.get("descricaoObservacao")), "%" + descricao.trim().toLowerCase() + "%");
		};
	}
}
