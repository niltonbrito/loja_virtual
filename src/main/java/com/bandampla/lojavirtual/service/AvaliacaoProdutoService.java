package com.bandampla.lojavirtual.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.dto.AvaliacaoProdutoDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.AvaliacaoProdutoMapper;
import com.bandampla.lojavirtual.model.AvaliacaoProduto;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Produto;
import com.bandampla.lojavirtual.repository.AvaliacaoProdutoRepository;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.ProdutoRepository;
import com.bandampla.lojavirtual.repository.specification.AvaliacaoProdutoSpec;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.util.WordFilterUtil;

@Service
public class AvaliacaoProdutoService {

	private final AvaliacaoProdutoRepository avaliacaoProdutoRepository;
	private final ProdutoRepository produtoRepository;
	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final PessoaFisicaRepository pessoaFisicaRepository;
	private final AvaliacaoProdutoMapper avaliacaoProdutoMapper;
	private final WordFilterUtil wordFilterUtil;

	public AvaliacaoProdutoService(AvaliacaoProdutoRepository avaliacaoProdutoRepository,
			ProdutoRepository produtoRepository, PessoaJuridicaRepository pessoaJuridicaRepository,
			PessoaFisicaRepository pessoaFisicaRepository, AvaliacaoProdutoMapper avaliacaoProdutoMapper,WordFilterUtil wordFilterUtil) {
		this.avaliacaoProdutoRepository = avaliacaoProdutoRepository;
		this.produtoRepository = produtoRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.pessoaFisicaRepository = pessoaFisicaRepository;
		this.avaliacaoProdutoMapper = avaliacaoProdutoMapper;
		this.wordFilterUtil = wordFilterUtil;
	}

	@Transactional
	public AvaliacaoProdutoDTO cadastrar(AvaliacaoProdutoDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		Produto produto = produtoRepository.findById(dto.getProdutoId())
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrado."));

		if (!produto.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("O produto informado não pertence à sua empresa.");
		}

		// 1. 🔥 IMPOSIÇÃO DA REGRA DE MERCADO: Impede duplicidade de avaliações (1 por
		// pessoa por produto)
		Specification<AvaliacaoProduto> specJaAvaliado = Specification
				.where(AvaliacaoProdutoSpec.produtoIgual(dto.getProdutoId()))
				.and(AvaliacaoProdutoSpec.pessoaIgual(dto.getPessoaId()));

		if (!avaliacaoProdutoRepository.findOne(specJaAvaliado).isEmpty()) {
			throw new ExceptionCustom("Você já enviou uma avaliação para este produto e não pode avaliar novamente.");
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		PessoaFisica pessoa = pessoaFisicaRepository.findById(dto.getPessoaId())
				.orElseThrow(() -> new ExceptionCustom("Pessoa avaliadora não encontrada"));
		// Checagem de palavras ofensivas
		if (wordFilterUtil.contemPalavraProibida(dto.getDescricao())) {
			throw new ExceptionCustom(
					"Sua avaliação contém termos ofensivos ou inadequados. Por favor, reescreva seu comentário.");
		}
		


		AvaliacaoProduto model = avaliacaoProdutoMapper.toModel(dto);
		model.setEmpresa(empresa);
		model.setPessoa(pessoa);
		model.setProduto(produto);

		// Se preferir Censurar Automaticamente com ****, antes de salvar o model:
		String descricaoLimpa = wordFilterUtil.mascararPalavrasProibidas(dto.getDescricao());
		model.setDescricao(descricaoLimpa); // O banco de dados grava a versão censurada
		
		AvaliacaoProduto avaliacaoProdutoSalvo = avaliacaoProdutoRepository.save(model);
		return avaliacaoProdutoMapper.toDTO(avaliacaoProdutoSalvo);
	}

	@Transactional
	public void deletar(Long id, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido");
		}

		// 🔥 CORREÇÃO: Moderação cirúrgica deleta apenas a avaliação, sem desvincular o
		// produto ou fornecedores
		AvaliacaoProduto avaliacao = avaliacaoProdutoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Avaliação não encontrada."));

		if (!avaliacao.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não tem permissão para excluir esta avaliação.");
		}

		avaliacaoProdutoRepository.delete(avaliacao);
	}

	public AvaliacaoProdutoDTO buscarPorId(Long id, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido.");
		}

		AvaliacaoProduto avaliacao = avaliacaoProdutoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Avaliação não encontrada."));

		if (!avaliacao.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não tem permissão para visualizar esta avaliação.");
		}

		return avaliacaoProdutoMapper.toDTO(avaliacao);
	}

	public List<AvaliacaoProdutoDTO> buscarPorDescricao(String descricao, UsuarioLogadoPrincipal usuarioLogado) {
		Specification<AvaliacaoProduto> spec = Specification
				.where(AvaliacaoProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()))
				.and(AvaliacaoProdutoSpec.descricaoContem(descricao));

		return avaliacaoProdutoRepository.findAll(spec).stream().map(avaliacaoProdutoMapper::toDTO)
				.collect(Collectors.toList());
	}

	public List<AvaliacaoProdutoDTO> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado) {
		Specification<AvaliacaoProduto> spec = Specification
				.where(AvaliacaoProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		return avaliacaoProdutoRepository.findAll(spec).stream().map(avaliacaoProdutoMapper::toDTO)
				.collect(Collectors.toList());
	}

	public Page<AvaliacaoProdutoDTO> buscarAvancado(String descricao, Integer nota, int page, int size,
			UsuarioLogadoPrincipal usuarioLogado) {

		Pageable pageable = PageRequest.of(page, size);

		Specification<AvaliacaoProduto> spec = Specification
				.where(AvaliacaoProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()))
				.and(AvaliacaoProdutoSpec.descricaoContem(descricao)).and(AvaliacaoProdutoSpec.nota(nota));

		return avaliacaoProdutoRepository.findAll(spec, pageable).map(avaliacaoProdutoMapper::toDTO);
	}

	public Page<AvaliacaoProdutoDTO> buscarPaginado(int page, int size, String sort, String direction,
			UsuarioLogadoPrincipal usuarioLogado) {

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);

		Specification<AvaliacaoProduto> spec = Specification
				.where(AvaliacaoProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		return avaliacaoProdutoRepository.findAll(spec, pageable).map(avaliacaoProdutoMapper::toDTO);
	}
}
