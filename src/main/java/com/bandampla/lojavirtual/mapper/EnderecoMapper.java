package com.bandampla.lojavirtual.mapper;

import org.springframework.stereotype.Component;

import com.bandampla.lojavirtual.dto.request.CepDTO;
import com.bandampla.lojavirtual.dto.request.EnderecoRequestDTO;
import com.bandampla.lojavirtual.enums.TipoEndereco;
import com.bandampla.lojavirtual.model.Endereco;
@Component
public class EnderecoMapper {

    public Endereco toEnderecoModel(EnderecoRequestDTO dto, CepDTO cepDto) {

        Endereco e = new Endereco();

        e.setRua(cepDto.getLogradouro());
        e.setBairro(cepDto.getBairro());
        e.setCidade(cepDto.getLocalidade());
        e.setUf(cepDto.getUf());
        e.setCep(dto.getCep());
        e.setComplemento(dto.getComplemento());
        e.setNumero(dto.getNumero());
        e.setTipoEndereco(TipoEndereco.valueOf(dto.getTipoEndereco()));

        return e;
    }
}
