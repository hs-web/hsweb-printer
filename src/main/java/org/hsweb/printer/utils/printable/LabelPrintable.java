package org.hsweb.printer.utils.printable;

import org.hsweb.printer.utils.printable.labelprint.LablePrint;
import org.hsweb.printer.utils.printable.labelprint.LablePrintLine;
import org.hsweb.printer.utils.printable.labelprint.LablePrintLineQrcode;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;

/**
 * Created by xiong on 2016/8/20.
 */
public class LabelPrintable implements BasePrintable {

    private static String printString="string";
    private static String printImage="image";

    private String printName;
    private LablePrint lablePrint;


    private Color fontColor = Color.black;

    public LabelPrintable(String printName, double width, String printString) {
        this.printName = printName;
        this.lablePrint = new LablePrint(width-getXpadding()*2, printString);
    }

    @Override
    public String getPrintDocName() {
        return printName;
    }

    @Override
    public double getWidth() {
        return lablePrint.getWidth();
    }

    @Override
    public double getHeight() {
        return lablePrint.getHeight()+lablePrint.get(0).getHeight()+getYpadding()*2;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        //转换成Graphics2D
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setColor(fontColor);

        float height=(float) getYpadding()+lablePrint.get(0).getHeight();
        for (LablePrintLine lablePrintLine : lablePrint) {
            float tempheight=height;
            height+=lablePrintLine.getHeight();
            if(!lablePrintLine.getClass().equals(LablePrintLineQrcode.class)){
                tempheight=height;
            }
            lablePrintLine.print((float)getXpadding(),tempheight,g2);
        }
        //graphics.drawString(" ",0,(int)getHeight());
        return PAGE_EXISTS;
    }

}

