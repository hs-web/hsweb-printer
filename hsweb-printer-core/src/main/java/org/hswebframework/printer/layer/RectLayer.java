package org.hswebframework.printer.layer;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;

/**
 * @author zhouhao
 * @since
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RectLayer extends AbstractLayer {
    private int width;

    private int height;
    private Color backgroundColor = null;

    @Override
    protected void doDraw(Graphics2D graphics) {
        graphics.drawRect(getX(), getY(), getWidth(), getHeight());
        if (null != backgroundColor) {
            Color temp = graphics.getColor();
            graphics.setColor(backgroundColor);
            graphics.fillRect(getX(), getY(), getWidth(), getHeight());
            graphics.setBackground(temp);
        }
    }
}
