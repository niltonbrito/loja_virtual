package com.bandampla.lojavirtual;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.bandampla.lojavirtual.controller.PessoaController;
import com.bandampla.lojavirtual.dto.NotaFiscalCompraDTO;
import com.bandampla.lojavirtual.dto.NotaItemProdutoDTO;
import com.bandampla.lojavirtual.enums.TipoPessoa;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.NotaFiscalCompra;
import com.bandampla.lojavirtual.model.NotaItemProduto;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Produto;
import com.bandampla.lojavirtual.repository.ContaPagarRepository;
import com.bandampla.lojavirtual.repository.NotaFiscalCompraRepository;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.ProdutoRepository;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.service.ContaPagarService;
import com.bandampla.lojavirtual.service.NotaFiscalCompraService;
import com.bandampla.lojavirtual.service.ProdutoService;

import junit.framework.TestCase;

@Profile("dev")
@SpringBootTest(classes = LojaVirtualApplication.class)
class TesteNotaFiscalCompra extends TestCase {

	@Autowired
	private PessoaController pessoaController;

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private NotaFiscalCompraRepository notaFiscalCompraRepository;
	@Autowired
	private ContaPagarRepository contaPagarRepository;

	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private NotaFiscalCompraService notaFiscalCompraService;
	@Autowired
	private ContaPagarService contaPagarService;

	private UsuarioLogadoPrincipal usuarioLogado;

	@Test
	void deveCadastrarNotaFiscalComFornecedorPJ() throws ExceptionCustom {

		NotaFiscalCompraDTO dto = new NotaFiscalCompraDTO();
		dto.setNumeroNota("071998");
		dto.setSerieNota("1");
		dto.setValorTotal(BigDecimal.valueOf(8750));
		dto.setValorIcms(BigDecimal.valueOf(10575));
		dto.setDataCompra(LocalDate.of(2026, 6, 16));
		dto.setPessoaId(10L);
		dto.setTipoPessoaFornecedor(TipoPessoa.JURIDICA);

		NotaItemProdutoDTO item = new NotaItemProdutoDTO();
		item.setProdutoId(12L);
		item.setQuantidade(BigDecimal.valueOf(5));
		item.setValorUnitarioCusto(BigDecimal.valueOf(1200));

		dto.setItens(List.of(item));

		PessoaJuridica empresa = new PessoaJuridica();
		empresa.setId(1L);

		PessoaJuridica fornecedor = new PessoaJuridica();
		fornecedor.setId(10L);

		Produto produto = new Produto();
		produto.setId(12L);
		produto.setQtdEstoque(BigDecimal.ZERO);
		produto.setEmpresa(empresa);

		when(pessoaJuridicaRepository.findById(1L)).thenReturn(Optional.of(empresa));
		when(pessoaJuridicaRepository.findById(10L)).thenReturn(Optional.of(fornecedor));
		when(produtoRepository.findById(12L)).thenReturn(Optional.of(produto));
		when(notaFiscalCompraRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
		when(contaPagarRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

		NotaFiscalCompraDTO retorno = notaFiscalCompraService.cadastrar(dto, usuarioLogado);

		assertNotNull(retorno);
		assertEquals("071998", retorno.getNumeroNota());
		assertEquals(1, retorno.getItens().size());
		assertEquals(BigDecimal.valueOf(5), retorno.getItens().get(0).getQuantidade());
		assertEquals(BigDecimal.valueOf(5), produto.getQtdEstoque());
	}

	@Test
	void deveCadastrarNotaFiscalComFornecedorPF() throws ExceptionCustom {

		NotaFiscalCompraDTO dto = new NotaFiscalCompraDTO();
		dto.setNumeroNota("000123");
		dto.setSerieNota("1");
		dto.setValorTotal(BigDecimal.valueOf(3200));
		dto.setValorIcms(BigDecimal.ZERO);
		dto.setDataCompra(LocalDate.of(2026, 6, 20));
		dto.setPessoaId(55L);
		dto.setTipoPessoaFornecedor(TipoPessoa.FISICA);

		NotaItemProdutoDTO item = new NotaItemProdutoDTO();
		item.setProdutoId(22L);
		item.setQuantidade(BigDecimal.valueOf(20));
		item.setValorUnitarioCusto(BigDecimal.valueOf(80));

		dto.setItens(List.of(item));

		PessoaJuridica empresa = new PessoaJuridica();
		empresa.setId(1L);

		PessoaFisica fornecedor = new PessoaFisica();
		fornecedor.setId(55L);

		Produto produto = new Produto();
		produto.setId(22L);
		produto.setQtdEstoque(BigDecimal.ZERO);
		produto.setEmpresa(empresa);

		when(pessoaJuridicaRepository.findById(1L)).thenReturn(Optional.of(empresa));
		when(pessoaFisicaRepository.findById(55L)).thenReturn(Optional.of(fornecedor));
		when(produtoRepository.findById(22L)).thenReturn(Optional.of(produto));
		when(notaFiscalCompraRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
		when(contaPagarRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

		NotaFiscalCompraDTO retorno = notaFiscalCompraService.cadastrar(dto, usuarioLogado);

		assertNotNull(retorno);
		assertEquals("000123", retorno.getNumeroNota());
		assertEquals(1, retorno.getItens().size());
		assertEquals(BigDecimal.valueOf(20), retorno.getItens().get(0).getQuantidade());
		assertEquals(BigDecimal.valueOf(20), produto.getQtdEstoque());
	}

	@Test
	void deveReverterEstoqueAoAtualizarNF() throws ExceptionCustom {

		NotaFiscalCompra nf = new NotaFiscalCompra();
		nf.setId(1L);

		Produto produto = new Produto();
		produto.setId(12L);
		produto.setQtdEstoque(BigDecimal.valueOf(10));

		NotaItemProduto itemAntigo = new NotaItemProduto();
		itemAntigo.setProduto(produto);
		itemAntigo.setQuantidade(BigDecimal.valueOf(5));

		nf.setItens(new ArrayList<>(List.of(itemAntigo)));

		when(notaFiscalCompraRepository.findById(1L)).thenReturn(Optional.of(nf));
		when(produtoRepository.findById(12L)).thenReturn(Optional.of(produto));

		NotaFiscalCompraDTO dto = new NotaFiscalCompraDTO();
		dto.setNumeroNota("071998");
		dto.setPessoaId(10L);
		dto.setTipoPessoaFornecedor(TipoPessoa.JURIDICA);

		NotaItemProdutoDTO novoItem = new NotaItemProdutoDTO();
		novoItem.setProdutoId(12L);
		novoItem.setQuantidade(BigDecimal.valueOf(3));
		novoItem.setValorUnitarioCusto(BigDecimal.valueOf(100));

		dto.setItens(List.of(novoItem));

		PessoaJuridica fornecedor = new PessoaJuridica();
		fornecedor.setId(10L);

		when(pessoaJuridicaRepository.findById(10L)).thenReturn(Optional.of(fornecedor));

		notaFiscalCompraService.atualizar(1L, dto, usuarioLogado);

		assertEquals(BigDecimal.valueOf(8), produto.getQtdEstoque());
	}

}
