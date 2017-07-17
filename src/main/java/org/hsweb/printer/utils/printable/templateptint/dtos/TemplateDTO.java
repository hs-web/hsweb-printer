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
import java.util.List;

/**
 * Created by xiong on 2017-07-17.
 */
public class TemplateDTO implements Serializable {
    private String printName;
    private Double windowWidth;
    private Double windowHeight;
    private List<TemplateComponentDTO> componentDTOS;

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
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

    public List<TemplateComponentDTO> getComponentDTOS() {
        return componentDTOS;
    }

    public void setComponentDTOS(List<TemplateComponentDTO> componentDTOS) {
        this.componentDTOS = componentDTOS;
    }
}
