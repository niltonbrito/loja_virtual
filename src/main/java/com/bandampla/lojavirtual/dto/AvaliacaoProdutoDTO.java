/**
 * 
 */
package com.bandampla.lojavirtual.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 4 de jul. de 2026
 */
public class AvaliacaoProdutoDTO {

	private Long id;

	@NotBlank(message = "A descrição da avaliação do produto deve ser informada.")
	private String descricao;

	@NotNull(message = "O valor da nota de avaliação deve ser informado.")
	@PositiveOrZero(message = "O valor da nota de avaliação deve ser maior ou igual zero.")
	private Integer nota;

	private Long produtoId;

	private Long empresaId;

	private Long pessoaId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Long getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}
}
