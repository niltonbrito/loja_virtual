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
import com.bandampla.lojavirtual.mapper.ProdutoMapper;
import com.bandampla.lojavirtual.model.CategoriaProduto;
import com.bandampla.lojavirtual.model.MarcaProduto;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Produto;
import com.bandampla.lojavirtual.model.ProdutoFornecedor;
import com.bandampla.lojavirtual.repository.CategoriaProdutoRepository;
import com.bandampla.lojavirtual.repository.MarcaProdutoRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.ProdutoFornecedorRepository;
import com.bandampla.lojavirtual.repository.ProdutoRepository;
import com.bandampla.lojavirtual.repository.specification.ProdutoFornecedorSpec;
import com.bandampla.lojavirtual.repository.specification.ProdutoSpec;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;
	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final CategoriaProdutoRepository categoriaProdutoRepository;
	private final MarcaProdutoRepository marcaProdutoRepository;
	private final ProdutoFornecedorRepository produtoFornecedorRepository;
	private final ProdutoMapper produtoMapper;

	public ProdutoService(ProdutoRepository produtoRepository, PessoaJuridicaRepository pessoaJuridicaRepository,
			CategoriaProdutoRepository categoriaProdutoRepository, ProdutoMapper produtoMapper,
			MarcaProdutoRepository marcaProdutoRepository, ProdutoFornecedorRepository produtoFornecedorRepository) {

		this.produtoRepository = produtoRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.categoriaProdutoRepository = categoriaProdutoRepository;
		this.marcaProdutoRepository = marcaProdutoRepository;
		this.produtoFornecedorRepository = produtoFornecedorRepository;
		this.produtoMapper = produtoMapper;
	}

	/*
	 * =================== CADASTRAR PRODUTO ===================
	 */
	public ProdutoDTO cadastrar(ProdutoDTO dto, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		/* 1. Validar duplicidade do nome */
		if (dto.getId() == null) {
			Specification<Produto> specDuplicado = Specification.where(ProdutoSpec.nomeExato(dto.getNome()))
					.and(ProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()));
			if (!produtoRepository.findAll(specDuplicado).isEmpty()) {
				throw new ExceptionCustom("Já existe Produto com nome '" + dto.getNome() + "' cadastrado.");
			}
		}

		/* 2. Buscar empresa */
		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		/* 3. Buscar categoria */
		CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(dto.getCategoriaId())
				.orElseThrow(() -> new ExceptionCustom("Categoria não encontrada"));

		if (!categoriaProduto.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("A categoria informada não pertence à sua empresa.");
		}

		/* 4. Buscar marca */
		MarcaProduto marcaProduto = marcaProdutoRepository.findById(dto.getMarcaId())
				.orElseThrow(() -> new ExceptionCustom("Marca não encontrada"));

		if (!marcaProduto.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("A marca informada não pertence à sua empresa.");
		}

		/* 5. Buscar fornecedor */
		PessoaJuridica fornecedor = pessoaJuridicaRepository.findById(dto.getFornecedorId())
				.orElseThrow(() -> new ExceptionCustom("Fornecedor não encontrado"));

		if (!fornecedor.getMatriz().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("Este fornecedor não pertence à sua empresa.");
		}

		/* 6. Validar duplicidade do código do fornecedor */
		Specification<ProdutoFornecedor> specCodigoDuplicado = Specification
				.where(ProdutoFornecedorSpec.codigoProdutoFornecedorExato(dto.getCodigoProdutoFornecedor()))
				.and(ProdutoFornecedorSpec.fornecedorIgual(dto.getFornecedorId()))
				.and(ProdutoFornecedorSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		if (produtoFornecedorRepository.findOne(specCodigoDuplicado).isPresent()) {
			throw new ExceptionCustom("Já existe um produto cadastrado com o código '"
					+ dto.getCodigoProdutoFornecedor() + "' para este fornecedor.");
		}

		/* 7. Salvar Produto */
		Produto model = produtoMapper.toModel(dto);
		model.setEmpresa(empresa);
		model.setCategoriaProduto(categoriaProduto);
		model.setMarcaProduto(marcaProduto);

		Produto produtoSalvo = produtoRepository.save(model);

		/* 8. Criar vínculo ProdutoFornecedor */
		ProdutoFornecedor produtoFornecedor = new ProdutoFornecedor();
		produtoFornecedor.setProduto(produtoSalvo);
		produtoFornecedor.setFornecedor(fornecedor);
		produtoFornecedor.setEmpresa(empresa);
		produtoFornecedor.setCodigoProdutoFornecedor(dto.getCodigoProdutoFornecedor());

		produtoFornecedorRepository.save(produtoFornecedor);

		/* 9. Montar DTO de retorno */
		ProdutoDTO dtoRetorno = produtoMapper.toDTO(produtoSalvo);
		dtoRetorno.setFornecedorId(fornecedor.getId());
		dtoRetorno.setCodigoProdutoFornecedor(produtoFornecedor.getCodigoProdutoFornecedor());

		return dtoRetorno;
	}

	/*
	 * =================== ATUALIZAR PRODUTO ===================
	 */
	public ProdutoDTO atualizar(Long id, ProdutoDTO dto, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido");
		}

		Produto existente = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrado"));

		if (!existente.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não tem permissão para alterar este produto.");
		}

		/* Verificar se houve alteração */
		ProdutoDTO dtoAntigo = produtoMapper.toDTO(existente);
		if (dtoAntigo.equals(dto)) {
			throw new ExceptionCustom("Nenhuma alteração detectada.");
		}

		/* Buscar empresa */
		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		/* Buscar categoria */
		CategoriaProduto novaCategoria = categoriaProdutoRepository.findById(dto.getCategoriaId())
				.orElseThrow(() -> new ExceptionCustom("Categoria não encontrada"));

		if (!novaCategoria.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("A categoria informada não pertence à sua empresa.");
		}

		/* Buscar marca */
		MarcaProduto marcaProduto = marcaProdutoRepository.findById(dto.getMarcaId())
				.orElseThrow(() -> new ExceptionCustom("Marca não encontrada"));

		if (!marcaProduto.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("A marca informada não pertence à sua empresa.");
		}

		/* Buscar fornecedor */
		PessoaJuridica fornecedor = pessoaJuridicaRepository.findById(dto.getFornecedorId())
				.orElseThrow(() -> new ExceptionCustom("Fornecedor não encontrado"));

		if (!fornecedor.getMatriz().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("Este fornecedor não pertence à sua empresa.");
		}

		/* Validar duplicidade do nome */
		Specification<Produto> specDuplicado = Specification.where(ProdutoSpec.nomeExato(dto.getNome()))
				.and(ProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		List<Produto> duplicados = produtoRepository.findAll(specDuplicado);

		if (!duplicados.isEmpty() && !duplicados.get(0).getId().equals(id)) {
			throw new ExceptionCustom("Já existe Produto com nome '" + dto.getNome() + "' cadastrado.");
		}

		/* Atualizar Produto */
		produtoMapper.atualizarCamposDoProduto(dto, existente);
		existente.setEmpresa(empresa);
		existente.setCategoriaProduto(novaCategoria);
		existente.setMarcaProduto(marcaProduto);

		Produto produtoAtualizado = produtoRepository.save(existente);

		/* Buscar vínculo ProdutoFornecedor */
		Specification<ProdutoFornecedor> spec = Specification
				.where(ProdutoFornecedorSpec.produtoIgual(produtoAtualizado.getId()))
				.and(ProdutoFornecedorSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		ProdutoFornecedor produtoFornecedor = produtoFornecedorRepository.findOne(spec).orElseGet(() -> {
			ProdutoFornecedor pf = new ProdutoFornecedor();
			pf.setProduto(produtoAtualizado);
			pf.setEmpresa(empresa);
			return pf;
		});

		/* Atualizar vínculo */
		produtoFornecedor.setFornecedor(fornecedor);
		produtoFornecedor.setCodigoProdutoFornecedor(dto.getCodigoProdutoFornecedor());

		produtoFornecedorRepository.save(produtoFornecedor);

		/* Retorno */
		ProdutoDTO dtoRetorno = produtoMapper.toDTO(produtoAtualizado);
		dtoRetorno.setFornecedorId(fornecedor.getId());
		dtoRetorno.setCodigoProdutoFornecedor(produtoFornecedor.getCodigoProdutoFornecedor());

		return dtoRetorno;
	}

	/*
	 * =================== DELETAR PRODUTO ===================
	 */
	public void deletar(Long id, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido");
		}

		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrado"));

		if (!produto.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não tem permissão para excluir este produto.");
		}

		/* Remover vínculos */
		Specification<ProdutoFornecedor> spec = Specification.where(ProdutoFornecedorSpec.produtoIgual(produto.getId()))
				.and(ProdutoFornecedorSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		List<ProdutoFornecedor> vinculos = produtoFornecedorRepository.findAll(spec);
		vinculos.forEach(produtoFornecedorRepository::delete);

		produtoRepository.delete(produto);
	}

	/*
	 * =================== CONSULTAS ===================
	 */

	public List<ProdutoDTO> buscarPorDescricao(String descricao, UsuarioLogadoPrincipal usuarioLogado) {
		Specification<Produto> spec = Specification.where(ProdutoSpec.descricaoContem(descricao))
				.and(ProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		return produtoRepository.findAll(spec).stream().map(produtoMapper::toDTO).collect(Collectors.toList());
	}

	public List<ProdutoDTO> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado) {
		Specification<Produto> spec = Specification.where(ProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return produtoRepository.findAll(spec).stream().map(produtoMapper::toDTO).collect(Collectors.toList());
	}

	public Page<ProdutoDTO> buscarAvancado(String descricao, Boolean ativo, int page, int size,
			UsuarioLogadoPrincipal usuarioLogado) {

		Pageable pageable = PageRequest.of(page, size);

		Specification<Produto> spec = Specification
				.where(ProdutoSpec.nomeContem(descricao).or(ProdutoSpec.descricaoContem(descricao)))
				.and(ProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()))
				.and(ProdutoSpec.statusAtivoEquivalente(ativo));

		return produtoRepository.findAll(spec, pageable).map(produtoMapper::toDTO);
	}

	public Page<ProdutoDTO> buscarPaginado(int page, int size, String sort, String direction,
			UsuarioLogadoPrincipal usuarioLogado) {

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);

		Specification<Produto> spec = Specification.where(ProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		return produtoRepository.findAll(spec, pageable).map(produtoMapper::toDTO);
	}
}