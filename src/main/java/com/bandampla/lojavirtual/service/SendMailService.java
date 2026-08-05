package com.bandampla.lojavirtual.service;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SendMailService {

	private final JavaMailSender mailSender;
	private final String remetente;

	public SendMailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String remetente) {
		this.mailSender = mailSender;
		this.remetente = remetente;
	}

	@Async("applicationTaskExecutor")
	public void enviarEmailHtml(String assunto, String mensagem, String emailDestino) throws MessagingException {
		MimeMessage mime = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mime, false, StandardCharsets.UTF_8.name());
		helper.setFrom(remetente);
		helper.setTo(emailDestino);
		helper.setSubject(assunto);
		helper.setText(mensagem, true);
		mailSender.send(mime);
	}
}