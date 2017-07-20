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

package org.hsweb.printer.fx.component.propertys.elements.image;

import org.hsweb.printer.fx.component.components.elements.ImageViewComponent;
import org.hsweb.printer.fx.component.propertys.dtos.PropertysDTO;
import org.hsweb.printer.fx.component.propertys.elements.ImageViewElementComponentProperty;
import org.hsweb.printer.utils.printable.templateptint.TemplatePrintConstants;
import org.hsweb.printer.utils.printable.templateptint.dtos.ImageComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public class ImageElementComponentProperty extends ImageViewElementComponentProperty<ImageViewComponent,ImageComponentDTO> {
    @Override
    public String getType() {
        return TemplatePrintConstants.IMAGE;
    }

    @Override
    public void imageComponentProperty(PropertysDTO propertys, ImageViewComponent baseComponentDTO, ImageComponentDTO basicComponent) {
        this.pubAll("变量");
    }
}
