package org.hsweb.printer.frame;

public enum  MenuItemName{
    open("menu_item_open", "打开"),
    exit("menu_item_exit", "退出")
    ;
    private String name;
    private String lable;

    MenuItemName(String name, String lable) {
        this.name = name;
        this.lable = lable;
    }

    public String getName() {
        return name;
    }

    public String getLable() {
        return lable;
    }
}