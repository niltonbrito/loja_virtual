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
@Constraint(validatedBy = Base64ImageValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ValidBase64Image {

	String message() default "Imagem inválida. Envie uma imagem em Base64 válida.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
