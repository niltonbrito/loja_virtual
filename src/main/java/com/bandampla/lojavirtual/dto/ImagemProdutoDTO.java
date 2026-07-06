/**
 * 
 */
package com.bandampla.lojavirtual.dto;

import com.bandampla.lojavirtual.validation.ValidBase64Image;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 4 de jul. de 2026
 */
public class ImagemProdutoDTO {

	private Long id;

    @ValidBase64Image(message = "Imagem original inválida.")
	private String imagemOriginal;
	
	private String imagemMiniatura;

	private Long produtoId;

	private Long empresaId	;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImagemOriginal() {
		return imagemOriginal;
	}

	public void setImagemOriginal(String imagemOriginal) {
		this.imagemOriginal = imagemOriginal;
	}

	public String getImagemMiniatura() {
		return imagemMiniatura;
	}

	public void setImagemMiniatura(String imagemMiniatura) {
		this.imagemMiniatura = imagemMiniatura;
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
}
