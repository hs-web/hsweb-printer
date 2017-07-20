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

package org.hsweb.printer.fx.component.propertys.dtos;

import javafx.scene.control.Menu;
import javafx.scene.control.TitledPane;

import java.io.Serializable;

/**
 * Created by xiong on 2017-07-19.
 */
public class PropertysDTO implements Serializable {
    private Menu elementMenu;

    private PropertyDTO pubProperty;

    private TitledPane propertyFont;
    private PropertyDTO fontProperty;

    public Menu getElementMenu() {
        return elementMenu;
    }

    public void setElementMenu(Menu elementMenu) {
        this.elementMenu = elementMenu;
    }

    public PropertyDTO getPubProperty() {
        return pubProperty;
    }

    public void setPubProperty(PropertyDTO pubProperty) {
        this.pubProperty = pubProperty;
    }

    public TitledPane getPropertyFont() {
        return propertyFont;
    }

    public void setPropertyFont(TitledPane propertyFont) {
        this.propertyFont = propertyFont;
    }

    public PropertyDTO getFontProperty() {
        return fontProperty;
    }

    public void setFontProperty(PropertyDTO fontProperty) {
        this.fontProperty = fontProperty;
    }
}
