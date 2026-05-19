package com.bandampla.lojavirtual.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.bandampla.lojavirtual.dto.response.UsuarioResponseDTO;
import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.model.Acesso;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Usuario;

@Component
public class UsuarioMapper {

	public UsuarioResponseDTO toResponse(Usuario usuario) {
		UsuarioResponseDTO dto = new UsuarioResponseDTO();

		dto.setId(usuario.getId());
		dto.setLogin(usuario.getLogin());
		dto.setCreateAt(usuario.getCreateAt());
		dto.setUpdateAt(usuario.getUpdateAt());

		if (usuario.getPessoa() != null) {
			dto.setNomePessoa(usuario.getPessoa().getNome());
			dto.setTipoPessoa(usuario.getPessoa().getTipoPessoa());
		}

		if (usuario.getEmpresa() != null) {
			dto.setEmpresaId(usuario.getEmpresa().getId());
			if (usuario.getEmpresa() instanceof PessoaJuridica) {
				dto.setEmpresaNomeFantasia(((PessoaJuridica) usuario.getEmpresa()).getNomeFantasia());
			}
		}

		List<String> acessos = null;
		if (usuario.getAcessos() != null) {
			acessos = usuario.getAcessos().stream().map(a -> a.getRoleUser().name()).collect(Collectors.toList());
		}

		return dto;
	}
}
