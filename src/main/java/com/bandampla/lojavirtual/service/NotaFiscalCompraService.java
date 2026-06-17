package com.bandampla.lojavirtual.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.dto.NotaFiscalCompraDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.NotaFiscalCompraMapper;
import com.bandampla.lojavirtual.model.ContaPagar;
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
import com.bandampla.lojavirtual.repository.specification.NotaFiscalCompraSpec;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

@Service
public class NotaFiscalCompraService {

	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final PessoaFisicaRepository pessoaFisicaRepository;
	private final NotaFiscalCompraRepository notaFiscalCompraRepository;
	private final ContaPagarRepository contaPagarRepository;
	private final ProdutoRepository produtoRepository;

	private final NotaFiscalCompraMapper notaFiscalCompraMapper;

	public NotaFiscalCompraService(PessoaJuridicaRepository pessoaJuridicaRepository,
			PessoaFisicaRepository pessoaFisicaRepository, NotaFiscalCompraRepository notaFiscalCompraRepository,
			NotaFiscalCompraMapper notaFiscalCompraMapper, ContaPagarRepository contaPagarRepository,
			ProdutoRepository produtoRepository) {
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.pessoaFisicaRepository = pessoaFisicaRepository;
		this.notaFiscalCompraRepository = notaFiscalCompraRepository;
		this.contaPagarRepository = contaPagarRepository;
		this.produtoRepository = produtoRepository;
		this.notaFiscalCompraMapper = notaFiscalCompraMapper;
	}

	public NotaFiscalCompraDTO cadastrar(NotaFiscalCompraDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		Specification<NotaFiscalCompra> specDuplicidade = Specification
				.where(NotaFiscalCompraSpec.numeroNotaExato(dto.getNumeroNota()))
				.and(NotaFiscalCompraSpec.contaPagarIgual(dto.getContaPagarId()))
				.and(NotaFiscalCompraSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		if (notaFiscalCompraRepository.exists(specDuplicidade)) {
			throw new ExceptionCustom("Já existe Nota Fiscal de Compra com numero '" + dto.getNumeroNota()
					+ "' cadastrada para esta Conta a Pagar.");
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(dto.getPessoaId())
				.orElseThrow(() -> new ExceptionCustom("Pessoa não encontrada"));

		ContaPagar contaPagar = contaPagarRepository.findById(dto.getContaPagarId())
				.orElseThrow(() -> new ExceptionCustom("Conta a Pagar não encontrada"));

		if (!contaPagar.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("A Conta a Pagar informada não pertence à empresa.");
		}

		NotaFiscalCompra model = notaFiscalCompraMapper.toModel(dto);
		model.setEmpresa(empresa);
		model.setPessoa(pessoaFisica);
		model.setContaPagar(contaPagar);

		// 4. 🔥 CORREÇÃO CRÍTICA: Processamento e amarração dos Itens da Nota
		if (model.getItens() != null && !model.getItens().isEmpty()) {
			for (NotaItemProduto item : model.getItens()) {

				if (item.getProduto() == null || item.getProduto().getId() == null) {
					throw new ExceptionCustom("Um dos itens enviados não possui um produto válido.");
				}
				
				// Opcional/Recomendado: Buscar o produto do banco para garantir que ele existe
				Produto produto = produtoRepository.findById(item.getProduto().getId()).orElseThrow(
						() -> new ExceptionCustom("Produto ID " + item.getProduto().getId() + " não encontrado"));
				
				item.setEmpresa(empresa);
				item.setNotaFiscalCompra(model);
				item.setProduto(produto);
			}
		} else {
			throw new ExceptionCustom("Não é possível cadastrar uma Nota Fiscal sem itens/produtos.");
		}

		return notaFiscalCompraMapper.toDTO(notaFiscalCompraRepository.save(model));
	}

	public NotaFiscalCompraDTO atualizar(Long id, NotaFiscalCompraDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido ou ausente");
		}

		NotaFiscalCompra notaFiscalCompra = notaFiscalCompraRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Nota Fiscal de Compra não encontrada com o código: " + id));

		// Verifica se o registro pertence à empresa do usuário
		if (!notaFiscalCompra.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Acesso negado: esta Nota Fiscal de Compra não pertence à sua empresa.");
		}

		// Validação de duplicidade (nome já existe em outro registro)
		Specification<NotaFiscalCompra> specDuplicidade = Specification
				.where(NotaFiscalCompraSpec.numeroNotaExato(dto.getNumeroNota()))
				.and(NotaFiscalCompraSpec.contaPagarIgual(dto.getContaPagarId()))
				.and(NotaFiscalCompraSpec.empresaIgual(usuarioLogado.getEmpresaId()))
				.and(NotaFiscalCompraSpec.idDiferente(id));

		if (notaFiscalCompraRepository.exists(specDuplicidade)) {
			throw new ExceptionCustom("Já existe Nota Fiscal de Compra com numero '" + dto.getNumeroNota()
					+ "' cadastrada para esta Conta a Pagar.");
		}
		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(dto.getPessoaId())
				.orElseThrow(() -> new ExceptionCustom("Pessoa não encontrada"));

		ContaPagar contaPagar = contaPagarRepository.findById(dto.getContaPagarId())
				.orElseThrow(() -> new ExceptionCustom("Conta a Pagar não encontrada"));

		if (!contaPagar.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("A Conta a Pagar informada não pertence à empresa.");
		}

		notaFiscalCompraMapper.atualizarCamposDaConta(dto, notaFiscalCompra);
		notaFiscalCompra.setEmpresa(empresa);
		notaFiscalCompra.setPessoa(pessoaFisica);
		notaFiscalCompra.setContaPagar(contaPagar);

		return notaFiscalCompraMapper.toDTO(notaFiscalCompraRepository.save(notaFiscalCompra));
	}

	@Transactional(rollbackFor = Exception.class)
	public void deletar(Long id, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido ou ausente");
		}

		NotaFiscalCompra notaFiscalCompra = notaFiscalCompraRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Nota Fiscal de Compra não encontrada com o código: " + id));

		// 🛡️ Segurança Multi-Tenant
		if (!notaFiscalCompra.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Acesso negado: esta Nota Fiscal de Compra não pertence à sua empresa.");
		}

		notaFiscalCompraRepository.delete(notaFiscalCompra);
	}

