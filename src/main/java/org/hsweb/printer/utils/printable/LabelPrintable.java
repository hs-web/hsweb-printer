package org.hsweb.printer.utils.printable;

import org.hsweb.printer.utils.printable.labelprint.LablePrint;
import org.hsweb.printer.utils.printable.labelprint.LablePrintLine;

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
        this.lablePrint = new LablePrint(width, printString);
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
        return lablePrint.getHeight();
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        //转换成Graphics2D
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setColor(fontColor);

        for (LablePrintLine lablePrintLine : lablePrint) {
            lablePrintLine.print(g2);
        }

        return PAGE_EXISTS;
    }

}

