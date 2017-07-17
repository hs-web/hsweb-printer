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

import org.hsweb.printer.utils.printable.templateptint.TemplatePrint;
import org.hsweb.printer.utils.printable.templateptint.dtos.PrintTemplateDTO;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;

/**
 * Created by xiong on 2017-02-24.
 */
public class TemplatePrintable implements BasePrintable {
    private String printName;
    private TemplatePrint positionPrint;
    private double height;
    private double width;
    private int xx=0;
    public TemplatePrintable(PrintTemplateDTO printDTOList, Object o) {
        this.printName=printDTOList.getPrintName();
        this.width=printDTOList.getWindowWidth();
        this.height=printDTOList.getWindowHeight();
        this.positionPrint = new TemplatePrint(printDTOList, o);
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
