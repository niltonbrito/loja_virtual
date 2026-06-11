package com.bandampla.lojavirtual.repository.specification;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   5 de jun. de 2026
 */

import org.springframework.data.jpa.domain.Specification;

import com.bandampla.lojavirtual.model.CategoriaProduto;

public class CategoriaProdutoSpec {

    public static Specification<CategoriaProduto> descricaoContem(String descricao) {
        return (root, query, cb) ->
                descricao == null ? null :
                        cb.like(cb.lower(root.get("nomeDescricao")), "%" + descricao.toLowerCase() + "%");
    }

    public static Specification<CategoriaProduto> empresaIgual(Long empresaId) {
        return (root, query, cb) ->
                empresaId == null ? null :
                        cb.equal(root.get("empresa").get("id"), empresaId);
    }
}