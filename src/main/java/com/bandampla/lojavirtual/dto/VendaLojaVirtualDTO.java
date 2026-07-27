package com.bandampla.lojavirtual.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class VendaLojaVirtualDTO {

	private Long id;
	private String numeroPedido;

	@NotNull(message = "Informe o valor total do pedido.")
	@Positive(message = "O valor total deve ser maior que zero.")
	private BigDecimal valorTotal;

	@PositiveOrZero(message = "O valor de desconto não pode ser negativo.")
	private BigDecimal valorDesconto;

	@NotNull(message = "Informe o valor do frete.")
	@PositiveOrZero(message = "O valor do frete não pode ser negativo.")
	private BigDecimal valorFrete;

	@NotNull(message = "Informe a quantidade de dias estimados para a entrega.")
	@Positive(message = "Os dias de entrega devem ser maiores que zero.")
	private Integer diasEntrega;

	@NotNull(message = "Informe a data da venda.")
	@PastOrPresent(message = "A data da venda deve ser hoje ou uma data passada.")
	private LocalDate dataVenda;

	@NotNull(message = "Informe a data estimada de entrega.")
	@FutureOrPresent(message = "A data de entrega deve ser hoje ou uma data futura.")
	private LocalDate dataEntrega;

	private Long notaFiscalVendaId;
	private Long cupomDescontoId;

	@NotNull(message = "A forma de pagamento deve ser informada.")
	@Positive(message = "ID da forma de pagamento inválido.")
	private Long formaPagamentoId;

	@NotNull(message = "O cliente comprador deve ser informado.")
	@Positive(message = "ID do comprador inválido.")
	private Long pessoaId;

	@NotNull(message = "O endereço de entrega deve ser informado.")
	@Positive(message = "ID do endereço de entrega inválido.")
	private Long enderecoEntregaId;

	@NotNull(message = "O endereço de cobrança deve ser informado.")
	@Positive(message = "ID do endereço de cobrança inválido.")
	private Long enderecoCobrancaId;

	private Long empresaId;

	@NotEmpty(message = "O carrinho de compras não pode estar vazio.")
	@Valid
	private List<ItemVendaLojaDTO> itens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
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

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public Integer getDiasEntrega() {
		return diasEntrega;
	}

	public void setDiasEntrega(Integer diasEntrega) {
		this.diasEntrega = diasEntrega;
	}

	public LocalDate getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Long getNotaFiscalVendaId() {
		return notaFiscalVendaId;
	}

	public void setNotaFiscalVendaId(Long notaFiscalVendaId) {
		this.notaFiscalVendaId = notaFiscalVendaId;
	}

	public Long getCupomDescontoId() {
		return cupomDescontoId;
	}

	public void setCupomDescontoId(Long cupomDescontoId) {
		this.cupomDescontoId = cupomDescontoId;
	}

	public Long getFormaPagamentoId() {
		return formaPagamentoId;
	}

	public void setFormaPagamentoId(Long formaPagamentoId) {
		this.formaPagamentoId = formaPagamentoId;
	}

	public Long getPessoaId() {
		return pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}

	public Long getEnderecoEntregaId() {
		return enderecoEntregaId;
	}

	public void setEnderecoEntregaId(Long enderecoEntregaId) {
		this.enderecoEntregaId = enderecoEntregaId;
	}

	public Long getEnderecoCobrancaId() {
		return enderecoCobrancaId;
	}

	public void setEnderecoCobrancaId(Long enderecoCobrancaId) {
		this.enderecoCobrancaId = enderecoCobrancaId;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public List<ItemVendaLojaDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemVendaLojaDTO> itens) {
		this.itens = itens;
	}
}