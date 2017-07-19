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

package org.hsweb.printer.fx.components.components;

import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;

/**
 * Created by xiong on 2017-07-18.
 */
public  class PanelComponentEvent {



    private Component component;
    private PropertyController propertyController;
    private TemplateComponentDTO baseComponentDTO;

    public PanelComponentEvent(Component component, TemplateComponentDTO baseComponentDTO, PropertyController propertyController) {
        this.component = component;
        this.propertyController = propertyController;
        this.baseComponentDTO = baseComponentDTO;
        init();
    }

    public void init(){
        this.component.getThisNode().setOnMousePressed((e)->{
            if (!e.getTarget().equals(this.component.getThisNode())) {
                return;
            }
            this.propertyController.property(this.component,this.baseComponentDTO);
            System.out.println(1);
            return;
        });

    }
}
