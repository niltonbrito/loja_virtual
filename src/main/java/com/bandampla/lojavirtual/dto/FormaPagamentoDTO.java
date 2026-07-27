package com.bandampla.lojavirtual.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.bandampla.lojavirtual.enums.TipoFormaPagamento;

public class FormaPagamentoDTO {

	private Long id;

	@NotBlank(message = "A descrição da forma de pagamento deve ser informada.")
	private String descricao;

	@Positive(message = "O valor minimo de parcela deve ser maior que zero.")
	private BigDecimal valorMinimoParcela;

	@Positive(message = "A quantidade maxima de parcela deve ser maior que zero.")
	private Integer quantidadeMaximaParcelas;

	@NotNull(message = "O tipo da forma de pagamento (Enum) deve ser informado.")
	private TipoFormaPagamento tipoPagamento;

	private Long empresaId;

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

	public BigDecimal getValorMinimoParcela() {
		return valorMinimoParcela;
	}

	public void setValorMinimoParcela(BigDecimal valorMinimoParcela) {
		this.valorMinimoParcela = valorMinimoParcela;
	}

	public Integer getQuantidadeMaximaParcelas() {
		return quantidadeMaximaParcelas;
	}

	public void setQuantidadeMaximaParcelas(Integer quantidadeMaximaParcelas) {
		this.quantidadeMaximaParcelas = quantidadeMaximaParcelas;
	}

	public TipoFormaPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(TipoFormaPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
}