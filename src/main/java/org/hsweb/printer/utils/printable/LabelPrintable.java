package org.hsweb.printer.utils.printable;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;

/**
 * Created by xiong on 2016/8/20.
 */
public class LabelPrintable implements BasePrintable {

    private String printName;
    private double width;
    private String[] printStrings;


    private Color fontColor = Color.black;

    private Font normalFont = new Font("黑体", Font.PLAIN,12);
    private Font boldFont = new Font("黑体", Font.BOLD, 12);

    private Font bigFont = new Font("黑体", Font.PLAIN, 24);
    private Font bigBoldFont = new Font("黑体", Font.BOLD, 24);


    private String tagBoldStart = "<B>";
    private String tagBoldEnd = "</B>";

    private String tagBigStart = "<G>";
    private String tagBigEnd = "</G>";

    private String tagBigBoldStart = "<GB>";
    private String tagBigBoldEnd = "</GB>";


    public LabelPrintable(String printName, double width, String printString) {
        this.printName = printName;
        this.width = width;
        this.printStrings = printString.split("\n");
    }

    @Override
    public String getPrintDocName() {
        return printName;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double gehHeight() {
        int normalRow = 0;
        int bigRow = 0;
        for (String printString : printStrings) {
            if (printString.indexOf(tagBigStart) != -1 || printString.indexOf(tagBigBoldStart) != -1) {
                bigRow++;
            } else {
                normalRow++;
            }
        }
        return getYpadding() * 2 + normalRow * normalFont.getSize2D() + bigRow * bigFont.getSize2D();
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if(pageIndex>0){
            return NO_SUCH_PAGE;
        }
        //转换成Graphics2D
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setColor(fontColor);


        float y=20;
        for (String printString : printStrings) {
            String printNewString=printString;
            g2.setFont(normalFont);

            if(printString.indexOf(tagBigStart)!=-1){
                y+=bigFont.getSize2D();
                g2.setFont(bigFont);

                printNewString=printString.replace(tagBigStart,"").replace(tagBigEnd,"");
            }else if(printString.indexOf(tagBigBoldStart)!=-1){
                y+=bigBoldFont.getSize2D();
                g2.setFont(bigBoldFont);

                printNewString=printString.replace(tagBigBoldStart,"").replace(tagBigBoldEnd,"");
            }else if(printString.indexOf(tagBoldStart)!=-1){
                y+=boldFont.getSize2D();
                g2.setFont(boldFont);

                printNewString=printString.replace(tagBoldStart,"").replace(tagBoldEnd,"");
            }else {
                y+=normalFont.getSize2D();
            }

            g2.drawString(printNewString, (float) getXpadding()*2, y);
        }
        return PAGE_EXISTS;
    }
}
