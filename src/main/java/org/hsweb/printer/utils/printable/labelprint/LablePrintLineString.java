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

package org.hsweb.printer.utils.printable.labelprint;

import org.hsweb.printer.utils.StringUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiongchuang on 2016/8/23 .
 */
public class LablePrintLineString implements LablePrintLine{
    private float maxFontSize2D=0;

    private float nextX=0;

    private List<PrintLineStringVo> printLineStringVos=new ArrayList<PrintLineStringVo>();

    int i=0;
    public LablePrintLineString() {
    }

    public LablePrintLineString(float x) {
        this.nextX=x;
    }

    public LablePrintLineString(Font font, String printString) {
        this.add(font,printString);
    }
    public LablePrintLineString(float x,Font font, String printString) {
        this.nextX=x;
        this.add(font,printString);
    }
    public void add(Font font, String printString){
        maxFontSize2D=font.getSize2D()>maxFontSize2D?font.getSize2D():maxFontSize2D;

        if(printLineStringVos.size()>0&&font.isBold()){
            Font upSize2D = printLineStringVos.get(printLineStringVos.size() - 1).getFont();
            if(upSize2D.isPlain()){
                i++;
               // this.nextX+=upSize2D/5*i;
            }
        }

        printLineStringVos.add(new PrintLineStringVo(getNextX(),font,printString));
        this.nextX+=StringUtil.length(printString)*(font.getSize2D()/2.0);
    }

    public float getNextX() {
        return nextX;
    }

    @Override
    public float getHeight() {
        return maxFontSize2D;
    }

    @Override
    public void print(float xpadding,float y,Graphics2D g2){
        float printX=xpadding;
        for (PrintLineStringVo printLineStringVo : printLineStringVos) {
            g2.setFont(printLineStringVo.getFont());

            String printString = printLineStringVo.getPrintString();
            for (char c : printString.toCharArray()) {
                String printOneString=String.valueOf(c);
                g2.drawString(printOneString,printX, y);
                printX+=StringUtil.length(printOneString)*(printLineStringVo.getFont().getSize2D()/2);
            }
        }
    }


    private class PrintLineStringVo{
        private float x;
        private Font font;
        private String printString;

        public PrintLineStringVo(float x,Font font, String printString) {
            this.x = x;
            this.font = font;
            this.printString = printString;
        }


        public float getX() {
            return x;
        }

        public String getPrintString() {
            return printString;
        }

        public Font getFont() {
            return font;
        }
    }
}
