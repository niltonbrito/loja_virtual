package com.bandampla.lojavirtual.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SuccessResponseDTO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String mensagem;
	private T dados;
	private String timestamp;
	private String path;
	private String traceId;

	public SuccessResponseDTO() {
		this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	public SuccessResponseDTO(String codigo, String mensagem, T dados, String path, String traceId) {
		this.codigo = codigo;
		this.mensagem = mensagem;
		this.dados = dados;
		this.path = path;
		this.traceId = traceId;
		this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public T getDados() {
		return dados;
	}

	public void setDados(T dados) {
		this.dados = dados;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
}
