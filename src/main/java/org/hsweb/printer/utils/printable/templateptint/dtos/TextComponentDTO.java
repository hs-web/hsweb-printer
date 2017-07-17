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

package org.hsweb.printer.utils.printable.templateptint.dtos;

import javafx.scene.text.FontPosture;

import java.io.Serializable;

/**
 * Created by xiong on 2017-07-10.
 */
public class TextComponentDTO extends TemplateComponentDTO implements Serializable {

    private String fontName="微软雅黑";
    private FontPosture fontPosture;
    private Integer fontSize=8;

    //样式
    private String color="#000000";
    private Integer align=0;

    public FontPosture getFontPosture() {
        return fontPosture;
    }

    public void setFontPosture(FontPosture fontPosture) {
        this.fontPosture = fontPosture;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
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
