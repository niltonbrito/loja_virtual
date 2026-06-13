package com.bandampla.lojavirtual.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.dto.ProdutoDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.ProdutoFisicaMapper;
import com.bandampla.lojavirtual.model.CategoriaProduto;
import com.bandampla.lojavirtual.model.MarcaProduto;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Produto;
import com.bandampla.lojavirtual.repository.CategoriaProdutoRepository;
import com.bandampla.lojavirtual.repository.MarcaProdutoRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.ProdutoRepository;
import com.bandampla.lojavirtual.repository.specification.ProdutoSpec;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;
	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final CategoriaProdutoRepository categoriaProdutoRepository;
	private final MarcaProdutoRepository marcaProdutoRepository;
	private final ProdutoFisicaMapper produtoMapper;

	public ProdutoService(ProdutoRepository produtoRepository, PessoaJuridicaRepository pessoaJuridicaRepository,
			CategoriaProdutoRepository categoriaProdutoRepository, ProdutoFisicaMapper produtoMapper,
			MarcaProdutoRepository marcaProdutoRepository) {
		this.produtoRepository = produtoRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.categoriaProdutoRepository = categoriaProdutoRepository;
		this.marcaProdutoRepository = marcaProdutoRepository;
		this.produtoMapper = produtoMapper;
	}

	public ProdutoDTO cadastrar(ProdutoDTO dto) throws ExceptionCustom {
		if (dto.getId() == null) {
			Specification<Produto> specDuplicado = Specification.where(ProdutoSpec.nomeExato(dto.getNome()))
					.and(ProdutoSpec.empresaIgual(dto.getEmpresaId()));
			List<Produto> existentes = produtoRepository.findAll(specDuplicado);
			if (!existentes.isEmpty()) {
				throw new ExceptionCustom("Já existe Produto com nome: '" + dto.getNome()
						+ "' cadastrado para a empresa de código: " + dto.getEmpresaId());
			}
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(dto.getCategoriaId())
				.orElseThrow(() -> new ExceptionCustom("Categoria não encontrada"));

		MarcaProduto marcaProduto = marcaProdutoRepository.findById(dto.getMarcaId())
				.orElseThrow(() -> new ExceptionCustom("Marca do produto não encontrada"));

		if (!categoriaProduto.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("Acesso Negado: Esta categoria não pertence à sua empresa.");
		}

		if (!marcaProduto.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("Acesso Negado: Esta marca não pertence à sua empresa.");
		}

		Produto model = produtoMapper.toModel(dto);
		model.setEmpresa(empresa);
		model.setCategoriaProduto(categoriaProduto);
		model.setMarcaProduto(marcaProduto);

		return produtoMapper.toDTO(produtoRepository.save(model));
	}

	public ProdutoDTO atualizar(Long id, ProdutoDTO dto) throws ExceptionCustom {
		Produto existente = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrada"));

		if (!existente.getEmpresa().getId().equals(dto.getEmpresaId())) {
			throw new ExceptionCustom("Acesso Negado: Você não tem permissão para alterar este produto.");
		}

		// 🛠️ O PULO DO GATO: Converte o produto atual do banco para DTO e compara com
		// o do Request.
		// Graças ao método equals() estruturado, qualquer alteração em 1 dos 30+ campos
		// libera o fluxo.
		ProdutoDTO dtoAntigo = produtoMapper.toDTO(existente);
		if (dtoAntigo.equals(dto)) {
			throw new ExceptionCustom("Nenhuma alteração detectada para atualizar");
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		CategoriaProduto novaCategoria = categoriaProdutoRepository.findById(dto.getCategoriaId())
				.orElseThrow(() -> new ExceptionCustom("Categoria informada não existe"));

		MarcaProduto marcaProduto = marcaProdutoRepository.findById(dto.getMarcaId())
				.orElseThrow(() -> new ExceptionCustom("Marca do produto não encontrada"));

		if (!novaCategoria.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("Acesso Negado: A nova categoria escolhida não pertence à sua empresa.");
		}

		if (!marcaProduto.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("Acesso Negado: Esta marca não pertence à sua empresa.");
		}

		Specification<Produto> specDuplicado = Specification.where(ProdutoSpec.nomeExato(dto.getNome()))
				.and(ProdutoSpec.empresaIgual(dto.getEmpresaId()));

		List<Produto> duplicados = produtoRepository.findAll(specDuplicado);

		if (!duplicados.isEmpty() && !duplicados.get(0).getId().equals(id)) {
			throw new ExceptionCustom("Já existe Produto com nome: '" + dto.getNome()
					+ "' cadastrado para a empresa de código: " + dto.getEmpresaId());
		}

		// 🔄 MapStruct atualiza os campos mutáveis em memória de forma limpa
		produtoMapper.atualizarCamposDoProduto(dto, existente);
		existente.setEmpresa(empresa);
		existente.setCategoriaProduto(novaCategoria);
		existente.setMarcaProduto(marcaProduto); // Adicionado o vínculo definitivo que faltava

		return produtoMapper.toDTO(produtoRepository.save(existente));
	}

	public void deletar(Long id, Long empresaId) throws ExceptionCustom {
		if (id <= 0) {
			throw new ExceptionCustom("ID inválido");
		}
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrada"));

		if (!produto.getEmpresa().getId().equals(empresaId)) {
			throw new ExceptionCustom("Acesso Negado: Você não tem permissão para excluir este produto.");
		}

		produtoRepository.delete(produto);
	}

	public List<ProdutoDTO> buscarPorDescricao(String descricao, Long empresaId) {
		Specification<Produto> spec = Specification.where(ProdutoSpec.descricaoContem(descricao))
				.and(ProdutoSpec.empresaIgual(empresaId));

		return produtoRepository.findAll(spec).stream().map(produto -> produtoMapper.toDTO(produto))
				.collect(Collectors.toList());
	}

	public List<ProdutoDTO> buscarTodosPorEmpresa(Long id) {
		Specification<Produto> spec = Specification.where(ProdutoSpec.empresaIgual(id));
		return produtoRepository.findAll(spec).stream().map(produto -> produtoMapper.toDTO(produto))
				.collect(Collectors.toList());
	}

	public Page<ProdutoDTO> buscarAvancado(String descricao, Boolean ativo, int page, int size, Long empresaId) {
		Pageable pageable = PageRequest.of(page, size);

		Specification<Produto> spec = Specification
				.where(ProdutoSpec.nomeContem(descricao).or(ProdutoSpec.descricaoContem(descricao)))
				.and(ProdutoSpec.empresaIgual(empresaId)).and(ProdutoSpec.statusAtivoEquivalente(ativo));

		return produtoRepository.findAll(spec, pageable).map(produto -> produtoMapper.toDTO(produto));
	}

	public Page<ProdutoDTO> buscarPaginado(int page, int size, String sort, String direction, Long empresaId) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);

		Specification<Produto> spec = Specification.where(ProdutoSpec.empresaIgual(empresaId));

		return produtoRepository.findAll(spec, pageable).map(produto -> produtoMapper.toDTO(produto));
	}
}