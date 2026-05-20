package com.bandampla.lojavirtual.service;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.UsuarioRepository;

@Service
public class PessoaUserService {

	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;

	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SendMailService sendMailService;

	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) throws ExceptionCustom {

		if (pessoaJuridica == null) {
			throw new ExceptionCustom("Pessoa Juridica não pode ser NULL");
		}

		Optional<PessoaJuridica> pjCnpj = pessoaJuridicaRepository.findByCnpj(pessoaJuridica.getCnpj());
		if (pjCnpj.isPresent() && !pjCnpj.get().getId().equals(pessoaJuridica.getId())) {
			throw new ExceptionCustom("CNPJ já cadastrado no sistema");
		}

		Optional<PessoaJuridica> pjIe = pessoaJuridicaRepository.findByInscricaoEstadual(pessoaJuridica.getInscricaoEstadual());
		if (pjIe.isPresent() && !pjIe.get().getId().equals(pessoaJuridica.getId())) {
			throw new ExceptionCustom("Inscrição Estadual já cadastrada");
		}

		// Adicione esta validação antes do loop for:
		if (pessoaJuridica.getEnderecos() != null) {
			for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
				pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
				pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
			}
		}

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
			usuarioRepository.insereAcessoUser(usuarioPJ.getId(), RoleUser.ROLE_ADMIN);

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

	public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) {
		// TODO Auto-generated method stub
		return null;
	}
}