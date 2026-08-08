package com.bandampla.lojavirtual.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.dto.onboarding.ConfirmarEmailOnboardingRequestDTO;
import com.bandampla.lojavirtual.dto.onboarding.ConfirmarEmailOnboardingResponseDTO;
import com.bandampla.lojavirtual.dto.onboarding.OnboardingLoginRequestDTO;
import com.bandampla.lojavirtual.dto.onboarding.OnboardingLoginResponseDTO;
import com.bandampla.lojavirtual.dto.onboarding.RegistroEmpresaRequestDTO;
import com.bandampla.lojavirtual.dto.onboarding.RegistroEmpresaResponseDTO;
import com.bandampla.lojavirtual.service.ConfirmacaoEmailOnboardingService;
import com.bandampla.lojavirtual.service.OnboardingLoginService;
import com.bandampla.lojavirtual.service.RegistroEmpresaService;

@RestController
@RequestMapping("/auth")
public class RegistroEmpresaController {

	private final RegistroEmpresaService registroEmpresaService;

	private final ConfirmacaoEmailOnboardingService confirmacaoEmailOnboardingService;
	private final OnboardingLoginService onboardingLoginService;

	public RegistroEmpresaController(RegistroEmpresaService registroEmpresaService,
			ConfirmacaoEmailOnboardingService confirmacaoEmailOnboardingService,
			OnboardingLoginService onboardingLoginService) {
		this.registroEmpresaService = registroEmpresaService;
		this.confirmacaoEmailOnboardingService = confirmacaoEmailOnboardingService;
		this.onboardingLoginService = onboardingLoginService;
	}

	@PostMapping("/onboarding/login")
	public ResponseEntity<OnboardingLoginResponseDTO> loginOnboarding(@Valid @RequestBody OnboardingLoginRequestDTO dto) {
		return ResponseEntity.ok(onboardingLoginService.autenticar(dto));
	}

	@PostMapping("/register/empresa")
	public ResponseEntity<RegistroEmpresaResponseDTO> registrar(@Valid @RequestBody RegistroEmpresaRequestDTO dto) {
		RegistroEmpresaResponseDTO resposta = registroEmpresaService.registrar(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
	}

	@PostMapping("/onboarding/confirmar-email")
	public ResponseEntity<ConfirmarEmailOnboardingResponseDTO> confirmarEmail(
			@Valid @RequestBody ConfirmarEmailOnboardingRequestDTO dto) {
		ConfirmarEmailOnboardingResponseDTO resposta = confirmacaoEmailOnboardingService.confirmar(dto);

		return ResponseEntity.ok(resposta);
	}
}