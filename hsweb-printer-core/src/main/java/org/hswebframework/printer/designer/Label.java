package org.hswebframework.printer.designer;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public class Label extends PrintObject {
    private int width;

    private int height;

    private String font;

    private String text;

    private Color color = new Color();

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
