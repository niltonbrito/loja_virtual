package com.bandampla.lojavirtual.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.dto.CupomDescontoDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.CupomDescontoMapper;
import com.bandampla.lojavirtual.model.CategoriaProduto;
import com.bandampla.lojavirtual.model.CupomDesconto;
import com.bandampla.lojavirtual.model.MarcaProduto;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Produto;
import com.bandampla.lojavirtual.repository.CategoriaProdutoRepository;
import com.bandampla.lojavirtual.repository.CupomDescontoRepository;
import com.bandampla.lojavirtual.repository.MarcaProdutoRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.ProdutoRepository;
import com.bandampla.lojavirtual.repository.specification.CupomDescontoSpec;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

@Service
public class CupomDescontoService {

	private final CupomDescontoRepository cupomDescontoRepository;
	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final CategoriaProdutoRepository categoriaProdutoRepository;
	private final MarcaProdutoRepository marcaProdutoRepository;
	private final ProdutoRepository produtoRepository;
	private final CupomDescontoMapper cupomDescontoMapper;

	// 🔥 Fórmula Dourada: Injeção de dependências estritamente por construtor
	// imutável (final)
	public CupomDescontoService(CupomDescontoRepository cupomDescontoRepository,
			PessoaJuridicaRepository pessoaJuridicaRepository, CategoriaProdutoRepository categoriaProdutoRepository,
			MarcaProdutoRepository marcaProdutoRepository, ProdutoRepository produtoRepository,
			CupomDescontoMapper cupomDescontoMapper) {
		this.cupomDescontoRepository = cupomDescontoRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.categoriaProdutoRepository = categoriaProdutoRepository;
		this.marcaProdutoRepository = marcaProdutoRepository;
		this.produtoRepository = produtoRepository;
		this.cupomDescontoMapper = cupomDescontoMapper;
	}

