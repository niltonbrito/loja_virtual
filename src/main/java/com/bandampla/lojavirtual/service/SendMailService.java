package com.bandampla.lojavirtual.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SendMailService {

	@Value("${app.mail.username}")
	private String userName;

	@Value("${app.mail.password}")
	private String senha;
	
	//private final String userName = "tinhosopc@gmail.com";
	//private String senha = "ygls nkbf szhz feeq";

	@Async
	public void enviarEmailHtml(String assunto, String mensagem, String emailDestino) throws UnsupportedEncodingException, MessagingException {
		Properties properties = new Properties();

		/* Parâmentros de conexão com servidor Gmail */

		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "false"); // Nome correto da propriedade
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.ssl.trust", "*");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});
		session.setDebug(true);
		
		Address[] toUser = InternetAddress.parse(emailDestino);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, "Nilton Brito - Dev Java", "UTF-8"));
		message.setRecipients(Message.RecipientType.TO, toUser);
		message.setSubject(assunto);
		message.setContent(mensagem,"text/html; charset=utf-8");
		
		Transport.send(message);
		
	}
}
