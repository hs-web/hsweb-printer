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

package org.hsweb.printer.dtos;

import java.io.Serializable;

/**
 * Created by xiong on 2017-02-24.
 */
public class PositionPrintDTO implements Serializable {
    private String type;
    private String context;
    private PositionPrintLocationDTO location=new PositionPrintLocationDTO();
    private PositionPrintFontDTO font=new PositionPrintFontDTO();
    private PositionPrintStyleDTO style=new PositionPrintStyleDTO();

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

    public PositionPrintLocationDTO getLocation() {
        return location;
    }

    public void setLocation(PositionPrintLocationDTO location) {
        this.location = location;
    }

    public PositionPrintFontDTO getFont() {
        return font;
    }

    public void setFont(PositionPrintFontDTO font) {
        this.font = font;
    }

    public PositionPrintStyleDTO getStyle() {
        return style;
    }

    public void setStyle(PositionPrintStyleDTO style) {
        this.style = style;
    }
}
