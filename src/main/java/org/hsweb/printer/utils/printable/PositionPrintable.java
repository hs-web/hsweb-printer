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

import org.hsweb.printer.dtos.PositionPrintDTO;
import org.hsweb.printer.utils.printable.positionprint.PositionPrint;
import org.hsweb.printer.utils.printable.positionprint.PositionPrintUnit;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.List;

/**
 * Created by xiong on 2017-02-24.
 */
public class PositionPrintable implements BasePrintable {
    private String printName;
    private PositionPrint positionPrint;
    private double height;
    private double width;
    public PositionPrintable(String printName,String height, String width, List<PositionPrintDTO> printDTOList) {

        this.printName = printName;
        this.height = PositionPrintUnit.parsingUnit(height);
        this.width = PositionPrintUnit.parsingUnit(width);

        this.positionPrint = new PositionPrint(this.height,this.width, printDTOList);
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
        positionPrint.print(pageIndex,graphics, getXPadding(), getYPadding());
        return PAGE_EXISTS;
    }
}
