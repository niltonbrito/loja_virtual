package com.bandampla.lojavirtual.service;

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
import com.bandampla.lojavirtual.model.CupomDesconto;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.repository.CupomDescontoRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.specification.CupomDescontoSpec;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

@Service
public class CupomDescontoService {

	private final CupomDescontoRepository cupomDescontoRepository;
	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final CupomDescontoMapper cupomDescontoMapper;

	public CupomDescontoService(CupomDescontoRepository cupomDescontoRepository,
			PessoaJuridicaRepository pessoaJuridicaRepository, CupomDescontoMapper cupomDescontoMapper) {
		this.cupomDescontoRepository = cupomDescontoRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.cupomDescontoMapper = cupomDescontoMapper;
	}

	@Transactional(rollbackFor = Exception.class)
	public CupomDescontoDTO cadastrar(CupomDescontoDTO dto, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		Specification<CupomDesconto> specDuplicidade = Specification
				.where(CupomDescontoSpec.codigoDescricaoExata(dto.getCodigoDescricao()))
				.and(CupomDescontoSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		if (cupomDescontoRepository.exists(specDuplicidade)) {
			throw new ExceptionCustom("Já existe Cupom de desconto com o código '" + dto.getCodigoDescricao() + "' cadastrado para esta empresa.");
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		CupomDesconto cupom = cupomDescontoMapper.toModel(dto);
		cupom.setEmpresa(empresa);

		return cupomDescontoMapper.toDTO(cupomDescontoRepository.save(cupom));
	}

	@Transactional(rollbackFor = Exception.class)
	public CupomDescontoDTO atualizar(Long id, CupomDescontoDTO dto, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido ou ausente");
		}

		CupomDesconto existente = cupomDescontoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Cupom de desconto não encontrado com o código: " + id));

		if (!existente.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Acesso negado: este Cupom de Desconto não pertence à sua empresa.");
		}

		Specification<CupomDesconto> specDuplicidade = Specification
				.where(CupomDescontoSpec.codigoDescricaoExata(dto.getCodigoDescricao()))
				.and(CupomDescontoSpec.empresaIgual(usuarioLogado.getEmpresaId()))
				.and(CupomDescontoSpec.idDiferente(id));

		if (cupomDescontoRepository.exists(specDuplicidade)) {
			throw new ExceptionCustom("Já existe outro Cupom de desconto com o código '" + dto.getCodigoDescricao() + "' cadastrado para esta empresa.");
		}

		cupomDescontoMapper.atualizarCamposDoCupomDesconto(dto, existente);
		return cupomDescontoMapper.toDTO(cupomDescontoRepository.save(existente));
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

	public Page<CupomDescontoDTO> buscarPaginado(int page, int size, String sort, String direction, UsuarioLogadoPrincipal usuarioLogado) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);
		Specification<CupomDesconto> spec = Specification.where(CupomDescontoSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return cupomDescontoRepository.findAll(spec, pageable).map(cupomDescontoMapper::toDTO);
	}

	public Page<CupomDescontoDTO> buscarAvancado(String descricao, int page, int size, UsuarioLogadoPrincipal usuarioLogado) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<CupomDesconto> spec = Specification.where(CupomDescontoSpec.codigoDescricaoContem(descricao))
				.and(CupomDescontoSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return cupomDescontoRepository.findAll(spec, pageable).map(cupomDescontoMapper::toDTO);
	}

	public List<CupomDescontoDTO> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado) {
		Specification<CupomDesconto> spec = Specification.where(CupomDescontoSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return cupomDescontoRepository.findAll(spec).stream().map(cupomDescontoMapper::toDTO).collect(Collectors.toList());
	}

	public List<CupomDescontoDTO> buscarPorDescricao(String descricao, UsuarioLogadoPrincipal usuarioLogado) {
		Specification<CupomDesconto> spec = Specification.where(CupomDescontoSpec.codigoDescricaoContem(descricao))
				.and(CupomDescontoSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return cupomDescontoRepository.findAll(spec).stream().map(cupomDescontoMapper::toDTO).collect(Collectors.toList());
	}
}