package com.bandampla.lojavirtual.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.bandampla.lojavirtual.enums.TipoCadastro;
import com.bandampla.lojavirtual.enums.TipoPessoa;

public class PessoaJuridicaDTO {

	private Long id;

	@NotBlank(message = "O nome ou razão social deve ser informado.")
	private String nome;

	@NotBlank(message = "O e-mail corporativo deve ser informado.")
	@Email(message = "Formato de e-mail inválido.")
	private String email;

	@NotBlank(message = "O telefone deve ser informado.")
	private String telefone;

	private TipoPessoa tipoPessoa;
	private TipoCadastro tipoCadastro;
	private Long setorId;

	@NotBlank(message = "O CNPJ deve ser informado.")
	private String cnpj;

	private String inscricaoEstadual;
	private String inscricaoMunicipal;

	@NotBlank(message = "O nome fantasia deve ser informado.")
	private String nomeFantasia;

	@NotBlank(message = "A razão social deve ser informada.")
	private String razaoSocial;

	private String categoria;
	private Long matrizId;
	private Long empresaId;

	// Getters e Setters
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

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public TipoCadastro getTipoCadastro() {
		return tipoCadastro;
	}

	public void setTipoCadastro(TipoCadastro tipoCadastro) {
		this.tipoCadastro = tipoCadastro;
	}

	public Long getSetorId() {
		return setorId;
	}

	public void setSetorId(Long setorId) {
		this.setorId = setorId;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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

	public Long getMatrizId() {
		return matrizId;
	}

	public void setMatrizId(Long matrizId) {
		this.matrizId = matrizId;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
}