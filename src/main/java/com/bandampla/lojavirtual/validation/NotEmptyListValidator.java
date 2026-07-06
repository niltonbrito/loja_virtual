/**
 * 
 */
package com.bandampla.lojavirtual.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 4 de jul. de 2026
 */
public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List<?>> {

	@Override
	public boolean isValid(List<?> value, ConstraintValidatorContext context) {
		return value != null && !value.isEmpty();
	}
}