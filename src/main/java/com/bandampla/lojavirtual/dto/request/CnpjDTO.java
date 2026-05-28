package com.bandampla.lojavirtual.dto.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CnpjDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String abertura;
	private String situacao;
	private String tipo;
	private String nome;
	private String fantasia;
	private String porte;
	private String natureza_juridica;
	private ArrayList<AtividadePrincipal> atividade_principal;
	private ArrayList<AtividadesSecundaria> atividades_secundarias;
	private ArrayList<Qsa> qsa;
	private String logradouro;
	private String numero;
	private String complemento;
	private String municipio;
	private String bairro;
	private String uf;
	private String cep;
	private String email;
	private String telefone;
	private String data_situacao;
	private String cnpj;
	private Date ultima_atualizacao;
	private String status;
	private String efr;
	private String motivo_situacao;
	private String situacao_especial;
	private String data_situacao_especial;
	private String capital_social;
	private Simples simples;
	private Simei simei;

	@JsonIgnore
	private Extra extra;
	private Billing billing;

	public String getAbertura() {
		return abertura;
	}

	public void setAbertura(String abertura) {
		this.abertura = abertura;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getPorte() {
		return porte;
	}

	public void setPorte(String porte) {
		this.porte = porte;
	}

	public String getNatureza_juridica() {
		return natureza_juridica;
	}

	public void setNatureza_juridica(String natureza_juridica) {
		this.natureza_juridica = natureza_juridica;
	}

	public ArrayList<AtividadePrincipal> getAtividade_principal() {
		return atividade_principal;
	}

	public void setAtividade_principal(ArrayList<AtividadePrincipal> atividade_principal) {
		this.atividade_principal = atividade_principal;
	}

	public ArrayList<AtividadesSecundaria> getAtividades_secundarias() {
		return atividades_secundarias;
	}

	public void setAtividades_secundarias(ArrayList<AtividadesSecundaria> atividades_secundarias) {
		this.atividades_secundarias = atividades_secundarias;
	}

	public ArrayList<Qsa> getQsa() {
		return qsa;
	}

	public void setQsa(ArrayList<Qsa> qsa) {
		this.qsa = qsa;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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

	public String getData_situacao() {
		return data_situacao;
	}

	public void setData_situacao(String data_situacao) {
		this.data_situacao = data_situacao;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Date getUltima_atualizacao() {
		return ultima_atualizacao;
	}

	public void setUltima_atualizacao(Date ultima_atualizacao) {
		this.ultima_atualizacao = ultima_atualizacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEfr() {
		return efr;
	}

	public void setEfr(String efr) {
		this.efr = efr;
	}

	public String getMotivo_situacao() {
		return motivo_situacao;
	}

	public void setMotivo_situacao(String motivo_situacao) {
		this.motivo_situacao = motivo_situacao;
	}

	public String getSituacao_especial() {
		return situacao_especial;
	}

	public void setSituacao_especial(String situacao_especial) {
		this.situacao_especial = situacao_especial;
	}

	public String getData_situacao_especial() {
		return data_situacao_especial;
	}

	public void setData_situacao_especial(String data_situacao_especial) {
		this.data_situacao_especial = data_situacao_especial;
	}

	public String getCapital_social() {
		return capital_social;
	}

	public void setCapital_social(String capital_social) {
		this.capital_social = capital_social;
	}

	public Simples getSimples() {
		return simples;
	}

	public void setSimples(Simples simples) {
		this.simples = simples;
	}

	public Simei getSimei() {
		return simei;
	}

	public void setSimei(Simei simei) {
		this.simei = simei;
	}

	public Extra getExtra() {
		return extra;
	}

	public void setExtra(Extra extra) {
		this.extra = extra;
	}

	public Billing getBilling() {
		return billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}
}

class AtividadePrincipal {
	public String code;
	public String text;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}

class AtividadesSecundaria {
	public String code;
	public String text;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}

class Billing {
	public boolean free;
	public boolean database;

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public boolean isDatabase() {
		return database;
	}

	public void setDatabase(boolean database) {
		this.database = database;
	}
}

class Extra {
}

class Qsa {
	public String nome;
	public String qual;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getQual() {
		return qual;
	}

	public void setQual(String qual) {
		this.qual = qual;
	}
}

class Simei {
	public boolean optante;
	public String data_opcao;
	public String data_exclusao;
	public Date ultima_atualizacao;

	public boolean isOptante() {
		return optante;
	}

	public void setOptante(boolean optante) {
		this.optante = optante;
	}

	public String getData_opcao() {
		return data_opcao;
	}

	public void setData_opcao(String data_opcao) {
		this.data_opcao = data_opcao;
	}

	public String getData_exclusao() {
		return data_exclusao;
	}

	public void setData_exclusao(String data_exclusao) {
		this.data_exclusao = data_exclusao;
	}

	public Date getUltima_atualizacao() {
		return ultima_atualizacao;
	}

	public void setUltima_atualizacao(Date ultima_atualizacao) {
		this.ultima_atualizacao = ultima_atualizacao;
	}
}

class Simples {
	public boolean optante;
	public String data_opcao;
	public Object data_exclusao;
	public Date ultima_atualizacao;

	public boolean isOptante() {
		return optante;
	}

	public void setOptante(boolean optante) {
		this.optante = optante;
	}

	public String getData_opcao() {
		return data_opcao;
	}

	public void setData_opcao(String data_opcao) {
		this.data_opcao = data_opcao;
	}

	public Object getData_exclusao() {
		return data_exclusao;
	}

	public void setData_exclusao(Object data_exclusao) {
		this.data_exclusao = data_exclusao;
	}

	public Date getUltima_atualizacao() {
		return ultima_atualizacao;
	}

	public void setUltima_atualizacao(Date ultima_atualizacao) {
		this.ultima_atualizacao = ultima_atualizacao;
	}
}