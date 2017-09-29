package org.hswebframework.printer.executor;

import org.hswebframework.printer.designer.Pager;

import java.awt.*;

/**
 * @author zhouhao
 * @since
 */
public interface PagerGraphicsService {
    void doGraphics(Pager pager, Graphics graphics);
}
