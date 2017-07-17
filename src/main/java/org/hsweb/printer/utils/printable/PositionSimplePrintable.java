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

package org.hsweb.printer.utils.printable;

import org.hsweb.printer.dtos.PositionSimplePrintDTO;
import org.hsweb.printer.utils.printable.positionprint.PositionPrintUnit;
import org.hsweb.printer.utils.printable.positionprint.simple.PositionSimplePrint;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.List;

/**
 * Created by xiong on 2017-02-24.
 */
public class PositionSimplePrintable implements BasePrintable {
    private String printName;
    private PositionSimplePrint positionPrint;
    private double height;
    private double width;
    private int xx=0;
    public PositionSimplePrintable(String printName, String height, String width, List<PositionSimplePrintDTO> printDTOList) {

        this.printName = printName;
        this.height = PositionPrintUnit.parsingUnit(height);
        this.width = PositionPrintUnit.parsingUnit(width);

        this.positionPrint = new PositionSimplePrint(this.height,this.width, printDTOList);
    }
    @Override
    public String getPrintDocName() {
        return printName;
    }

    @Override
    public double getWidth() {
        return width;//width;
    }

    @Override
    public double getHeight() {
        return height;// height;
    }

    @Override
    public double getXpadding() {
        return 0;
    }

    @Override
    public double getYpadding() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return positionPrint.getPageSize();
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex >=positionPrint.getPageSize()) {
            return NO_SUCH_PAGE;
        }
        if(xx==0){
            xx++;
            return PAGE_EXISTS;
        }

        positionPrint.print(pageIndex,graphics,getXpadding(),getYpadding());
        return PAGE_EXISTS;
    }
}
