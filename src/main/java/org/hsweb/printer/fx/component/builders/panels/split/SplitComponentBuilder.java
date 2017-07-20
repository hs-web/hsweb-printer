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

package org.hsweb.printer.fx.component.builders.panels.split;

import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.component.builders.panels.SplitPaneComponentBuilder;
import org.hsweb.printer.fx.component.components.PanelComponent;
import org.hsweb.printer.fx.component.components.panels.SplitPaneComponent;
import org.hsweb.printer.utils.printable.templateptint.TemplatePrintConstants;
import org.hsweb.printer.utils.printable.templateptint.dtos.SplitPaneComponentDTO;

/**
 * Created by xiong on 2017-07-20.
 */
public class SplitComponentBuilder extends SplitPaneComponentBuilder<SplitPaneComponent,SplitPaneComponentDTO>{
    @Override
    public String getType() {
        return TemplatePrintConstants.TABLE;
    }


    @Override
    protected SplitPaneComponentDTO getTemplateComponentDTO() {
        SplitPaneComponentDTO splitPaneComponentDTO = new SplitPaneComponentDTO();
        splitPaneComponentDTO.setContext("变量名");
        return splitPaneComponentDTO;
    }

    @Override
    public SplitPaneComponent builderSplitComponent(SplitPaneComponentDTO templateComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {
        return new SplitPaneComponent(templateComponentDTO,propertyController,parentComponent);
    }
}
