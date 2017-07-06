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

package org.hsweb.printer.utils.printable.positionprint.simple.component;

import org.hsweb.printer.dtos.PositionSimplePrintDTO;
import org.hsweb.printer.utils.printable.positionprint.PositionPrintUnit;

import java.awt.*;

/**
 * Created by xiong on 2017-07-06.
 */
public class PositionSimpleComponentImage implements PositionSimpleComponent {
    private PositionSimplePrintDTO positionSimplePrintDTO;
    private double height;
    private double width;
    public PositionSimpleComponentImage(PositionSimplePrintDTO positionSimplePrintDTO, double height, double width){
        this.positionSimplePrintDTO=positionSimplePrintDTO;
        this.height=height;
        this.width=width;
    }
    @Override
    public void print(int pageIndex, Graphics graphics, double xpadding, double ypadding) {

            graphics.drawImage(null,
                    (int) PositionPrintUnit.parsingUnit(positionSimplePrintDTO.getX()),
                    (int)(PositionPrintUnit.parsingUnit(positionSimplePrintDTO.getY())-pageIndex*height),
                    (int)(PositionPrintUnit.parsingUnit(positionSimplePrintDTO.getWidth())),
                    (int)(PositionPrintUnit.parsingUnit(positionSimplePrintDTO.getHeight())),
                    null
            );
    }
}
