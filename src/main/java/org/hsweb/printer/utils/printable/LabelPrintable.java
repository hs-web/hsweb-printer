package org.hsweb.printer.utils.printable;


import org.hsweb.printer.utils.PrintUtil;
import org.hsweb.printer.utils.printable.labelprint.LablePrint;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.attribute.Attribute;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.Media;
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

    private double Xpadding;

/*    pagewidth = 纸张宽度mm/3.55
    字数= pagewidth/9 -1*/
    private int fontSize=8;
    public LabelPrintable(String printName, double width, String printString) {
        this.init(printName,width,this.fontSize,printString);

    }
    public LabelPrintable(String printName, double width,Integer fontSize, String printString) {
        this.init(printName,width,fontSize,printString);
    }
    public void init(String printName, double width,Integer fontSize, String printString) {
        if(fontSize!=null) {
            this.fontSize = fontSize;
        }
        this.printName = printName;

        width=width/ 3.55555555555555;

        int intWidth=(int)(width-width*0.1);

        int i = intWidth / this.fontSize ;

        int printWidth = i * this.fontSize;

        this.Xpadding=(width-printWidth)/2;

        this.lablePrint = new LablePrint(printWidth,this.fontSize, printString);
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
        return lablePrint.getHeight()+lablePrint.get(0).getHeight()+ getYPadding()*2;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        lablePrint.print(graphics, getXPadding(), getYPadding());
        return PAGE_EXISTS;
    }

    @Override
    public double getXPadding(){
        return Xpadding;
    }


    public static void main(String[] args) {

        PrintService printer =  PrintUtil.getPrintService("EPSON TM-T81II ReceiptSC4");

        Media[] objs = (Media[]) printer.getSupportedAttributeValues(Media.class, null, null);
        for (Media obj : objs) {
            Class<? extends PrintServiceAttribute> category = (Class<? extends PrintServiceAttribute>) obj.getCategory();
            PrintServiceAttribute attribute = printer.getAttribute(category);
            System.out.println(1);
        }

        Class<Attribute>[] supportedAttributeCategories = (Class<Attribute>[]) printer.getSupportedAttributeCategories();
        for (Class<Attribute> supportedAttributeCategory : supportedAttributeCategories) {
            Object supportedAttributeValues = printer.getSupportedAttributeValues(supportedAttributeCategory, null, null);
            System.out.println(1);
        }
        DocFlavor[] supportedDocFlavors = printer.getSupportedDocFlavors();
        System.out.println(1);
    }
}

