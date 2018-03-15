package org.hswebframework.printer;


import java.util.List;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface PrinterDriver {
    PrintJob print(List<Pager> pagers);

    PrintState getState();
}
