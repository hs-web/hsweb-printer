package org.hswebframework.printer.executor.painter;

import org.hswebframework.printer.designer.PrintObject;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public abstract class AbstractObjectPainter<T extends PrintObject> implements ObjectPainter {

    T object;

    public AbstractObjectPainter(T object) {
        this.object = object;
    }
}
