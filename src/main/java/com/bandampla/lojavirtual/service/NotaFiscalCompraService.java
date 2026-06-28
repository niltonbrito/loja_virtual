package com.bandampla.lojavirtual.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.dto.NotaFiscalCompraDTO;
import com.bandampla.lojavirtual.dto.NotaItemProdutoDTO;
import com.bandampla.lojavirtual.enums.StatusContaPagar;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.NotaFiscalCompraMapper;
import com.bandampla.lojavirtual.model.ContaPagar;
import com.bandampla.lojavirtual.model.NotaFiscalCompra;
import com.bandampla.lojavirtual.model.NotaItemProduto;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Produto;
import com.bandampla.lojavirtual.repository.ContaPagarRepository;
import com.bandampla.lojavirtual.repository.NotaFiscalCompraRepository;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.ProdutoRepository;
import com.bandampla.lojavirtual.repository.specification.NotaFiscalCompraSpec;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

@Service
public class NotaFiscalCompraService {

	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final NotaFiscalCompraRepository notaFiscalCompraRepository;
	private final ContaPagarRepository contaPagarRepository;
	private final ProdutoRepository produtoRepository;
	private final NotaFiscalCompraMapper notaFiscalCompraMapper;

	public NotaFiscalCompraService(PessoaJuridicaRepository pessoaJuridicaRepository,
			PessoaFisicaRepository pessoaFisicaRepository, NotaFiscalCompraRepository notaFiscalCompraRepository,
			NotaFiscalCompraMapper notaFiscalCompraMapper, ContaPagarRepository contaPagarRepository,
			ProdutoRepository produtoRepository) {

		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.notaFiscalCompraRepository = notaFiscalCompraRepository;
		this.contaPagarRepository = contaPagarRepository;
		this.produtoRepository = produtoRepository;
		this.notaFiscalCompraMapper = notaFiscalCompraMapper;
	}

	// ============================================================================================
	// CADASTRAR NOTA FISCAL (BigDecimal + Conta a Pagar automática + PF/PJ)
	// ============================================================================================
	@Transactional(rollbackFor = Exception.class)
	public NotaFiscalCompraDTO cadastrar(NotaFiscalCompraDTO dto, UsuarioLogadoPrincipal usuarioLogado)
	        throws ExceptionCustom {

	    PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
	            .orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

	    PessoaJuridica fornecedor = pessoaJuridicaRepository.findById(dto.getPessoaId())
	            .orElseThrow(() -> new ExceptionCustom("Fornecedor não encontrado"));

	    NotaFiscalCompra nf = notaFiscalCompraMapper.toModel(dto);
	    nf.setEmpresa(empresa);
	    nf.setPessoa(fornecedor);
	    nf.setItens(new ArrayList<>());

	    for (NotaItemProdutoDTO itemDTO : dto.getItens()) {

	        Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
	                .orElseThrow(() -> new ExceptionCustom("Produto não encontrado"));

	        NotaItemProduto item = new NotaItemProduto();
	        item.setEmpresa(empresa);
	        item.setNotaFiscalCompra(nf);
	        item.setProduto(produto);
	        item.setValorUnitarioCusto(itemDTO.getValorUnitarioCusto());
	        item.setQuantidade(itemDTO.getQuantidade());

	        // Atualizar estoque

            BigDecimal estoqueAtual = produto.getQtdEstoque() == null ? BigDecimal.ZERO : produto.getQtdEstoque();
            BigDecimal entrada = item.getQuantidade();
            produto.setQtdEstoque(estoqueAtual.add(entrada));
            produtoRepository.save(produto);
	        nf.getItens().add(item);
	    }

	    nf = notaFiscalCompraRepository.save(nf);

	    // Criar conta a pagar
	    ContaPagar conta = new ContaPagar();
	    conta.setDescricao("NF Compra nº " + nf.getNumeroNota());
	    conta.setEmpresa(empresa);
	    conta.setPessoa(empresa); // quem paga é a empresa
	    conta.setPessoaFornecedor(fornecedor);
	    conta.setValorTotal(nf.getValorTotal());
	    conta.setStatus(StatusContaPagar.ABERTA);
	    conta.setDataVencimento(nf.getDataCompra());

	    conta = contaPagarRepository.save(conta);

	    nf.setContaPagar(conta);
	    nf = notaFiscalCompraRepository.save(nf);

	    // Montar retorno com itens
	    NotaFiscalCompraDTO dtoRetorno = notaFiscalCompraMapper.toDTO(nf);
	    dtoRetorno.setItens(
	        nf.getItens().stream().map(item -> {
	            NotaItemProdutoDTO itemDTO = new NotaItemProdutoDTO();
	            itemDTO.setId(item.getId());
	            itemDTO.setQuantidade(item.getQuantidade());
	            itemDTO.setValorUnitarioCusto(item.getValorUnitarioCusto());
	            itemDTO.setProdutoId(item.getProduto().getId());
	            itemDTO.setNotaFiscalCompra(item.getNotaFiscalCompra().getId());
	            return itemDTO;
	        }).toList()
	    );

	    return dtoRetorno;
	}


