package com.bandampla.lojavirtual.service.validation;

import org.springframework.stereotype.Component;

import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.Usuario;

@Component
public class UsuarioTenantValidator {

	public void validar(Usuario usuario) {

		if (usuario == null) {
			throw new ExceptionCustom("Usuário não pode ser nulo.");
		}

		if (usuario.getAcessos() == null || usuario.getAcessos().isEmpty()) {

			throw new ExceptionCustom("Usuário deve possuir ao menos um acesso.");
		}

		boolean superAdmin = usuario.possuiRole(RoleUser.ROLE_SUPER_ADMIN);

		if (superAdmin) {
			validarSuperAdmin(usuario);
			return;
		}

		validarUsuarioTenant(usuario);
	}

	private void validarSuperAdmin(Usuario usuario) {

		if (usuario.getEmpresa() != null) {
			throw new ExceptionCustom("SUPER_ADMIN não deve estar vinculado " + "a uma empresa cliente.");
		}

		if (usuario.getPessoa() == null) {
			throw new ExceptionCustom("SUPER_ADMIN deve estar vinculado " + "a uma pessoa física.");
		}
	}

	private void validarUsuarioTenant(Usuario usuario) {

		if (usuario.getEmpresa() == null) {
			throw new ExceptionCustom("Usuários de tenant devem estar " + "vinculados a uma empresa.");
		}

		if (usuario.getPessoa() == null) {
			throw new ExceptionCustom("Usuário deve estar vinculado " + "a uma pessoa.");
		}

		if (usuario.getPessoa() instanceof PessoaFisica) {

			PessoaFisica pessoaFisica = (PessoaFisica) usuario.getPessoa();

			if (pessoaFisica.getEmpresa() == null) {
				throw new ExceptionCustom("A pessoa física do usuário deve " + "estar vinculada à empresa.");
			}

			if (!pessoaFisica.getEmpresa().getId().equals(usuario.getEmpresa().getId())) {

				throw new ExceptionCustom("A pessoa física e o usuário devem " + "pertencer à mesma empresa.");
			}
		}
	}
}