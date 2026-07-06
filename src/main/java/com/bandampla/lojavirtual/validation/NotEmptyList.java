/**
 * 
 */
package com.bandampla.lojavirtual.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 4 de jul. de 2026
 */

@Documented
@Constraint(validatedBy = NotEmptyListValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface NotEmptyList {

	String message() default "A lista não pode estar vazia.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