	// ============================================================================================
	// ATUALIZAR NOTA FISCAL (reversão e reentrada de estoque + atualizar Conta a
	// Pagar)
	// ============================================================================================
	@Transactional(rollbackFor = Exception.class)
	public NotaFiscalCompraDTO atualizar(Long id, NotaFiscalCompraDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido ou ausente");
		}

		NotaFiscalCompra nf = notaFiscalCompraRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Nota Fiscal de Compra não encontrada com o código: " + id));

		if (!nf.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Acesso negado: esta Nota Fiscal de Compra não pertence à sua empresa.");
		}

		Specification<NotaFiscalCompra> specDuplicidade = Specification
				.where(NotaFiscalCompraSpec.numeroNotaExato(dto.getNumeroNota()))
				.and(NotaFiscalCompraSpec.empresaIgual(usuarioLogado.getEmpresaId()))
				.and(NotaFiscalCompraSpec.idDiferente(id));

		if (notaFiscalCompraRepository.exists(specDuplicidade)) {
			throw new ExceptionCustom("Já existe Nota Fiscal de Compra com número '" + dto.getNumeroNota()
					+ "' cadastrada para esta empresa.");
		}

		PessoaJuridica fornecedor = pessoaJuridicaRepository.findById(dto.getPessoaId())
				.orElseThrow(() -> new ExceptionCustom("Fornecedor não encontrado"));

		if (dto.getItens() == null || dto.getItens().isEmpty()) {
			throw new ExceptionCustom("A nota fiscal deve conter ao menos um item.");
		}

		notaFiscalCompraMapper.atualizarCamposDaConta(dto, nf);
		nf.setPessoa(fornecedor);

		// Reverter estoque dos itens antigos
		if (nf.getItens() != null) {
			for (NotaItemProduto antigo : nf.getItens()) {
				Produto produto = antigo.getProduto();
				BigDecimal estoqueAtual = produto.getQtdEstoque() == null ? BigDecimal.ZERO : produto.getQtdEstoque();
				BigDecimal saida = antigo.getQuantidade();
				produto.setQtdEstoque(estoqueAtual.subtract(saida));
				produtoRepository.save(produto);
			}
		}

		nf.getItens().clear();

