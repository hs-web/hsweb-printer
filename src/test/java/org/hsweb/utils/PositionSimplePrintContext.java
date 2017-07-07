/*
 *  Copyright (c) 2015.  meicanyun.com Corporation Limited.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of
 *  meicanyun Company. ("Confidential Information").  You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with meicanyun.com.
 */

package org.hsweb.utils;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by xiong on 2017-07-07.
 */
public class PositionSimplePrintContext extends ArrayList<PositionSimplePrintContext.PositionSimplePrintDTO> implements Serializable  {
    private final String TEXT="TEXT";
    private final String IMAGE="IMAGE";
    private final String FONT="FONT";
    private final String STYLE="STYLE";

    public PositionSimplePrintContext() {
    }

    public PositionSimplePrintContext changeFont(Font font){
        this.add(new PositionSimplePrintDTO(FONT,font.getFontName(),font.getStyle(),font.getSize()));
        return this;
    }
    public PositionSimplePrintContext changeFont(String fontName, Integer fontStyle, Integer fontSize){
        this.add(new PositionSimplePrintDTO(FONT,fontName,fontStyle,fontSize));
        return this;
    }
    public PositionSimplePrintContext changeStyle(String color, Integer align){
        this.add(new PositionSimplePrintDTO(STYLE,color,align));
        return this;
    }
    public PositionSimplePrintContext changeStyle(Color color, Integer align){
        this.add(new PositionSimplePrintDTO(STYLE,"#"+Integer.toHexString(color.getRed())+Integer.toHexString(color.getGreen())+Integer.toHexString(color.getBlue()),align));
        return this;
    }
    public PositionSimplePrintContext addText(String context,String x,String y,String height,String width){
        this.add(new PositionSimplePrintDTO(TEXT,context,x,y,height,width));
        return this;
    }
    public PositionSimplePrintContext addText(String context,Integer x,Integer y,Integer height,Integer width){
        this.add(new PositionSimplePrintDTO(TEXT,context,x+"",y+"",height+"",width+""));
        return this;
    }
    public PositionSimplePrintContext addImage(String context,String x,String y,String height,String width){
        this.add(new PositionSimplePrintDTO(IMAGE,context,x,y,height,width));
        return this;
    }
    public PositionSimplePrintContext addImage(String context,Integer x,Integer y,Integer height,Integer width){
        this.add(new PositionSimplePrintDTO(IMAGE,context,x+"",y+"",height+"",width+""));
        return this;
    }


    protected class PositionSimplePrintDTO implements Serializable {

        private String type;
        private String context;
        private String x;
        private String y;
        private String width;
        private String height;

        //字体
        private String fontName = "微软雅黑";
        private Integer fontStyle = 0;
        private Integer fontSize = 8;

        //样式
        private String color = "#000000";
        private Integer align = 0;

        public PositionSimplePrintDTO() {
        }

        public PositionSimplePrintDTO(String type, String color, Integer align) {
            this.type = type;
            this.color = color;
            this.align = align;
        }

        public PositionSimplePrintDTO(String type, String fontName, Integer fontStyle, Integer fontSize) {
            this.type = type;
            this.fontName = fontName;
            this.fontStyle = fontStyle;
            this.fontSize = fontSize;
        }

        public PositionSimplePrintDTO(String type, String context, String x, String y, String height, String width) {
            this.type = type;
            this.context = context;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.fontName = fontName;
            this.fontStyle = fontStyle;
            this.fontSize = fontSize;
            this.color = color;
            this.align = align;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getFontName() {
            return fontName;
        }

        public void setFontName(String fontName) {
            this.fontName = fontName;
        }

        public Integer getFontStyle() {
            return fontStyle;
        }

        public void setFontStyle(Integer fontStyle) {
            this.fontStyle = fontStyle;
        }

        public Integer getFontSize() {
            return fontSize;
        }

        public void setFontSize(Integer fontSize) {
            this.fontSize = fontSize;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Integer getAlign() {
            return align;
        }

        public void setAlign(Integer align) {
            this.align = align;
        }
    }

}
