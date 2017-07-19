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

package org.hsweb.printer.fx.components.builders.elements.image;

import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.components.builders.elements.BaseElementComponentBuilder;
import org.hsweb.printer.fx.components.components.PanelComponent;
import org.hsweb.printer.fx.components.components.elements.ImageViewComponent;
import org.hsweb.printer.utils.printable.templateptint.TemplatePrintConstants;
import org.hsweb.printer.utils.printable.templateptint.dtos.ImageComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public  class ImageElementComponentBuilder extends BaseElementComponentBuilder<ImageViewComponent,ImageComponentDTO> {
    @Override
    public String getType() {
        return TemplatePrintConstants.IMAGE;
    }

    @Override
    protected ImageComponentDTO getTemplateComponentDTO() {
        ImageComponentDTO textComponentDTO=new ImageComponentDTO();
        textComponentDTO.setContext("变量名");
        return textComponentDTO;
    }

    @Override
    public ImageViewComponent builderComponent(ImageComponentDTO templateComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {
        return new ImageViewComponent(templateComponentDTO,propertyController,parentComponent);
    }
}
