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
import org.hsweb.printer.fx.component.components.panels.SplitPaneComponent;
import org.hsweb.printer.utils.printable.templateptint.dtos.SplitPaneComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public abstract class SplitPaneComponentBuilder<T extends SplitPaneComponent,A extends SplitPaneComponentDTO> extends BasePanelComponentBuilder<T,A> {
    @Override
    public T builderComponent(A templateComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {
        return this.builderSplitComponent(templateComponentDTO,propertyController,parentComponent);
    }
    public abstract T builderSplitComponent(A templateComponentDTO, PropertyController propertyController, PanelComponent parentComponent);
}