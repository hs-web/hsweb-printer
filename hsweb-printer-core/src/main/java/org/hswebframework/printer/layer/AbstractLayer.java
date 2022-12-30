package org.hswebframework.printer.layer;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hswebframework.printer.Layer;
import org.hswebframework.printer.PrinterUtils;

import java.awt.*;

/**
 * @author zhouhao
 * @since 1.0
 */
@Getter
@Setter
@Slf4j
public abstract class AbstractLayer implements Layer {

    private Color color = Color.BLACK;

    private Font font = PrinterUtils.defaultFont;

    private int x;

    private int y;

    private Stroke stroke;

    protected abstract void doDraw(Graphics2D graphics);

    @Override
    public final void draw(Graphics2D graphics) {
        Color oldColor = graphics.getColor();
        Font oldFont = graphics.getFont();
        Stroke oldStroke = graphics.getStroke();
        if (stroke != null) {
            graphics.setStroke(stroke);
        }
        graphics.setColor(getColor());
        if (font != null) {
            graphics.setFont(font);
        }
        try {
            doDraw(graphics);
        } catch (Exception e) {
            log.error("绘制打印内容失败:{}", this, e);
        } finally {
            graphics.setColor(oldColor);
            graphics.setFont(oldFont);
            graphics.setStroke(oldStroke);
        }

    }
}
