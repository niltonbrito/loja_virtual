/**
 * 
 */
package com.bandampla.lojavirtual.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.bandampla.lojavirtual.repository.PalavraProibidaRepository;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 23 de jul. de 2026
 */

@Component
public class WordFilterUtil {

	private final PalavraProibidaRepository palavraProibidaRepository;

	public WordFilterUtil(PalavraProibidaRepository palavraProibidaRepository) {
		this.palavraProibidaRepository = palavraProibidaRepository;
	}

	// Cache thread-safe em memória RAM para velocidade de microssonsegundos
	private final List<String> palavrasBanidas = Collections.synchronizedList(new ArrayList<>());

	/**
	 * Inicializa o cache carregando os dados do banco de dados uma única vez no
	 * boot.
	 */
	@PostConstruct
	public void inicializarCache() {
		recarregarCache();
	}

	/**
	 * Método público que sincroniza os dados da memória com os dados do banco.
	 */
	public void recarregarCache() {
		try {
			synchronized (palavrasBanidas) {
				palavrasBanidas.clear();
				List<String> termosDoBanco = palavraProibidaRepository.buscarTodosOsTermosLimpas();
				if (termosDoBanco != null) {
					palavrasBanidas.addAll(termosDoBanco);
				}
			}
			System.out.println("====== [Cache WordFilter] " + palavrasBanidas.size()
					+ " termos proibidos sincronizados do banco! ======");
		} catch (Exception e) {
			System.err.println("====== [Cache WordFilter] Erro crítico ao conectar no banco para carregar termos: "
					+ e.getMessage());
		}
	}

	/**
	 * Verifica se o texto possui alguma palavra ofensiva camuflada ou direta e
	 * bloqueia o cadastro se detectar xingamentos (mesmo burlados).
	 */
	public boolean contemPalavraProibida(String texto) {
		if (texto == null || texto.trim().isEmpty()) {
			return false;
		}

		String textoMinusculo = texto.toLowerCase();

		// Clona a lista para evitar concorrência de leitura durante a varredura
		List<String> termosAtuais = new ArrayList<>(palavrasBanidas);

		for (String palavra : termosAtuais) {
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
	 * Aceita o texto, mas substitui os xingamentos por asteriscos.
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