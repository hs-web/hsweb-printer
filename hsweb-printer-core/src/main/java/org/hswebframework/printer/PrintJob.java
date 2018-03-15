package org.hswebframework.printer;

import java.util.function.BiConsumer;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface PrintJob {
    String getPrintJobId();

    void onPrintDone(BiConsumer<Integer, Pager> onPagerPrintDone);
}
