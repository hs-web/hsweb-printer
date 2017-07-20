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

package org.hsweb.printer.fx.component.builders.elements.image;

import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.component.builders.elements.BaseElementComponentBuilder;
import org.hsweb.printer.fx.component.components.PanelComponent;
import org.hsweb.printer.fx.component.components.elements.ImageViewComponent;
import org.hsweb.printer.utils.printable.templateptint.TemplatePrintConstants;
import org.hsweb.printer.utils.printable.templateptint.dtos.ImageViewComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public  class ImageElementComponentBuilder extends BaseElementComponentBuilder<ImageViewComponent,ImageViewComponentDTO> {
    @Override
    public String getType() {
        return TemplatePrintConstants.IMAGE;
    }

    @Override
    protected ImageViewComponentDTO getTemplateComponentDTO() {
        ImageViewComponentDTO textComponentDTO=new ImageViewComponentDTO();
        textComponentDTO.setContext("变量名");
        return textComponentDTO;
    }

    @Override
    public ImageViewComponent builderComponent(ImageViewComponentDTO templateComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {
        return new ImageViewComponent(templateComponentDTO,propertyController,parentComponent);
    }
}
