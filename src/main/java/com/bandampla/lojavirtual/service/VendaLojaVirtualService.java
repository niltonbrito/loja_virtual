package com.bandampla.lojavirtual.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.dto.ItemVendaLojaDTO;
import com.bandampla.lojavirtual.dto.VendaLojaVirtualDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.VendaLojaVirtualMapper;
import com.bandampla.lojavirtual.model.CupomDesconto;
import com.bandampla.lojavirtual.model.Endereco;
import com.bandampla.lojavirtual.model.FormaPagamento;
import com.bandampla.lojavirtual.model.ItemVendaLoja;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Produto;
import com.bandampla.lojavirtual.model.VendaLojaVirtual;
import com.bandampla.lojavirtual.repository.CupomDescontoRepository;
import com.bandampla.lojavirtual.repository.EnderecoRepository;
import com.bandampla.lojavirtual.repository.FormaPagamentoRepository;
import com.bandampla.lojavirtual.repository.ItemVendaLojaRepository;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.ProdutoRepository;
import com.bandampla.lojavirtual.repository.VendaLojaVirtualRepository;
import com.bandampla.lojavirtual.repository.specification.VendaLojaVirtualSpec;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

@Service
public class VendaLojaVirtualService {

	private final VendaLojaVirtualRepository vendaLojaVirtualRepository;
	private final ItemVendaLojaRepository itemVendaLojaRepository;
	private final ProdutoRepository produtoRepository;
	private final CupomDescontoRepository cupomDescontoRepository;
	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final PessoaFisicaRepository pessoaFisicaRepository;
	private final EnderecoRepository enderecoRepository;
	private final FormaPagamentoRepository formaPagamentoRepository;
	private final VendaLojaVirtualMapper vendaLojaVirtualMapper;
	private final SendMailService sendMailService;

	public VendaLojaVirtualService(VendaLojaVirtualRepository vendaLojaVirtualRepository,
			ItemVendaLojaRepository itemVendaLojaRepository, ProdutoRepository produtoRepository,
			CupomDescontoRepository cupomDescontoRepository, PessoaJuridicaRepository pessoaJuridicaRepository,
			PessoaFisicaRepository pessoaFisicaRepository, EnderecoRepository enderecoRepository,
			FormaPagamentoRepository formaPagamentoRepository, VendaLojaVirtualMapper vendaLojaVirtualMapper,
			SendMailService sendMailService) {
		this.vendaLojaVirtualRepository = vendaLojaVirtualRepository;
		this.itemVendaLojaRepository = itemVendaLojaRepository;
		this.produtoRepository = produtoRepository;
		this.cupomDescontoRepository = cupomDescontoRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.pessoaFisicaRepository = pessoaFisicaRepository;
		this.enderecoRepository = enderecoRepository;
		this.formaPagamentoRepository = formaPagamentoRepository;
		this.vendaLojaVirtualMapper = vendaLojaVirtualMapper;
		this.sendMailService = sendMailService;
	}

