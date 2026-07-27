package com.bandampla.lojavirtual.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.controller.api.NotaFaturamentoControllerAPI;
import com.bandampla.lojavirtual.dto.NotaFiscalVendaDTO;
import com.bandampla.lojavirtual.dto.StatusRastreioDTO;
import com.bandampla.lojavirtual.dto.response.ResponseDefaultDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.NotaFaturamentoService;

@RestController
@RequestMapping("/faturamento")
public class NotaFaturamentoController implements NotaFaturamentoControllerAPI {

	@Autowired
	private NotaFaturamentoService notaFaturamentoService;

	@Autowired
	private HttpServletRequest request;

	@Override
	public ResponseEntity<ResponseDefaultDTO<NotaFiscalVendaDTO>> emitirNotaFiscalVenda(Long vendaId,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();

		// Invoca o serviço de faturamento manual assíncrono/tardio
		NotaFiscalVendaDTO retorno = notaFaturamentoService.emitirNotaFiscalVenda(vendaId, usuarioLogado);

		ResponseDefaultDTO<NotaFiscalVendaDTO> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Nota Fiscal NF-e emitida com sucesso e vinculada ao pedido", retorno, request.getRequestURI(),
				traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<StatusRastreioDTO>> registrarMovimentacaoEntrega(StatusRastreioDTO dto,
			UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		String traceId = UUID.randomUUID().toString();

		StatusRastreioDTO retorno = notaFaturamentoService.registrarMovimentacaoEntrega(dto, usuarioLogado);

		ResponseDefaultDTO<StatusRastreioDTO> response = new ResponseDefaultDTO<>(HttpStatus.CREATED.toString(),
				"Novo marco de rastreamento logístico registrado", retorno, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Override
	public ResponseEntity<ResponseDefaultDTO<List<StatusRastreioDTO>>> consultarLinhaDoTempoEntrega(Long vendaId) {

		String traceId = UUID.randomUUID().toString();

		List<StatusRastreioDTO> retorno = notaFaturamentoService.consultarLinhaDoTempoEntrega(vendaId);

		ResponseDefaultDTO<List<StatusRastreioDTO>> response = new ResponseDefaultDTO<>(HttpStatus.OK.toString(),
				"Linha do tempo de rastreamento recuperada com sucesso", retorno, request.getRequestURI(), traceId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}