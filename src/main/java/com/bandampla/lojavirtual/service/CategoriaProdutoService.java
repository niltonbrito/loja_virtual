/**
 * 
 */
package com.bandampla.lojavirtual.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.dto.CategoriaProdutoDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
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

	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;

	public CategoriaProdutoDTO cadastrar(CategoriaProdutoDTO dto) throws ExceptionCustom {

		// Validação de nome duplicado
		if (dto.getId() == null) {
			List<CategoriaProduto> existentes = categoriaProdutoRepository
					.findByNomeDescricaoIgnoreCaseAndEmpresaId(dto.getNomeDescricao(), dto.getEmpresaId());
			if (!existentes.isEmpty()) {
				throw new ExceptionCustom("Já existe Categoria com nome: " + dto.getNomeDescricao()
						+ " para a empresa de código: " + dto.getEmpresaId());
			}
		}

		// Buscar empresa
		PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));
		CategoriaProduto model = toModel(dto, empresa);

		// Salvar
		model = categoriaProdutoRepository.save(model);
		return toDTO(model);
	}

	public CategoriaProdutoDTO atualizar(Long id, CategoriaProdutoDTO dto) throws ExceptionCustom {

	    // 1. Verificar se a categoria existe
	    CategoriaProduto existente = categoriaProdutoRepository.findById(id)
	            .orElseThrow(() -> new ExceptionCustom("Categoria não encontrada"));

	    // 2. Buscar empresa
	    PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
	            .orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

	    // 3. Verificar se houve alteração real
	    boolean nomeIgual = existente.getNomeDescricao().equalsIgnoreCase(dto.getNomeDescricao());
	    boolean empresaIgual = existente.getEmpresa().getId().equals(dto.getEmpresaId());

	    if (nomeIgual && empresaIgual) {
	        throw new ExceptionCustom("Nenhuma alteração detectada para atualizar");
	    }

	    // 4. Validação de duplicidade no UPDATE
	    List<CategoriaProduto> duplicados = categoriaProdutoRepository
	            .findByNomeDescricaoIgnoreCaseAndEmpresaId(dto.getNomeDescricao(), dto.getEmpresaId());

	    if (!duplicados.isEmpty() && !duplicados.get(0).getId().equals(id)) {
	        throw new ExceptionCustom("Já existe Categoria com nome '" + dto.getNomeDescricao()
	                + "' cadastrada para esta empresa de código: " + dto.getEmpresaId());
	    }

	    // 5. Atualizar dados
	    existente.setNomeDescricao(dto.getNomeDescricao());
	    existente.setEmpresa(empresa);

	    existente = categoriaProdutoRepository.save(existente);

	    return toDTO(existente);
	}


	public void deletar(Long id) throws ExceptionCustom {
		if (id <= 0) {
			throw new ExceptionCustom("ID inválido");
		}
		CategoriaProduto categoria = categoriaProdutoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Categoria não encontrada"));
		categoriaProdutoRepository.delete(categoria);
	}

	public List<CategoriaProdutoDTO> buscarPorDescricao(String descricao) {
		List<CategoriaProduto> categorias = categoriaProdutoRepository
				.findByNomeDescricaoContainingIgnoreCase(descricao.toLowerCase());
		return categorias.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public CategoriaProdutoDTO buscarPorId(Long id) throws ExceptionCustom {
		CategoriaProduto categoria = categoriaProdutoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Categoria não encontrada"));
		return toDTO(categoria);
	}

	public List<CategoriaProdutoDTO> buscarPorEmpresa(Long id) {
		List<CategoriaProduto> categorias = categoriaProdutoRepository.findByEmpresaId(id);
		return categorias.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public List<CategoriaProdutoDTO> listarTodos() {
		return categoriaProdutoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	public Page<CategoriaProdutoDTO> listarPaginado(int page, int size, String sort, String direction) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);
		return categoriaProdutoRepository.findAll(pageable).map(this::toDTO);
	}

	public Page<CategoriaProdutoDTO> buscarAvancado(String descricao, Long empresaId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<CategoriaProduto> spec = Specification.where(CategoriaProdutoSpec.descricaoContem(descricao))
				.and(CategoriaProdutoSpec.empresaIgual(empresaId));
		return categoriaProdutoRepository.findAll(spec, pageable).map(this::toDTO);
	}

	public CategoriaProdutoDTO findById(Long id) throws ExceptionCustom {
		CategoriaProduto categoria = categoriaProdutoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Categoria não encontrada com o código: " + id));
		return toDTO(categoria);
	}

	private CategoriaProduto toModel(CategoriaProdutoDTO dto, PessoaJuridica empresa) {
		// Converter DTO → Entidade
		CategoriaProduto model = new CategoriaProduto();
		model.setId(dto.getId());
		model.setNomeDescricao(dto.getNomeDescricao().trim());
		model.setEmpresa(empresa);
		return model;
	}

	private CategoriaProdutoDTO toDTO(CategoriaProduto categoria) {
		// Converter Entidade → ResponseDTO
		CategoriaProdutoDTO dto = new CategoriaProdutoDTO();
		dto.setId(categoria.getId());
		dto.setNomeDescricao(categoria.getNomeDescricao());
		dto.setEmpresaId(categoria.getEmpresa().getId());
		return dto;
	}
}
