package com.bandampla.lojavirtual.util;

import com.bandampla.lojavirtual.exception.ExceptionCustom;

public class ValidaCEP {

	public static boolean isCepValido(String cep) {

		if (cep == null) {
			return false;
		}

		cep = cep.replaceAll("[^0-9]", "");

		return cep.matches("^[0-9]{8}$");
	}

	public static String cepSemMascara(String cep) throws ExceptionCustom {


		cep = cep.replaceAll("[^0-9]", "");

		if (cep.isEmpty()) {
			throw new ExceptionCustom("CEP informado não pode ser vazio.");
		}
		
		if (cep.length() != 8) {
			throw new ExceptionCustom("CEP deve conter exatamente 8 dígitos numéricos.");
		}
		return cep;
	}

	public static String cepComMascara(String cep) throws ExceptionCustom {
		cep = cepSemMascara(cep);
		return cep.substring(0, 5) + "-" + cep.substring(5);
	}
}
