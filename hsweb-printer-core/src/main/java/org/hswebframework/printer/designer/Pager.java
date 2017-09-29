package org.hswebframework.printer.designer;

import java.util.List;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public class Pager {
    private String name;

    private List<PrintObject> printObjects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PrintObject> getPrintObjects() {
        return printObjects;
    }

    public void setPrintObjects(List<PrintObject> printObjects) {
        this.printObjects = printObjects;
    }
}
