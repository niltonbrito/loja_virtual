/**
 * 
 */
package com.bandampla.lojavirtual.dto.response;

import java.util.List;

import com.bandampla.lojavirtual.dto.request.EmpresaRequestDTO;
import com.bandampla.lojavirtual.util.ValidaCNPJ;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   18 de mai. de 2026
 */
public class PessoaJuridicaResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;

    private String cnpj;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;

    private String nomeFantasia;
    private String razaoSocial;
    private String categoria;
    private String tipoPessoa;
    private EmpresaResponseDTO empresa;
    private List<EnderecoResponseDTO> enderecos;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCnpj() {
		return ValidaCNPJ.cnpjComMascara(cnpj);
	}
	public void setCnpj(String cnpj) {
		this.cnpj = ValidaCNPJ.cnpjComMascara(cnpj);
	}
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}
	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}
	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}
	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	public EmpresaResponseDTO getEmpresa() {
		return empresa;
	}
	public void setEmpresa(EmpresaResponseDTO empresa) {
		this.empresa = empresa;
	}
	public List<EnderecoResponseDTO> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<EnderecoResponseDTO> enderecos) {
		this.enderecos = enderecos;
	}

}
