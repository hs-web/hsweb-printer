package org.hsweb.printer.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Hashtable;

public class SemacodeTool {

	/**
	 * 将指定的内容生成成二维码
	 *
	 * @param content 将要生成二维码的内容
	 * @return 返回生成好的二维码事件
	 * @throws com.google.zxing.WriterException WriterException异常
	 */

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;


	public static BitMatrix CreateTwoDCodeHasWhite(String content, int size,Boolean white) {
		size = size < 10 ? 10 : size;
		Hashtable hints = new Hashtable();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		BitMatrix matrix = null;
		try {
			matrix = new MultiFormatWriter().encode(content,
					BarcodeFormat.QR_CODE, size, size, hints);
		} catch (WriterException e) {
			throw new RuntimeException(e);
		}
		if(white==null||!white){
			return deleteWhite(matrix);
		}
		return matrix;
	}
	public static BitMatrix CreateTwoDCode(String content, int size) {

		return CreateTwoDCodeHasWhite(content,size,false);
	}
	public static BitMatrix deleteWhite(BitMatrix matrix){
		int[] rec = matrix.getEnclosingRectangle();
		int resWidth = rec[2] + 1;
		int resHeight = rec[3] + 1;

		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
		resMatrix.clear();
		for (int i = 0; i < resWidth; i++) {
			for (int j = 0; j < resHeight; j++) {
				if (matrix.get(i + rec[0], j + rec[1]))
					resMatrix.set(i, j);
			}
		}
		return resMatrix;
	}


	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static BufferedImage toBufferedImage(String xxx) {
		return toBufferedImage(xxx, 300);
	}

	public static BufferedImage toBufferedImage(String xxx, int size) {
		return toBufferedImage(CreateTwoDCode(xxx, size));
	}


	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}

	public static ByteArrayOutputStream getStream(String xxx) {
		return getStream(xxx, 300);
	}


	public static ByteArrayOutputStream getStream(String xxx, int size,Boolean white) {
		BufferedImage bufferedImage = toBufferedImage(CreateTwoDCodeHasWhite(xxx, size,white));
		//ImageIO.write(BufferedImage image,String format,OutputStream out);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "jpg", baos);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return baos;
	}
	public static ByteArrayOutputStream getStream(String xxx, int size) {
		BufferedImage bufferedImage = toBufferedImage(CreateTwoDCode(xxx, size));
		//ImageIO.write(BufferedImage image,String format,OutputStream out);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "jpg", baos);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return baos;
	}

	public static ByteArrayInputStream getInputStream(String xxx) {
		return getInputStream(xxx, 300);
	}

	public static ByteArrayInputStream getInputStream(String xxx, int size) {
		ByteArrayOutputStream arrayOutputStream = getStream(xxx, size);
		return new ByteArrayInputStream(arrayOutputStream.toByteArray());
	}

	public static String getBase64(String xxx) {

		return getBase64(xxx, 300);
	}

	public static String getBase64(String xxx, int size) {
		ByteArrayOutputStream baos = getStream(xxx, size);
		return "data:image/jpg;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
	}
	/*public static String getBase64Logo(String xxx,String url) throws WriterException, IOException {
		ByteArrayOutputStream outputStream=HttpBase.baseHttpFileOutputStream(url);
		ImageIcon imageIcon=new ImageIcon(outputStream.toByteArray());
		BufferedImage bufferedImage2=ImageUtil.markImageByIcon(imageIcon.getImage(), toBufferedImage(xxx), 0, 1f,0.25f);
		//ImageIO.write(BufferedImage image,String format,OutputStream out);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage2, "jpg", baos);
		return "data:image/jpg;base64,"+encoder.encodeBuffer(baos.toByteArray()).trim();
	}

	public static void main(String[] args) throws IOException, WriterException {
		String s=getBase64Logo("http://www.baidu.com", "http://wx.qlogo.cn/mmopen/pbcaF3N4ib9AXibgoDnHcvuAykmtH2Q5SicsUIFzAdiaXtrDGloPsWDciaadyguRkcNzKZEKkXhKic0QqteRFYicIvQITbEx98T1RTp/0");
		System.out.println(s);
	}*/
	public static void main(String[] args) {

	}

}
