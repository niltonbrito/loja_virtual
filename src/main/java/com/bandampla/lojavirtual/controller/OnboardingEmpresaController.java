package com.bandampla.lojavirtual.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.dto.onboarding.AtualizarDadosOnboardingRequestDTO;
import com.bandampla.lojavirtual.dto.onboarding.AtualizarDadosOnboardingResponseDTO;
import com.bandampla.lojavirtual.dto.onboarding.FinalizarOnboardingResponseDTO;
import com.bandampla.lojavirtual.security.OnboardingPrincipal;
import com.bandampla.lojavirtual.service.DadosEmpresaOnboardingService;
import com.bandampla.lojavirtual.service.FinalizacaoOnboardingService;

@RestController
@RequestMapping("/onboarding")
public class OnboardingEmpresaController {

	private final DadosEmpresaOnboardingService dadosEmpresaOnboardingService;
	private final FinalizacaoOnboardingService finalizacaoOnboardingService;

	public OnboardingEmpresaController(DadosEmpresaOnboardingService dadosEmpresaOnboardingService,
			FinalizacaoOnboardingService finalizacaoOnboardingService) {

		this.dadosEmpresaOnboardingService = dadosEmpresaOnboardingService;
		this.finalizacaoOnboardingService = finalizacaoOnboardingService;
	}

	@PutMapping("/empresa")
	public ResponseEntity<AtualizarDadosOnboardingResponseDTO> atualizarEmpresa(
			@AuthenticationPrincipal OnboardingPrincipal principal,
			@Valid @RequestBody AtualizarDadosOnboardingRequestDTO dto) {

		AtualizarDadosOnboardingResponseDTO resposta = dadosEmpresaOnboardingService.atualizar(principal, dto);

		return ResponseEntity.ok(resposta);
	}

	@PostMapping("/finalizar")
	public ResponseEntity<FinalizarOnboardingResponseDTO> finalizar(
			@AuthenticationPrincipal OnboardingPrincipal principal) {

		FinalizarOnboardingResponseDTO resposta = finalizacaoOnboardingService.finalizar(principal);

		return ResponseEntity.ok(resposta);
	}
}