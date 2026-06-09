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
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Produto;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.ProdutoRepository;
import com.bandampla.lojavirtual.repository.specification.ProdutoSpec;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;

	private final PessoaJuridicaRepository pessoaJuridicaRepository;

	public ProdutoService(ProdutoRepository produtoRepository, PessoaJuridicaRepository pessoaJuridicaRepository) {
		this.produtoRepository = produtoRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
	}

	public ProdutoDTO salvar(ProdutoDTO dto) throws ExceptionCustom {

		// Validação de nome duplicado
		if (dto.getId() == null) {
			List<Produto> existentes = produtoRepository.findByNomeIgnoreCaseAndEmpresaId(dto.getNome(),
					dto.getEmpresaId());
			if (!existentes.isEmpty()) {
				throw new ExceptionCustom("Já existe Produto com nome: '" + dto.getNome()
						+ "' cadastrado para a empresa de código: " + dto.getEmpresaId());
			}
		}

		// Buscar empresa
		PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));
		Produto model = toModel(dto, empresa);

		// Salvar
		model = produtoRepository.save(model);
		return toDTO(model);
	}

	public ProdutoDTO atualizar(Long id, ProdutoDTO dto) throws ExceptionCustom {

		// 1. Verificar se a Produto existe
		Produto existente = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrada"));

		// 2. Buscar empresa
		PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		// 3. Verificar se houve alteração real
		boolean nomeIgual = existente.getNome().equalsIgnoreCase(dto.getNome());
		boolean empresaIgual = existente.getEmpresa().getId().equals(dto.getEmpresaId());

		if (nomeIgual && empresaIgual) {
			throw new ExceptionCustom("Nenhuma alteração detectada para atualizar");
		}

		// 4. Validação de duplicidade no UPDATE
		List<Produto> duplicados = produtoRepository.findByNomeIgnoreCaseAndEmpresaId(dto.getNome(),
				dto.getEmpresaId());

		if (!duplicados.isEmpty() && !duplicados.get(0).getId().equals(id)) {
			throw new ExceptionCustom("Já existe Produto com nome: '" + dto.getNome()
					+ "' cadastrado para a empresa de código: " + dto.getEmpresaId());
		}

		// 5. Atualizar dados
		existente.setNome(dto.getNome());
		existente.setTipoUnidade(dto.getTipoUnidade());
		existente.setAtivo(dto.getAtivo());
		existente.setDescricao(dto.getDescricao());
		existente.setPeso(dto.getPeso());
		existente.setLargura(dto.getLargura());
		existente.setAltura(dto.getAltura());
		existente.setProfundidade(dto.getProfundidade());
		existente.setValorVenda(dto.getValorVenda());
		existente.setQtdEstoqueMinimo(dto.getQtdEstoqueMinimo());
		existente.setQtdEstoque(dto.getQtdEstoque());
		existente.setAlertaEstoque(dto.getAlertaEstoque());
		existente.setLinkYoutube(dto.getLinkYoutube());
		existente.setQtdClickProduto(dto.getQtdClickProduto());
		existente.setEmpresa(empresa);

		existente = produtoRepository.save(existente);

		return toDTO(existente);
	}

	public void deletar(Long id) throws ExceptionCustom {
		if (id <= 0) {
			throw new ExceptionCustom("ID inválido");
		}
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrada"));
		produtoRepository.delete(produto);
	}

	public List<ProdutoDTO> buscarPorDescricao(String descricao) {
		List<Produto> produtos = produtoRepository.findByDescricaoContainingIgnoreCase(descricao.toLowerCase());
		return produtos.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public ProdutoDTO buscarPorId(Long id) throws ExceptionCustom {
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrada"));
		return toDTO(produto);
	}

	public List<ProdutoDTO> buscarPorEmpresa(Long id) {
		List<Produto> produtos = produtoRepository.findByEmpresaId(id);
		return produtos.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public List<ProdutoDTO> listarTodos() {
		return produtoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	public Page<ProdutoDTO> listarPaginado(int page, int size, String sort, String direction) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);
		return produtoRepository.findAll(pageable).map(this::toDTO);
	}

	public Page<ProdutoDTO> buscarAvancado(String descricao, Long empresaId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Produto> spec = Specification.where(ProdutoSpec.descricaoContem(descricao))
				.and(ProdutoSpec.empresaIgual(empresaId));
		return produtoRepository.findAll(spec, pageable).map(this::toDTO);
	}

	public ProdutoDTO findById(Long id) throws ExceptionCustom {
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrada com o código: " + id));
		return toDTO(produto);
	}

	private Produto toModel(ProdutoDTO dto, PessoaJuridica empresa) {
		// Converter DTO → Entidade
		Produto model = new Produto();
		model.setId(dto.getId());
		model.setTipoUnidade(dto.getTipoUnidade());
		model.setNome(dto.getNome().trim());
		model.setAtivo(dto.getAtivo());
		model.setDescricao(dto.getDescricao());
		model.setPeso(dto.getPeso());
		model.setLargura(dto.getLargura());
		model.setAltura(dto.getAltura());
		model.setProfundidade(dto.getProfundidade());
		model.setValorVenda(dto.getValorVenda());
		model.setQtdEstoque(dto.getQtdEstoque());
		model.setQtdEstoqueMinimo(dto.getQtdEstoqueMinimo());
		model.setAlertaEstoque(dto.getAlertaEstoque());
		model.setLinkYoutube(dto.getLinkYoutube());
		model.setQtdClickProduto(dto.getQtdClickProduto());
		model.setEmpresa(empresa);
		return model;
	}

	private ProdutoDTO toDTO(Produto model) {
		// Converter Entidade → ResponseDTO
		ProdutoDTO dto = new ProdutoDTO();
		dto.setId(model.getId());
		dto.setTipoUnidade(model.getTipoUnidade());
		dto.setNome(model.getNome());
		dto.setAtivo(model.getAtivo());
		dto.setDescricao(model.getDescricao());
		dto.setPeso(model.getPeso());
		dto.setLargura(model.getLargura());
		dto.setAltura(model.getAltura());
		dto.setProfundidade(model.getProfundidade());
		dto.setValorVenda(model.getValorVenda());
		dto.setQtdEstoque(model.getQtdEstoque());
		dto.setQtdEstoqueMinimo(model.getQtdEstoqueMinimo());
		dto.setAlertaEstoque(model.getAlertaEstoque());
		dto.setLinkYoutube(model.getLinkYoutube());
		dto.setQtdClickProduto(model.getQtdClickProduto());
		dto.setEmpresaId(model.getEmpresa().getId()); // ou apenas o ID, depende do seu design
		return dto;
	}
}
