package org.hsweb.printer.utils.printable;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xiong on 2016/8/20.
 */
public class LabelPrintable implements BasePrintable {

    private static Set set=new HashSet(){
        {
            add("B");
            add("G");
            add("GB");
        }
    };


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


    private void xxx(String x){
        x.indexOf("<");

    }

    private static LableIndex getLableInde(String x){


        LableIndex lableIndex = new LableIndex();
        int upIndex=0;
        while (true) {
            int _index = x.indexOf("<");
            int _lasIndex = x.indexOf(">");

            if (_index == -1||_lasIndex==-1) {
                return null;
            }

            String x2 = x.substring(_index + 1, _lasIndex);

            int _index2 = x2.lastIndexOf("<");
            if(_index2!=-1) {
                _index = _index2 != -1 ? _lasIndex - _index2 : _index;
                x2 = x.substring(_index + 1, _lasIndex);
            }

            if (!set.contains(x2)) {
                upIndex=upIndex+_lasIndex + 1;
                x= x.substring(_lasIndex + 1);
                continue;
            }

            lableIndex.setIndex(upIndex + _index);
            lableIndex.setLastIndex(upIndex + _lasIndex);
            lableIndex.setStart(true);
            lableIndex.setLable(x2);
            if (x2.indexOf("/") == 0) {
                lableIndex.setLable(x2.substring(1));
                lableIndex.setStart(false);
            }
            return lableIndex;
        }

    }
    private static void print(String x){
        while (true){
            LableIndex lableInde = getLableInde(x);
            if(lableInde==null){
                System.out.println(x);
                break;
            }

            String substring = x.substring(0, lableInde.getIndex());
            System.out.println(substring);
            x=x.substring(lableInde.getLastIndex()+1);
        }
    }


    public static void main(String[] args) {
        String x="1231231<B>XXXX</B>XXXXX<G>XXXXX</G>XXXXXX<GB>XCCCCC</GB>CCCCC";
        print(x);
    }


    String x2="1231231<B>XXXXXXXXX<G>XXXXXXXXXXX<GB>XCCCCC</GB>CCCCC</G>XXX</B>XXX";

}
class LableIndex{
    private Integer index;
    private Integer lastIndex;
    private String lable;
    private Boolean start;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(Integer lastIndex) {
        this.lastIndex = lastIndex;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public Boolean getStart() {
        return start;
    }

    public void setStart(Boolean start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "LableIndex{" +
                "index=" + index +
                ", lastIndex=" + lastIndex +
                ", lable='" + lable + '\'' +
                ", start=" + start +
                '}';
    }
}
