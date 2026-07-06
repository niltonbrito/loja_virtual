/**
 * 
 */
package com.bandampla.lojavirtual.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

/**
 * @author: Nilton Brito
 * @Email: <nilton.brito@outlook.com>
 * @Data: 4 de jul. de 2026
 */

public class ImageUtil {

	public static String gerarMiniatura(String base64Original) throws Exception {

		String base64 = base64Original;

		if (base64.startsWith("data:image")) {
			base64 = base64.split(",")[1];
		}

		byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64);

		BufferedImage original = ImageIO.read(new ByteArrayInputStream(imageBytes));

		if (original == null) {
			throw new Exception("Imagem inválida.");
		}

		int largura = 800;
		int altura = 600;

		BufferedImage miniatura = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = miniatura.createGraphics();
		g.drawImage(original, 0, 0, largura, altura, null);
		g.dispose();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(miniatura, "png", baos);

		return "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
	}
}