	@Transactional(rollbackOn = Exception.class)
	public VendaLojaVirtualDTO cadastrar(VendaLojaVirtualDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, MessagingException, IOException {

		// 1. Validar e Buscar o Comprador e Empresa proprietária
		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa vendedora não encontrada."));

		PessoaFisica comprador = pessoaFisicaRepository.findById(dto.getPessoaId())
				.orElseThrow(() -> new ExceptionCustom("Comprador não encontrado."));
		
		// 2. Validar o Endereço de Entrega e Cobrança
		Endereco entrega = enderecoRepository.findById(dto.getEnderecoEntregaId())
				.orElseThrow(() -> new ExceptionCustom("Endereço de entrega não localizado."));

		Endereco cobranca = enderecoRepository.findById(dto.getEnderecoCobrancaId())
				.orElseThrow(() -> new ExceptionCustom("Endereço de cobrança não localizado."));

		// Valida se os endereços pertencem ao comprador
		if (entrega.getPessoa() == null || !entrega.getPessoa().getId().equals(comprador.getId())) {
			throw new ExceptionCustom("Segurança Violada: O endereço de entrega informado não pertence ao cliente logado.");
		}

		if (cobranca.getPessoa() == null || !cobranca.getPessoa().getId().equals(comprador.getId())) {
			throw new ExceptionCustom("Segurança Violada: O endereço de cobrança informado não pertence ao cliente logado.");
		}

		FormaPagamento formaPagamento = formaPagamentoRepository.findById(dto.getFormaPagamentoId())
				.orElseThrow(() -> new ExceptionCustom("Forma de pagamento não cadastrada."));

		// 3. Validar Itens em Memória e Separar para Processamento
		List<ItemVendaLoja> itensParaSalvar = new ArrayList<>();

		for (ItemVendaLojaDTO itemDto : dto.getItens()) {
			Produto produto = produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(
					() -> new ExceptionCustom("Produto ID " + itemDto.getProdutoId() + " não existe no catálogo."));

			// Validação de Estoque Físico
			Double qtdDesejada = itemDto.getQuantidade();
			if (produto.getQtdEstoque().compareTo(BigDecimal.valueOf(qtdDesejada)) < 0) {
				throw new ExceptionCustom("Estoque insuficiente para o produto: " + produto.getNome()
						+ ". Estoque atual: " + produto.getQtdEstoque() + ", Solicitado: " + qtdDesejada);
			}

			// Executa a baixa física imediata no estoque do produto
			produto.setQtdEstoque(produto.getQtdEstoque().subtract(BigDecimal.valueOf(qtdDesejada)));
			produtoRepository.save(produto);

			// Monta a entidade associativa do item
			ItemVendaLoja itemVenda = new ItemVendaLoja();
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(qtdDesejada.doubleValue());
			itemVenda.setEmpresa(empresa);
			itensParaSalvar.add(itemVenda);
		}

		// 4. Calcular Valores Finais e Mapear Registro Principal
		VendaLojaVirtual model = vendaLojaVirtualMapper.toModel(dto);

		// Impede o MapStruct de criar instâncias transientes
		// vazias se o ID for nulo
		if (dto.getCupomDescontoId() == null) {
			model.setCupomDesconto(null); // Garante o null absoluto no banco de dados
		} else {
			// Caso o ID exista, opcionalmente você pode buscar o cupom no banco para
			// validar se ele é real
			CupomDesconto cupom = cupomDescontoRepository.findById(dto.getCupomDescontoId()).orElseThrow(
					() -> new ExceptionCustom("Cupom de desconto com ID '" + dto.getCupomDescontoId() + "' não existe no catálogo."));
			model.setCupomDesconto(cupom);
		}
		model.setEmpresa(empresa);
		model.setPessoa(comprador);
		model.setEnderecoEntrega(entrega);
		model.setEnderecoCobranca(cobranca);
		model.setFormaPagamento(formaPagamento);
		model.setNotaFiscalVenda(null); // Nota inicia nula

		// 5. Persistir o Registro Principal (Gera o ID e dispara o @PrePersist com o
		// numeroPedido)
		VendaLojaVirtual vendaSalva = vendaLojaVirtualRepository.save(model);

		// 6. Vincular os Itens do Carrinho à Venda Salva e Persistir
		for (ItemVendaLoja item : itensParaSalvar) {
			item.setVendaLojaVirtual(vendaSalva);
			itemVendaLojaRepository.save(item);
		}

		// 7. 🔥 SIMULAÇÃO DO GATEWAY DE PAGAMENTO (Fica pronto para acoplamento de API
		// futura)
		boolean pagamentoAprovado = simularGatewayPagamento(vendaSalva, dto);
		if (!pagamentoAprovado) {
			throw new ExceptionCustom("A transação financeira foi recusada pelo operadora de pagamento.");
		}

		// 8. Enviar E-mail de Confirmação para o Cliente
		enviarEmailConfirmacaoPedido(vendaSalva);

		// Construir Resposta DTO contendo a árvore populada
		VendaLojaVirtualDTO dtoRetorno = vendaLojaVirtualMapper.toDTO(vendaSalva);
		dtoRetorno.setItens(converterItensParaDTO(itemVendaLojaRepository.buscarPorVendaId(vendaSalva.getId())));

		return dtoRetorno;
	}

	/**
	 * 🔥 PONTO DE EXTENSÃO: Método isolado para receber o código de integração do
	 * Gateway no futuro.
	 */
	private boolean simularGatewayPagamento(VendaLojaVirtual venda, VendaLojaVirtualDTO dto) {
		// No futuro, aqui entrará a chamada HTTP para Asaas, Mercado Pago, etc.
		// Exemplo: String response = asaasService.gerarCobranca(venda);
		/*
		 * FormaPagamento forma =
		 * formaPagamentoRepository.findById(venda.getFormaPagamento());
		 * 
		 * // O Java inspeciona o ENUM para saber qual API de Gateway chamar: if
		 * (forma.getTipoPagamento() == TipoFormaPagamento.PIX) { // Dispara a chamada
		 * de API do Asaas/MercadoPago para gerar o QR Code do PIX } else if
		 * (forma.getTipoPagamento() == TipoFormaPagamento.CARTAOCREDITO) { // Dispara a
		 * chamada de API enviando o token do cartão para captura de crédito } else if
		 * (forma.getTipoPagamento() == TipoFormaPagamento.BOLETO) { // Dispara a
		 * geração de PDF de linha digitável do Boleto bancário }
		 */
		System.out.println(
				"====== [GATEWAY SIMULADO] Processando pagamento do pedido: " + venda.getNumeroPedido() + " ======");
		return true; // Simula resposta estável de sucesso com aprovação imediata
	}

	private void enviarEmailConfirmacaoPedido(VendaLojaVirtual venda) {
		try {
			String html = "<h2>Olá " + venda.getPessoa().getNome() + "!</h2>" + "<p>Seu pedido <b>"
					+ venda.getNumeroPedido() + "</b> foi recebido e o pagamento aprovado.</p>"
					+ "<p>Valor total faturado: R$ " + venda.getValorTotal() + "</p>"
					+ "<p>Em breve iniciaremos a emissão da Nota Fiscal.</p>";

			sendMailService.enviarEmailHtml("Pedido Confirmado - " + venda.getNumeroPedido(), html,
					venda.getPessoa().getEmail());
		} catch (Exception e) {
			System.err.println("====== Falha não impeditiva ao despachar e-mail de confirmação: " + e.getMessage());
		}
	}

	@Transactional
	public void deletar(Long id, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID de venda inválido.");
		}
		VendaLojaVirtual venda = vendaLojaVirtualRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Venda não encontrada para cancelamento."));

		if (!venda.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não possui permissão para alterar dados desta empresa.");
		}

		// Estorna os itens removidos de volta para o estoque ao cancelar o pedido
		List<ItemVendaLoja> itens = itemVendaLojaRepository.buscarPorVendaId(venda.getId());
		for (ItemVendaLoja item : itens) {
			Produto p = item.getProduto();
			p.setQtdEstoque(p.getQtdEstoque().add(BigDecimal.valueOf(item.getQuantidade())));
			produtoRepository.save(p);
			itemVendaLojaRepository.delete(item);
		}

		vendaLojaVirtualRepository.delete(venda);
	}

