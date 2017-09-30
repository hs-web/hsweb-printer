package org.hswebframework.printer.executor.painter;

import org.hswebframework.printer.designer.Direction;
import org.hswebframework.printer.designer.Line;

import java.awt.*;

/**
 * @author zhouhao
 * @since
 */
public class LinePainter implements ObjectPainter {

    private Line line;

    public LinePainter(Line line) {
        this.line = line;
    }

    @Override
    public void painting(Graphics graphics) {
        Color oldColor = graphics.getColor();

        Color color = new Color(line.getColor().getR(), line.getColor().getG(), line.getColor().getB());
        graphics.setColor(color);

        if (line.getDirection() == Direction.DOWN) {
            graphics.drawLine(line.getX(), line.getY(), line.getX(), line.getY() + line.getLength());
        } else {
            graphics.drawLine(line.getX(), line.getY(), line.getX() + line.getLength(), line.getY());
        }
        graphics.setColor(oldColor);
    }
}
