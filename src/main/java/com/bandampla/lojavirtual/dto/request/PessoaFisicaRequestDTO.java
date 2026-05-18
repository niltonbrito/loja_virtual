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

import org.hibernate.validator.constraints.br.CPF;

import com.bandampla.lojavirtual.util.ValidaCPF;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 18 de mai. de 2026
 */
public class PessoaFisicaRequestDTO {

	private Long id;

	@NotBlank(message = "Nome deve ser informado.")
	@Size(min = 4, message = "O nome deve ter no mínimo 4 letras")
	private String nome;

	@NotBlank(message = "E-mail deve ser informado.")
	@Email(message = "E-mail inválido")
	private String email;

	@NotBlank(message = "Telefone deve ser informado.")
	private String telefone;

	@NotBlank(message = "CPF deve ser informado.")
	@CPF(message = "CPF inválido")
	private String cpf;

	@NotBlank(message = "Data de nascimento é obrigatória.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private String dataNascimento;

	@NotBlank(message = "Tipo pessoa deve ser informado.")
	private String tipoPessoa;

	@Valid
	@NotBlank(message = "Empresa deve ser informada.")
	private EmpresaRequestDTO empresa;

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

	public String getCpf() {
		return ValidaCPF.cpfSemMascara(cpf);
	}

	public void setCpf(String cpf) {
		this.cpf = ValidaCPF.cpfSemMascara(cpf);
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public EmpresaRequestDTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaRequestDTO empresa) {
		this.empresa = empresa;
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
