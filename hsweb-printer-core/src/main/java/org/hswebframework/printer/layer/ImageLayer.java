package org.hswebframework.printer.layer;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * @author zhouhao
 * @since 1.0
 */
@Getter
@Setter
public class ImageLayer extends AbstractLayer {

    private Image image;

    private int width;

    private int height;

    @Override
    protected void doDraw(Graphics2D graphics) {
        if(image==null){
            return;
        }
        graphics.drawImage(image, getX(), getY(), getWidth(), getHeight(), (img, infoflags, x, y, w, h) -> true);
    }
}
