package com.bandampla.lojavirtual.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.bandampla.lojavirtual.model.StatusRastreio;
import com.bandampla.lojavirtual.model.VendaLojaVirtual;
import com.bandampla.lojavirtual.repository.CupomDescontoRepository;
import com.bandampla.lojavirtual.repository.EnderecoRepository;
import com.bandampla.lojavirtual.repository.FormaPagamentoRepository;
import com.bandampla.lojavirtual.repository.ItemVendaLojaRepository;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.ProdutoRepository;
import com.bandampla.lojavirtual.repository.StatusRastreioRepository;
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
	private final StatusRastreioRepository statusRastreioRepository; // 🔥 Injetado para automatizar o primeiro status
																		// do frete logístico
	private final VendaLojaVirtualMapper vendaLojaVirtualMapper;
	private final SendMailService sendMailService;

	// 🔥 Fórmula Dourada: Injeção por construtor puro, final e imutável de todas as
	// dependências
	public VendaLojaVirtualService(VendaLojaVirtualRepository vendaLojaVirtualRepository,
			ItemVendaLojaRepository itemVendaLojaRepository, ProdutoRepository produtoRepository,
			CupomDescontoRepository cupomDescontoRepository, PessoaJuridicaRepository pessoaJuridicaRepository,
			PessoaFisicaRepository pessoaFisicaRepository, EnderecoRepository enderecoRepository,
			FormaPagamentoRepository formaPagamentoRepository, StatusRastreioRepository statusRastreioRepository,
			VendaLojaVirtualMapper vendaLojaVirtualMapper, SendMailService sendMailService) {
		this.vendaLojaVirtualRepository = vendaLojaVirtualRepository;
		this.itemVendaLojaRepository = itemVendaLojaRepository;
		this.produtoRepository = produtoRepository;
		this.cupomDescontoRepository = cupomDescontoRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.pessoaFisicaRepository = pessoaFisicaRepository;
		this.enderecoRepository = enderecoRepository;
		this.formaPagamentoRepository = formaPagamentoRepository;
		this.statusRastreioRepository = statusRastreioRepository;
		this.vendaLojaVirtualMapper = vendaLojaVirtualMapper;
		this.sendMailService = sendMailService;
	}

	@Transactional(rollbackFor = Exception.class)
	public VendaLojaVirtualDTO cadastrar(VendaLojaVirtualDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, MessagingException, IOException {

		// 1. Validar e Buscar o Comprador e Empresa proprietária
		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa vendedora não encontrada."));

		PessoaFisica comprador = pessoaFisicaRepository.findById(dto.getPessoaId())
				.orElseThrow(() -> new ExceptionCustom("Comprador não encontrado."));

		if (comprador.getEmpresa() == null || !comprador.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("Segurança Violada: O cliente informado não pertence à sua empresa.");
		}

		// 2. Validar o Endereço de Entrega e Cobrança
		Endereco entrega = enderecoRepository.findById(dto.getEnderecoEntregaId())
				.orElseThrow(() -> new ExceptionCustom("Endereço de entrega não localizado."));

		Endereco cobranca = enderecoRepository.findById(dto.getEnderecoCobrancaId())
				.orElseThrow(() -> new ExceptionCustom("Endereço de cobrança não localizado."));

		if (entrega.getPessoa() == null || !entrega.getPessoa().getId().equals(comprador.getId())) {
			throw new ExceptionCustom(
					"Segurança Violada: O endereço de entrega informado não pertence ao cliente logado.");
		}

		if (cobranca.getPessoa() == null || !cobranca.getPessoa().getId().equals(comprador.getId())) {
			throw new ExceptionCustom(
					"Segurança Violada: O endereço de cobrança informado não pertence ao cliente logado.");
		}

		FormaPagamento formaPagamento = formaPagamentoRepository.findById(dto.getFormaPagamentoId())
				.orElseThrow(() -> new ExceptionCustom("Forma de pagamento não cadastrada."));

		// 🔥 3. MOTOR DE VALIDAÇÃO DE CUPONS AVANÇADO (ENTERPRISE)
		CupomDesconto cupomValido = null;
		if (dto.getCupomDescontoId() != null) {
			cupomValido = cupomDescontoRepository.findById(dto.getCupomDescontoId())
					.orElseThrow(() -> new ExceptionCustom("Cupom de desconto informado não existe no catálogo."));

			if (!cupomValido.getEmpresa().getId().equals(empresa.getId())) {
				throw new ExceptionCustom("Acesso Negado: Este cupom promocional pertence a outra empresa.");
			}

			// Validação de Vigência temporal
			if (cupomValido.getDataValidade().isBefore(LocalDate.now())) {
				throw new ExceptionCustom(
						"Campanha Encerrada: Este cupom de desconto expirou em " + cupomValido.getDataValidade());
			}

			// Validação de Saldo/Limite de Uso Global do cupom
			if (cupomValido.getLimiteUsoTotal() != null && cupomValido.getQuantidadeUsado() != null) {
				if (cupomValido.getQuantidadeUsado() >= cupomValido.getLimiteUsoTotal()) {
					throw new ExceptionCustom(
							"Cupom Esgotado: Este cupom atingiu a quantidade máxima de utilizações permitidas.");
				}
			}

			// Validação de Limite por Cliente (Impede que o mesmo CPF compre usando o mesmo
			// cupom em pedidos separados)
			int totalVendasComCupomDesteCliente = vendaLojaVirtualRepository
					.countVendasPorClienteECupom(comprador.getId(), cupomValido.getId());
			if (totalVendasComCupomDesteCliente >= 1) {
				throw new ExceptionCustom(
						"Campanha Restrita: Seu usuário já utilizou este cupom de desconto em uma compra anterior.");
			}
		}

		// 4. Validar Itens em Memória e Separar para Processamento
		List<ItemVendaLoja> itensParaSalvar = new ArrayList<>();
		boolean cupomPossuiRestricaoDeEscopo = cupomValido != null
				&& ((cupomValido.getCategorias() != null && !cupomValido.getCategorias().isEmpty())
						|| (cupomValido.getMarcas() != null && !cupomValido.getMarcas().isEmpty())
						|| (cupomValido.getProdutos() != null && !cupomValido.getProdutos().isEmpty()));

		boolean peloMenosUmItemValidoParaOCupom = false;

		for (ItemVendaLojaDTO itemDto : dto.getItens()) {
			Produto produto = produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(
					() -> new ExceptionCustom("Produto ID " + itemDto.getProdutoId() + " não existe no catálogo."));

			if (!produto.getEmpresa().getId().equals(empresa.getId())) {
				throw new ExceptionCustom("O produto '" + produto.getNome() + "' não pertence a esta empresa.");
			}

			// Validação de Estoque Físico
			Double qtdDesejada = itemDto.getQuantidade();
			if (produto.getQtdEstoque().compareTo(BigDecimal.valueOf(qtdDesejada)) < 0) {
				throw new ExceptionCustom("Estoque insuficiente para o produto: " + produto.getNome()
						+ ". Estoque atual: " + produto.getQtdEstoque() + ", Solicitado: " + qtdDesejada);
			}

			// 🔥 Validação Cruzada do Cupom: Verifica se o item atende às restrições de
			// categoria/marca/produto
			if (cupomPossuiRestricaoDeEscopo) {
				boolean atendeCategoria = cupomValido.getCategorias() == null || cupomValido.getCategorias().isEmpty()
						|| cupomValido.getCategorias().contains(produto.getCategoriaProduto());
				boolean atendeMarca = cupomValido.getMarcas() == null || cupomValido.getMarcas().isEmpty()
						|| cupomValido.getMarcas().contains(produto.getMarcaProduto());
				boolean atendeProduto = cupomValido.getProdutos() == null || cupomValido.getProdutos().isEmpty()
						|| cupomValido.getProdutos().contains(produto);

				if (atendeCategoria && atendeMarca && atendeProduto) {
					peloMenosUmItemValidoParaOCupom = true;
				}
			}

			// Executa a baixa física imediata no estoque do produto
			produto.setQtdEstoque(produto.getQtdEstoque().subtract(BigDecimal.valueOf(qtdDesejada)));
			produtoRepository.save(produto);

			// Monta a entidade associativa do item (Cast seguro de BigDecimal para Double
			// do banco)
			ItemVendaLoja itemVenda = new ItemVendaLoja();
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(qtdDesejada.doubleValue());
			itemVenda.setEmpresa(empresa);
			itensParaSalvar.add(itemVenda);
		}

		// Se o lojista definiu restrições de escopo no cupom e nenhum item do carrinho era elegível, barra o checkout
		if (cupomValido != null && cupomPossuiRestricaoDeEscopo && !peloMenosUmItemValidoParaOCupom) {
			throw new ExceptionCustom(
					"Cupom Inválido: O cupom informado não é elegível para nenhum dos produtos do seu carrinho.");
		}
		// 5. Mapear e Configurar o Registro Principal da Venda
		VendaLojaVirtual model = vendaLojaVirtualMapper.toModel(dto);
		model.setCupomDesconto(cupomValido);
		model.setEmpresa(empresa);
		model.setPessoa(comprador);
		model.setEnderecoEntrega(entrega);
		model.setEnderecoCobranca(cobranca);
		model.setFormaPagamento(formaPagamento);
		model.setNotaFiscalVenda(null);
		
		// 6. Persistir o Registro Principal (Aciona o @PrePersist com a autogeração do numeroPedido)
		VendaLojaVirtual vendaSalva = vendaLojaVirtualRepository.save(model);
		
		// 7. Vincular os Itens do Carrinho à Venda Salva e Persistir Chaves
		for (ItemVendaLoja item : itensParaSalvar) {
			item.setVendaLojaVirtual(vendaSalva);
			itemVendaLojaRepository.save(item);
		}
		
		// 8. Se o cupom passou em todas as regras, computa o uso somando +1 no banco
		if (cupomValido != null) {
			cupomValido.setQuantidadeUsado(cupomValido.getQuantidadeUsado() + 1);
			cupomDescontoRepository.save(cupomValido);
		}
		
		// 🔥 9. AUTOMATIZAÇÃO DO FRETE: Gera o marco inicial cronológico na linha do tempo logística do cliente
		StatusRastreio marcoInicial = new StatusRastreio();
		marcoInicial.setVendaLojaVirtual(vendaSalva);
		marcoInicial.setEmpresa(empresa);
		marcoInicial.setCentroDistribuicao("CD Central BandAmpla");
		marcoInicial.setCidade(entrega.getCidade());
		marcoInicial.setEstado(entrega.getUf());
		marcoInicial.setStatus("Pedido recebido no sistema. Frete reservado e aguardando confirmação do pagamento.");
		statusRastreioRepository.save(marcoInicial);
		
		// 10. Simulação do Gateway de Pagamento
		boolean pagamentoAprovado = simularGatewayPagamento(vendaSalva, dto);
		if (!pagamentoAprovado) {
			throw new ExceptionCustom("A transação financeira foi recusada pela operadora de cartão/banco.");
		}
		
		// 🔥 Atualiza a linha do tempo logística do frete indicando aprovação financeira imediata
		StatusRastreio marcoPagamento = new StatusRastreio();
		marcoPagamento.setVendaLojaVirtual(vendaSalva);
		marcoPagamento.setEmpresa(empresa);
		marcoPagamento.setCentroDistribuicao("CD Central BandAmpla");
		marcoPagamento.setCidade(entrega.getCidade());
		marcoPagamento.setEstado(entrega.getUf());
		marcoPagamento.setStatus("Pagamento confirmado com sucesso. O prazo estimado de " + vendaSalva.getDiasEntrega()
				+ " dias para entrega foi iniciado.");
		statusRastreioRepository.save(marcoPagamento);
		enviarEmailConfirmacaoPedido(vendaSalva);
		
		// Construir Resposta DTO contendo a árvore populada unificada
		VendaLojaVirtualDTO dtoRetorno = vendaLojaVirtualMapper.toDTO(vendaSalva);
		dtoRetorno.setItens(converterItensParaDTO(itemVendaLojaRepository.buscarPorVendaId(vendaSalva.getId())));
		return dtoRetorno;
	}

	private boolean simularGatewayPagamento(VendaLojaVirtual venda, VendaLojaVirtualDTO dto) {
		System.out.println(
				"====== [GATEWAY SIMULADO] Processando faturamento do pedido: " + venda.getNumeroPedido() + " ======");
		return true;
	}

	private void enviarEmailConfirmacaoPedido(VendaLojaVirtual venda) {
		try {
			String html = "Olá " + venda.getPessoa().getNome() + "!" + "Seu pedido " + venda.getNumeroPedido()
					+ " foi recebido e o frete já foi contratado com sucesso." + "Prazo estimado de entrega: "
					+ venda.getDiasEntrega() + " dias úteis." + "Valor total pago: R$ " + venda.getValorTotal() + "";
			sendMailService.enviarEmailHtml("Pedido Confirmado e Frete Contratado - " + venda.getNumeroPedido(), html,
					venda.getPessoa().getEmail());
		} catch (Exception e) {
			System.err.println("====== Falha não impeditiva ao despachar e-mail: " + e.getMessage());
		}
	}

	@Transactional(rollbackFor = Exception.class)
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

		// Limpa o histórico físico de rastreio de frete associado antes de apagar a
		// venda pai do banco
		List<StatusRastreio> rastreios = statusRastreioRepository.buscarRastreioPorVendaId(venda.getId());
		statusRastreioRepository.deleteAll(rastreios);
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
		dto.setItens(converterItensParaDTO(itemVendaLojaRepository.buscarPorVendaId(venda.getId())));
		return dto;
	}

	public List<VendaLojaVirtualDTO> buscarPorNumeroPedido(String numeroPedido, UsuarioLogadoPrincipal usuarioLogado) {
		Specification<VendaLojaVirtual> spec = Specification
				.where(VendaLojaVirtualSpec.empresaIgual(usuarioLogado.getEmpresaId()))
				.and(VendaLojaVirtualSpec.numeroPedidoContem(numeroPedido));

		return vendaLojaVirtualRepository.findAll(spec).stream().map(v -> {
			VendaLojaVirtualDTO d = vendaLojaVirtualMapper.toDTO(v);
			d.setItens(converterItensParaDTO(itemVendaLojaRepository.buscarPorVendaId(v.getId())));
			return d;
		}).collect(Collectors.toList());
	}

	public List<VendaLojaVirtualDTO> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado) {
		Specification<VendaLojaVirtual> spec = Specification
				.where(VendaLojaVirtualSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return vendaLojaVirtualRepository.findAll(spec).stream().map(v -> {
			VendaLojaVirtualDTO d = vendaLojaVirtualMapper.toDTO(v);
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
			d.setItens(converterItensParaDTO(itemVendaLojaRepository.buscarPorVendaId(v.getId())));
			return d;
		});
	}
	// 🔥 Conversores auxiliares internos de tipos para tráfego seguro de
	// BigDecimal em itens de carrinho DTO

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