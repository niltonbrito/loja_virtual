/**
 * 
 */
package com.bandampla.lojavirtual.dto;

import javax.validation.constraints.NotBlank;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 18 de mai. de 2026
 */
public class MarcaProdutoDTO {

	private Long id;

	@NotBlank(message = "A Marca do produto deve ser informado.")
	private String nomeDescricao;

	private Long empresaId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeDescricao() {
		return nomeDescricao;
	}
	public void setNomeDescricao(String nomeDescricao) {
		this.nomeDescricao = nomeDescricao;
	}
	public Long getEmpresaId() {
		return empresaId;
	}
	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
}