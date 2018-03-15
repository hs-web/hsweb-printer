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

    @Override
    protected void doDraw(Graphics2D graphics) {
        graphics.drawRect(getX(), getY(), getWidth(), getHeight());
    }
}
