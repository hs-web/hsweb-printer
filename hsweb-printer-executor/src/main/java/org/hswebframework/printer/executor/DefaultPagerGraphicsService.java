package org.hswebframework.printer.executor;

import org.hswebframework.printer.designer.Pager;
import org.hswebframework.printer.designer.PrintObject;
import org.hswebframework.printer.executor.painter.DefaultObjectPainterFactory;
import org.hswebframework.printer.executor.painter.ObjectPainterFactory;

import java.awt.*;

/**
 * @author zhouhao
 * @since
 */
public class DefaultPagerGraphicsService implements PagerGraphicsService {

    private ObjectPainterFactory objectPainterFactory = DefaultObjectPainterFactory.instance;

    public void setObjectPainterFactory(ObjectPainterFactory objectPainterFactory) {
        this.objectPainterFactory = objectPainterFactory;
    }

    @Override
    public void doGraphics(Pager pager, Graphics graphics) {
        for (PrintObject printObject : pager.getPrintObjects()) {
            objectPainterFactory
                    .createPainter(printObject)
                    .painting(graphics);
        }
    }
}
