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

package org.hsweb.printer.fx.components.propertys.elements;

import org.hsweb.printer.fx.components.components.Component;
import org.hsweb.printer.fx.components.propertys.BaseComponentProperty;
import org.hsweb.printer.fx.components.propertys.dtos.PropertysDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public abstract class BaseElementComponentProperty<T extends Component,A extends TemplateComponentDTO>   extends BaseComponentProperty<T,A> {


    @Override
    public void componentProperty(PropertysDTO propertys, T basicComponent, A baseComponentDTO){
        propertys.getElementMenu().setVisible(true);
        this.elementComponentProperty(propertys,basicComponent,baseComponentDTO);
    }
    public abstract void elementComponentProperty(PropertysDTO propertys, T basicComponent, A baseComponentDTO);

}
