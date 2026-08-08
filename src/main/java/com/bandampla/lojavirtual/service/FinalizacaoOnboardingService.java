package com.bandampla.lojavirtual.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.dto.onboarding.FinalizarOnboardingResponseDTO;
import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.enums.StatusOnboarding;
import com.bandampla.lojavirtual.enums.TipoCadastro;
import com.bandampla.lojavirtual.enums.TipoPessoa;
import com.bandampla.lojavirtual.enums.TipoTokenJwt;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.PreCadastroEmpresa;
import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.PreCadastroEmpresaRepository;
import com.bandampla.lojavirtual.repository.UsuarioRepository;
import com.bandampla.lojavirtual.security.JWTTokenAutenticacaoService;
import com.bandampla.lojavirtual.security.OnboardingPrincipal;

@Service
public class FinalizacaoOnboardingService {

	private final PreCadastroEmpresaRepository preCadastroEmpresaRepository;

	private final PessoaJuridicaRepository pessoaJuridicaRepository;

	private final PessoaFisicaRepository pessoaFisicaRepository;

	private final UsuarioRepository usuarioRepository;

	private final UsuarioProvisionamentoService usuarioProvisionamentoService;

	private final JWTTokenAutenticacaoService jwtTokenAutenticacaoService;

	public FinalizacaoOnboardingService(PreCadastroEmpresaRepository preCadastroEmpresaRepository,
			PessoaJuridicaRepository pessoaJuridicaRepository, PessoaFisicaRepository pessoaFisicaRepository,
			UsuarioRepository usuarioRepository, UsuarioProvisionamentoService usuarioProvisionamentoService,
			JWTTokenAutenticacaoService jwtTokenAutenticacaoService) {

		this.preCadastroEmpresaRepository = preCadastroEmpresaRepository;

		this.pessoaJuridicaRepository = pessoaJuridicaRepository;

		this.pessoaFisicaRepository = pessoaFisicaRepository;

		this.usuarioRepository = usuarioRepository;

		this.usuarioProvisionamentoService = usuarioProvisionamentoService;

		this.jwtTokenAutenticacaoService = jwtTokenAutenticacaoService;
	}

	@Transactional(rollbackFor = Exception.class)
	public FinalizarOnboardingResponseDTO finalizar(OnboardingPrincipal principal) {

		validarPrincipal(principal);

		PreCadastroEmpresa preCadastro = preCadastroEmpresaRepository
				.buscarPorIdParaFinalizacao(principal.getOnboardingId())
				.orElseThrow(() -> new ExceptionCustom("Pré-cadastro não encontrado."));

		validarIdentidade(preCadastro, principal);

		validarStatus(preCadastro);
		validarDadosObrigatorios(preCadastro);
		validarDuplicidades(preCadastro);

		String senhaHash = preCadastro.getSenhaHash();

		PessoaJuridica empresa = criarEmpresa(preCadastro);

		PessoaFisica responsavel = criarResponsavel(preCadastro, empresa);

		Usuario usuario = usuarioProvisionamentoService.criarUsuarioComSenhaHash(responsavel, empresa,
				preCadastro.getEmail(), senhaHash, RoleUser.ROLE_ADMIN, false);

		LocalDateTime agora = LocalDateTime.now();

		preCadastro.setStatus(StatusOnboarding.CONCLUIDO);

		preCadastro.setConcluidoEm(agora);
		preCadastro.setUpdatedAt(agora);

		/*
		 * Remove credenciais provisórias.
		 */
		preCadastro.setSenhaHash(null);
		preCadastro.setTokenConfirmacaoHash(null);
		preCadastro.setTokenExpiraEm(null);

		preCadastroEmpresaRepository.save(preCadastro);

		String tokenAccess = jwtTokenAutenticacaoService.gerarTokenAcesso(usuario);

		return new FinalizarOnboardingResponseDTO(tokenAccess, "Bearer", TipoTokenJwt.ACCESS, empresa.getId(),
				responsavel.getId(), usuario.getId(), usuario.getLogin(), preCadastro.getStatus(),
				"Empresa criada com sucesso. " + "Sua conta administrativa está ativa.");
	}

