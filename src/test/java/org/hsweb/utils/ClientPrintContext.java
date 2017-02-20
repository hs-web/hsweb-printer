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
    public static final int  align_left=1;
    public static final int  align_center=2;
    public static final int  align_right=3;

    private final int maxSize;

    private int[] width;
    private int[] align;
    private int tablepadding=0;

    public static final String BR = "<BR>";//换行

    public static final String RightFont_S ="<R>";//右对齐开始
    public static final String RightFont_E ="</R>";//右对齐结束

    public static final String BigFont_S = "<G>";//放大
    public static final String BigFont_E = "</G>";//放大结束

    public static final String BoldFont_S = "<B>";//加粗开始
    public static final String BoldFont_E = "</B>";//加粗结束

    public static final String BigBoldFont_S = "<GB>";//加粗开始
    public static final String BigBoldFont_E = "</GB>";//加粗结束



    public static final String HeightFont_S = "<H>";//字体变高
    public static final String HeightFont_E = "</H>";//字体变高结束

    public static final String WidthFont_S = "<W>";//字体变宽
    public static final String WidthFont_E = "</W>";//字体变宽结束

    public static final String CenterFont_S="<C>";//居中
    public static final String CenterFont_E="</C>";//居中结束

    public static final String CenterBigFont_S="<C><G>";//居中放大
    public static final String CenterBigFont_E="</G></C>";//居中放大结束

    public static final String QR_S="<QR>";//二维码开始<QRcode></QRcode>测试

    public static final String QR_E="</QR>";//二维码结束


    private StringBuilder stringBuilder=new StringBuilder();


    public ClientPrintContext(Integer pageWidth) {
        pageWidth=pageWidth==null?800:pageWidth;
        double pageWidth2 = pageWidth / 3.55555555555555;
        this.maxSize=((int)(pageWidth2-pageWidth2*0.1)/8)*2;
        System.out.println(maxSize);
    }

    @Override
    public ClientPrintContext BR(){
        this.append(BR);
        return this;
    }

    /**
     * 注意注意  width为比例 不是字数
     * @param width
     * @return
     */
    @Override
    public ClientPrintContext tableWidth(int...width){
        this.tablepadding=0;
        this.width=width;
        return this;
    }
    @Override
    public ClientPrintContext tableAlign(int...align){

        this.align=align;

        return this;
    }

    @Override
    public ClientPrintContext tablePadding(int padding) {
        this.tablepadding=padding;
        return this;
    }

    @Override
    public ClientPrintContext tableWidthPadding(int padding, int...width){
        this.tablepadding=padding;
        this.width=width;
        return this;
    }
    @Override
    public ClientPrintContext appendTable(String...text ){
        this.appendTable("",text,"");
        return this;
    }

    @Override
    public int[] getTableWidth() {
        return width;
    }

    @Override
    public int[] getTableAlign() {
        return align;
    }

    @Override
    public int getTablepadding() {
        return tablepadding;
    }

    @Override
    public int getAlignLeft() {
        return align_left;
    }

    @Override
    public int getAlignCenter() {
        return align_center;
    }

    @Override
    public int getAlignRight() {
        return align_right;
    }


    @Override
    public ClientPrintContext appendTableHeightFont(String...text ){
        this.appendTable(ClientPrintContext.HeightFont_S,text, ClientPrintContext.HeightFont_E);
        return this;
    }


    @Override
    public ClientPrintContext append(String s){
        stringBuilder.append(getString(s));
        return this;
    }

    @Override
    public ClientPrintContext appendRightFont(String s){
        this.stringBuilder.append(RightFont_S);
        this.append(s);
        this.stringBuilder.append(RightFont_E);
        return this;
    }
    @Override
    public ClientPrintContext appendRightBigFont(String s){
        this.stringBuilder.append(BigFont_S);
        this.append(s);
        this.stringBuilder.append(BigFont_E);
        return this;
    }
    @Override
    public ClientPrintContext appendBigFont(String s){
        this.append(BigFont_S).append(s).append(BigFont_E);
        return this;
    }
    @Override
    public ClientPrintContext appendBoldFont(String s){
        this.append(BoldFont_S).append(s).append(BoldFont_E);
        return this;
    }
    public ClientPrintContext appendBigBoldFont(String s){
        this.append(BigBoldFont_S).append(s).append(BigBoldFont_E);
        return this;
    }
    public ClientPrintContext appendBigBoldFont(Object s){
        this.appendBigBoldFont(s==null?"":s.toString());
        return this;
    }
    @Override
    public ClientPrintContext appendHeightFont(String s){
        this.append(HeightFont_S).append(s).append(HeightFont_E);
        return this;
    }
    @Override
    public ClientPrintContext appendWidthFont(String s){
        this.append(WidthFont_S).append(s).append(WidthFont_E);
        return this;
    }
    @Override
    public ClientPrintContext appendCenterFont(String s){
        this.append(CenterFont_S).append(s).append(CenterFont_E);
        return this;
    }
    @Override
    public ClientPrintContext appendCenterBigFont(String s){
        this.append(CenterBigFont_S).append(s).append(CenterBigFont_E);
        return this;
    }
    @Override
    public ClientPrintContext appendCenterBoldFont(String s){
        this.append(CenterFont_S).append(BoldFont_S).append(s).append(BoldFont_E).append(CenterFont_E);
        return this;
    }
    @Override
    public ClientPrintContext appendQRcode(String s){
        this.append(QR_S).append(s).append(QR_E);
        return this;
    }
    @Override
    public int getMaxSize(){
        return maxSize;
    }

    @Override
    public String getContext(){
        return stringBuilder.toString();
    }


    @Override
    public ClientPrintContext clear(){
        stringBuilder=new StringBuilder();
        return this;
    }

    @Override
    public String toString() {
        return this.getContext();
    }


}
