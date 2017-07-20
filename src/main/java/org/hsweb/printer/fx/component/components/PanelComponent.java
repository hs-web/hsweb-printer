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

package org.hsweb.printer.fx.component.components;

import javafx.scene.Node;
import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.component.ComponentFactory;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;

import java.util.List;

/**
 * Created by xiong on 2017-07-19.
 */
public interface PanelComponent<T extends TemplateComponentDTO,N extends Node> extends Component<T,N> {
    PropertyController getPropertyController();
    void add(Component component);
    List<Component> getComponents();
    default void add(String type){
        Component builder = ComponentFactory.builder(type, this.getComponent(), this.getPropertyController());
        if(builder!=null) {
            this.add(builder);
        }
    }
    default double getWidth(){
        return this.getComponent().getWidth();
    }
    default double getHeight(){
        return this.getComponent().getHeight();
    }
}
