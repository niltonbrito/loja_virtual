package com.bandampla.lojavirtual.bootstrap;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.config.SuperAdminProperties;
import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.enums.StatusUsuario;
import com.bandampla.lojavirtual.enums.TipoCadastro;
import com.bandampla.lojavirtual.enums.TipoPessoa;
import com.bandampla.lojavirtual.model.Acesso;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.AcessoRepository;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.UsuarioRepository;
import com.bandampla.lojavirtual.util.ValidaCPF;

@Component
public class SuperAdminBootstrap implements ApplicationRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(SuperAdminBootstrap.class);

	private final SuperAdminProperties properties;
	private final UsuarioRepository usuarioRepository;
	private final PessoaFisicaRepository pessoaFisicaRepository;
	private final AcessoRepository acessoRepository;
	private final PasswordEncoder passwordEncoder;

	public SuperAdminBootstrap(SuperAdminProperties properties, UsuarioRepository usuarioRepository,
			PessoaFisicaRepository pessoaFisicaRepository, AcessoRepository acessoRepository,
			PasswordEncoder passwordEncoder) {

		this.properties = properties;
		this.usuarioRepository = usuarioRepository;
		this.pessoaFisicaRepository = pessoaFisicaRepository;
		this.acessoRepository = acessoRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void run(ApplicationArguments args) {

		/*
		 * Idempotência: se já existir SUPER_ADMIN, o bootstrap não altera nada.
		 */
		if (usuarioRepository.existeUsuarioComRole(RoleUser.ROLE_SUPER_ADMIN)) {

			LOGGER.info("Bootstrap SUPER_ADMIN ignorado: " + "já existe um administrador da plataforma.");

			return;
		}

		/*
		 * Caso não exista SUPER_ADMIN, a aplicação exige que o bootstrap esteja
		 * habilitado.
		 */
		if (!properties.isEnabled()) {
			throw new IllegalStateException("Não existe usuário ROLE_SUPER_ADMIN e o bootstrap "
					+ "está desabilitado. Configure " + "SUPER_ADMIN_BOOTSTRAP_ENABLED=true no primeiro deploy.");
		}

		validarConfiguracao();

		String email = normalizarEmail(properties.getEmail());
		String cpf = normalizarCpf(properties.getCpf());

		if (usuarioRepository.existsByLoginIgnoreCase(email)) {
			throw new IllegalStateException(
					"O e-mail configurado para o SUPER_ADMIN " + "já está vinculado a outro usuário.");
		}

		if (pessoaFisicaRepository.findByCpf(cpf).isPresent()) {
			throw new IllegalStateException("O CPF configurado para o SUPER_ADMIN " + "já está cadastrado.");
		}

		Acesso acessoSuperAdmin = acessoRepository.findByRoleUser(RoleUser.ROLE_SUPER_ADMIN)
				.orElseThrow(() -> new IllegalStateException(
						"ROLE_SUPER_ADMIN não foi encontrado na tabela acesso. " + "Verifique a migration de perfis."));

		PessoaFisica pessoa = criarPessoaSuperAdmin(email, cpf);

		Usuario usuario = criarUsuarioSuperAdmin(pessoa, email, acessoSuperAdmin);

		LOGGER.info("SUPER_ADMIN inicial criado com sucesso. " + "Usuário ID: {}. Desabilite o bootstrap "
				+ "e remova a senha do ambiente.", usuario.getId());
	}

	private PessoaFisica criarPessoaSuperAdmin(String email, String cpf) {

		PessoaFisica pessoa = new PessoaFisica();

		pessoa.setNome(properties.getNome().trim());
		pessoa.setEmail(email);
		pessoa.setCpf(cpf);
		pessoa.setTipoPessoa(TipoPessoa.FISICA);
		pessoa.setTipoCadastro(TipoCadastro.ADMIN_PLATAFORMA);
		pessoa.setTelefone(somenteNumeros(properties.getTelefone()));

		/*
		 * O SUPER_ADMIN pertence à plataforma, não a uma empresa cliente.
		 */
		pessoa.setEmpresa(null);

		return pessoaFisicaRepository.save(pessoa);
	}

	private Usuario criarUsuarioSuperAdmin(PessoaFisica pessoa, String email, Acesso acessoSuperAdmin) {

		Usuario usuario = new Usuario();

		usuario.setLogin(email);
		usuario.setSenha(passwordEncoder.encode(properties.getPassword()));

		usuario.setPessoa(pessoa);
		usuario.setEmpresa(null);
		usuario.setStatus(StatusUsuario.ATIVO);
		usuario.setTrocaSenhaObrigatoria(true);
		usuario.setCreatedAt(LocalDateTime.now());

		/*
		 * Método recomendado na entidade Usuario.
		 */
		usuario.adicionarAcesso(acessoSuperAdmin);

		return usuarioRepository.save(usuario);
	}

	private void validarConfiguracao() {

		if (properties.getNome() == null || properties.getNome().trim().isEmpty()) {

			throw new IllegalStateException("SUPER_ADMIN_NOME não foi configurado.");
		}

		if (properties.getEmail() == null || properties.getEmail().trim().isEmpty()) {

			throw new IllegalStateException("SUPER_ADMIN_EMAIL não foi configurado.");
		}

		if (properties.getCpf() == null || properties.getCpf().trim().isEmpty()) {

			throw new IllegalStateException("SUPER_ADMIN_CPF não foi configurado.");
		}
		String telefone = somenteNumeros(properties.getTelefone());

		if (telefone == null || telefone.length() < 10 || telefone.length() > 11) {

			throw new IllegalStateException("SUPER_ADMIN_TELEFONE deve possuir 10 ou 11 dígitos.");
		}

		if (properties.getPassword() == null || properties.getPassword().length() < 12) {

			throw new IllegalStateException("SUPER_ADMIN_PASSWORD deve possuir pelo menos 12 caracteres.");
		}

		if (!properties.getPassword().matches(".*[A-Z].*") || !properties.getPassword().matches(".*[a-z].*")
				|| !properties.getPassword().matches(".*\\d.*")) {

			throw new IllegalStateException(
					"SUPER_ADMIN_PASSWORD deve possuir letra maiúscula, letra minúscula e número.");
		}
	}

	private String normalizarEmail(String email) {
		return email.trim().toLowerCase();
	}

	private String somenteNumeros(String valor) {

		if (valor == null) {
			return null;
		}

		return valor.replaceAll("\\D", "");
	}

	private String normalizarCpf(String cpf) {

		String cpfNormalizado = ValidaCPF.cpfSemMascara(cpf.trim());

		if (cpfNormalizado == null || cpfNormalizado.length() != 11) {

			throw new IllegalStateException("SUPER_ADMIN_CPF deve possuir 11 dígitos.");
		}

		return cpfNormalizado;
	}
}