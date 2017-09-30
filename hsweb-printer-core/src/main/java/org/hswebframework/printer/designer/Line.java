package org.hswebframework.printer.designer;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public class Line extends PrintObject {

    private int length;

    private int size;

    private Color color = new Color();

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
