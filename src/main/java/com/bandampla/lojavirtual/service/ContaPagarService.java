package com.bandampla.lojavirtual.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.dto.ContaPagarDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.ContaPagarMapper;
import com.bandampla.lojavirtual.model.ContaPagar;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.repository.ContaPagarRepository;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.specification.ContaPagarSpec;

@Service
public class ContaPagarService {

	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final PessoaFisicaRepository pessoaFisicaRepository;
	private final ContaPagarRepository contaPagarRepository;
	private final ContaPagarMapper contaPagarMapper;

	public ContaPagarService(PessoaJuridicaRepository pessoaJuridicaRepository,
			PessoaFisicaRepository pessoaFisicaRepository, ContaPagarRepository contaPagarRepository,
			ContaPagarMapper contaPagarMapper) {
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.pessoaFisicaRepository = pessoaFisicaRepository;
		this.contaPagarRepository = contaPagarRepository;
		this.contaPagarMapper = contaPagarMapper;
	}

	public ContaPagarDTO cadastrar(ContaPagarDTO dto) throws ExceptionCustom {
		// Validação de duplicidade
		Specification<ContaPagar> specDuplicidade = Specification
				.where(ContaPagarSpec.descricaoExata(dto.getDescricao()))
				.and(ContaPagarSpec.empresaIgual(dto.getEmpresaId()));

		if (contaPagarRepository.exists(specDuplicidade)) {
			throw new ExceptionCustom(
					"Já existe Conta a pagar com nome '" + dto.getDescricao() + "' cadastrada para esta empresa.");
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(dto.getPessoaId())
				.orElseThrow(() -> new ExceptionCustom("Pessoa não encontrada"));

		PessoaJuridica fornecedor = pessoaJuridicaRepository.findById(dto.getPessoaFornecedorId())
				.orElseThrow(() -> new ExceptionCustom("Fornecedor não encontrado"));

		if (!pessoaFisica.getEmpresa().getId().equals(dto.getEmpresaId())) {
			throw new ExceptionCustom("A pessoa informada não pertence à empresa.");
		}

		if (!fornecedor.getId().equals(dto.getEmpresaId())) {
			throw new ExceptionCustom("O fornecedor informado não pertence à empresa.");
		}

		ContaPagar model = contaPagarMapper.toModel(dto);
		model.setEmpresa(empresa);
		model.setPessoa(pessoaFisica);
		model.setPessoaFornecedor(fornecedor);

		return contaPagarMapper.toDTO(contaPagarRepository.save(model));
	}

	public ContaPagarDTO atualizar(Long id, ContaPagarDTO dto) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido ou ausente");
		}

		ContaPagar contaPagar = contaPagarRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Conta a pagar não encontrada com o código: " + id));

		// Verifica se o registro pertence à empresa do usuário
		if (!contaPagar.getEmpresa().getId().equals(dto.getEmpresaId())) {
			throw new ExceptionCustom("Acesso negado: esta conta não pertence à sua empresa.");
		}

		// Validação de duplicidade (nome já existe em outro registro)
		Specification<ContaPagar> specDuplicidade = Specification
				.where(ContaPagarSpec.descricaoExata(dto.getDescricao()))
				.and(ContaPagarSpec.empresaIgual(dto.getEmpresaId())).and(ContaPagarSpec.idDiferente(id));

		if (contaPagarRepository.exists(specDuplicidade)) {
			throw new ExceptionCustom(
					"Já existe Conta a pagar com nome '" + dto.getDescricao() + "' cadastrada para esta empresa.");
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		contaPagarMapper.atualizarCamposDaConta(dto, contaPagar);
		contaPagar.setEmpresa(empresa);

		return contaPagarMapper.toDTO(contaPagarRepository.save(contaPagar));
	}

	public void deletar(Long id, Long empresaId) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido ou ausente");
		}

		ContaPagar contaPagar = contaPagarRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Conta a pagar não encontrada com o código: " + id));
		if (!contaPagar.getEmpresa().getId().equals(empresaId)) {
			throw new ExceptionCustom("Acesso negado: esta conta não pertence à sua empresa.");
		}

		contaPagarRepository.delete(contaPagar);
	}

	public List<ContaPagarDTO> buscarPorDescricao(String descricao, Long empresaId) {
		Specification<ContaPagar> spec = Specification.where(ContaPagarSpec.descricaoContem(descricao))
				.and(ContaPagarSpec.empresaIgual(empresaId));
		return contaPagarRepository.findAll(spec).stream().map(contaPagarMapper::toDTO).toList();
	}

	public List<ContaPagarDTO> buscarTodosPorEmpresa(Long empresaId) {
		Specification<ContaPagar> spec = Specification.where(ContaPagarSpec.empresaIgual(empresaId));
		return contaPagarRepository.findAll(spec).stream().map(contaPagarMapper::toDTO).toList();
	}

	public Page<ContaPagarDTO> buscarAvancado(String descricao, int page, int size, Long empresaId) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<ContaPagar> spec = Specification.where(ContaPagarSpec.descricaoContem(descricao))
				.and(ContaPagarSpec.empresaIgual(empresaId));
		return contaPagarRepository.findAll(spec, pageable).map(contaPagarMapper::toDTO);
	}

	public Page<ContaPagarDTO> buscarPaginado(int page, int size, String sort, String direction, Long empresaId) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);
		Specification<ContaPagar> spec = Specification.where(ContaPagarSpec.empresaIgual(empresaId));
		return contaPagarRepository.findAll(spec, pageable).map(contaPagarMapper::toDTO);
	}
}