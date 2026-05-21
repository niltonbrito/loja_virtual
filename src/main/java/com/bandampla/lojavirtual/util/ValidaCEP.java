package com.bandampla.lojavirtual.util;

import com.bandampla.lojavirtual.exception.ExceptionCustom;

public class ValidaCEP {

	public static String limpar(String cep) {
		return cep == null ? null : cep.replaceAll("[^0-9]", "");
	}

	public static void validarFormato(String cep) throws ExceptionCustom {

		if (cep == null || cep.trim().isEmpty()) {
			throw new ExceptionCustom("CEP informado não pode ser vazio.");
		}

		cep = limpar(cep);

		if (cep.length() != 8) {
			throw new ExceptionCustom("CEP deve conter exatamente 8 dígitos numéricos.");
		}

		if (!cep.matches("\\d{8}")) {
			throw new ExceptionCustom("CEP deve conter apenas números.");
		}
	}
}
