/**
 * 
 */
package com.bandampla.lojavirtual;

import com.bandampla.lojavirtual.util.ValidaCNPJ;
import com.bandampla.lojavirtual.util.ValidaCPF;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 16 de mai. de 2026
 */
public class TesteCPFCNPJ {

	public static void main(String[] args) {

		String CNPJ = "64.916.306/0001-75";

		// usando os métodos isCNPJ() e imprimeCNPJ() da classe "ValidaCNPJ"

		if (ValidaCNPJ.isCNPJ(CNPJ) == true) {
			System.out.println("CNPJ Válido: " + ValidaCNPJ.isCNPJ(CNPJ));
			System.out.printf("\nResultado: ");
			System.out.printf("%s\n", ValidaCNPJ.cnpjSemMascara(CNPJ));
			System.out.printf("%s\n", ValidaCNPJ.cnpjComMascara(CNPJ));
		} else {
			System.out.printf("Erro, CNPJ inválido !!!\n");
		}

		System.out.println("");
		
		String CPF = "795.661.020-62";

		// usando os metodos isCPF() e imprimeCPF() da classe "ValidaCPF"
		if (ValidaCPF.isCPF(CPF) == true) {
			System.out.println("CPF Válido: " + ValidaCPF.isCPF(CPF));
			System.out.printf("\nResultado: ");
			System.out.printf("%s\n", ValidaCPF.cpfSemMascara(CPF));
			System.out.printf("%s\n", ValidaCPF.cpfComMascara(CPF));
		} else {
			System.out.printf("Erro, CPF invalido !!!\n");
		}

	}

}
