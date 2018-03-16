package org.hswebframework.printer.layer;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;

/**
 * @author zhouhao
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LineLayer extends AbstractLayer {
    private int endY;
    private int endX;

    @Override
    protected void doDraw(Graphics2D graphics) {
        graphics.drawLine(getX(), getY(), getEndX(), getEndY());
    }
}
