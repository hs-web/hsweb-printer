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

package org.hsweb.printer.fx.components.builders.elements;

import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.components.components.PanelComponent;
import org.hsweb.printer.fx.components.components.elements.ImageViewComponent;
import org.hsweb.printer.utils.printable.templateptint.dtos.TextComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public abstract class ImageViewElementComponentBuilder<T extends ImageViewComponent,A extends TextComponentDTO> extends BaseElementComponentBuilder<T,A> {

    public T builderComponent(A templateComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {
        templateComponentDTO.setHeight(100D);
        return this.builderImageComponent(templateComponentDTO,propertyController,parentComponent);
    }
    public abstract T builderImageComponent(A templateComponentDTO, PropertyController propertyController, PanelComponent parentComponent);
}
