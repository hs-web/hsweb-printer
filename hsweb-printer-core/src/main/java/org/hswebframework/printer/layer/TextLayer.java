package org.hswebframework.printer.layer;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouhao
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TextLayer extends AbstractLayer {

    private String text;

    //宽度，用于居中和自动换行
    private int width = -1;
    //高度
    private int height;

    private Align align = Align.left;

    private VerticalAlign verticalAlign = VerticalAlign.top;

    @Override
    protected void doDraw(Graphics2D graphics) {
        String text = getText();
        if (text == null) {
            text = "";
        }
        FontMetrics fontMetrics = graphics.getFontMetrics();

        //字体宽度大于总宽度,需要换行
        if (text.contains("\n") || (width > 0 && getTextWidth(text, fontMetrics) > width)) {
            int height = fontMetrics.getHeight();
            int nowY = getY();
            for (String line : text.split("[\n]")) {
                StringBuilder temp = new StringBuilder();
                for (char c : line.toCharArray()) {
                    temp.append(c);
                    String tempStr = temp.toString();
                    if (getTextWidth(temp.toString(), fontMetrics) >= width + 2) {
                        align.draw(graphics, tempStr, getWidth(), getX(), nowY );
                        nowY+= height + 2;
                        temp = new StringBuilder();
                    }
                }
                if (temp.length() > 0) {
                    align.draw(graphics, temp.toString(), getWidth(), getX(),  nowY );
                }
                nowY += height + 2;
            }
        } else {
            align.draw(graphics, text, getWidth(), getX(), getY());
        }
    }

    private static void doDrawString(Graphics2D graphics, String text, int x, int y) {
        graphics.drawString(text, x, y + graphics.getFontMetrics().getMaxAscent() - graphics.getFontMetrics().getLeading());
    }


    private static int getTextWidth(String text, FontMetrics metrics) {
        return metrics.stringWidth(text);
    }

    public enum Align implements TextAlignPainter {
        /**
         * 2端对齐居中
         * <pre>
         *  -------------------
         *  |文              字|
         *  |两    端    对   齐|
         * </pre>
         */
        both {
            @Override
            public void draw(Graphics2D graphics, String text, int width, int x, int y) {
                int everyWidth = width / text.length();

                char[] chars = text.toCharArray();
                int xTemp = x;
                int lstWidth = 0;
                for (int i = 0; i < chars.length; i++) {
                    String singleChar = String.valueOf(chars[i]);
                    int textWidth = getTextWidth(singleChar, graphics.getFontMetrics());
                    int t = xTemp;
                    if (i == chars.length - 1) {//最后一个字符串
                        t += everyWidth;
                    } else if (i != 0) { //中间的字符
                        if (lstWidth > textWidth) {
                            t += lstWidth - textWidth;
                        }
                        t += everyWidth / ((text.length() - 1)) * i;

                    }
                    doDrawString(graphics, singleChar, t, y);
                    xTemp += everyWidth;
                    lstWidth = textWidth;
                }
            }
        },
        /**
         * 居中
         */
        center {
            @Override
            public void draw(Graphics2D graphics, String text, int width, int x, int y) {
                int textWidth = getTextWidth(text, graphics.getFontMetrics());
                x = x + (width - textWidth / 2);
                doDrawString(graphics, text, x, y);
            }
        },
        /**
         * 居右
         */
        right {
            @Override
            public void draw(Graphics2D graphics, String text, int width, int x, int y) {
                int textWidth = getTextWidth(text, graphics.getFontMetrics());
                x = x + (width - textWidth);
                doDrawString(graphics, text, x, y);
            }
        },
        /**
         * 居左
         */
        left {
            @Override
            public void draw(Graphics2D graphics, String text, int width, int x, int y) {
                doDrawString(graphics, text, x, y);
            }
        }
        ;
        public static Align from(String align) {
            if (align == null) {
                return null;
            }
            return Align.valueOf(align.toLowerCase());
        }
    }

    public enum VerticalAlign {
        top, center, bottom;
        public static VerticalAlign from(String align) {
            if (align == null) {
                return null;
            }
            return VerticalAlign.valueOf(align.toLowerCase());
        }
    }

    interface TextAlignPainter {
        void draw(Graphics2D graphics, String text, int width, int x, int y);
    }


}
