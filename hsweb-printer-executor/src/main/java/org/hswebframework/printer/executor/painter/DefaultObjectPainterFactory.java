package org.hswebframework.printer.executor.painter;

import org.hswebframework.printer.designer.Label;
import org.hswebframework.printer.designer.Line;
import org.hswebframework.printer.designer.PrintObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public class DefaultObjectPainterFactory implements ObjectPainterFactory {

    private static final Map<Class, Function<PrintObject, ObjectPainter>> objectPainterMap = new HashMap<>();

    public static final DefaultObjectPainterFactory instance = new DefaultObjectPainterFactory();

    static {
        registerPainter(Line.class, () -> LinePainter::new);

        registerPainter(Label.class, () -> LabelPainter::new);
    }

    private DefaultObjectPainterFactory() {

    }

    private static DefaultObjectPainterFactory getInstance() {
        return instance;
    }

    @SuppressWarnings("all")
    public static <T extends PrintObject> void registerPainter(Class<T> tClass, Supplier<Function<T, ObjectPainter>> supplier) {
        objectPainterMap.put(tClass, (Function) supplier.get());
    }

    @Override
    public ObjectPainter createPainter(PrintObject printObject) {
        Function<PrintObject, ObjectPainter> function = objectPainterMap.get(printObject.getClass());

        if (null != function) {
            return function.apply(printObject);
        }
        throw new UnsupportedOperationException("un support print object class :" + printObject.getClass());
    }
}
