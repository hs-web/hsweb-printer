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
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;

/**
 * Created by xiong on 2017-07-18.
 */
public interface Component {
    PanelComponent getParentComponent();
    TemplateComponentDTO getComponent();
    void changeProperty(TemplateComponentDTO baseComponentDTO);
    Node getThisNode();
    double getX();
    double getY();
}
