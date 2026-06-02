package com.bandampla.lojavirtual.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bandampla.lojavirtual.dto.request.CepDTO;
import com.bandampla.lojavirtual.dto.request.CnpjDTO;
import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.enums.TipoPessoa;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.Endereco;
import com.bandampla.lojavirtual.model.Pessoa;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.EnderecoRepository;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.UsuarioRepository;
import com.bandampla.lojavirtual.util.ValidaCEP;
import com.bandampla.lojavirtual.util.ValidaCNPJ;
import com.bandampla.lojavirtual.util.ValidaCPF;

@Service
public class PessoaUserService {

	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SendMailService sendMailService;

	/*
	 * ===================== SALVAR PESSOA JURÍDICA (EMPRESA / MATRIZ / FILIAL)
	 * =====================
	 */
	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) throws ExceptionCustom {

		if (pessoaJuridica == null) {
			throw new ExceptionCustom("Pessoa Juridica não pode ser NULL");
		}

		if (pessoaJuridica.getTipoPessoa() == null) {
			throw new ExceptionCustom("Informe o tipo de Pessoa Juridica.");
		}

		/* Validação de CNPJ */
		Optional<PessoaJuridica> pjCnpj = pessoaJuridicaRepository
				.findByCnpj(ValidaCNPJ.cnpjSemMascara(pessoaJuridica.getCnpj().trim()));
		if (pjCnpj.isPresent() && !pjCnpj.get().getId().equals(pessoaJuridica.getId())) {
			throw new ExceptionCustom("CNPJ já cadastrado no sistema");
		}

		/* Validação de IE */
		if (pessoaJuridica.getInscricaoEstadual() != null) {
			Optional<PessoaJuridica> pjIe = pessoaJuridicaRepository
					.findByInscricaoEstadual(pessoaJuridica.getInscricaoEstadual().trim());
			if (pjIe.isPresent() && !pjIe.get().getId().equals(pessoaJuridica.getId())) {
				throw new ExceptionCustom("Inscrição Estadual já cadastrada");
			}
		}

		/* MATRIZ / FILIAL */
		if (pessoaJuridica.getMatriz() != null && pessoaJuridica.getMatriz().getCnpj() != null) {
			String cnpjMatriz = ValidaCNPJ.cnpjSemMascara(pessoaJuridica.getMatriz().getCnpj().trim());
			PessoaJuridica matriz = pessoaJuridicaRepository.findByCnpj(cnpjMatriz)
					.orElseThrow(() -> new ExceptionCustom("Matriz com CNPJ " + cnpjMatriz + " não encontrada."));
			pessoaJuridica.setMatriz(matriz);
		} else {
			pessoaJuridica.setMatriz(null);
		}

		cadastrarEnderecos(pessoaJuridica);
		pessoaJuridica.setTipoPessoa(TipoPessoa.JURIDICA);
		pessoaJuridica.setCnpj(ValidaCNPJ.cnpjSemMascara(pessoaJuridica.getCnpj().trim()));
		pessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);

		criarUsuario(pessoaJuridica, pessoaJuridica);
		return pessoaJuridica;

	}

	public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) throws ExceptionCustom {

		if (pessoaFisica == null) {
			throw new ExceptionCustom("Pessoa Fisica não pode ser NULL");
		}

		if (pessoaFisica.getTipoPessoa() == null) {
			throw new ExceptionCustom("Informe o tipo de Pessoa.");
		}

		/* Validação de CPF */
		Optional<PessoaFisica> pessoaFisicaOpt = pessoaFisicaRepository
				.findByCpf(ValidaCPF.cpfSemMascara(pessoaFisica.getCpf()));
		if (pessoaFisicaOpt.isPresent() && !pessoaFisicaOpt.get().getId().equals(pessoaFisica.getId())) {
			throw new ExceptionCustom("CPF já cadastrado no sistema");
		}

		/* Empresa obrigatória */
		if (pessoaFisica.getEmpresa() == null || pessoaFisica.getEmpresa().getCnpj() == null) {
			throw new ExceptionCustom("Pessoa Física deve estar vinculada a uma empresa (CNPJ).");
		}

		cadastrarEnderecos(pessoaFisica);

		String cnpjEmpresa = ValidaCNPJ.cnpjSemMascara(pessoaFisica.getEmpresa().getCnpj());
		PessoaJuridica empresa = pessoaJuridicaRepository.findByCnpj(cnpjEmpresa)
				.orElseThrow(() -> new ExceptionCustom("Empresa com CNPJ " + cnpjEmpresa + " não encontrada."));

		pessoaFisica.setEmpresa(empresa);
		pessoaFisica.setTipoPessoa(TipoPessoa.FISICA);
		pessoaFisica.setCpf(ValidaCPF.cpfSemMascara(pessoaFisica.getCpf().trim()));
		pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);

		criarUsuario(pessoaFisica, empresa);

		return pessoaFisica;
	}

	private void cadastrarEnderecos(Pessoa pessoa) throws ExceptionCustom {

		for (Endereco end : pessoa.getEnderecos()) {

			/*
			 * ===================== 1) NOVA PESSOA cadastra todos os endereços
			 * =====================
			 */
			if (pessoa.getId() == null) {

				CepDTO cepDTO = consultaCep(ValidaCEP.cepSemMascara(end.getCep()));
				if (cepDTO.getCep() == null) {
					throw new ExceptionCustom("CEP inválido.");
				}

				end.setBairro(cepDTO.getBairro());
				end.setCep(ValidaCEP.cepSemMascara(cepDTO.getCep()));
				end.setCidade(cepDTO.getLocalidade());
				end.setComplemento(cepDTO.getComplemento());
				end.setRua(cepDTO.getLogradouro());
				end.setUf(cepDTO.getUf());
				end.setPessoa(pessoa);

				continue;
			}

			/*
			 * ===================== 2) PESSOA EXISTENTE + ENDEREÇO SEM ID → cadastrar novo
			 * =====================
			 */
			if (end.getId() == null) {

				CepDTO cepDTO = consultaCep(ValidaCEP.cepSemMascara(end.getCep()));
				if (cepDTO.getCep() == null) {
					throw new ExceptionCustom("CEP inválido.");
				}

				end.setBairro(cepDTO.getBairro());
				end.setCep(ValidaCEP.cepSemMascara(cepDTO.getCep()));
				end.setCidade(cepDTO.getLocalidade());
				end.setComplemento(cepDTO.getComplemento());
				end.setRua(cepDTO.getLogradouro());
				end.setUf(cepDTO.getUf());
				end.setPessoa(pessoa);

				continue;
			}

			/*
			 * ===================== 3) PESSOA EXISTENTE + ENDEREÇO EXISTENTE → atualizar se
			 * CEP mudou =====================
			 */
			Endereco endBanco = enderecoRepository.findById(end.getId())
					.orElseThrow(() -> new ExceptionCustom("Endereço não encontrado."));

			String cepNovo = ValidaCEP.cepSemMascara(end.getCep());
			String cepAntigo = endBanco.getCep();

			if (!cepNovo.equals(cepAntigo)) {

				CepDTO cepDTO = consultaCep(cepNovo);
				if (cepDTO.getCep() == null) {
					throw new ExceptionCustom("CEP inválido.");
				}

				end.setBairro(cepDTO.getBairro());
				end.setCep(ValidaCEP.cepSemMascara(cepDTO.getCep()));
				end.setCidade(cepDTO.getLocalidade());
				end.setComplemento(cepDTO.getComplemento());
				end.setRua(cepDTO.getLogradouro());
				end.setUf(cepDTO.getUf());
				end.setPessoa(pessoa);

			} else {
				/* Se o CEP não mudou → mantém dados do banco */
				end.setPessoa(pessoa);
			}
		}
	}

	/*
	 * ===================== CRIA USUÁRIO AUTOMATICAMENTE SE NÃO EXISTIR
	 * =====================
	 */
	private void criarUsuario(Pessoa pessoa, PessoaJuridica empresa) {

		Usuario usuario = usuarioRepository.finUserByPessoa(pessoa.getId(), pessoa.getEmail());

		if (usuario != null) {
			return;
		}

		String constraint = usuarioRepository.consultaConstraintAcesso();
		if (constraint != null) {
			jdbcTemplate.execute("begin; alter table usuario_acesso drop constraint " + constraint + "; commit;");
		}

		usuario = new Usuario();
		usuario.setLogin(pessoa.getEmail());

		String senha = "" + Calendar.getInstance().getTimeInMillis();
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));

		usuario.setCreateAt(Calendar.getInstance().getTime());
		usuario.setUpdateAt(Calendar.getInstance().getTime());
		usuario.setPessoa(pessoa);
		usuario.setEmpresa(empresa);

		usuario = usuarioRepository.save(usuario);

		String role = (pessoa instanceof PessoaJuridica) ? RoleUser.ROLE_ADMIN.name() : RoleUser.ROLE_USER.name();
		usuarioRepository.insereAcessoUser(usuario.getId(), role);

		// Envio de email opcional

		StringBuilder mensagemHtml = new StringBuilder();

		mensagemHtml.append("<b>Olá!: " + pessoa.getNome() + " </b>").append("<br/>");
		mensagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b>").append("<br/>");
		mensagemHtml.append("<b>Login: </b>" + pessoa.getEmail()).append("<br/>");
		mensagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
		mensagemHtml.append("Obrigado");

		try { // Fazer o envio de e-mail do login e senha
			sendMailService.enviarEmailHtml("Credencial Criada para acesso a plataforma Loja Virtual Bandampla!",
					mensagemHtml.toString(), pessoa.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * ===================== CONSULTA CEP =====================
	 */
	public CepDTO consultaCep(String cep) throws RestClientException, ExceptionCustom {
		return new RestTemplate()
				.getForEntity("https://viacep.com.br/ws/" + ValidaCEP.cepSemMascara(cep) + "/json/", CepDTO.class)
				.getBody();
	}

	/*
	 * ===================== CONSULTA CNPJ =====================
	 */
	public CnpjDTO consultaCnpj(String cnpj) throws ExceptionCustom {
		if (cnpj.length() != 14) {
			throw new ExceptionCustom("CNPJ informado deve possuir 14 caracteres.");
		}

		if (ValidaCNPJ.isCNPJ(cnpj) == false) {
			throw new ExceptionCustom("CNPJ informado é inválido.");
		}
		return new RestTemplate()
				.getForEntity("https://receitaws.com.br/v1/cnpj/" + ValidaCNPJ.cnpjSemMascara(cnpj), CnpjDTO.class)
				.getBody();
	}

	public List<PessoaJuridica> consultaPessoaJuridicaPorNome(String nome) throws ExceptionCustom {
		if (nome == null || nome.trim().isEmpty())
			throw new ExceptionCustom("Nome não pode estar vazio");
		List<PessoaJuridica> lista = pessoaJuridicaRepository.findAllByNome(nome.trim());
		if (lista.isEmpty())
			throw new ExceptionCustom("Nenhuma pessoa jurídica encontrada com o nome informado.");
		return lista; // ou retorne a lista inteira se quiser
	}

	public List<PessoaJuridica> consultaPessoaJuridicaPorCnpj(String cnpj) throws ExceptionCustom {
		if (cnpj == null || cnpj.trim().isEmpty())
			throw new ExceptionCustom("CNPJ não pode estar vazio");
		List<PessoaJuridica> lista = pessoaJuridicaRepository.findAllByCnpj(cnpj.trim());
		if (lista.isEmpty())
			throw new ExceptionCustom("Nenhuma pessoa jurídica encontrada com o CNPJ informado.");
		return lista; // ou retorne a lista inteira se quiser
	}
}