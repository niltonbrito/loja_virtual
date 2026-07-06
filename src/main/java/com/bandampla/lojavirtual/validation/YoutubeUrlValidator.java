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
public class YoutubeUrlValidator implements ConstraintValidator<ValidYoutubeUrl, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null || value.trim().isEmpty()) {
			return true;
		}

		String url = value.trim().toLowerCase();

		return url.contains("youtube.com/watch") || url.contains("youtu.be/");
	}
}