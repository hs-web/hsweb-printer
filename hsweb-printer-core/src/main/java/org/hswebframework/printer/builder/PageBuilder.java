package org.hswebframework.printer.builder;


import org.hswebframework.printer.Pager;

import java.util.List;

/**
 * @author zhouhao
 * @since 1.0
 */
public interface PageBuilder {
    List<Pager> build(String config);
}
