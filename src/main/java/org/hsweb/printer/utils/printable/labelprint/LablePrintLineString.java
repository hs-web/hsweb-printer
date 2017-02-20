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


import org.hsweb.printer.utils.FontUtil;
import org.hsweb.printer.utils.StringUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xiongchuang on 2016/8/23 .
 */
public class LablePrintLineString implements LablePrintLine{
    private static final String  letterString="1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM.:,;'\"(!?)+-*/=";
    private static final Set<String> letterSet=new HashSet<>();
    static {
        for (char c : letterString.toCharArray()) {
            letterSet.add(String.valueOf(c));
        }
    }


    private float maxFontSize2D=0;

    private float nextX=0;

    private String align;
    private float width;
    private int textLine;

    private List<PrintLineStringVo> printLineStringVos=new ArrayList<PrintLineStringVo>();

    int i=0;
    public LablePrintLineString(int textLine,String align, float width) {
        this.align=align;
        this.width=width;
        this.textLine=textLine;
    }

    public LablePrintLineString(int textLine,String align, float width, float x) {
        this.align=align;
        this.width=width;
        this.nextX=x;
        this.textLine=textLine;
    }

    public LablePrintLineString(int textLine,String align, float width, Font font,String fontTag, String printString) {
        this.align=align;
        this.width=width;
        this.textLine=textLine;
        this.add(font,fontTag,printString);
    }
    public LablePrintLineString(int textLine,float x, float width, String align, Font font,String fontTag, String printString) {
        this.nextX=x;
        this.align=align;
        this.width=width;
        this.textLine=textLine;
        this.add(font,fontTag,printString);
    }
    public void add(Font font,String fontTag, String printString){
        float fontSize2D = font.getSize2D();
        if("W".equals(fontTag)){
            fontSize2D=fontSize2D/2;
        }else if("H".equals(fontTag)){
            fontSize2D=fontSize2D*2;
        }

        maxFontSize2D=fontSize2D>maxFontSize2D? fontSize2D :maxFontSize2D;

        if(printLineStringVos.size()>0&&font.isBold()){
            Font upSize2D = printLineStringVos.get(printLineStringVos.size() - 1).getFont();
            if(upSize2D.isPlain()){
                i++;
               // this.nextX+=upSize2D/5*i;
            }
        }

        printLineStringVos.add(new PrintLineStringVo(getNextX(),font,fontTag,printString));
        this.nextX+= StringUtil.length(printString)*(font.getSize2D() /2.0);
    }

    public float getNextX() {
        return nextX;
    }

    @Override
    public float getHeight() {
        return maxFontSize2D+textLine*2;
    }

    private float getLeft(){
        if("R".equals(align)){
            return width-nextX;
        }else if("C".equals(align)){
            return (width-nextX)/2;
        }
        return 0;
    }

    private BufferedImage getStringBufferedImage(Font font2,String s){
        int bs=10;
        Font font=font2.deriveFont(font2.getStyle(),font2.getSize()*bs);
        BufferedImage bufferedImage = new BufferedImage((int)font.getSize2D()+2*bs,(int)font.getSize2D()+2*bs,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        ((Graphics2D) graphics).setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));

//((Graphics2D)g).setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));

//((Graphics2D)g).setStroke(new BasicStroke(bold));

//((Graphics2D)g).setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        // 设定背景色
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, (int)font.getSize2D()+2*bs, (int)font.getSize2D()+2*bs);
        graphics.setColor(Color.BLACK);
        graphics.setFont(font);
        graphics.drawString(s,0+1,(int)font.getSize2D()-1*bs);
        graphics.dispose();
        return bufferedImage;
    }

    @Override
    public void print(float xpadding,float y,Graphics2D g2){
        float newY = y + maxFontSize2D ;
        float printX=xpadding+getLeft();
        for (PrintLineStringVo printLineStringVo : printLineStringVos) {
            String printString = printLineStringVo.getPrintString();
            Font font = printLineStringVo.getFont();
            Font letterFont= FontUtil.printLetterFont(font.getStyle(),font.getSize());
            if("W".equals(printLineStringVo.fontTag)){
                for (char c : printString.toCharArray()) {
                    String printOneString= String.valueOf(c);
                    Font font1 =null;
                    if(letterSet.contains(printOneString)){
                        font1=letterFont;
                    }else {
                        font1=font;
                    }
                    BufferedImage stringBufferedImage = getStringBufferedImage(font1, printOneString);
                    g2.drawImage(stringBufferedImage,(int)printX, (int)newY-(int)(font1.getSize2D()/2),(int)font1.getSize2D(),(int)(font1.getSize2D()/2)+1,null);
                    printX+=StringUtil.length(printOneString)*(font1.getSize2D()/2);
                }

            }else if("H".equals(printLineStringVo.fontTag)){

                for (char c : printString.toCharArray()) {
                    String printOneString= String.valueOf(c);
                    Font font1 =null;
                    if(letterSet.contains(printOneString)){
                        font1=letterFont.deriveFont(letterFont.getStyle(),letterFont.getSize()*2);
                    }else {
                        font1=font.deriveFont(font.getStyle(),font.getSize()*2);
                    }
                    BufferedImage stringBufferedImage = getStringBufferedImage(font1, printOneString);
                    g2.drawImage(stringBufferedImage,(int)printX, (int)newY-(int)font1.getSize2D(),(int)(font1.getSize2D()/2),(int)font1.getSize2D()+1,null);
                    printX+=StringUtil.length(printOneString)*(font.getSize2D()/2);
                }

            }else {

                for (char c : printString.toCharArray()) {
                    String printOneString= String.valueOf(c);
                    if(letterSet.contains(printOneString)){
                        g2.setFont(letterFont);
                    }else {
                        g2.setFont(font);
                    }

                    g2.drawString(printOneString,printX, newY);
                    printX+=StringUtil.length(printOneString)*(font.getSize2D()/2);
                }
            }

        }
    }


    private class PrintLineStringVo{
        private float x;
        private Font font;
        private String printString;
        private String fontTag;

        public PrintLineStringVo(float x, Font font,String fontTag, String printString) {
            this.x = x;
            this.font = font;
            this.fontTag = fontTag;
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

        public String getFontTag() {
            return fontTag;
        }
    }
}
