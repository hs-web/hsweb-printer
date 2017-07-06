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
import org.hsweb.printer.dtos.PositionSimplePrintFontDTO;
import org.hsweb.printer.dtos.PositionSimplePrintStyleDTO;
import org.hsweb.printer.utils.StringUtil;
import org.hsweb.printer.utils.printable.positionprint.PositionPrintUnit;

import java.awt.*;
import java.util.List;

/**
 * Created by xiong on 2017-07-06.
 */
public class PositionSimpleComponentString implements PositionSimpleComponent {
    private PositionSimplePrintDTO positionSimplePrintDTO;
    private PositionSimplePrintFontDTO positionSimplePrintFontDTO;
    private PositionSimplePrintStyleDTO positionSimplePrintStyleDTO;
    private double height;
    private double width;
    public PositionSimpleComponentString(PositionSimplePrintDTO positionSimplePrintDTO,
                                         PositionSimplePrintFontDTO positionSimplePrintFontDTO,
                                         PositionSimplePrintStyleDTO positionSimplePrintStyleDTO,
                                         double height, double width){
        this.positionSimplePrintDTO=positionSimplePrintDTO;
        this.positionSimplePrintFontDTO=positionSimplePrintFontDTO;
        this.positionSimplePrintStyleDTO=positionSimplePrintStyleDTO;
        this.height=height;
        this.width=width;
    }
    @Override
    public void print(int pageIndex, Graphics graphics, double xpadding, double ypadding) {
        List<String> strings = StringUtil.lengthSplit(positionSimplePrintDTO.getContext(),(int)(PositionPrintUnit.parsingUnit(positionSimplePrintDTO.getWidth())/ positionSimplePrintFontDTO.getFont().getSize2D()));

        graphics.setFont(positionSimplePrintFontDTO.getFont());
        graphics.setColor(positionSimplePrintStyleDTO.getColor());

        double lineHeight = PositionPrintUnit.parsingUnit(positionSimplePrintDTO.getHeight());
        double x = PositionPrintUnit.parsingUnit(positionSimplePrintDTO.getX());
        double y = PositionPrintUnit.parsingUnit(positionSimplePrintDTO.getY());
        int i=0;
        for (String string : strings) {

            float v = positionSimplePrintFontDTO.getFont().getSize2D() * i;
            if(i!=0&&v+positionSimplePrintFontDTO.getFont().getSize2D()>lineHeight){
                return;
            }
            graphics.drawString(string,
                    (int)x,
                    (int)(y-pageIndex*height+v)
            );
            ++i;
        }
    }
}
