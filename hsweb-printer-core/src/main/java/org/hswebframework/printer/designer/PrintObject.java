package org.hswebframework.printer.designer;

/**
 * 打印对象
 *
 * @author zhouhao
 * @since 1.0
 */
public abstract class PrintObject {
    private String name;

    private String comment;

    private int x;

    private int y;

    private Direction direction;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
