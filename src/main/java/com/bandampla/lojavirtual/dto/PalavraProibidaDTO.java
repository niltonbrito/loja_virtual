package com.bandampla.lojavirtual.dto;

import javax.validation.constraints.NotBlank;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 18 de mai. de 2026
 */
public class PalavraProibidaDTO {

	private Long id;

	@NotBlank(message = "O termo da palavra proibida deve ser informado.")
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao.toLowerCase().trim();
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao.toLowerCase().trim();
	}
}