	public List<NotaFiscalCompraDTO> buscarPorDescricao(String numeroNota, UsuarioLogadoPrincipal usuarioLogado) {
		Specification<NotaFiscalCompra> spec = Specification.where(NotaFiscalCompraSpec.numeroNotaContem(numeroNota))
				.and(NotaFiscalCompraSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return notaFiscalCompraRepository.findAll(spec).stream().map(notaFiscalCompraMapper::toDTO).toList();
	}

	public List<NotaFiscalCompraDTO> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado) {
		Specification<NotaFiscalCompra> spec = Specification
				.where(NotaFiscalCompraSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return notaFiscalCompraRepository.findAll(spec).stream().map(notaFiscalCompraMapper::toDTO).toList();
	}

	public Page<NotaFiscalCompraDTO> buscarAvancado(String numeroNota, int page, int size,
			UsuarioLogadoPrincipal usuarioLogado) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<NotaFiscalCompra> spec = Specification.where(NotaFiscalCompraSpec.numeroNotaContem(numeroNota))
				.and(NotaFiscalCompraSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return notaFiscalCompraRepository.findAll(spec, pageable).map(notaFiscalCompraMapper::toDTO);
	}

	public Page<NotaFiscalCompraDTO> buscarPaginado(int page, int size, String sort, String direction,
			UsuarioLogadoPrincipal usuarioLogado) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);
		Specification<NotaFiscalCompra> spec = Specification
				.where(NotaFiscalCompraSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return notaFiscalCompraRepository.findAll(spec, pageable).map(notaFiscalCompraMapper::toDTO);
	}
}