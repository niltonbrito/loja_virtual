/**
 * 
 */
package com.bandampla.lojavirtual.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.UsuarioRepository;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 27 de abr. de 2026
 */

@Service
public class TarefasAutomatizadaService {

	private static final Logger log = LoggerFactory.getLogger(TarefasAutomatizadaService.class);
	private final UsuarioRepository usuarioRepository;
	private final SendMailService sendMailService;

	public TarefasAutomatizadaService(UsuarioRepository usuarioRepository, SendMailService sendMailService) {
		this.usuarioRepository = usuarioRepository;
		this.sendMailService = sendMailService;
	}

	@Scheduled(initialDelay = 2000, fixedDelay = 86400000) /* Roda a cada 24horas */
	// @Scheduled(cron = "0 0 11 * * *", zone = "America/Sao Paulo") /*Vai rodar
	// todo dia as11 horas da manha horario de Sao Paulo*/
	public void notificarUsuariosComSenhaVencida() {

		List<Usuario> usuarios = usuarioRepository.usuarioSenhaVencida();
		for (Usuario usuario : usuarios) {
			if (usuario.getUpdatedAt() == null || usuario.getLogin() == null) {
				log.warn("Usuário {} ignorado por ausência de updateAt/login", usuario.getId());
				continue;
			}
			String html = "Olá, " + usuario.getPessoa().getNome()
					+ "!<br/>Sua senha possui mais de 90 dias. Atualize-a na Loja Virtual Bandampla.";
			try {
				sendMailService.enviarEmailHtml("Atualização de senha", html, usuario.getLogin());
			} catch (Exception ex) {
				log.error("Falha ao enfileirar e-mail para usuário {}", usuario.getId(), ex);
			}
		}
	}
}