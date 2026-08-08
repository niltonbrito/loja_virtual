package com.bandampla.lojavirtual.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class TokenSeguroService {

	private static final int TOKEN_BYTES = 32;

	private final SecureRandom secureRandom = new SecureRandom();

	public String gerarToken() {

		byte[] bytes = new byte[TOKEN_BYTES];

		secureRandom.nextBytes(bytes);

		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

	public String gerarHash(String token) {

		if (token == null || token.trim().isEmpty()) {
			throw new IllegalArgumentException("Token não pode ser vazio.");
		}

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

			byte[] hash = messageDigest.digest(token.getBytes(StandardCharsets.UTF_8));

			return converterParaHexadecimal(hash);

		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Algoritmo SHA-256 não disponível.", e);
		}
	}

	private String converterParaHexadecimal(byte[] bytes) {

		StringBuilder resultado = new StringBuilder(bytes.length * 2);

		for (byte valor : bytes) {
			resultado.append(String.format("%02x", valor & 0xff));
		}

		return resultado.toString();
	}
}