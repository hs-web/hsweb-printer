package org.hswebframework.printer;

import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * @author zhouhao
 * @since 1.0
 */
public class ImageUtils {

    /**
     * 设置白色背景透明
     *
     * @param inputImage 输入图片
     * @return 1.0
     */
    @SneakyThrows
    public static BufferedImage setBackgroundTransparency(BufferedImage inputImage) {
        ImageIcon imageIcon = new ImageIcon(inputImage);
        BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
        g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
        int alpha = 0;
        for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
            for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                int rgb = bufferedImage.getRGB(j2, j1);
                int R = (rgb & 0xff0000) >> 16;
                int G = (rgb & 0xff00) >> 8;
                int B = (rgb & 0xff);
                if (((255 - R) < 30) && ((255 - G) < 30) && ((255 - B) < 30)) {
                    rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
                }
                bufferedImage.setRGB(j2, j1, rgb);
            }
        }
        g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
        return bufferedImage;
    }
}
