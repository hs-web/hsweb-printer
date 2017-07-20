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

package org.hsweb.printer.fx.component.propertys.panels;

import org.hsweb.printer.fx.component.components.panels.AnchorPaneComponent;
import org.hsweb.printer.fx.component.propertys.dtos.PropertysDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.PosPanelComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public abstract class AnchorPaneComponentProperty <T extends AnchorPaneComponent,A extends PosPanelComponentDTO>  extends BasePanelComponentProperty<T,A> {


    @Override
    public void panelComponentProperty(PropertysDTO propertys, T basicComponent, A baseComponentDTO){
        propertys.getElementMenu().setVisible(true);
        this.posComponentProperty(propertys,basicComponent,baseComponentDTO);
    }
    public abstract void posComponentProperty(PropertysDTO propertys, T basicComponent, A baseComponentDTO);


}