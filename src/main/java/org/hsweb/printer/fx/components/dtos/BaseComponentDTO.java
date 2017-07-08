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

package org.hsweb.printer.fx.components.dtos;

import java.io.Serializable;

/**
 * Created by xiong on 2017-07-08.
 */
public class BaseComponentDTO implements Serializable {
    private String type;
    private Double windowWidth;
    private Double windowHeight;
    private String context;
    private Double initX=10D;
    private Double initY=20D;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getInitX() {
        return initX;
    }

    public void setInitX(Double initX) {
        this.initX = initX;
    }

    public Double getInitY() {
        return initY;
    }

    public void setInitY(Double initY) {
        this.initY = initY;
    }

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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
