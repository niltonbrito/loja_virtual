package com.bandampla.lojavirtual.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bandampla.lojavirtual.dto.request.*;
import com.bandampla.lojavirtual.dto.response.*;
import com.bandampla.lojavirtual.enums.TipoEndereco;
import com.bandampla.lojavirtual.model.*;
import com.bandampla.lojavirtual.util.ValidaCNPJ;

@Component
public class EnderecoMapper {


  
    // ============================
    //   ENDEREÇOS
    // ============================

    public Endereco toEnderecoModel(EnderecoRequestDTO dto, Object pessoa, CepDTO cepDto) {
        Endereco e = new Endereco();
        e.setRua(cepDto.getLogradouro());
        e.setNumero(cepDto.getDdd());
        e.setComplemento(cepDto.getComplemento());
        e.setBairro(cepDto.getBairro());
        e.setCidade(cepDto.getLocalidade());
        e.setUf(cepDto.getUf());
        e.setCep(dto.getCep());
        e.setTipoEndereco(TipoEndereco.valueOf(dto.getTipoEndereco()));
        return e;
    }

}