		// Processar novos itens
		for (NotaItemProdutoDTO itemDTO : dto.getItens()) {

			Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
					.orElseThrow(() -> new ExceptionCustom("Produto ID " + itemDTO.getProdutoId() + " não encontrado"));

			if (!produto.getEmpresa().getId().equals(nf.getEmpresa().getId())) {
				throw new ExceptionCustom("Produto ID " + produto.getId() + " não pertence à empresa.");
			}

			NotaItemProduto item = new NotaItemProduto();
			item.setEmpresa(nf.getEmpresa());
			item.setNotaFiscalCompra(nf);
			item.setProduto(produto);
			item.setValorUnitarioCusto(itemDTO.getValorUnitarioCusto());
			item.setQuantidade(itemDTO.getQuantidade());

			BigDecimal estoqueAtual = produto.getQtdEstoque() == null ? BigDecimal.ZERO : produto.getQtdEstoque();
			BigDecimal entrada = item.getQuantidade();
			produto.setQtdEstoque(estoqueAtual.add(entrada));
			produtoRepository.save(produto);

			nf.getItens().add(item);
		}

		// Atualizar Conta a Pagar vinculada
		if (nf.getContaPagar() != null) {
			ContaPagar conta = nf.getContaPagar();
			conta.setDescricao("NF Compra nº " + nf.getNumeroNota());
			conta.setPessoaFornecedor(fornecedor);
			conta.setValorTotal(nf.getValorTotal());
			conta.setDataVencimento(nf.getDataCompra());
			contaPagarRepository.save(conta);
		}

		nf = notaFiscalCompraRepository.save(nf);
		return notaFiscalCompraMapper.toDTO(nf);
	}

	// ============================================================================================
	// DELETAR (reversão de estoque)
	// ============================================================================================
	@Transactional(rollbackFor = Exception.class)
	public void deletar(Long id, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido ou ausente");
		}

		NotaFiscalCompra nf = notaFiscalCompraRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Nota Fiscal de Compra não encontrada com o código: " + id));

		if (!nf.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Acesso negado: esta Nota Fiscal de Compra não pertence à sua empresa.");
		}

		if (nf.getItens() != null) {
			for (NotaItemProduto item : nf.getItens()) {
				Produto produto = item.getProduto();
				BigDecimal estoqueAtual = produto.getQtdEstoque() == null ? BigDecimal.ZERO : produto.getQtdEstoque();
				BigDecimal saida = item.getQuantidade();
				produto.setQtdEstoque(estoqueAtual.subtract(saida));
				produtoRepository.save(produto);
			}
		}

		notaFiscalCompraRepository.delete(nf);
	}

	// ============================================================================================
	// CONSULTAS
	// ============================================================================================
	public Page<NotaFiscalCompraDTO> buscarPaginado(int page, int size, String sort, String direction,
			UsuarioLogadoPrincipal usuarioLogado) {

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);
		Specification<NotaFiscalCompra> spec = Specification
				.where(NotaFiscalCompraSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return notaFiscalCompraRepository.findAll(spec, pageable).map(notaFiscalCompraMapper::toDTO);
	}

	public Page<NotaFiscalCompraDTO> buscarAvancado(String numeroNota, int page, int size,
			UsuarioLogadoPrincipal usuarioLogado) {

		Pageable pageable = PageRequest.of(page, size);
		Specification<NotaFiscalCompra> spec = Specification.where(NotaFiscalCompraSpec.numeroNotaContem(numeroNota))
				.and(NotaFiscalCompraSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return notaFiscalCompraRepository.findAll(spec, pageable).map(notaFiscalCompraMapper::toDTO);
	}

	public List<NotaFiscalCompraDTO> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado) {
		Specification<NotaFiscalCompra> spec = Specification
				.where(NotaFiscalCompraSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return notaFiscalCompraRepository.findAll(spec).stream().map(notaFiscalCompraMapper::toDTO).toList();
	}

	public List<NotaFiscalCompraDTO> buscarPorDescricao(String numeroNota, UsuarioLogadoPrincipal usuarioLogado) {
		Specification<NotaFiscalCompra> spec = Specification.where(NotaFiscalCompraSpec.numeroNotaContem(numeroNota))
				.and(NotaFiscalCompraSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return notaFiscalCompraRepository.findAll(spec).stream().map(notaFiscalCompraMapper::toDTO).toList();
	}
}
