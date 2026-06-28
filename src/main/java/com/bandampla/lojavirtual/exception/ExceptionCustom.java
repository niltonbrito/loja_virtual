package com.bandampla.lojavirtual.exception;

import java.io.Serializable;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 5 de mai. de 2026
 */
public class ExceptionCustom extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	public ExceptionCustom(String msgError) {
		super(msgError);
	}
}
