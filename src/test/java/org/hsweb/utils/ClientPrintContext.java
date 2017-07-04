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

package org.hsweb.utils;

/**
 * Created by xiong on 2017-02-09.
 */
public class ClientPrintContext implements PrintContext {

    private  int maxSize;


    private static final String BR = "<BR>";//换行


    private static final String BigFont_S = "<G>";//放大
    private static final String BigFont_E = "</G>";//放大结束

    private static final String BoldFont_S = "<B>";//加粗开始
    private static final String BoldFont_E = "</B>";//加粗结束

    private static final String BigBoldFont_S = "<GB>";//加粗开始
    private static final String BigBoldFont_E = "</GB>";//加粗结束


    private static final String HeightFont_S = "<H>";//字体变高
    private static final String HeightFont_E = "</H>";//字体变高结束

    private static final String WidthFont_S = "<W>";//字体变宽
    private static final String WidthFont_E = "</W>";//字体变宽结束

    private static final String CenterBigFont_S = "<C><G>";//居中放大
    private static final String CenterBigFont_E = "</G></C>";//居中放大结束


    private static final String CenterFont_S = "<C>";//居中
    private static final String CenterFont_E = "</C>";//居中结束

    private static final String RightFont_S = "<R>";//右对齐开始
    private static final String RightFont_E = "</R>";//右对齐结束


    private static final String QR_S = "<QR>";//二维码开始<QRcode></QRcode>测试

    private static final String QR_E = "</QR>";//二维码结束

    private static final String SOUND_S = "<SOUND>";
    private static final String SOUND_E = "</SOUND>";

    private static final String TSOUND_S = "<TSOUND>";
    private static final String TSOUND_E = "</TSOUND>";

    private static final String SOUND64_S = "<SOUND64>";
    private static final String SOUND64_E = "</SOUND64>";

    private static final String TSOUND64_S = "<TSOUND64>";
    private static final String TSOUND64_E = "</TSOUND64>";

    public interface SystemSound {
        String newPrint = "new_print";
        String newOrder = "new_order";
    }


    private StringBuilder stringBuilder = new StringBuilder();
    private PrintTableConfig printTableConfig=new PrintTableConfig();

    private Integer fontBig=0;
    private Integer fontWidth=0;

    /**
     * 纸张宽带 mm
     *
     * @param pageWidth
     */
    public ClientPrintContext(Integer pageWidth) {
        this.init(pageWidth,8);
    }
    public ClientPrintContext(Integer pageWidth,Integer fontSize) {
        this.init(pageWidth,fontSize);
    }
    public void init(Integer pageWidth,Integer fontSize) {
        if(fontSize==null){
            fontSize=8;
        }
        pageWidth = pageWidth == null ? 800 : pageWidth;
        double pageWidth2 = pageWidth / 3.55555555555555;

        this.maxSize = ((int) (pageWidth2 - pageWidth2 * 0.1) / fontSize) * 2;
        System.out.println(maxSize);
    }

    @Override
    public PrintTableConfig getTableConfig() {
        return printTableConfig;
    }


    @Override
    public ClientPrintContext BR() {
        return this.addText(BR);
    }

    @Override
    public ClientPrintContext addText(String s) {
        stringBuilder.append(s);
        return this;
    }

    @Override
    public ClientPrintContext addQRcode(String s) {
        return this.addText(QR_S)
                .addText(s)
                .addText(QR_E);
    }

    @Override
    public ClientPrintContext addSystemSound(String fileName, boolean... b) {
        if (b != null && b.length > 0 && b[0]) {
            return this.addText(TSOUND_S)
                    .addText(fileName)
                    .addText(TSOUND_E);
        }
        return this.addText(SOUND_S)
                .addText(fileName)
                .addText(SOUND_E);
    }

    @Override
    public ClientPrintContext addSound(String base64, boolean... b) {
        if (b != null && b.length > 0 && b[0]) {
            return this.addText(TSOUND64_S)
                    .addText(base64)
                    .addText(TSOUND64_E);
        }
        return this.addText(SOUND64_S)
                .addText(base64)
                .addText(SOUND64_E);
    }


    @Override
    public ClientPrintContext alignCenter() {
        return this.addText(CenterFont_S);
    }

    @Override
    public ClientPrintContext alignCenterClear() {
        return this.addText(CenterFont_E);
    }

    @Override
    public ClientPrintContext alignRight() {
        return this.addText(RightFont_S);
    }

    @Override
    public ClientPrintContext alignRightClear() {
        return this.addText(RightFont_E);
    }

    @Override
    public ClientPrintContext fontBold() {
        return this.addText(BoldFont_S);
    }

    @Override
    public ClientPrintContext fontBoldClear() {
        return this.addText(BoldFont_E);
    }

    @Override
    public ClientPrintContext fontHeight() {
        return this.addText(HeightFont_S);
    }

    @Override
    public ClientPrintContext fontHeightClear() {
        return this.addText(HeightFont_E);
    }

    @Override
    public ClientPrintContext fontWidth() {
        fontWidth++;
        return this.addText(WidthFont_S);
    }

    @Override
    public ClientPrintContext fontWidthClear() {
        fontWidth--;
        return this.addText(WidthFont_E);
    }

    @Override
    public ClientPrintContext fontBig() {
        fontBig++;
        return this.addText(BigFont_S);
    }

    @Override
    public ClientPrintContext fontBigClear() {
        fontBig--;
        return this.addText(BigFont_E);
    }




    @Override
    public int getMaxSize() {
        int b=1;
        if(fontWidth>0){
            b++;
        }
        if(fontBig>0){
            b++;
        }
        return maxSize/b;
    }

    @Override
    public String getContext() {
        return stringBuilder.toString();
    }


    @Override
    public ClientPrintContext clear() {
        stringBuilder = new StringBuilder();
        return this;
    }

    @Override
    public String toString() {
        return this.getContext();
    }


}
