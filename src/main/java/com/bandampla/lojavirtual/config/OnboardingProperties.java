package com.bandampla.lojavirtual.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.onboarding")
public class OnboardingProperties {

	private long emailTokenExpirationMinutes = 30;

	private String confirmationUrl;

	public long getEmailTokenExpirationMinutes() {
		return emailTokenExpirationMinutes;
	}

	public void setEmailTokenExpirationMinutes(long emailTokenExpirationMinutes) {

		this.emailTokenExpirationMinutes = emailTokenExpirationMinutes;
	}

	public String getConfirmationUrl() {
		return confirmationUrl;
	}

	public void setConfirmationUrl(String confirmationUrl) {
		this.confirmationUrl = confirmationUrl;
	}
}