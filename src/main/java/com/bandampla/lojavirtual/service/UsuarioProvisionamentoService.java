package com.bandampla.lojavirtual.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.enums.StatusUsuario;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.Acesso;
import com.bandampla.lojavirtual.model.Pessoa;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.AcessoRepository;
import com.bandampla.lojavirtual.repository.UsuarioRepository;
import com.bandampla.lojavirtual.service.validation.UsuarioTenantValidator;

@Service
public class UsuarioProvisionamentoService {

	private final UsuarioRepository usuarioRepository;
	private final AcessoRepository acessoRepository;
	private final PasswordEncoder passwordEncoder;
	private final UsuarioTenantValidator tenantValidator;

	public UsuarioProvisionamentoService(UsuarioRepository usuarioRepository, AcessoRepository acessoRepository,
			PasswordEncoder passwordEncoder, UsuarioTenantValidator tenantValidator) {

		this.usuarioRepository = usuarioRepository;
		this.acessoRepository = acessoRepository;
		this.passwordEncoder = passwordEncoder;
		this.tenantValidator = tenantValidator;
	}

	@Transactional(rollbackFor = Exception.class)
	public Usuario criarUsuario(Pessoa pessoa, PessoaJuridica empresa, String login, String senha, RoleUser role,
			boolean trocaSenhaObrigatoria) {

		validarParametros(pessoa, login, senha, role);

		String loginNormalizado = login.trim().toLowerCase();

		if (usuarioRepository.existsByLoginIgnoreCase(loginNormalizado)) {

			throw new ExceptionCustom("Já existe usuário cadastrado " + "com este login.");
		}

		Acesso acesso = acessoRepository.findByRoleUser(role)
				.orElseThrow(() -> new ExceptionCustom("Perfil " + role.name() + " não encontrado."));

		Usuario usuario = new Usuario();

		usuario.setLogin(loginNormalizado);
		usuario.setSenha(passwordEncoder.encode(senha));
		usuario.setPessoa(pessoa);
		usuario.setEmpresa(empresa);
		usuario.setStatus(StatusUsuario.ATIVO);
		usuario.setTrocaSenhaObrigatoria(trocaSenhaObrigatoria);

		usuario.adicionarAcesso(acesso);

		tenantValidator.validar(usuario);

		return usuarioRepository.save(usuario);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public Usuario criarUsuarioComSenhaHash(Pessoa pessoa, PessoaJuridica empresa, String login, String senhaHash,
			RoleUser role, boolean trocaSenhaObrigatoria) {

		validarParametrosComSenhaHash(pessoa, login, senhaHash, role);

		String loginNormalizado = login.trim().toLowerCase();

		if (usuarioRepository.existsByLoginIgnoreCase(loginNormalizado)) {

			throw new ExceptionCustom("Já existe um usuário cadastrado " + "com este login.");
		}

		Acesso acesso = acessoRepository.findByRoleUser(role)
				.orElseThrow(() -> new ExceptionCustom("Perfil " + role.name() + " não encontrado."));

		Usuario usuario = new Usuario();

		usuario.setLogin(loginNormalizado);

		/*
		 * A senha já está criptografada com BCrypt.
		 */
		usuario.setSenha(senhaHash);

		usuario.setPessoa(pessoa);
		usuario.setEmpresa(empresa);
		usuario.setStatus(StatusUsuario.ATIVO);
		usuario.setTrocaSenhaObrigatoria(trocaSenhaObrigatoria);
		usuario.setCreatedAt(LocalDateTime.now());
		usuario.adicionarAcesso(acesso);

		tenantValidator.validar(usuario);

		return usuarioRepository.save(usuario);
	}

	private void validarParametrosComSenhaHash(Pessoa pessoa, String login, String senhaHash, RoleUser role) {

		if (pessoa == null) {
			throw new ExceptionCustom("Pessoa do usuário não informada.");
		}

		if (login == null || login.trim().isEmpty()) {
			throw new ExceptionCustom("Login do usuário não informado.");
		}

		if (senhaHash == null || senhaHash.trim().isEmpty()) {

			throw new ExceptionCustom("Senha criptografada não informada.");
		}

		/*
		 * Hashes BCrypt normalmente começam com $2a$, $2b$ ou $2y$.
		 */
		if (!senhaHash.startsWith("$2")) {
			throw new ExceptionCustom("A senha informada não possui " + "um hash BCrypt válido.");
		}

		if (role == null) {
			throw new ExceptionCustom("Perfil do usuário não informado.");
		}
	}

	private void validarParametros(Pessoa pessoa, String login, String senha, RoleUser role) {

		if (pessoa == null) {
			throw new ExceptionCustom("Pessoa do usuário não informada.");
		}

		if (login == null || login.trim().isEmpty()) {
			throw new ExceptionCustom("Login não informado.");
		}

		if (senha == null || senha.length() < 8) {
			throw new ExceptionCustom("A senha deve possuir ao menos " + "8 caracteres.");
		}

		if (role == null) {
			throw new ExceptionCustom("Perfil do usuário não informado.");
		}
	}
}