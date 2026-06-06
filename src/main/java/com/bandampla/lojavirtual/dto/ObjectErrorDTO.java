/**
 * 
 */
package com.bandampla.lojavirtual.dto;

import java.io.Serializable;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 3 de mai. de 2026
 */
public class ObjectErrorDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String error;
	private String code;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}