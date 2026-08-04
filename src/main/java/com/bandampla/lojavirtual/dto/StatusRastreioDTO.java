package com.bandampla.lojavirtual.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StatusRastreioDTO {

	private Long id;
	private String centroDistribuicao;
	private String codigo;
	private String cidade;
	private String estado;

	@NotBlank(message = "O texto de status do rastreamento deve ser informado.")
	private String status;

	@NotNull(message = "O ID da venda correspondente é obrigatório.")
	private Long vendaLojaVirtualId;

	// Propriedade opcional para o futuro rastreamento B2B de entrada de insumos
	private Long compraLojaVirtualId;
	
	private Long empresaId;

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCentroDistribuicao() {
		return centroDistribuicao;
	}

	public void setCentroDistribuicao(String centroDistribuicao) {
		this.centroDistribuicao = centroDistribuicao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getVendaLojaVirtualId() {
		return vendaLojaVirtualId;
	}

	public void setVendaLojaVirtualId(Long vendaLojaVirtualId) {
		this.vendaLojaVirtualId = vendaLojaVirtualId;
	}

	public Long getCompraLojaVirtualId() {
		return compraLojaVirtualId;
	}

	public void setCompraLojaVirtualId(Long compraLojaVirtualId) {
		this.compraLojaVirtualId = compraLojaVirtualId;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
}