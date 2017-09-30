package org.hswebframework.printer.executor.painter;

import org.hswebframework.printer.designer.Label;

import java.awt.*;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public class LabelPainter extends AbstractObjectPainter<Label> {

    public LabelPainter(Label label) {
        super(label);
    }

    @Override
    public void painting(Graphics graphics) {
        Color oldColor = graphics.getColor();
        Color color = new Color(object.getColor().getR(), object.getColor().getG(), object.getColor().getB());
        graphics.setColor(color);

        graphics.drawString(object.getText(), object.getX(), object.getY());

        graphics.setColor(oldColor);
    }
}
