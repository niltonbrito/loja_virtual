/**
 * 
 */
package com.bandampla.lojavirtual;

import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.util.ValidaCEP;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 16 de mai. de 2026
 */
public class TesteCEP {

	public static void main(String[] args) throws ExceptionCustom {

		String cep = "4hg1256-h255";

        System.out.println("CEP válido? " + ValidaCEP.isCepValido(cep));

        String limpo = ValidaCEP.cepSemMascara(cep);
        System.out.println("CEP sem máscara: " + limpo);

        String mascarado = ValidaCEP.cepComMascara(limpo);
        System.out.println("CEP com máscara novamente: " + mascarado);

	}
}