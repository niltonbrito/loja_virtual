package com.bandampla.lojavirtual.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import com.bandampla.lojavirtual.dto.FormaPagamentoDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.FormaPagamentoMapper;
import com.bandampla.lojavirtual.model.FormaPagamento;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.repository.FormaPagamentoRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

@Service
public class FormaPagamentoService {

	private final FormaPagamentoRepository formaPagamentoRepository;
	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final FormaPagamentoMapper formaPagamentoMapper;

	public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository,
			PessoaJuridicaRepository pessoaJuridicaRepository, FormaPagamentoMapper formaPagamentoMapper) {
		this.formaPagamentoRepository = formaPagamentoRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.formaPagamentoMapper = formaPagamentoMapper;
	}

	@Transactional
	public FormaPagamentoDTO cadastrar(FormaPagamentoDTO dto, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada."));

		FormaPagamento model = formaPagamentoMapper.toModel(dto);
		model.setEmpresa(empresa);

		FormaPagamento salva = formaPagamentoRepository.save(model);
		return formaPagamentoMapper.toDTO(salva);
	}

	@Transactional
	public FormaPagamentoDTO atualizar(Long id, FormaPagamentoDTO dto, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido.");
		}

		FormaPagamento existente = formaPagamentoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Forma de pagamento não encontrada."));

		if (!existente.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não possui permissão para alterar as configurações desta empresa.");
		}

		formaPagamentoMapper.atualizarCamposDaFormaPagamento(dto, existente);
		FormaPagamento atualizada = formaPagamentoRepository.save(existente);
		return formaPagamentoMapper.toDTO(atualizada);
	}

	@Transactional
	public void deletar(Long id, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido.");
		}

		FormaPagamento existente = formaPagamentoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Forma de pagamento não encontrada."));

		if (!existente.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não possui permissão para excluir registros desta empresa.");
		}

		formaPagamentoRepository.delete(existente);
	}

	public FormaPagamentoDTO buscarPorId(Long id, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido.");
		}

		FormaPagamento existente = formaPagamentoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Forma de pagamento não encontrada."));

		if (!existente.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Acesso negado para o recurso solicitado.");
		}

		return formaPagamentoMapper.toDTO(existente);
	}

	public List<FormaPagamentoDTO> buscarPorDescricao(String descricao, UsuarioLogadoPrincipal usuarioLogado) {
		return formaPagamentoRepository.buscarPorDescricaoEEmpresa(descricao, usuarioLogado.getEmpresaId())
				.stream()
				.map(formaPagamentoMapper::toDTO)
				.collect(Collectors.toList());
	}

	public List<FormaPagamentoDTO> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado) {
		return formaPagamentoRepository.buscarFormasPagamentoPorEmpresa(usuarioLogado.getEmpresaId())
				.stream()
				.map(formaPagamentoMapper::toDTO)
				.collect(Collectors.toList());
	}
}