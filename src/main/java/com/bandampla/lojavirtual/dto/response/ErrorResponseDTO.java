package com.bandampla.lojavirtual.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String mensagem;
	private String detalhes;
	private String timestamp;
	private String path;
	private String traceId;

	public ErrorResponseDTO() {
		this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	public ErrorResponseDTO(String codigo, String mensagem) {
		this.codigo = codigo;
		this.mensagem = mensagem;
		this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	public ErrorResponseDTO(String codigo, String mensagem, String detalhes) {
		this.codigo = codigo;
		this.mensagem = mensagem;
		this.detalhes = detalhes;
		this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	public ErrorResponseDTO(String codigo, String mensagem, String detalhes, String path, String traceId) {
		this.codigo = codigo;
		this.mensagem = mensagem;
		this.detalhes = detalhes;
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

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
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
