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

package org.hsweb.printer.fx.component.builders.elements;

import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.component.builders.ComponentBuilder;
import org.hsweb.printer.fx.component.components.Component;
import org.hsweb.printer.fx.component.components.PanelComponent;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public abstract class BaseElementComponentBuilder<T extends Component,A extends TemplateComponentDTO> implements ComponentBuilder<T> {
    protected abstract A getTemplateComponentDTO();

    @Override
    public T builder(TemplateComponentDTO docComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {
        A templateComponentDTO = getTemplateComponentDTO();
        templateComponentDTO.setType(this.getType());
        templateComponentDTO.setWindowHeight(docComponentDTO.getHeight());
        templateComponentDTO.setWindowWidth(docComponentDTO.getWidth());
        return this.builderComponent(templateComponentDTO,propertyController,parentComponent);
    }
    public abstract T builderComponent(A docComponentDTO, PropertyController propertyController, PanelComponent parentComponent);
}
