/**
 * 
 */
package com.bandampla.lojavirtual.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

import com.bandampla.lojavirtual.util.ValidaCNPJ;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 18 de mai. de 2026
 */
public class PessoaJuridicaRequestDTO {

	private Long id;

	@NotBlank(message = "Nome deve ser informado.")
	@Size(min = 4, message = "O nome deve ter no mínimo 4 letras")
	private String nome;

	@NotBlank(message = "E-mail deve ser informado.")
	@Email(message = "E-mail inválido")
	private String email;

	@NotBlank(message = "Telefone deve ser informado.")
	private String telefone;

	@NotBlank(message = "CNPJ deve ser informado.")
	@CNPJ(message = "CNPJ inválido")
	private String cnpj;

	@NotBlank(message = "Inscrição Estadual deve ser informada.")
	private String inscricaoEstadual;

	@NotBlank(message = "Inscrição Municipal deve ser informada.")
	private String inscricaoMunicipal;

	@NotBlank(message = "Nome fantasia deve ser informado.")
	private String nomeFantasia;

	@NotBlank(message = "Razão social deve ser informada.")
	private String razaoSocial;

	@NotBlank(message = "Categoria deve ser informada.")
	private String categoria;

	@NotBlank(message = "Tipo pessoa deve ser informado.")
	private String tipoPessoa;

	@Valid
	private List<EnderecoRequestDTO> enderecos;

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
		return ValidaCNPJ.cnpjSemMascara(cnpj);
	}

	public void setCnpj(String cnpj) {
		this.cnpj = ValidaCNPJ.cnpjSemMascara(cnpj);
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

	public List<EnderecoRequestDTO> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoRequestDTO> enderecos) {
		this.enderecos = enderecos;
	}
}
