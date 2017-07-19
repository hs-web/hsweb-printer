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

package org.hsweb.printer.fx.components.builders.panels.pos;

import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.components.builders.panels.AnchorPaneComponentBuilder;
import org.hsweb.printer.fx.components.components.PanelComponent;
import org.hsweb.printer.fx.components.components.panels.AnchorPaneComponent;
import org.hsweb.printer.utils.printable.templateptint.TemplatePrintConstants;
import org.hsweb.printer.utils.printable.templateptint.dtos.PosPanelComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public class PosPanelComponentBudiler extends AnchorPaneComponentBuilder<AnchorPaneComponent,PosPanelComponentDTO>{

    @Override
    public String getType() {
        return TemplatePrintConstants.POS;
    }

    @Override
    protected PosPanelComponentDTO getTemplateComponentDTO() {
        PosPanelComponentDTO textComponentDTO=new PosPanelComponentDTO();
        //textComponentDTO.setContext("变量");
        return textComponentDTO;
    }

    @Override
    public AnchorPaneComponent builderImageComponent(PosPanelComponentDTO templateComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {
        return new AnchorPaneComponent(templateComponentDTO,propertyController,parentComponent);
    }
}