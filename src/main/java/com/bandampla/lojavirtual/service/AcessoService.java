package com.bandampla.lojavirtual.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.dto.AcessoDTO;
import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.AcessoMapper;
import com.bandampla.lojavirtual.model.Acesso;
import com.bandampla.lojavirtual.repository.AcessoRepository;

@Service
public class AcessoService {

	private final AcessoRepository acessoRepository;
	private final AcessoMapper acessoMapper;

	public AcessoService(AcessoRepository acessoRepository, AcessoMapper acessoMapper) {
		this.acessoRepository = acessoRepository;
		this.acessoMapper = acessoMapper;
	}

	@Transactional(rollbackFor = Exception.class)
	public AcessoDTO cadastrar(AcessoDTO dto) throws ExceptionCustom {

		RoleUser roleUser = dto.getRoleUser();

		if (dto.getId() == null) {
			List<Acesso> acessos = acessoRepository.findByRoleUser(roleUser);
			if (!acessos.isEmpty()) {
				throw new ExceptionCustom("Já existe Acesso com a descrição: " + roleUser.getDescricao());
			}
		}

		Acesso model = acessoMapper.toModel(dto);
		Acesso salvo = acessoRepository.save(model);
		return acessoMapper.toDTO(salvo);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deletar(AcessoDTO dto) throws ExceptionCustom {
		if (dto == null || dto.getId() == null) {
			throw new ExceptionCustom("ID de acesso inválido para exclusão.");
		}
		acessoRepository.deleteById(dto.getId());
	}

	@Transactional(rollbackFor = Exception.class)
	public void deletePorId(Long id) {
		acessoRepository.deleteById(id);
	}

	public AcessoDTO buscarPorId(Long id) throws ExceptionCustom {
		Acesso acesso = acessoRepository.findById(id).orElse(null);
		if (acesso == null) {
			throw new ExceptionCustom("Não encontrou Acesso com o código: " + id);
		}
		return acessoMapper.toDTO(acesso);
	}

	public List<AcessoDTO> buscarPorRole(RoleUser roleUser) {
		List<Acesso> lista = acessoRepository.findByRoleUser(roleUser);
		return lista.stream().map(acessoMapper::toDTO).collect(Collectors.toList());
	}

	public List<AcessoDTO> buscarTodos() {
		return acessoRepository.findAll().stream()
				.map(acesso -> acessoMapper.toDTO(acesso)).collect(Collectors.toList());
	}
}