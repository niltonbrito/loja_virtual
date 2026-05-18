/**
 * 
 */
package com.bandampla.lojavirtual.mapper;

import org.springframework.stereotype.Component;

import com.bandampla.lojavirtual.dto.response.UsuarioResponseDTO;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Usuario;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   18 de mai. de 2026
 */
@Component
public class UsuarioMapper {

    public UsuarioResponseDTO toResponse(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();

        dto.setId(usuario.getId());
        dto.setLogin(usuario.getLogin());
        dto.setCreateAt(usuario.getCreateAt());
        dto.setUpdateAt(usuario.getUpdateAt());

        dto.setNomePessoa(usuario.getPessoa().getNome());
        dto.setTipoPessoa(usuario.getPessoa().getTipoPessoa());

        dto.setEmpresaId(usuario.getEmpresa().getId());
        dto.setEmpresaNomeFantasia(
            usuario.getEmpresa() instanceof PessoaJuridica 
                ? ((PessoaJuridica) usuario.getEmpresa()).getNomeFantasia()
                : null
        );

        dto.setAcessos(
            usuario.getAcessos().stream()
                .map(a -> a.getDescricao())
                .toList()
        );

        return dto;
    }
}
