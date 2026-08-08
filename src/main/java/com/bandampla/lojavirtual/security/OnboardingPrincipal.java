package com.bandampla.lojavirtual.security;

import java.io.Serializable;

public class OnboardingPrincipal implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Long onboardingId;
	private final String email;

	public OnboardingPrincipal(Long onboardingId, String email) {

		this.onboardingId = onboardingId;
		this.email = email;
	}

	public Long getOnboardingId() {
		return onboardingId;
	}

	public String getEmail() {
		return email;
	}
}