package com.bandampla.lojavirtual.service;

import java.util.List;
import java.util.stream.Collectors;

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
		if (dto.getId() == null) {

			Specification<ContaPagar> specContaPagarDuplicado = Specification
					.where(ContaPagarSpec.descricaoExata(dto.getDescricao()))
					.and(ContaPagarSpec.empresaIgual(dto.getEmpresaId()));
			List<ContaPagar> existentes = contaPagarRepository.findAll(specContaPagarDuplicado);
			if (!existentes.isEmpty()) {
				throw new ExceptionCustom("Já existe Conta a pagar com nome: '" + dto.getDescricao()
						+ "' cadastrado para a empresa de código: " + dto.getEmpresaId());
			}
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(dto.getPessoaId())
				.orElseThrow(() -> new ExceptionCustom("Pessoa não encontrada"));

		PessoaJuridica fornecedor = pessoaJuridicaRepository.findById(dto.getPessoaFornecedorId())
				.orElseThrow(() -> new ExceptionCustom("Fornecedor não encontrado"));

		if (!pessoaFisica.getEmpresa().getId().equals(dto.getEmpresaId())) {
			throw new ExceptionCustom("A pessoa informada da Conta a pagar do produto, não pertence à empresa.");
		}
		if (!fornecedor.getId().equals(dto.getEmpresaId())) {
			throw new ExceptionCustom("O fornecedor informado da Conta a pagar do produto, não pertence à empresa.");
		}
		ContaPagar model = contaPagarMapper.toModel(dto);
		model.setEmpresa(empresa);
		model.setPessoa(pessoaFisica);
		model.setPessoaFornecedor(fornecedor);

		return contaPagarMapper.toDTO(contaPagarRepository.save(model));
	}

	public ContaPagarDTO atualizar(Long id, ContaPagarDTO dto) throws ExceptionCustom {

		ContaPagar contaPagar = contaPagarRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Marca não encontrada com o código: " + id));

		if (!contaPagar.getEmpresa().getId().equals(dto.getEmpresaId())) {
			throw new ExceptionCustom(
					"Acesso Negado: Você não tem permissão para alterar a Conta a pagar do produto, não pertence à sua empresa.");
		}

		Specification<ContaPagar> specContaPagarDuplicado = Specification
				.where(ContaPagarSpec.descricaoExata(dto.getDescricao()))
				.and(ContaPagarSpec.empresaIgual(dto.getEmpresaId()));

		List<ContaPagar> duplicados = contaPagarRepository.findAll(specContaPagarDuplicado);

		if (!duplicados.isEmpty() && !duplicados.get(0).getId().equals(id)) {
			throw new ExceptionCustom("Já existe Conta a pagar com nome: '" + dto.getDescricao()
					+ "' cadastrado para a empresa de código: " + dto.getEmpresaId());
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(dto.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		contaPagarMapper.atualizarCamposDaConta(dto, contaPagar);
		contaPagar.setEmpresa(empresa);

		return contaPagarMapper.toDTO(contaPagarRepository.save(contaPagar));
	}

	public void deletar(Long id, Long empresaId) throws ExceptionCustom {
		if (id <= 0) {
			throw new ExceptionCustom("ID inválido");
		}
		ContaPagar contaPagar = contaPagarRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Conta a pagar não encontrada com o código: " + id));

		if (!contaPagar.getEmpresa().getId().equals(empresaId)) {
			throw new ExceptionCustom("Acesso Negado: Você não tem permissão para excluir esta Conta a pagar.");
		}

		contaPagarRepository.delete(contaPagar);
	}

	public List<ContaPagarDTO> buscarPorDescricao(String descricao, Long empresaId) {
		Specification<ContaPagar> specContaPagar = Specification.where(ContaPagarSpec.descricaoContem(descricao))
				.and(ContaPagarSpec.empresaIgual(empresaId));

		return contaPagarRepository.findAll(specContaPagar).stream()
				.map(contaPagar -> contaPagarMapper.toDTO(contaPagar)).collect(Collectors.toList());
	}

	public List<ContaPagarDTO> buscarTodosPorEmpresa(Long empresaId) {
		Specification<ContaPagar> specContaPagar = Specification.where(ContaPagarSpec.empresaIgual(empresaId));
		return contaPagarRepository.findAll(specContaPagar).stream()
				.map(contaPagar -> contaPagarMapper.toDTO(contaPagar)).collect(Collectors.toList());
	}

	public Page<ContaPagarDTO> buscarAvancado(String descricao, int page, int size, Long empresaId) {
		Pageable pageable = PageRequest.of(page, size);

		Specification<ContaPagar> specContaPagar = Specification.where(ContaPagarSpec.descricaoContem(descricao))
				.and(ContaPagarSpec.empresaIgual(empresaId));

		return contaPagarRepository.findAll(specContaPagar, pageable)
				.map(contaPagar -> contaPagarMapper.toDTO(contaPagar));
	}

	public Page<ContaPagarDTO> buscarPaginado(int page, int size, String sort, String direction, Long empresaId) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);

		Specification<ContaPagar> specContaPagar = Specification.where(ContaPagarSpec.empresaIgual(empresaId));

		return contaPagarRepository.findAll(specContaPagar, pageable)
				.map(contaPagar -> contaPagarMapper.toDTO(contaPagar));
	}
}