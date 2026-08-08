package com.bandampla.lojavirtual.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.bandampla.lojavirtual.config.OnboardingProperties;
import com.bandampla.lojavirtual.event.EmailConfirmacaoOnboardingEvent;
import com.bandampla.lojavirtual.service.SendMailService;

@Component
public class EmailConfirmacaoOnboardingListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailConfirmacaoOnboardingListener.class);

	private final SendMailService sendMailService;

	private final OnboardingProperties onboardingProperties;

	public EmailConfirmacaoOnboardingListener(SendMailService sendMailService,
			OnboardingProperties onboardingProperties) {

		this.sendMailService = sendMailService;
		this.onboardingProperties = onboardingProperties;
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void enviar(EmailConfirmacaoOnboardingEvent event) {

		try {
			String link = onboardingProperties.getConfirmationUrl() + "?token=" + event.getToken();

			String assunto = "Confirme seu cadastro";

			String mensagem = "Olá, " + event.getNomeResponsavel() + ".\n\n" + "Confirme seu e-mail para continuar "
					+ "o cadastro da empresa:\n\n" + link + "\n\n" + "O link expira em " + event.getExpiraEm() + ".\n\n"
					+ "Caso você não tenha iniciado este " + "cadastro, ignore esta mensagem.";

			/*
			 * Ajuste o nome deste método conforme o método existente no seu
			 * SendMailService.
			 */
			sendMailService.enviarEmailTexto(event.getEmail(), assunto, mensagem);

		} catch (Exception e) {
			LOGGER.error("Não foi possível enviar o e-mail " + "de confirmação para {}.", event.getEmail(), e);
		}
	}
}