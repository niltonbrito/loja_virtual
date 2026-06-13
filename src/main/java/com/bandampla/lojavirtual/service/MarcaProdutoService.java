package com.bandampla.lojavirtual.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.dto.MarcaProdutoDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.MarcaProdutoMapper;
import com.bandampla.lojavirtual.model.MarcaProduto;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.repository.MarcaProdutoRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.specification.MarcaProdutoSpec;

@Service
public class MarcaProdutoService {

	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final MarcaProdutoRepository marcaProdutoRepository;
	private final MarcaProdutoMapper marcaProdutoMapper;

	private MarcaProdutoService(PessoaJuridicaRepository pessoaJuridicaRepository,
			MarcaProdutoRepository marcaProdutoRepository, MarcaProdutoMapper marcaProdutoMapper) {
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.marcaProdutoRepository = marcaProdutoRepository;
		this.marcaProdutoMapper = marcaProdutoMapper;
	}

	public MarcaProdutoDTO cadastrar(MarcaProdutoDTO dto) throws ExceptionCustom {
		if (dto.getId() == null) {
			Specification<MarcaProduto> specMarcaProdutoDuplicado = Specification
					.where(MarcaProdutoSpec.descricaoExata(dto.getNomeDescricao()))
					.and(MarcaProdutoSpec.empresaIgual(dto.getEmpresaId()));
			List<MarcaProduto> existentes = marcaProdutoRepository.findAll(specMarcaProdutoDuplicado);
			if (!existentes.isEmpty()) {
				throw new ExceptionCustom("Já existe Marca com nome: '" + dto.getNomeDescricao()
						+ "' cadastrado para a empresa de código: " + dto.getEmpresaId());
			}
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		MarcaProduto model = marcaProdutoMapper.toModel(dto);
		model.setEmpresa(empresa);
		
		return marcaProdutoMapper.toDTO(marcaProdutoRepository.save(model));
	}

	public MarcaProdutoDTO atualizar(Long id, MarcaProdutoDTO dto) throws ExceptionCustom {

		MarcaProduto marcaProduto = marcaProdutoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Marca não encontrada com o código: " + id));

		if (!marcaProduto.getEmpresa().getId().equals(dto.getEmpresaId())) {
			throw new ExceptionCustom(
					"Acesso Negado: Você não tem permissão para alterar a marca do produto, não pertence à sua empresa.");
		}

		Specification<MarcaProduto> specMarcaProdutoDuplicado = Specification
				.where(MarcaProdutoSpec.descricaoExata(dto.getNomeDescricao()))
				.and(MarcaProdutoSpec.empresaIgual(dto.getEmpresaId()));

		List<MarcaProduto> duplicados = marcaProdutoRepository.findAll(specMarcaProdutoDuplicado);

		if (!duplicados.isEmpty() && !duplicados.get(0).getId().equals(id)) {
			throw new ExceptionCustom("Já existe Marca com nome: '" + dto.getNomeDescricao()
					+ "' cadastrado para a empresa de código: " + dto.getEmpresaId());
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		marcaProdutoMapper.atualizarCamposDoProduto(dto, marcaProduto);
		marcaProduto.setEmpresa(empresa);

		return marcaProdutoMapper.toDTO(marcaProdutoRepository.save(marcaProduto));
	}

	public void deletar(Long id, Long empresaId) throws ExceptionCustom {
		if (id <= 0) {
			throw new ExceptionCustom("ID inválido");
		}
		MarcaProduto marcaProduto = marcaProdutoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Marca não encontrada com o código: " + id));

		if (!marcaProduto.getEmpresa().getId().equals(empresaId)) {
			throw new ExceptionCustom("Acesso Negado: Você não tem permissão para excluir esta marca.");
		}

		marcaProdutoRepository.delete(marcaProduto);
	}

	public List<MarcaProdutoDTO> buscarPorDescricao(String descricao, Long empresaId) {
		Specification<MarcaProduto> specMarcaProduto = Specification.where(MarcaProdutoSpec.descricaoContem(descricao))
				.and(MarcaProdutoSpec.empresaIgual(empresaId));

		return marcaProdutoRepository.findAll(specMarcaProduto).stream()
				.map(marcaProduto -> marcaProdutoMapper.toDTO(marcaProduto)).collect(Collectors.toList());
	}

	public List<MarcaProdutoDTO> buscarTodosPorEmpresa(Long empresaId) {
		Specification<MarcaProduto> specMarcaProduto = Specification.where(MarcaProdutoSpec.empresaIgual(empresaId));
		return marcaProdutoRepository.findAll(specMarcaProduto).stream()
				.map(marcaProduto -> marcaProdutoMapper.toDTO(marcaProduto)).collect(Collectors.toList());
	}

	public Page<MarcaProdutoDTO> buscarAvancado(String textoBusca, int page, int size, Long empresaId) {
		Pageable pageable = PageRequest.of(page, size);

		Specification<MarcaProduto> specMarcaProduto = Specification.where(MarcaProdutoSpec.descricaoContem(textoBusca))
				.and(MarcaProdutoSpec.empresaIgual(empresaId));

		return marcaProdutoRepository.findAll(specMarcaProduto, pageable)
				.map(marcaProduto -> marcaProdutoMapper.toDTO(marcaProduto));
	}

	public Page<MarcaProdutoDTO> buscarPaginado(int page, int size, String sort, String direction, Long empresaId) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);

		Specification<MarcaProduto> specMarcaProduto = Specification.where(MarcaProdutoSpec.empresaIgual(empresaId));

		return marcaProdutoRepository.findAll(specMarcaProduto, pageable)
				.map(marcaProduto -> marcaProdutoMapper.toDTO(marcaProduto));
	}
}