	public VendaLojaVirtualDTO buscarPorId(Long id, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido.");
		}
		VendaLojaVirtual venda = vendaLojaVirtualRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Venda não encontrada."));

		if (!venda.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Acesso negado para este faturamento.");
		}

		VendaLojaVirtualDTO dto = vendaLojaVirtualMapper.toDTO(venda);
		// 🔥 CORRIGIDO: Agora chama o método correto do carrinho
		dto.setItens(converterItensParaDTO(itemVendaLojaRepository.buscarPorVendaId(venda.getId())));
		return dto;
	}

	public List<VendaLojaVirtualDTO> buscarPorNumeroPedido(String numeroPedido, UsuarioLogadoPrincipal usuarioLogado) {
		Specification<VendaLojaVirtual> spec = Specification
				.where(VendaLojaVirtualSpec.empresaIgual(usuarioLogado.getEmpresaId()))
				.and(VendaLojaVirtualSpec.numeroPedidoContem(numeroPedido));

		return vendaLojaVirtualRepository.findAll(spec).stream().map(v -> {
			VendaLojaVirtualDTO d = vendaLojaVirtualMapper.toDTO(v);
			// 🔥 CORRIGIDO: Agora chama o método correto do carrinho
			d.setItens(converterItensParaDTO(itemVendaLojaRepository.buscarPorVendaId(v.getId())));
			return d;
		}).collect(Collectors.toList());
	}

	public List<VendaLojaVirtualDTO> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado) {
		Specification<VendaLojaVirtual> spec = Specification
				.where(VendaLojaVirtualSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return vendaLojaVirtualRepository.findAll(spec).stream().map(v -> {
			VendaLojaVirtualDTO d = vendaLojaVirtualMapper.toDTO(v);
			// 🔥 CORRIGIDO: Agora chama o método correto do carrinho
			d.setItens(converterItensParaDTO(itemVendaLojaRepository.buscarPorVendaId(v.getId())));
			return d;
		}).collect(Collectors.toList());
	}

	public Page<VendaLojaVirtualDTO> buscarAvancado(String numeroPedido, int page, int size,
			UsuarioLogadoPrincipal usuarioLogado) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<VendaLojaVirtual> spec = Specification
				.where(VendaLojaVirtualSpec.empresaIgual(usuarioLogado.getEmpresaId()))
				.and(VendaLojaVirtualSpec.numeroPedidoContem(numeroPedido));

		return vendaLojaVirtualRepository.findAll(spec, pageable).map(v -> {
			VendaLojaVirtualDTO d = vendaLojaVirtualMapper.toDTO(v);
			// 🔥 CORRIGIDO: Agora chama o método correto do carrinho
			d.setItens(converterItensParaDTO(itemVendaLojaRepository.buscarPorVendaId(v.getId())));
			return d;
		});
	}

	public Page<VendaLojaVirtualDTO> buscarPaginado(int page, int size, String sort, String direction,
			UsuarioLogadoPrincipal usuarioLogado) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);
		Specification<VendaLojaVirtual> spec = Specification
				.where(VendaLojaVirtualSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		return vendaLojaVirtualRepository.findAll(spec, pageable).map(v -> {
			VendaLojaVirtualDTO d = vendaLojaVirtualMapper.toDTO(v);
			// 🔥 CORRIGIDO: Agora chama o método correto do carrinho
			d.setItens(converterItensParaDTO(itemVendaLojaRepository.buscarPorVendaId(v.getId())));
			return d;
		});
	}

	private List<ItemVendaLojaDTO> converterItensParaDTO(List<ItemVendaLoja> itens) {
		if (itens == null)
			return new ArrayList<>();
		return itens.stream().map(i -> {
			ItemVendaLojaDTO d = new ItemVendaLojaDTO();
			d.setId(i.getId());
			d.setQuantidade(i.getQuantidade());
			d.setProdutoId(i.getProduto().getId());
			d.setVendaLojaVirtualId(i.getVendaLojaVirtual().getId());
			d.setEmpresaId(i.getEmpresa().getId());
			return d;
		}).collect(Collectors.toList());
	}
}