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

package org.hsweb.printer.fx.component.builders.elements.text;

import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.component.builders.elements.TextViewElementComponentBuilder;
import org.hsweb.printer.fx.component.components.PanelComponent;
import org.hsweb.printer.fx.component.components.elements.TextViewComponent;
import org.hsweb.printer.utils.printable.templateptint.TemplatePrintConstants;
import org.hsweb.printer.utils.printable.templateptint.dtos.TextViewComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public class TextElementComponentBuilder extends TextViewElementComponentBuilder<TextViewComponent,TextViewComponentDTO> {
    @Override
    public String getType() {
        return TemplatePrintConstants.TEXT;
    }

    @Override
    protected TextViewComponentDTO getTemplateComponentDTO() {
        TextViewComponentDTO textComponentDTO=new TextViewComponentDTO();
        textComponentDTO.setContext("文本");
        return textComponentDTO;
    }

    @Override
    public TextViewComponent builderTextComponent(TextViewComponentDTO templateComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {
        return new TextViewComponent(templateComponentDTO,propertyController,parentComponent);
    }

}
