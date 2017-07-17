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

import java.io.Serializable;

/**
 * Created by xiong on 2017-07-17.
 */
public class TemplateComponentDTO implements Serializable {
    private Double windowWidth;
    private Double windowHeight;
    private String type;
    private String context;
    private Double x=10D;
    private Double y=20D;
    private Double width=100D;
    private Double height=20D;

    public Double getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(Double windowWidth) {
        this.windowWidth = windowWidth;
    }

    public Double getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(Double windowHeight) {
        this.windowHeight = windowHeight;
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

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
