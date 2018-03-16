package org.hswebframework.printer.listener.event;

import java.util.EventObject;

/**
 * @author zhouhao
 * @since 1.0
 */
public class PrinterPageDoneEvent extends EventObject {
    public PrinterPageDoneEvent(int pageIndex) {
        super(pageIndex);
    }

    public int getPageIndex() {
        return (int) getSource();
    }
}
