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

package org.hsweb.printer.fx.components.builders.panels;

import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.components.components.PanelComponent;
import org.hsweb.printer.fx.components.components.panels.AnchorPaneComponent;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public abstract class AnchorPaneComponentBuilder<T extends AnchorPaneComponent,A extends TemplateComponentDTO> extends BasePanelComponentBuilder<T,A> {
    @Override
    public T builderComponent(A templateComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {
        return this.builderImageComponent(templateComponentDTO,propertyController,parentComponent);
    }
    public abstract T builderImageComponent(A templateComponentDTO, PropertyController propertyController, PanelComponent parentComponent);
}