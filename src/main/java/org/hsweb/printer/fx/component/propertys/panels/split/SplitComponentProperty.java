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

package org.hsweb.printer.fx.component.propertys.panels.split;

import org.hsweb.printer.fx.component.components.panels.SplitPaneComponent;
import org.hsweb.printer.fx.component.propertys.dtos.PropertysDTO;
import org.hsweb.printer.fx.component.propertys.panels.SplitPaneComponentProperty;
import org.hsweb.printer.utils.printable.templateptint.TemplatePrintConstants;
import org.hsweb.printer.utils.printable.templateptint.dtos.SplitPaneComponentDTO;

/**
 * Created by xiong on 2017-07-20.
 */
public class SplitComponentProperty extends SplitPaneComponentProperty<SplitPaneComponent, SplitPaneComponentDTO> {


    @Override
    public String getType() {
        return TemplatePrintConstants.POS;
    }


    @Override
    public void splitComponentProperty(PropertysDTO propertys, SplitPaneComponent basicComponent, SplitPaneComponentDTO baseComponentDTO) {
        this.pubContextProperty("变量");
        this.pubWidthProperty();
        this.pubHeightProperty();
    }
}
