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

package org.hsweb.printer.fx.component.propertys.elements;

import org.hsweb.printer.fx.component.components.Component;
import org.hsweb.printer.fx.component.propertys.dtos.PropertysDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.ImageComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public abstract class ImageViewElementComponentProperty<T extends Component,A extends ImageComponentDTO>  extends BaseElementComponentProperty<T,A>  {


    @Override
    public void elementComponentProperty(PropertysDTO propertys, T basicComponent, A baseComponentDTO){
        //propertys.getPropertyFont().setVisible(true);
        this.imageComponentProperty(propertys,basicComponent,baseComponentDTO);
    }
    public abstract void imageComponentProperty(PropertysDTO propertys, T basicComponent, A baseComponentDTO);


}
