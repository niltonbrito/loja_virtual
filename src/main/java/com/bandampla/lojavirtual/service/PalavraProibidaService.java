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

import com.bandampla.lojavirtual.dto.PalavraProibidaDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.PalavraProibidaMapper;
import com.bandampla.lojavirtual.model.PalavraProibida;
import com.bandampla.lojavirtual.repository.PalavraProibidaRepository;
import com.bandampla.lojavirtual.repository.specification.PalavraProibidaSpec;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Service
public class PalavraProibidaService {

	private final PalavraProibidaRepository palavraProibidaRepository;
	private final PalavraProibidaMapper palavraProibidaMapper;

	private PalavraProibidaService(PalavraProibidaRepository palavraProibidaRepository,
			PalavraProibidaMapper palavraProibidaMapper) {
		this.palavraProibidaRepository = palavraProibidaRepository;
		this.palavraProibidaMapper = palavraProibidaMapper;
	}

	public PalavraProibidaDTO cadastrar(PalavraProibidaDTO dto) throws ExceptionCustom {

		if (dto.getId() == null) {
			Specification<PalavraProibida> specPalavraProibidaDuplicado = Specification
					.where(PalavraProibidaSpec.termoExata(dto.getTermo()));
			if (palavraProibidaRepository.exists(specPalavraProibidaDuplicado) == true) {
				throw new ExceptionCustom(
						"Já existe Palavra proibida com o termo: '" + dto.getTermo() + "' cadastrada no sistema.");
			}
		}

		PalavraProibida model = palavraProibidaMapper.toModel(dto);

		return palavraProibidaMapper.toDTO(palavraProibidaRepository.save(model));
	}

	public PalavraProibidaDTO atualizar(Long id, PalavraProibidaDTO dto) throws ExceptionCustom {

		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido ou ausente");
		}

		PalavraProibida palavraProibida = palavraProibidaRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Palavra proibida não encontrada com o código: " + id));

		Specification<PalavraProibida> specPalavraProibidaDuplicado = Specification
				.where(PalavraProibidaSpec.termoExata(dto.getTermo()));
		if (palavraProibidaRepository.exists(specPalavraProibidaDuplicado) == true) {
			throw new ExceptionCustom(
					"Já existe Palavra proibida com o termo: '" + dto.getTermo() + "' cadastrada no sistema.");
		}

		palavraProibidaMapper.atualizarTermoPalavraProibida(dto, palavraProibida);

		return palavraProibidaMapper.toDTO(palavraProibidaRepository.save(palavraProibida));
	}

	public void deletar(Long id) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido ou ausente");
		}

		PalavraProibida palavraProibida = palavraProibidaRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Palavra proibida não encontrada com o código: " + id));

		palavraProibidaRepository.delete(palavraProibida);
	}

	public List<PalavraProibidaDTO> buscarTodos() {

		return palavraProibidaRepository.findAll().stream()
				.map(palavraProibida -> palavraProibidaMapper.toDTO(palavraProibida)).collect(Collectors.toList());
	}

	public List<PalavraProibidaDTO> buscarPorDescricao(String termo) {
		Specification<PalavraProibida> specPalavraProibida = Specification
				.where(PalavraProibidaSpec.termoContem(termo));

		return palavraProibidaRepository.findAll(specPalavraProibida).stream()
				.map(palavraProibida -> palavraProibidaMapper.toDTO(palavraProibida)).collect(Collectors.toList());
	}

	public Page<PalavraProibidaDTO> buscarAvancado(String termo, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<PalavraProibida> specPalavraProibida = Specification
				.where(PalavraProibidaSpec.termoContem(termo));

		return palavraProibidaRepository.findAll(specPalavraProibida, pageable)
				.map(palavraProibida -> palavraProibidaMapper.toDTO(palavraProibida));
	}

	public Page<PalavraProibidaDTO> buscarPaginado(int page, int size,  String sort,
			String direction) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);

		return palavraProibidaRepository.findAll(pageable)
				.map(palavraProibida -> palavraProibidaMapper.toDTO(palavraProibida));
	}
}
