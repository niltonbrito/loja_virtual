package com.bandampla.lojavirtual.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.bandampla.lojavirtual.enums.StatusContaPagar;

public class ContaPagarDTO {

	private Long id;

	@NotBlank(message = "A descrição da Conta a Pagar deve ser informada.")
	private String descricao;

	@NotNull(message = "Informe o status da Conta a Pagar.")
	private StatusContaPagar status;

	@NotNull(message = "Informe o valor total da conta a pagar.")
	@Positive(message = "O valor da conta a pagar deve ser maior que zero.")
	private BigDecimal valorTotal;

	private BigDecimal valorDesconto;

	@NotNull(message = "Informe a data de vencimento da conta a pagar.")
	private LocalDate dataVencimento;

	private LocalDate dataPagamento;

	@NotNull(message = "O ID da pessoa deve ser informado.")
	@Positive(message = "O ID da pessoa deve ser maior que zero.")
	private Long pessoaId;

	@NotNull(message = "O ID do fornecedor deve ser informado.")
	@Positive(message = "O ID do fornecedor deve ser maior que zero.")
	private Long pessoaFornecedorId;

	private Long empresaId;

	// =========================================================================
	// 🔄 EQUALS INTELEGENTE PARA EVITAR TRAVAMENTOS FALSOS (JAVA 11)
	// =========================================================================
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		ContaPagarDTO other = (ContaPagarDTO) obj;

		boolean valorTotalIgual = (this.valorTotal == null && other.valorTotal == null) || (this.valorTotal != null
				&& other.valorTotal != null && this.valorTotal.compareTo(other.valorTotal) == 0);

		boolean valorDescIgual = (this.valorDesconto == null && other.valorDesconto == null)
				|| (this.valorDesconto != null && other.valorDesconto != null
						&& this.valorDesconto.compareTo(other.valorDesconto) == 0);

		return Objects.equals(id, other.id) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(status, other.status) && valorTotalIgual && valorDescIgual
				&& Objects.equals(dataVencimento, other.dataVencimento)
				&& Objects.equals(dataPagamento, other.dataPagamento) && Objects.equals(pessoaId, other.pessoaId)
				&& Objects.equals(pessoaFornecedorId, other.pessoaFornecedorId)
				&& Objects.equals(empresaId, other.empresaId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, descricao, status, valorTotal, valorDesconto, dataVencimento, dataPagamento, pessoaId,
				pessoaFornecedorId, empresaId);
	}

	// Getters e Setters
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

	public StatusContaPagar getStatus() {
		return status;
	}

	public void setStatus(StatusContaPagar status) {
		this.status = status;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Long getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}

	public Long getPessoaFornecedorId() {
		return pessoaFornecedorId;
	}

	public void setPessoaFornecedorId(Long pessoaFornecedorId) {
		this.pessoaFornecedorId = pessoaFornecedorId;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
}
