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

package org.hsweb.printer.fx.component.builders.elements.image;

import org.hsweb.printer.utils.printable.templateptint.TemplatePrintConstants;

/**
 * Created by xiong on 2017-07-19.
 */
public  class QrcodeElementComponentBuilder extends ImageElementComponentBuilder {
    @Override
    public String getType() {
        return TemplatePrintConstants.QRCODE;
    }

}
