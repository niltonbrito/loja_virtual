/**
 * 
 */
package com.bandampla.lojavirtual.dto.request;

import javax.validation.constraints.NotBlank;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   18 de mai. de 2026
 */
public class EmpresaRequestDTO {
	

    @NotBlank
    private String cnpj;

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
}