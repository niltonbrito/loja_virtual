package com.bandampla.lojavirtual.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import com.bandampla.lojavirtual.exception.ExceptionCustom;

public class ImageUtil {

	/**
	 * Processa uma string Base64, limpa seus dados e gera os bytes originais e da
	 * miniatura.
	 */
	public static ResultadoImagem processarImagem(String base64Original) throws IOException {
		if (base64Original == null || base64Original.trim().isEmpty()) {
			throw new ExceptionCustom("A string da imagem não pode estar vazia.");
		}

		String base64 = base64Original;

		// 1. Isolar o cabeçalho data:image se ele existir
		if (base64.contains(",")) {
			String[] partes = base64.split(",");
			if (partes.length > 1) {
				base64 = partes[1];
			}
		}

		// 2. Sanitizar quebras de linha ou espaços gerados no envio do JSON
		base64 = base64.replaceAll("\\s+", "");

		// 3. Converter a string Base64 para os bytes originais da foto
		byte[] bytesOriginal = DatatypeConverter.parseBase64Binary(base64);

		// 4. Ler como BufferedImage para manipulação gráfica
		BufferedImage imagemOriginal = ImageIO.read(new ByteArrayInputStream(bytesOriginal));
		if (imagemOriginal == null) {
			throw new IOException("Formato de imagem inválido ou corrompido.");
		}

		// 5. Desenhar a miniatura (Thumbnail) em tamanho 800x600
		int largura = 800;
		int altura = 600;
		BufferedImage miniaturaRedimensionada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = miniaturaRedimensionada.createGraphics();
		g.drawImage(imagemOriginal, 0, 0, largura, altura, null);
		g.dispose();

		// 6. Extrair os bytes puros da miniatura gerada
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(miniaturaRedimensionada, "png", baos);
		byte[] bytesMiniatura = baos.toByteArray();

		// 7. Liberar recursos de memória RAM (Java 11)
		imagemOriginal.flush();
		miniaturaRedimensionada.flush();
		baos.close();

		return new ResultadoImagem(bytesOriginal, bytesMiniatura);
	}

	/**
	 * Estrutura de transferência para carregar os dois arrays de bytes juntos.
	 */
	public static class ResultadoImagem {
		private final byte[] bytesOriginal;
		private final byte[] bytesMiniatura;

		public ResultadoImagem(byte[] bytesOriginal, byte[] bytesMiniatura) {
			this.bytesOriginal = bytesOriginal;
			this.bytesMiniatura = bytesMiniatura;
		}

		public byte[] getBytesOriginal() {
			return bytesOriginal;
		}

		public byte[] getBytesMiniatura() {
			return bytesMiniatura;
		}
	}
}