	private PessoaJuridica criarEmpresa(PreCadastroEmpresa preCadastro) {

		PessoaJuridica empresa = new PessoaJuridica();

		empresa.setRazaoSocial(preCadastro.getRazaoSocial());

		empresa.setNomeFantasia(preCadastro.getNomeFantasia());

		empresa.setCnpj(preCadastro.getCnpj());

		empresa.setInscricaoEstadual(preCadastro.getInscricaoEstadual());

		empresa.setTelefone(preCadastro.getTelefoneEmpresa());

		empresa.setEmail(preCadastro.getEmail());

		empresa.setTipoPessoa(TipoPessoa.JURIDICA);

		empresa.setTipoCadastro(TipoCadastro.EMPRESA);

		/*
		 * Ajuste os campos conforme a sua entidade.
		 */
		return pessoaJuridicaRepository.save(empresa);
	}

	private PessoaFisica criarResponsavel(PreCadastroEmpresa preCadastro, PessoaJuridica empresa) {

		PessoaFisica responsavel = new PessoaFisica();

		responsavel.setNome(preCadastro.getNomeResponsavel());

		responsavel.setCpf(preCadastro.getCpfResponsavel());

		responsavel.setTelefone(preCadastro.getTelefoneResponsavel());

		responsavel.setEmail(preCadastro.getEmail());

		responsavel.setEmpresa(empresa);

		responsavel.setTipoPessoa(TipoPessoa.FISICA);

		/*
		 * Use um valor existente no seu enum. Caso não exista, adicione ADMIN_EMPRESA.
		 */
		responsavel.setTipoCadastro(TipoCadastro.ADMIN_EMPRESA);

		return pessoaFisicaRepository.save(responsavel);
	}

	private void validarPrincipal(OnboardingPrincipal principal) {

		if (principal == null || principal.getOnboardingId() == null) {

			throw new ExceptionCustom("Token de onboarding inválido.");
		}

		if (principal.getEmail() == null || principal.getEmail().trim().isEmpty()) {

			throw new ExceptionCustom("Token não possui e-mail válido.");
		}
	}

	private void validarIdentidade(PreCadastroEmpresa preCadastro, OnboardingPrincipal principal) {

		if (!preCadastro.getEmail().equalsIgnoreCase(principal.getEmail())) {

			throw new ExceptionCustom("O token não pertence " + "a este pré-cadastro.");
		}
	}

	private void validarStatus(PreCadastroEmpresa preCadastro) {

		if (preCadastro.getStatus() == StatusOnboarding.PRONTO_PARA_FINALIZAR) {

			return;
		}

		if (preCadastro.getStatus() == StatusOnboarding.CONCLUIDO) {

			throw new ExceptionCustom("Este onboarding já foi concluído.");
		}

		if (preCadastro.getStatus() == StatusOnboarding.DADOS_EMPRESA_PENDENTES) {

			throw new ExceptionCustom("Informe os dados da empresa " + "antes de finalizar.");
		}

		throw new ExceptionCustom("O onboarding não está disponível " + "para finalização.");
	}

	private void validarDadosObrigatorios(PreCadastroEmpresa preCadastro) {

		validarTexto(preCadastro.getNomeResponsavel(), "Nome do responsável");

		validarTexto(preCadastro.getCpfResponsavel(), "CPF do responsável");

		validarTexto(preCadastro.getRazaoSocial(), "Razão social");

		validarTexto(preCadastro.getNomeFantasia(), "Nome fantasia");

		validarTexto(preCadastro.getCnpj(), "CNPJ");

		validarTexto(preCadastro.getEmail(), "E-mail");

		validarTexto(preCadastro.getSenhaHash(), "Senha do pré-cadastro");
	}

	private void validarDuplicidades(PreCadastroEmpresa preCadastro) {

		if (usuarioRepository.existsByLoginIgnoreCase(preCadastro.getEmail())) {

			throw new ExceptionCustom("Já existe usuário cadastrado " + "com este e-mail.");
		}

		if (pessoaFisicaRepository.existsByCpf(preCadastro.getCpfResponsavel())) {

			throw new ExceptionCustom("CPF já cadastrado no sistema.");
		}

		if (pessoaJuridicaRepository.existsByCnpj(preCadastro.getCnpj())) {

			throw new ExceptionCustom("CNPJ já cadastrado no sistema.");
		}
	}

	private void validarTexto(String valor, String campo) {

		if (valor == null || valor.trim().isEmpty()) {

			throw new ExceptionCustom(campo + " não informado.");
		}
	}
}