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
        return 0D;//width;
    }

    @Override
    public double getHeight() {
        return 0D;// height;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex >=positionPrint.getPageSize()) {
            return NO_SUCH_PAGE;
        }
        positionPrint.print(pageIndex,graphics,getXpadding(),getYpadding());
        return PAGE_EXISTS;
    }
}
