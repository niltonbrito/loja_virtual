/**
 * 
 */
package com.bandampla.lojavirtual.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.repository.PessoaRepository;
import com.bandampla.lojavirtual.repository.UsuarioRepository;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Service
public class PessoaUserService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

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
		usuarioRepository.finUserByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());
		return pessoaRepository.save(pessoaJuridica);
		
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
