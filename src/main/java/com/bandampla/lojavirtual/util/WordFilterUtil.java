/**
 * 
 */
package com.bandampla.lojavirtual.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 23 de jul. de 2026
 */

@Component // 🔥 Transforma em um componente gerenciado pelo Spring
public class WordFilterUtil {

	// Mantém a lista em cache na memória RAM para alta performance
	private final List<String> palavrasBanidas = new ArrayList<>();

	/**
	 * Método executado automaticamente pelo Spring LOGO APÓS a inicialização da
	 * aplicação. Ele faz a leitura do arquivo .txt uma única vez.
	 */
	@PostConstruct
	public void carregarPalavrasBanidas() {
		try {
			// Carrega o arquivo de dentro da pasta src/main/resources com suporte a
			// caracteres especiais (UTF-8)
			ClassPathResource resource = new ClassPathResource("palavras_banidas.txt");

			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
				// Ignora linhas vazias ou comentários
				this.palavrasBanidas.addAll(
						reader.lines().map(String::trim).filter(linha -> !linha.isEmpty() && !linha.startsWith("#"))
								.map(String::toLowerCase).collect(Collectors.toList()));
			}
			System.out.println("====== [WordFilterUtil] " + palavrasBanidas.size()
					+ " palavras banidas carregadas com sucesso! ======");
		} catch (Exception e) {
			// Fallback de segurança: Caso o arquivo suma ou dê erro, carrega uma lista
			// padrão para a API não quebrar
			System.err.println(
					"====== [WordFilterUtil] ERRO ao carregar arquivo de palavras banidas. Usando fallback de segurança. ======");
			this.palavrasBanidas.addAll(Arrays.asList("lixo", "porcaria", "fuder", "caralho", "merda"));
		}
	}

	/**
	 * Abordagem 1: Bloqueia o cadastro se detectar xingamentos (mesmo burlados).
	 */
	public boolean contemPalavraProibida(String texto) {
		if (texto == null || texto.trim().isEmpty()) {
			return false;
		}

		String textoMinusculo = texto.toLowerCase();

		for (String palavra : palavrasBanidas) {
			String regex = construirRegexAntiBurla(palavra);
			Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(textoMinusculo);

			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Abordagem 2: Aceita o texto, mas substitui os xingamentos por asteriscos.
	 */
	public String mascararPalavrasProibidas(String texto) {
		if (texto == null || texto.trim().isEmpty()) {
			return texto;
		}

		String textoMascarado = texto;

		for (String palavra : palavrasBanidas) {
			String regex = construirRegexAntiBurla(palavra);
			Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(textoMascarado);

			char[] asteriscos = new char[palavra.length()];
			Arrays.fill(asteriscos, '*');
			String mascara = new String(asteriscos);

			textoMascarado = matcher.replaceAll(mascara);
		}

		return textoMascarado;
	}

	/**
	 * Constrói a expressão regular que intercepta letras separadas por espaços,
	 * pontos ou traços.
	 */
	private String construirRegexAntiBurla(String palavra) {
		StringBuilder sb = new StringBuilder();
		char[] letras = palavra.toCharArray();

		for (int i = 0; i < letras.length; i++) {
			sb.append(Pattern.quote(String.valueOf(letras[i])));
			if (i < letras.length - 1) {
				sb.append("[\\s\\p{Punct}]*");
			}
		}
		return sb.toString();
	}
}