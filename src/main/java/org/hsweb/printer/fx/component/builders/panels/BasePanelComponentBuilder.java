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

package org.hsweb.printer.fx.component.builders.panels;

import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.component.components.PanelComponent;
import org.hsweb.printer.fx.component.builders.ComponentBuilder;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public abstract class BasePanelComponentBuilder<T extends PanelComponent,A extends TemplateComponentDTO> implements ComponentBuilder<T>{
    protected abstract A getTemplateComponentDTO();

    @Override
    public T builder(TemplateComponentDTO docComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {
        A templateComponentDTO = getTemplateComponentDTO();
        templateComponentDTO.setType(this.getType());
        templateComponentDTO.setWindowHeight(docComponentDTO.getWidth());
        templateComponentDTO.setWindowWidth(docComponentDTO.getWidth());
        templateComponentDTO.setWidth(docComponentDTO.getWidth());
        templateComponentDTO.setHeight(docComponentDTO.getWidth());
        return this.builderComponent(templateComponentDTO,propertyController,parentComponent);
    }
    public abstract T builderComponent(A docComponentDTO, PropertyController propertyController, PanelComponent parentComponent);

}
