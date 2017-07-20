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

import org.hsweb.printer.fx.component.components.PanelComponent;
import org.hsweb.printer.fx.component.propertys.BaseComponentProperty;
import org.hsweb.printer.fx.component.propertys.dtos.PropertysDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public abstract class BasePanelComponentProperty<T extends PanelComponent,A extends TemplateComponentDTO>   extends BaseComponentProperty<T,A> {


    @Override
    public void componentProperty(PropertysDTO propertys, T basicComponent, A baseComponentDTO){
        this.panelComponentProperty(propertys,basicComponent,baseComponentDTO);
    }
    public abstract void panelComponentProperty(PropertysDTO propertys, T basicComponent, A baseComponentDTO);

}
