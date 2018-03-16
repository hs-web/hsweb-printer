package org.hswebframework.printer.listener;

import org.hswebframework.utils.ClassUtils;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouhao
 * @since 1.0
 */
public class PrinterListenerEventPublisher {

    private static Map<Class<? extends EventObject>, Map<Long, PrinterListener>> eventObjectMap = new HashMap<>();

    public static <E extends EventObject> long subscribe(Class<E> type, PrinterListener<? super E> printerListener) {
        long id = System.currentTimeMillis();
        eventObjectMap.computeIfAbsent(type, c -> new HashMap<>(8)).put(id, printerListener);
        return id;
    }

    public static <E extends EventObject> long subscribe(PrinterListener<E> printerListener) {
        long id = System.currentTimeMillis();

        Class<E> clzz = (Class) ClassUtils.getGenericType(printerListener.getClass());

        if (clzz.equals(Object.class)) {
            throw new UnsupportedOperationException("无法识别的事件监听");
        }
        subscribe(clzz, printerListener);
        return id;
    }


    public static <E extends EventObject> void publish(E eventObject) {
        eventObjectMap.computeIfAbsent(eventObject.getClass(), c -> new HashMap<>(8))
                .values().forEach(printerListener -> printerListener.on(eventObject));
    }
}