	@Transactional(rollbackFor = Exception.class)
	public CupomDescontoDTO cadastrar(CupomDescontoDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		Specification<CupomDesconto> specDuplicidade = Specification
				.where(CupomDescontoSpec.codigoExata(dto.getCodigo()))
				.and(CupomDescontoSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		if (cupomDescontoRepository.exists(specDuplicidade)) {
			throw new ExceptionCustom("Já existe Cupom de desconto com o código '" + dto.getCodigo()
					+ "' cadastrado para esta empresa.");
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		CupomDesconto cupom = cupomDescontoMapper.toModel(dto);
		cupom.setEmpresa(empresa);
		cupom.setQuantidadeUsado(0); // Força inicialização segura do contador de saldos

		// 🔥 PROCESSA AS INTERMEDIÁRIAS COM VALIDAÇÃO ANTI-TENANT LEAK
		vincularRelacionamentosDeEscopo(dto, cupom, usuarioLogado.getEmpresaId());

		return cupomDescontoMapper.toDTO(cupomDescontoRepository.save(cupom));
	}

	@Transactional(rollbackFor = Exception.class)
	public CupomDescontoDTO atualizar(Long id, CupomDescontoDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido ou ausente");
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		CupomDesconto existente = cupomDescontoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Cupom de desconto não encontrado com o código: " + id));

		if (!existente.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("Acesso negado: este Cupom de Desconto não pertence à sua empresa.");
		}

		Specification<CupomDesconto> specDuplicidade = Specification
				.where(CupomDescontoSpec.codigoExata(dto.getCodigo()))
				.and(CupomDescontoSpec.empresaIgual(usuarioLogado.getEmpresaId()))
				.and(CupomDescontoSpec.idDiferente(id));

		if (cupomDescontoRepository.exists(specDuplicidade)) {
			throw new ExceptionCustom("Já existe outro Cupom de desconto com o código '" + dto.getCodigo()
					+ "' cadastrado para esta empresa.");
		}
		
		// 1. Recupera o saldo atual antes da colagem do Mapper
		Integer qtdUsadaAntes = existente.getQuantidadeUsado();
		Integer limiteTotalAntes = existente.getLimiteUsoTotal();

		existente.setEmpresa(empresa);
		cupomDescontoMapper.atualizarCamposDoCupomDesconto(dto, existente);

		// Se o DTO veio nulo, força a volta do valor que já estava no banco
		if (dto.getQuantidadeUsado() == null) {
			existente.setQuantidadeUsado(qtdUsadaAntes);
		}
		if (dto.getLimiteUsoTotal() == null) {
			existente.setLimiteUsoTotal(limiteTotalAntes);
		}
		// 🔥 ATUALIZA OS VÍNCULOS INTERMEDIÁRIOS DINÂMICOS
		vincularRelacionamentosDeEscopo(dto, existente, usuarioLogado.getEmpresaId());

		return cupomDescontoMapper.toDTO(cupomDescontoRepository.save(existente));
	}

	private void vincularRelacionamentosDeEscopo(CupomDescontoDTO dto, CupomDesconto cupom, Long empresaId)
			throws ExceptionCustom {
		// 1. Vincular Categorias Restritas
		if (dto.getCategoriasIds() != null && !dto.getCategoriasIds().isEmpty()) {
			List<CategoriaProduto> categorias = new ArrayList<>();
			for (Long catId : dto.getCategoriasIds()) {
				CategoriaProduto cat = categoriaProdutoRepository.findById(catId).orElseThrow(
						() -> new ExceptionCustom("Categoria ID " + catId + " inválida para amarração do escopo."));
				if (!cat.getEmpresa().getId().equals(empresaId)) {
					// 🔥 Ajustado para usar o campo real do seu modelo: getNomeDescricao()
					throw new ExceptionCustom("Segurança Violada: A categoria '" + cat.getDescricao()
							+ "' não pertence ao catálogo da sua empresa.");
				}
				categorias.add(cat);
			}
			cupom.setCategorias(categorias);
		} else {
			cupom.setCategorias(null);
		}

		// 2. Vincular Marcas Restritas
		if (dto.getMarcasIds() != null && !dto.getMarcasIds().isEmpty()) {
			List<MarcaProduto> marcas = new ArrayList<>();
			for (Long marcaId : dto.getMarcasIds()) {
				MarcaProduto marca = marcaProdutoRepository.findById(marcaId).orElseThrow(
						() -> new ExceptionCustom("Marca ID " + marcaId + " inválida para amarração do escopo."));
				if (!marca.getEmpresa().getId().equals(empresaId)) {
					// 🔥 Ajustado para usar o campo real do seu modelo: getNomeDescricao()
					throw new ExceptionCustom("Segurança Violada: A marca '" + marca.getDescricao()
							+ "' não pertence à sua empresa.");
				}
				marcas.add(marca);
			}
			cupom.setMarcas(marcas);
		} else {
			cupom.setMarcas(null);
		}

		// 3. Vincular Produtos Cirúrgicos
		if (dto.getProdutosIds() != null && !dto.getProdutosIds().isEmpty()) {
			List<Produto> produtos = new ArrayList<>();
			for (Long prodId : dto.getProdutosIds()) {
				Produto prod = produtoRepository.findById(prodId).orElseThrow(() -> new ExceptionCustom(
						"Produto ID " + prodId + " inválido para faturar campanha promocional."));
				if (!prod.getEmpresa().getId().equals(empresaId)) {
					// 🔥 Mantido o campo real do seu modelo: getNome()
					throw new ExceptionCustom(
							"Segurança Violada: O produto '" + prod.getNome() + "' não pertence à sua empresa.");
				}
				produtos.add(prod);
			}
			cupom.setProdutos(produtos);
		} else {
			cupom.setProdutos(null);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void deletar(Long id, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido ou ausente");
		}

		CupomDesconto cupomDesconto = cupomDescontoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Cupom de desconto não encontrado com o código: " + id));

		if (!cupomDesconto.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Acesso negado: este Cupom de Desconto não pertence à sua empresa.");
		}

		cupomDescontoRepository.delete(cupomDesconto);
	}

	public Page<CupomDescontoDTO> buscarPaginado(int page, int size, String sort, String direction,
			UsuarioLogadoPrincipal usuarioLogado) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);
		Specification<CupomDesconto> spec = Specification
				.where(CupomDescontoSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return cupomDescontoRepository.findAll(spec, pageable).map(cupomDescontoMapper::toDTO);
	}

	public Page<CupomDescontoDTO> buscarAvancado(String codigo, int page, int size,
			UsuarioLogadoPrincipal usuarioLogado) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<CupomDesconto> spec = Specification.where(CupomDescontoSpec.codigoContem(codigo))
				.and(CupomDescontoSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return cupomDescontoRepository.findAll(spec, pageable).map(cupomDescontoMapper::toDTO);
	}

	public List<CupomDescontoDTO> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado) {
		Specification<CupomDesconto> spec = Specification
				.where(CupomDescontoSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return cupomDescontoRepository.findAll(spec).stream().map(cupomDescontoMapper::toDTO)
				.collect(Collectors.toList());
	}

	public List<CupomDescontoDTO> buscarPorDescricao(String codigo, UsuarioLogadoPrincipal usuarioLogado) {
		Specification<CupomDesconto> spec = Specification.where(CupomDescontoSpec.codigoContem(codigo))
				.and(CupomDescontoSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return cupomDescontoRepository.findAll(spec).stream().map(cupomDescontoMapper::toDTO)
				.collect(Collectors.toList());
	}
}