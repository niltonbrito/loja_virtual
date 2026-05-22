package com.bandampla.lojavirtual.service;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bandampla.lojavirtual.dto.request.CepDTO;
import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.Endereco;
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

	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) throws ExceptionCustom {

		if (pessoaJuridica == null) {
			throw new ExceptionCustom("Pessoa Juridica não pode ser NULL");
		}

		Optional<PessoaJuridica> pjCnpj = pessoaJuridicaRepository.findByCnpj(pessoaJuridica.getCnpj().trim());
		if (pjCnpj.isPresent() && !pjCnpj.get().getId().equals(pessoaJuridica.getId())) {
			throw new ExceptionCustom("CNPJ já cadastrado no sistema");
		}

		Optional<PessoaJuridica> pjIe = pessoaJuridicaRepository
				.findByInscricaoEstadual(pessoaJuridica.getInscricaoEstadual());
		if (pjIe.isPresent() && !pjIe.get().getId().equals(pessoaJuridica.getId())) {
			throw new ExceptionCustom("Inscrição Estadual já cadastrada");
		}

		if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				CepDTO cepDTO = consultaCep(ValidaCEP.limpar(pessoaJuridica.getEnderecos().get(p).getCep()));
				if (cepDTO.getCep() == null || cepDTO.getCep().trim().isEmpty()) {
					throw new ExceptionCustom("CEP informado não pode ser vazio ou nullo.");
				}
				pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
				pessoaJuridica.getEnderecos().get(p).setCep(ValidaCEP.limpar(cepDTO.getCep()));
				pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
				pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
				pessoaJuridica.getEnderecos().get(p).setEmpresa(pessoaJuridica);
				pessoaJuridica.getEnderecos().get(p).setPessoa(pessoaJuridica);
				pessoaJuridica.getEnderecos().get(p).setRua(cepDTO.getLogradouro());
				pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
			}

		} else {

			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				Endereco enderecoTemp = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();
				if (!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {

					CepDTO cepDTO = consultaCep(ValidaCEP.limpar(pessoaJuridica.getEnderecos().get(p).getCep()));
					if (cepDTO.getCep() == null || cepDTO.getCep().trim().isEmpty()) {
						throw new ExceptionCustom("CEP informado não pode ser vazio ou nullo.");
					}
					pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
					pessoaJuridica.getEnderecos().get(p).setCep(ValidaCEP.limpar(cepDTO.getCep()));
					pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
					pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
					pessoaJuridica.getEnderecos().get(p).setEmpresa(pessoaJuridica);
					pessoaJuridica.getEnderecos().get(p).setPessoa(pessoaJuridica);
					pessoaJuridica.getEnderecos().get(p).setRua(cepDTO.getLogradouro());
					pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
				}
			}
		}

		// Adicione esta validação antes do loop for:
		/*
		 * if (pessoaJuridica.getEnderecos() != null) { for (int i = 0; i <
		 * pessoaJuridica.getEnderecos().size(); i++) {
		 * pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
		 * pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica); } }
		 */

		pessoaJuridica.setCnpj(ValidaCNPJ.cnpjSemMascara(pessoaJuridica.getCnpj().trim()));
		pessoaJuridica.setEmpresa(pessoaJuridica);
		pessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);

		Usuario usuarioPJ = usuarioRepository.finUserByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());

		if (usuarioPJ == null) {
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null) {
				jdbcTemplate.execute("begin; alter table usuario_acesso drop constraint " + constraint + "; commit;");
			}
			usuarioPJ = new Usuario();
			usuarioPJ.setLogin(pessoaJuridica.getEmail());

			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
			usuarioPJ.setSenha(senhaCriptografada);

			usuarioPJ.setCreateAt(Calendar.getInstance().getTime());
			usuarioPJ.setUpdateAt(Calendar.getInstance().getTime());
			usuarioPJ.setEmpresa(pessoaJuridica);
			usuarioPJ.setPessoa(pessoaJuridica);

			usuarioPJ = usuarioRepository.save(usuarioPJ);
			usuarioRepository.insereAcessoUser(usuarioPJ.getId(), RoleUser.ROLE_ADMIN.name());

			StringBuilder mensagemHtml = new StringBuilder();
			mensagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b>").append("<br/>");
			mensagemHtml.append("<b>Login: </b>" + pessoaJuridica.getEmail()).append("<br/>");
			mensagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
			mensagemHtml.append("Obrigado");
			try {
				/* Fazer o envio de e-mail do login e senha */
				sendMailService.enviarEmailHtml("Credencial Criada para acesso a plataforma Loja Virtual Bandampla!",
						mensagemHtml.toString(), pessoaJuridica.getEmail());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return pessoaJuridica;

	}

	public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) throws ExceptionCustom {

		if (pessoaFisica == null) {
			throw new ExceptionCustom("Pessoa Fisica não pode ser NULL");
		}

		Optional<PessoaFisica> pessoaOpt = pessoaFisicaRepository.findByCpf(pessoaFisica.getCpf().trim());
		if (pessoaOpt.isPresent() && !pessoaOpt.get().getId().equals(pessoaFisica.getId())) {
			throw new ExceptionCustom("CPF já cadastrado no sistema");
		}
		
		
		if (pessoaFisica.getId() == null || pessoaFisica.getId() <= 0) {
			for (int p = 0; p < pessoaFisica.getEnderecos().size(); p++) {
				CepDTO cepDTO = consultaCep(ValidaCEP.limpar(pessoaFisica.getEnderecos().get(p).getCep()));
				if (cepDTO.getCep() == null || cepDTO.getCep().trim().isEmpty()) {
					throw new ExceptionCustom("CEP informado não pode ser vazio ou nullo.");
				}
				pessoaFisica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
				pessoaFisica.getEnderecos().get(p).setCep(ValidaCEP.limpar(cepDTO.getCep()));
				pessoaFisica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
				pessoaFisica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
				pessoaFisica.getEnderecos().get(p).setEmpresa(pessoaFisica.getEmpresa());
				pessoaFisica.getEnderecos().get(p).setPessoa(pessoaFisica);
				pessoaFisica.getEnderecos().get(p).setRua(cepDTO.getLogradouro());
				pessoaFisica.getEnderecos().get(p).setUf(cepDTO.getUf());
			}

		} else {

			for (int p = 0; p < pessoaFisica.getEnderecos().size(); p++) {
				Endereco enderecoTemp = enderecoRepository.findById(pessoaFisica.getEnderecos().get(p).getId()).get();
				if (!enderecoTemp.getCep().equals(pessoaFisica.getEnderecos().get(p).getCep())) {

					CepDTO cepDTO = consultaCep(ValidaCEP.limpar(pessoaFisica.getEnderecos().get(p).getCep()));
					if (cepDTO.getCep() == null || cepDTO.getCep().trim().isEmpty()) {
						throw new ExceptionCustom("CEP informado não pode ser vazio ou nullo.");
					}
					pessoaFisica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
					pessoaFisica.getEnderecos().get(p).setCep(ValidaCEP.limpar(cepDTO.getCep()));
					pessoaFisica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
					pessoaFisica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
					pessoaFisica.getEnderecos().get(p).setEmpresa(pessoaFisica.getEmpresa());
					pessoaFisica.getEnderecos().get(p).setPessoa(pessoaFisica);
					pessoaFisica.getEnderecos().get(p).setRua(cepDTO.getLogradouro());
					pessoaFisica.getEnderecos().get(p).setUf(cepDTO.getUf());
				}
			}
		}

		// Adicione esta validação antes do loop for:
		/*
		 * if (pessoaJuridica.getEnderecos() != null) { for (int i = 0; i <
		 * pessoaJuridica.getEnderecos().size(); i++) {
		 * pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
		 * pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica); } }
		 */

		pessoaFisica.setCpf(ValidaCPF.cpfSemMascara(pessoaFisica.getCpf().trim()));
		pessoaFisica.setEmpresa(pessoaFisica.getEmpresa());
		pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);

		Usuario usuarioPF = usuarioRepository.finUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());

		if (usuarioPF == null) {
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null) {
				jdbcTemplate.execute("begin; alter table usuario_acesso drop constraint " + constraint + "; commit;");
			}
			usuarioPF = new Usuario();
			usuarioPF.setLogin(pessoaFisica.getEmail());

			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
			usuarioPF.setSenha(senhaCriptografada);

			usuarioPF.setCreateAt(Calendar.getInstance().getTime());
			usuarioPF.setUpdateAt(Calendar.getInstance().getTime());
			usuarioPF.setEmpresa(pessoaFisica.getEmpresa());
			usuarioPF.setPessoa(pessoaFisica);

			usuarioPF = usuarioRepository.save(usuarioPF);
			usuarioRepository.insereAcessoUser(usuarioPF.getId(), RoleUser.ROLE_USER.name());

			StringBuilder mensagemHtml = new StringBuilder();
			mensagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b>").append("<br/>");
			mensagemHtml.append("<b>Login: </b>" + pessoaFisica.getEmail()).append("<br/>");
			mensagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
			mensagemHtml.append("Obrigado");
			try {
				/* Fazer o envio de e-mail do login e senha */
				sendMailService.enviarEmailHtml("Credencial Criada para acesso a plataforma Loja Virtual Bandampla!",
						mensagemHtml.toString(), pessoaFisica.getEmail());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pessoaFisica;
	}

	public CepDTO consultaCep(String cep) {
		return new RestTemplate()
				.getForEntity("https://viacep.com.br/ws/" + ValidaCEP.limpar(cep) + "/json/", CepDTO.class).getBody();
	}
}