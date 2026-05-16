/**
 * 
 */
package com.bandampla.lojavirtual.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.UsuarioRepository;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Service
public class TarefasAutomatizadaService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private SendMailService sendMailService;

	@Scheduled(initialDelay = 2000, fixedDelay = 86400000) /* Roda a cada 24horas */
	// @Scheduled(cron = "0 0 11 * * *", zone = "America/Sao Paulo") /*Vai rodar
	// todo dia as11 horas da manha horario de Sao Paulo*/
	public void notificarUserTrocaSenha()
			throws UnsupportedEncodingException, MessagingException, InterruptedException, ExceptionCustom {

		List<Usuario> usuarios = usuarioRepository.usuarioSenhaVencida();

		for (Usuario usuario : usuarios) {
			if (usuario.getUpdateAt() == null) {
				throw new ExceptionCustom("O campo atualização Data Senha nao pode ser nulo ");
			}
			StringBuilder mensagemHtml = new StringBuilder();
			mensagemHtml.append("Olá, ").append(usuario.getPessoa().getNome() + "!").append("<br/>");
			mensagemHtml.append("Está na hora de trocar a sua senha, já passou 90 dias de validade.").append("<br/>");
			mensagemHtml.append("Troque sua senha da loja virtual Bandampla").append("<br/><br/>");

			sendMailService.enviarEmailHtml("Troca de senha!", mensagemHtml.toString(), usuario.getLogin());

			Thread.sleep(3000);
		}

	}

}
