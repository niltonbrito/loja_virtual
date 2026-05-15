package com.bandampla.lojavirtual.service;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.PessoaRepository;
import com.bandampla.lojavirtual.repository.UsuarioRepository;

@Service
public class PessoaUserService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public PessoaJuridica save(PessoaJuridica pessoaJuridica) throws ExceptionCustom {

		if (pessoaJuridica == null) {
			throw new ExceptionCustom("Pessoa Juridica não pode ser NULL");
		}

		if (pessoaJuridica.getId() == null) {
			Optional<PessoaJuridica> pessoaJuridicas = pessoaRepository.findByCnpj(pessoaJuridica.getCnpj());
			if (!pessoaJuridicas.isEmpty()) {
				throw new ExceptionCustom("Já existe Pessoa Juridica com este CNPJ: " + pessoaJuridica.getCnpj());
			}
		}

		for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
			pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
			pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
		}

		pessoaJuridica = pessoaRepository.save(pessoaJuridica);
		
		Usuario usuarioPJ = usuarioRepository.finUserByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());

		if (usuarioPJ == null) {
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null) {
				jdbcTemplate.execute("begin; alter table usuario_acesso drop constraint " + constraint + "; commit;");
			}
			usuarioPJ = new Usuario();
			usuarioPJ.setCreateAt(Calendar.getInstance().getTime());
			usuarioPJ.setEmpresa(pessoaJuridica);
			usuarioPJ.setPessoa(pessoaJuridica);
			usuarioPJ.setLogin(pessoaJuridica.getEmail());

			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
			usuarioPJ.setSenha(senhaCriptografada);

			usuarioPJ = usuarioRepository.save(usuarioPJ);
			usuarioRepository.insereAcessoPj(usuarioPJ.getId());

			/* Fazer o envio de e-mail do login e senha */

		}

		return pessoaJuridica;

	}

	/*
	 * public void delete(Acesso acesso) {
	 * acessoRepository.deleteById(acesso.getId()); }
	 * 
	 * public void deleteById(Long id) { acessoRepository.deleteById(id); }
	 * 
	 * public Acesso buscarById(Long id) throws ExceptionCustom {
	 * 
	 * Acesso acesso = acessoRepository.findById(id).orElse(null); if (acesso ==
	 * null) { throw new ExceptionCustom("Não encontrou Acesso com o código: " +
	 * id); } return acesso; }
	 * 
	 * public List<Acesso> buscarPorDescricao(String desc) { return
	 * acessoRepository.buscarAcessoDesc(desc); }
	 */

}
