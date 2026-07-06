/**
 * 
 */
package com.bandampla.lojavirtual.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 4 de jul. de 2026
 */

public class BooleanValidator implements ConstraintValidator<ValidBoolean, Boolean> {

	private String message;

	@Override
	public void initialize(ValidBoolean constraintAnnotation) {
		this.message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(Boolean value, ConstraintValidatorContext context) {

		if (value == null) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
			return false;
		}

		return true;
	}
}