package com.bandampla.lojavirtual.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bandampla.lojavirtual.enums.TipoCadastro;
import com.bandampla.lojavirtual.enums.TipoPessoa;

public class PessoaFisicaDTO {

	private Long id;

	@NotBlank(message = "O nome deve ser informado.")
	private String nome;

	@NotBlank(message = "O e-mail deve ser informado.")
	@Email(message = "Formato de e-mail inválido.")
	private String email;

	@NotBlank(message = "O telefone deve ser informado.")
	private String telefone;

	private TipoPessoa tipoPessoa;
	private TipoCadastro tipoCadastro;
	private Long setorId;

	@NotBlank(message = "O CPF deve ser informado.")
	private String cpf;

	@NotNull(message = "A data de nascimento deve ser informada.")
	private LocalDate dataNascimento;

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
}