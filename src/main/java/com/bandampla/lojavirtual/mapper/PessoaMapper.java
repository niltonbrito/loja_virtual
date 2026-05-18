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
public class PessoaMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ============================
    //   PESSOA FÍSICA
    // ============================

    public PessoaFisica toModel(PessoaFisicaRequestDTO dto, PessoaJuridica empresa) {
        PessoaFisica pf = new PessoaFisica();
        pf.setId(dto.getId());
        pf.setNome(dto.getNome());
        pf.setEmail(dto.getEmail());
        pf.setTelefone(dto.getTelefone());
        pf.setCpf(dto.getCpf());
        pf.setEmpresa(empresa);
        pf.setTipoPessoa(dto.getTipoPessoa());

        LocalDate data = LocalDate.parse(dto.getDataNascimento(), FORMATTER);
        pf.setDataNascimento(data);

        if (dto.getEnderecos() != null) {
            for (EnderecoRequestDTO e : dto.getEnderecos()) {
                pf.getEnderecos().add(toEnderecoModel(e, pf, empresa));
            }
        }

        return pf;
    }

    public PessoaFisicaResponseDTO toResponse(PessoaFisica pf) {
        PessoaFisicaResponseDTO dto = new PessoaFisicaResponseDTO();
        dto.setId(pf.getId());
        dto.setNome(pf.getNome());
        dto.setEmail(pf.getEmail());
        dto.setTelefone(pf.getTelefone());
        dto.setCpf(pf.getCpf());
        dto.setDataNascimento(pf.getDataNascimento().format(FORMATTER));
        dto.setTipoPessoa(pf.getTipoPessoa());
        dto.setEmpresa(toEmpresaResponse((PessoaJuridica) pf.getEmpresa()));

        List<EnderecoResponseDTO> enderecos = new ArrayList<>();
        for (Endereco e : pf.getEnderecos()) {
            enderecos.add(toEnderecoResponse(e));
        }
        dto.setEnderecos(enderecos);

        return dto;
    }

    // ============================
    //   PESSOA JURÍDICA
    // ============================

    public PessoaJuridica toModel(PessoaJuridicaRequestDTO dto) {
        PessoaJuridica pj = new PessoaJuridica();
        pj.setId(dto.getId());
        pj.setNome(dto.getNome());
        pj.setEmail(dto.getEmail());
        pj.setTelefone(dto.getTelefone());
        pj.setCnpj(dto.getCnpj());
        pj.setInscricaoEstadual(dto.getInscricaoEstadual());
        pj.setInscricaoMunicipal(dto.getInscricaoMunicipal());
        pj.setNomeFantasia(dto.getNomeFantasia());
        pj.setRazaoSocial(dto.getRazaoSocial());
        pj.setCategoria(dto.getCategoria());
        pj.setTipoPessoa(dto.getTipoPessoa());
        pj.setEmpresa(pj);

        if (dto.getEnderecos() != null) {
            for (EnderecoRequestDTO e : dto.getEnderecos()) {
                pj.getEnderecos().add(toEnderecoModel(e, pj, pj));
            }
        }

        return pj;
    }

    public PessoaJuridicaResponseDTO toResponse(PessoaJuridica pj) {
        PessoaJuridicaResponseDTO dto = new PessoaJuridicaResponseDTO();
        dto.setId(pj.getId());
        dto.setNome(pj.getNome());
        dto.setEmail(pj.getEmail());
        dto.setTelefone(pj.getTelefone());
        dto.setCnpj(pj.getCnpj());
        dto.setInscricaoEstadual(pj.getInscricaoEstadual());
        dto.setInscricaoMunicipal(pj.getInscricaoMunicipal());
        dto.setNomeFantasia(pj.getNomeFantasia());
        dto.setRazaoSocial(pj.getRazaoSocial());
        dto.setCategoria(pj.getCategoria());
        dto.setTipoPessoa(pj.getTipoPessoa());
        dto.setEmpresa(toEmpresaResponse((PessoaJuridica) pj.getEmpresa()));

        List<EnderecoResponseDTO> enderecos = new ArrayList<>();
        for (Endereco e : pj.getEnderecos()) {
            enderecos.add(toEnderecoResponse(e));
        }
        dto.setEnderecos(enderecos);

        return dto;
    }

    // ============================
    //   ENDEREÇOS
    // ============================

    private Endereco toEnderecoModel(EnderecoRequestDTO dto, Object pessoa, PessoaJuridica empresa) {
        Endereco e = new Endereco();
        e.setRua(dto.getRua());
        e.setNumero(dto.getNumero());
        e.setComplemento(dto.getComplemento());
        e.setBairro(dto.getBairro());
        e.setCidade(dto.getCidade());
        e.setUf(dto.getUf());
        e.setCep(dto.getCep());
        e.setTipoEndereco(TipoEndereco.valueOf(dto.getTipoEndereco()));
        e.setPessoa((Pessoa) pessoa);
        e.setEmpresa(empresa);
        return e;
    }

    private EnderecoResponseDTO toEnderecoResponse(Endereco e) {
        EnderecoResponseDTO dto = new EnderecoResponseDTO();
        dto.setRua(e.getRua());
        dto.setNumero(e.getNumero());
        dto.setComplemento(e.getComplemento());
        dto.setBairro(e.getBairro());
        dto.setCidade(e.getCidade());
        dto.setUf(e.getUf());
        dto.setCep(e.getCep());
        dto.setTipoEndereco(e.getTipoEndereco().name());
        return dto;
    }

    // ============================
    //   EMPRESA
    // ============================

    private EmpresaResponseDTO toEmpresaResponse(PessoaJuridica pj) {
        EmpresaResponseDTO dto = new EmpresaResponseDTO();
        dto.setId(pj.getId());
        dto.setCnpj(ValidaCNPJ.cnpjComMascara(pj.getCnpj()));
        dto.setNomeFantasia(pj.getNomeFantasia());
        dto.setRazaoSocial(pj.getRazaoSocial());
        return dto;
    }
}
