/**
 * 
 */
package com.bandampla.lojavirtual.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.dto.CategoriaProdutoDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.CategoriaProdutoMapper;
import com.bandampla.lojavirtual.model.CategoriaProduto;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.repository.CategoriaProdutoRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.specification.CategoriaProdutoSpec;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Service
public class CategoriaProdutoService {

	private final CategoriaProdutoRepository categoriaProdutoRepository;
	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final CategoriaProdutoMapper categoriaProdutoMapper;

	private CategoriaProdutoService(CategoriaProdutoRepository categoriaProdutoRepository,
			PessoaJuridicaRepository pessoaJuridicaRepository, CategoriaProdutoMapper categoriaProdutoMapper) {
		this.categoriaProdutoRepository = categoriaProdutoRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.categoriaProdutoMapper = categoriaProdutoMapper;
	}

	public CategoriaProdutoDTO cadastrar(CategoriaProdutoDTO dto) throws ExceptionCustom {

		// Validação de nome duplicado por empresa
		if (dto.getId() == null) {
			Specification<CategoriaProduto> specCategoriaProdutoDuplicado = Specification
					.where(CategoriaProdutoSpec.descricaoExata(dto.getNomeDescricao()))
					.and(CategoriaProdutoSpec.empresaIgual(dto.getEmpresaId()));
			if (categoriaProdutoRepository.exists(specCategoriaProdutoDuplicado) == true) {
				throw new ExceptionCustom("Já existe Categoria com nome: '" + dto.getNomeDescricao()
						+ "' para a empresa de código: " + dto.getEmpresaId());
			}
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		CategoriaProduto model = categoriaProdutoMapper.toModel(dto);
		model.setEmpresa(empresa);

		return categoriaProdutoMapper.toDTO(categoriaProdutoRepository.save(model));
	}

	public CategoriaProdutoDTO atualizar(Long id, CategoriaProdutoDTO dto) throws ExceptionCustom {

		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido ou ausente");
		}

		CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Categoria não encontrada com o código: " + id));

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		Specification<CategoriaProduto> specCategoriaProdutoDuplicado = Specification
				.where(CategoriaProdutoSpec.descricaoExata(dto.getNomeDescricao()))
				.and(CategoriaProdutoSpec.empresaIgual(dto.getEmpresaId()));

		if (categoriaProdutoRepository.exists(specCategoriaProdutoDuplicado) == true) {
			throw new ExceptionCustom("Já existe Categoria com nome: '" + dto.getNomeDescricao()
					+ "' para a empresa de código: " + dto.getEmpresaId());
		}

		if (!categoriaProduto.getEmpresa().getId().equals(dto.getEmpresaId())) {
			throw new ExceptionCustom(
					"Acesso Negado: Você não tem permissão para alterar a categoria do produto, não pertence à sua empresa.");
		}

		categoriaProdutoMapper.atualizarCamposCategoriaProduto(dto, categoriaProduto);
		categoriaProduto.setEmpresa(empresa);

		return categoriaProdutoMapper.toDTO(categoriaProdutoRepository.save(categoriaProduto));
	}

	public void deletar(Long id, Long empresaId) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido ou ausente");
		}

		CategoriaProduto categoria = categoriaProdutoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Categoria não encontrada"));

		if (!categoria.getEmpresa().getId().equals(empresaId)) {
			throw new ExceptionCustom("Acesso Negado: Você não tem permissão para excluir esta Categoria.");
		}
		categoriaProdutoRepository.delete(categoria);
	}

	public List<CategoriaProdutoDTO> buscarPorDescricao(String descricao, Long empresaId) {
		Specification<CategoriaProduto> specCategoriaProduto = Specification
				.where(CategoriaProdutoSpec.descricaoContem(descricao))
				.and(CategoriaProdutoSpec.empresaIgual(empresaId));

		return categoriaProdutoRepository.findAll(specCategoriaProduto).stream()
				.map(categoriaProduto -> categoriaProdutoMapper.toDTO(categoriaProduto)).collect(Collectors.toList());
	}

	public List<CategoriaProdutoDTO> buscarTodosPorEmpresa(Long empresaId) throws ExceptionCustom {
		Specification<CategoriaProduto> specCategoriaProduto = Specification
				.where(CategoriaProdutoSpec.empresaIgual(empresaId));
		return categoriaProdutoRepository.findAll(specCategoriaProduto).stream()
				.map(categoriaProduto -> categoriaProdutoMapper.toDTO(categoriaProduto)).collect(Collectors.toList());
	}

	public Page<CategoriaProdutoDTO> buscarAvancado(String descricao, Long empresaId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<CategoriaProduto> specCategoriaProduto = Specification
				.where(CategoriaProdutoSpec.descricaoContem(descricao))
				.and(CategoriaProdutoSpec.empresaIgual(empresaId));

		return categoriaProdutoRepository.findAll(specCategoriaProduto, pageable)
				.map(categoriaProduto -> categoriaProdutoMapper.toDTO(categoriaProduto));
	}

	public Page<CategoriaProdutoDTO> buscarPaginado(int page, int size, String sort, String direction, Long empresaId) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);

		Specification<CategoriaProduto> specCategoriaProduto = Specification
				.where(CategoriaProdutoSpec.empresaIgual(empresaId));

		return categoriaProdutoRepository.findAll(specCategoriaProduto, pageable)
				.map(categoriaProduto -> categoriaProdutoMapper.toDTO(categoriaProduto));
	}
}
