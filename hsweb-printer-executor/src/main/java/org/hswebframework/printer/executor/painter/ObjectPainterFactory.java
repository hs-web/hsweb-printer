package org.hswebframework.printer.executor.painter;

import org.hswebframework.printer.designer.PrintObject;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public interface ObjectPainterFactory {

    <T extends PrintObject> ObjectPainter createPainter(T printObject);
}
