package com.bandampla.lojavirtual.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.dto.ColaboradorRequestDTO;
import com.bandampla.lojavirtual.dto.ColaboradorResponseDTO;
import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.enums.StatusConvite;
import com.bandampla.lojavirtual.event.ConviteColaboradorCriadoEvent;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.ConviteColaborador;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.ConviteColaboradorRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.UsuarioRepository;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;

@Service
public class ColaboradorService {

	private static final long DURACAO_CONVITE_HORAS = 24;

	private final ConviteColaboradorRepository conviteColaboradorRepository;

	private final PessoaJuridicaRepository pessoaJuridicaRepository;

	private final UsuarioRepository usuarioRepository;

	private final TokenSeguroService tokenSeguroService;

	private final ApplicationEventPublisher eventPublisher;

	public ColaboradorService(ConviteColaboradorRepository conviteColaboradorRepository,
			PessoaJuridicaRepository pessoaJuridicaRepository, UsuarioRepository usuarioRepository,
			TokenSeguroService tokenSeguroService, ApplicationEventPublisher eventPublisher) {

		this.conviteColaboradorRepository = conviteColaboradorRepository;

		this.pessoaJuridicaRepository = pessoaJuridicaRepository;

		this.usuarioRepository = usuarioRepository;
		this.tokenSeguroService = tokenSeguroService;
		this.eventPublisher = eventPublisher;
	}

	@Transactional(rollbackFor = Exception.class)
	public ColaboradorResponseDTO cadastrar(ColaboradorRequestDTO dto, UsuarioLogadoPrincipal usuarioLogado) {

		validarUsuarioLogado(usuarioLogado);
		validarPerfilPermitido(dto.getPerfil());

		Long empresaId = usuarioLogado.getEmpresaId();

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(empresaId)
				.orElseThrow(() -> new ExceptionCustom("Empresa do usuário autenticado " + "não encontrada."));

		/*
		 * Este método pressupõe que o principal possui o ID do usuário autenticado.
		 */
		Usuario criadoPor = usuarioRepository.findById(usuarioLogado.getId())
				.orElseThrow(() -> new ExceptionCustom("Usuário autenticado não encontrado."));

		String email = dto.getEmail().trim().toLowerCase();

		if (usuarioRepository.existsByLoginIgnoreCase(email)) {

			throw new ExceptionCustom("Já existe um usuário cadastrado " + "com este e-mail.");
		}

		validarConvitePendente(empresa.getId(), email);

		String tokenPuro = tokenSeguroService.gerarToken();

		String tokenHash = tokenSeguroService.gerarHash(tokenPuro);

		LocalDateTime agora = LocalDateTime.now();

		ConviteColaborador convite = new ConviteColaborador();

		convite.setNome(dto.getNome().trim());
		convite.setEmail(email);
		convite.setPerfil(dto.getPerfil());
		convite.setTokenHash(tokenHash);
		convite.setStatus(StatusConvite.PENDENTE);
		convite.setEmpresa(empresa);
		convite.setCriadoPor(criadoPor);
		convite.setCreatedAt(agora);
		convite.setUpdatedAt(agora);
		convite.setExpiraEm(agora.plusHours(DURACAO_CONVITE_HORAS));

		ConviteColaborador salvo = conviteColaboradorRepository.save(convite);

		eventPublisher.publishEvent(new ConviteColaboradorCriadoEvent(salvo.getNome(), salvo.getEmail(),
				empresa.getNomeFantasia(), tokenPuro, salvo.getExpiraEm()));

		return new ColaboradorResponseDTO(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getPerfil(),
				salvo.getStatus(), salvo.getExpiraEm(), "Convite enviado com sucesso.");
	}

	private void validarConvitePendente(Long empresaId, String email) {

		Optional<ConviteColaborador> existente = conviteColaboradorRepository
				.findByEmpresaIdAndEmailIgnoreCaseAndStatus(empresaId, email, StatusConvite.PENDENTE);

		if (!existente.isPresent()) {
			return;
		}

		ConviteColaborador convite = existente.get();

		LocalDateTime agora = LocalDateTime.now();

		if (!convite.estaExpirado(agora)) {
			throw new ExceptionCustom("Já existe um convite pendente " + "para este e-mail.");
		}

		convite.setStatus(StatusConvite.EXPIRADO);
		convite.setUpdatedAt(agora);

		conviteColaboradorRepository.save(convite);
	}

	private void validarUsuarioLogado(UsuarioLogadoPrincipal usuarioLogado) {

		if (usuarioLogado == null) {
			throw new ExceptionCustom("Usuário autenticado não informado.");
		}

		if (usuarioLogado.getEmpresaId() == null) {
			throw new ExceptionCustom("Usuário autenticado não possui empresa.");
		}

		if (usuarioLogado.getId() == null) {
			throw new ExceptionCustom("ID do usuário autenticado não informado.");
		}
	}

	private void validarPerfilPermitido(RoleUser role) {

		if (role == null) {
			throw new ExceptionCustom("Perfil do colaborador não informado.");
		}

		switch (role) {
		case ROLE_USER:
		case ROLE_FINANCEIRO:
		case ROLE_ESTOQUE:
		case ROLE_GERENTE:
		case ROLE_ADMIN:
			return;

		case ROLE_SUPER_ADMIN:
			throw new ExceptionCustom(
					"ROLE_SUPER_ADMIN não pode ser " + "atribuído por administradores " + "de empresa.");

		case ROLE_CLIENTE:
			throw new ExceptionCustom("ROLE_CLIENTE deve ser criado pelo " + "fluxo público de clientes.");

		default:
			throw new ExceptionCustom("Perfil não permitido para colaborador.");
		}
	}
}