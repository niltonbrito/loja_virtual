/**
 * 
 */
package com.bandampla.lojavirtual.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.xml.bind.DatatypeConverter;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 4 de jul. de 2026
 */
public class Base64ImageValidator implements ConstraintValidator<ValidBase64Image, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null || value.trim().isEmpty()) {
			return false;
		}

		try {
			String base64 = value;

			if (base64.startsWith("data:image")) {
				base64 = base64.split(",")[1];
			}

			DatatypeConverter.parseBase64Binary(base64);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}