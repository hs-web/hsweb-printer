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

import java.awt.*;
import java.io.Serializable;

/**
 * Created by xiong on 2017-07-06.
 */
public class PositionSimplePrintStyleDTO implements Serializable {
    private Integer id;
    private Color color;
    private Integer align=0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Integer getAlign() {
        return align;
    }

    public void setAlign(Integer align) {
        this.align = align;
    }
}
