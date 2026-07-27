package com.bandampla.lojavirtual.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.dto.NotaFiscalVendaDTO;
import com.bandampla.lojavirtual.dto.StatusRastreioDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.NotaFiscalVendaMapper;
import com.bandampla.lojavirtual.mapper.StatusRastreioMapper;
import com.bandampla.lojavirtual.model.NotaFiscalVenda;
import com.bandampla.lojavirtual.model.StatusRastreio;
import com.bandampla.lojavirtual.model.VendaLojaVirtual;
import com.bandampla.lojavirtual.repository.NotaFiscalVendaRepository;
import com.bandampla.lojavirtual.repository.StatusRastreioRepository;
import com.bandampla.lojavirtual.repository.VendaLojaVirtualRepository;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

@Service
public class NotaFaturamentoService {

	private final NotaFiscalVendaRepository notaRepository;
	private final StatusRastreioRepository rastreioRepository;
	private final VendaLojaVirtualRepository vendaRepository;
	private final NotaFiscalVendaMapper notaMapper;
	private final StatusRastreioMapper rastreioMapper;

	public NotaFaturamentoService(NotaFiscalVendaRepository notaRepository, StatusRastreioRepository rastreioRepository,
			VendaLojaVirtualRepository vendaRepository, NotaFiscalVendaMapper notaMapper,
			StatusRastreioMapper rMapper) {
		this.notaRepository = notaRepository;
		this.rastreioRepository = rastreioRepository;
		this.vendaRepository = vendaRepository;
		this.notaMapper = notaMapper;
		this.rastreioMapper = rMapper;
	}

	/**
	 * 🔥 ETAPA ASSÍNCRONA: Gera a nota fiscal para um pedido pago e acopla o
	 * vínculo tardio.
	 */
	@Transactional(rollbackOn = Exception.class)
	public NotaFiscalVendaDTO emitirNotaFiscalVenda(Long vendaId, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		VendaLojaVirtual venda = vendaRepository.findById(vendaId)
				.orElseThrow(() -> new ExceptionCustom("Venda não encontrada para faturamento."));

		if (!venda.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Acesso não autorizado para esta empresa.");
		}

		if (venda.getNotaFiscalVenda() != null) {
			throw new ExceptionCustom(
					"Este pedido já possui Nota Fiscal emitida: NF-e " + venda.getNotaFiscalVenda().getNumeroNota());
		}

		// Simulação de resposta estruturada da API da SEFAZ
		NotaFiscalVenda nota = new NotaFiscalVenda();
		nota.setNumeroNota(String.valueOf((int) (Math.random() * 900000) + 100000));
		nota.setSerieNota("1");
		nota.setTipo("SAIDA");
		nota.setDescricao("NF-E DE VENDA DE MERCADORIAS CONSUMIDOR FINAL - PEDIDO " + venda.getNumeroPedido());
		nota.setValorTotal(venda.getValorTotal());
		nota.setValorDesconto(venda.getValorDesconto() != null ? venda.getValorDesconto() : BigDecimal.ZERO);
		nota.setValorIcms(venda.getValorTotal().multiply(BigDecimal.valueOf(0.18))); // Simula alíquota padrão 18%
		nota.setXml("<xml><nfe>Autorizada SEFAZ " + nota.getNumeroNota() + "</nfe></xml>");
		nota.setPdf("https://bandampla.com" + nota.getNumeroNota() + ".pdf");
		nota.setVendaCompraLojaVirtual(venda);
		nota.setEmpresa(venda.getEmpresa());

		NotaFiscalVenda notaSalva = notaRepository.save(nota);

		// 🔥 O ACOPLAMENTO TARDIO: Vincula a nota gerada de volta na tabela pai da
		// venda
		venda.setNotaFiscalVenda(notaSalva);
		vendaRepository.save(venda);

		// Insere o primeiro marco da linha do tempo logística automaticamente
		adicionarMarcoLogistico(venda, "Nota Fiscal emitida com sucesso. Pedido entrou na fila de separação.");

		return notaMapper.toDTO(notaSalva);
	}

	/**
	 * Adiciona uma movimentação de trânsito ou despacho na linha do tempo do
	 * cliente.
	 */
	@Transactional(rollbackOn = Exception.class)
	public StatusRastreioDTO registrarMovimentacaoEntrega(StatusRastreioDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		VendaLojaVirtual venda = vendaRepository.findById(dto.getVendaLojaVirtualId())
				.orElseThrow(() -> new ExceptionCustom("Venda de destino inválida."));

		if (!venda.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não tem permissão para atualizar a logística desta empresa.");
		}

		StatusRastreio rastreio = rastreioMapper.toModel(dto);
		rastreio.setVendaLojaVirtual(venda);
		rastreio.setEmpresa(venda.getEmpresa());

		StatusRastreio salvo = rastreioRepository.save(rastreio);
		return rastreioMapper.toDTO(salvo);
	}

	/**
	 * Consulta a linha do tempo completa do rastreamento do pedido.
	 */
	public List<StatusRastreioDTO> consultarLinhaDoTempoEntrega(Long vendaId) {
		List<StatusRastreio> marcos = rastreioRepository.buscarRastreioPorVendaId(vendaId);
		return marcos.stream().map(rastreioMapper::toDTO).collect(Collectors.toList());
	}

	private void adicionarMarcoLogistico(VendaLojaVirtual venda, String textoStatus) {
		StatusRastreio marco = new StatusRastreio();
		marco.setVendaLojaVirtual(venda);
		marco.setEmpresa(venda.getEmpresa());
		marco.setStatus(textoStatus);
		marco.setCentroDistribuicao("CD Central BandAmpla");
		rastreioRepository.save(marco);
	}
}