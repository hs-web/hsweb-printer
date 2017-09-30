package org.hswebframework.printer.designer;

import java.util.List;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public class PrintCommand {

    private List<Pager> pagers;

    public int getPageSize() {
        return pagers == null ? 0 : pagers.size();
    }

    public List<Pager> getPagers() {
        return pagers;
    }

    public void setPagers(List<Pager> pagers) {
        this.pagers = pagers;
    }
}
