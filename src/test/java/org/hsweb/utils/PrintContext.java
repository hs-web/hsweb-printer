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
 * Created by xiong on 2016/3/1 .
 */
public interface PrintContext {
    String Blank = " ";

    int getMaxSize();


    static String getBlankString(int size){
        if(size<1){
            return "";
        }
        StringBuilder builder=new StringBuilder();
        for (int i=0;i<size;i++){
            builder.append(Blank);
        }
        return builder.toString();
    }

    default PrintContext appendBlank(){
        this.append(Blank).append(Blank);
        return this;
    }
    default PrintContext appendBlank(int size){
            this.append(getBlankString(size));
        return this;
    }
    PrintContext BR();

    default PrintContext appendDecollator(){
        for (  int i=0;i<getMaxSize();i++ ) {
            this.append("-");
        }
       this.BR();
        return this;
    }
    default PrintContext append(String s, int padding){

        int rowsize=getMaxSize()-padding;
        if(rowsize<1){
            rowsize=getMaxSize();
        }
        int k = 0x80;
        if (s == null)
            return this;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            char cc=c[i];

            len++;
            boolean han=!(cc / k == 0 ? true : false);
            if (han) {
                len++;
            }

            if(len>rowsize){
                len=han?2:1;
                this.BR().appendBlank(padding);
            }
            this.append(new String(new char[]{cc}));
        }
        return this;
    }
    default PrintContext append(String s, int padding, String s2){

        if(lengthPadding(s,padding)){
            this.append(s+s2,padding);
        }else {
            this.append(s).BR()
            .appendBlank(padding).append(s2,padding);
        }
        return this;
    }
    /**
     * 注意注意 width为比例 不是字数
     * @param width
     * @return
     */
    PrintContext tableWidth(int... width);
    PrintContext tableAlign(int... align);

    PrintContext tablePadding(int padding);
    /**
     * 注意注意 width为比例 不是字数   padding字数 padding 会减去 width[0]的长度中的字数
     * @param width
     * @return
     */
    PrintContext tableWidthPadding(int padding, int... width);
    PrintContext appendTable(String... text);

    int[] getTableWidth();
    int[] getTableAlign();
    int getTablepadding();
    int getAlignLeft();
    int getAlignCenter();
    int getAlignRight();
     default PrintContext appendTable(String start, String[] text, String end){
         int[] width=getTableWidth();
         int[] align=getTableAlign();

         this.append(start);
         if(width==null||text==null || (width.length!=text.length)||(align!=null&&(align.length!=text.length))){
             if(text!=null){
                 for (String s:text){
                     this.append(s);
                 }
             }
         }else{
             int maxRow=0;

             for(int i=0;i<text.length;i++){
                 int maxLength =  this.getTableWidthMaxSize(i);
                 if(maxLength<1){
                     maxLength=1;
                 }
                 int maxSizeRow =lengthMaxSizeRow(text[i],maxLength);
                 maxRow=maxRow<maxSizeRow?maxSizeRow:maxRow;
             }

             for(int i=0;i<maxRow;i++){
                 for(int j=0;j<text.length;j++){
                     int maxLength =  this.getTableWidthMaxSize(j);
                     if(j==0) {
                         int tablepadding = getTablepadding();
                         if (maxLength < 1) {
                             this.appendBlank(tablepadding - (1 - maxLength));
                             maxLength = 1;
                         } else {
                             this.appendBlank(tablepadding);
                         }
                     }

                     String rowString=getRowString(getString(text[j]),maxLength,i+1);
                     if(align!=null){
                         int ali = align[j];
                         if(ali==getAlignCenter()){
                             rowString=getCentetString(rowString,maxLength);
                         }else if(ali==getAlignRight()){
                             rowString=getRightString(rowString,maxLength);
                         }else {
                             rowString=getLeftString(rowString,maxLength);
                         }
                     }
                     this.append(rowString);
                 }
                 if(i+1<maxRow){
                     this.BR();
                 }
             }
         }
         this.append(end);
         this.BR();
         return this;
    }

    default int getTableWidthMaxSize(int index) {
        int maxLength = getMaxSize() * getTableWidth()[index] / 100;
        if(index==0){
            maxLength=maxLength-getTablepadding();
        }
        return maxLength;
    }

    PrintContext appendTableHeightFont(String... text);
   /* PrintContext appendTableWidthFont(String...text ){
        return appendTable(PrintContext.WidthFont_S,text,PrintContext.WidthFont_E);
    }
    PrintContext appendTableFontBigFont(String...text ){
        return appendTable(PrintContext.BigFont_S,text,PrintContext.BigFont_E);
    }
    PrintContext appendTableFontBoldFont(String...text ){

        return appendTable(PrintContext.BoldFont_S,text,PrintContext.BoldFont_E);
    }*/
    static String getLeftString(String s, int maxLength){
        if(null==s||s.length()==0){
            return getBlankString(maxLength);
        }
        int krows=maxLength-length(s);
        return s+getBlankString(krows);
    }
    static String getRightString(String s, int maxLength){
        if(null==s||s.length()==0){
            return getBlankString(maxLength);
        }
        int krows=maxLength-length(s);
        return getBlankString(krows)+s;
    }
    static String getCentetString(String s, int maxLength){
        if(null==s||s.length()==0){
            return getBlankString(maxLength);
        }
        int krows=maxLength-length(s);
        boolean isOu=krows%2==0;
        return getBlankString(krows/2)+s+getBlankString(isOu?krows/2:krows/2+1);
    }

    static String getRowString(String s, int maxLength, int row){
        if(length(s)<maxLength*(row-1)){
            return "";
        }

        char[] c = s.toCharArray();
        int len = 0;
        int k = 0x80;
        StringBuilder builder=new StringBuilder();
        int srow=1;
        for (int i = 0; i < c.length; i++) {
            char cc=c[i];

            len++;
            boolean han=!(cc / k == 0 ? true : false);
            if (han) {
                len++;
            }
            if(len>maxLength){
                len=han?2:1;
                srow++;
            }
            if(srow>row){
                return builder.toString();
            }
            if(srow==row){
                builder.append(new String(new char[]{cc}));
            }
        }
        return builder.toString();
    }

    PrintContext append(String s);
    default String getString(String s){
       return s==null?"":s.replaceAll("\t|\n","");
    }


    default PrintContext append(Object s){
        this.append(s==null?"":s.toString());
        return this;
    }
    default PrintContext appendTextAndBR(String s){
        this.append(s).BR();
        return this;
    }
    default PrintContext appendTextAndBR(Object s){
        this.append(s).BR();
        return this;
    }
    PrintContext appendRightFont(String s);
    default PrintContext appendRightFont(Object s){
        this.appendRightFont(s==null?"":s.toString());
        return this;
    }
    PrintContext appendRightBigFont(String s);
    default PrintContext appendRightBigFont(Object s){
        this.appendRightBigFont(s==null?"":s.toString());
        return this;
    }
    PrintContext appendBigFont(String s);
    default PrintContext appendBigFont(Object s){
        this.appendBigFont(s==null?"":s.toString());
        return this;
    }
    PrintContext appendBoldFont(String s);
    default PrintContext appendBoldFont(Object s){
        this.appendBoldFont(s==null?"":s.toString());
        return this;
    }
    PrintContext appendHeightFont(String s);
    default PrintContext appendHeightFont(Object s){
        this.appendHeightFont(s==null?"":s.toString());
        return this;
    }
    PrintContext appendWidthFont(String s);
    default PrintContext appendWidthFont(Object s){
        this.appendWidthFont(s==null?"":s.toString());
        return this;
    }
    PrintContext appendCenterFont(String s);
    default PrintContext appendCenterFont(Object s){
        this.appendCenterFont(s==null?"":s.toString());
        return this;
    }
    PrintContext appendCenterBigFont(String s);
    default PrintContext appendCenterBigFont(Object s){
        this.appendCenterBigFont(s==null?"":s.toString());
        return this;
    }
    PrintContext appendCenterBoldFont(String s);
    default PrintContext appendCenterBoldFont(Object s){
        this.appendCenterBoldFont(s==null?"":s.toString());
        return this;
    }
    PrintContext appendQRcode(String s);

    String getContext();

    PrintContext clear();



    static int length(String s) {
        int k = 0x80;
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!(c[i] / k == 0 ? true : false)) {
                len++;
            }
        }
        return len;
    }
    default int lengthPaddingRow(String s, int padding) {
        return lengthMaxSizeRow(s,this.getMaxSize()-padding);
    }
    default boolean lengthPadding(String s, int padding) {
        return lengthMaxSize(s,this.getMaxSize()-padding);
    }
    static int lengthMaxSizeRow(String s, int maxSize) {
        return (length(s)+maxSize-1)/maxSize;
    }
    static boolean lengthMaxSize(String s, int maxSize) {
        return length(s)>maxSize;
    }

}
