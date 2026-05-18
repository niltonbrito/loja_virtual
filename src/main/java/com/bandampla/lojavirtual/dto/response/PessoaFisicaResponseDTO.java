/**
 * 
 */
package com.bandampla.lojavirtual.dto.response;

import java.util.List;

import com.bandampla.lojavirtual.util.ValidaCPF;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   18 de mai. de 2026
 */
public class PessoaFisicaResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private String dataNascimento;
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
	public String getCpf() {
		return ValidaCPF.cpfComMascara(cpf);
	}
	public void setCpf(String cpf) {
		this.cpf = ValidaCPF.cpfComMascara(cpf);;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public EmpresaResponseDTO getEmpresa() {
		return empresa;
	}
	public void setEmpresa(EmpresaResponseDTO empresa) {
		this.empresa = empresa;
	}
	public String getTipoPessoa() {
		return tipoPessoa;
	}
	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	public List<EnderecoResponseDTO> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<EnderecoResponseDTO> enderecos) {
		this.enderecos = enderecos;
	}
    
}
