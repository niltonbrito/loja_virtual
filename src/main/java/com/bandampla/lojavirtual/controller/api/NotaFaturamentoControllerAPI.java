package com.bandampla.lojavirtual.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bandampla.lojavirtual.dto.NotaFiscalVendaDTO;
import com.bandampla.lojavirtual.dto.StatusRastreioDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Faturamento e Logística", description = "Operações de emissão de Notas Fiscais e rastreamento de entregas")
public interface NotaFaturamentoControllerAPI {

	@Operation(summary = "Emitir Nota Fiscal Manual", description = "Dispara o faturamento de uma venda aprovada, gerando os dados fiscais da NF-e e acoplando o vínculo tardio à venda.")
	@PostMapping("/venda/{vendaId}")
	ResponseEntity<ResponseDefaultDTO<NotaFiscalVendaDTO>> emitirNotaFiscalVenda(@PathVariable Long vendaId,
			@AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom;

	@Operation(summary = "Registrar Movimentação de Entrega", description = "Insere um novo marco físico ou logístico na linha do tempo de trânsito da mercadoria (Operação da Transportadora/Admin).")
	@PostMapping("/rastreio")
	ResponseEntity<ResponseDefaultDTO<StatusRastreioDTO>> registrarMovimentacaoEntrega(
			@Valid @RequestBody StatusRastreioDTO dto, @AuthenticationPrincipal UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom;

	@Operation(summary = "Consultar Linha do Tempo de Rastreio", description = "Recupera o histórico completo e cronológico de movimentações logísticas de uma determinada venda.")
	@GetMapping("/rastreio/venda/{vendaId}")
	ResponseEntity<ResponseDefaultDTO<List<StatusRastreioDTO>>> consultarLinhaDoTempoEntrega(
			@PathVariable Long vendaId);
}