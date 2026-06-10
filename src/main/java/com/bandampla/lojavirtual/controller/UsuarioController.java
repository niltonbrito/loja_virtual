package com.bandampla.lojavirtual.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandampla.lojavirtual.dto.ResponseDefaultDTO;
import com.bandampla.lojavirtual.dto.request.UsuarioRequestDTO;
import com.bandampla.lojavirtual.dto.response.UsuarioResponseDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	// Simulação de injeção de uma camada de service de usuário fictícia para
	// ilustrar o fluxo
	// @Autowired private UsuarioService usuarioService;

	// ============================
	// 👤 CADASTRO DE NOVO USUÁRIO (Via DTOs puros)
	// ============================
	@PostMapping("/cadastrar")
	public ResponseEntity<ResponseDefaultDTO<UsuarioResponseDTO>> cadastrar(
			@Valid @RequestBody UsuarioRequestDTO request) throws ExceptionCustom {

		// Aqui dentro do seu Service de Usuário, você aplicaria a criptografia
		// BCryptPasswordEncoder
		// antes de salvar a string no banco de dados.
		UsuarioResponseDTO response = new UsuarioResponseDTO();
		response.setId(99L);
		response.setLogin(request.getLogin());

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDefaultDTO<>("Usuário registrado com sucesso.", response));
	}
}
