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


import java.io.*;
import java.util.Base64;
import java.util.function.Consumer;

/**
 * Created by xiong on 2016/3/1 .
 */
public interface PrintContext {
    interface TableAlign {
        int left = 0;
        int center = 1;
        int right = 2;
    }

    String Blank = " ";//空格

    PrintTableConfig getTableConfig();

    /**
     * 获取纸张普通字体一行的最大的英文字数
     *
     * @return
     */
    int getMaxSize();

    /**
     * 当满足条件执行consumer的代码
     *
     * @param b
     * @param consumer
     * @return
     */
    default PrintContext when(boolean b, Consumer<PrintContext> consumer) {
        if (b) {
            consumer.accept(this);
        }
        return this;
    }

    /**
     * 清空
     *
     * @return
     */
    PrintContext clear();

    String getContext();

    /*****************************************************************************/
    /*************************         基础添加        ****************************/
    /*****************************************************************************/

    /**
     * 换行
     *
     * @return
     */
    PrintContext BR();


    /**
     * 画分割线
     *
     * @return
     */
    default PrintContext addDecollator(int type) {
        for (int i = 0; i < getMaxSize(); i++) {
            this.addText("-");
        }
        this.BR();
        return this;
    }


    /**
     * 添加文字
     *
     * @param s
     * @return
     */
    PrintContext addText(String s);

    default PrintContext addText(String s, int padding) {

        int rowsize = getMaxSize() - padding;
        if (rowsize < 1) {
            rowsize = getMaxSize();
        }
        int k = 0x80;
        if (s == null)
            return this;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            char cc = c[i];

            len++;
            boolean han = !(cc / k == 0 ? true : false);
            if (han) {
                len++;
            }

            if (len > rowsize) {
                len = han ? 2 : 1;
                this.BR().addBlank(padding);
            }
            this.addText(new String(new char[]{cc}));
        }
        return this;
    }

    default PrintContext addText(String s, int padding, String s2) {

        if (lengthPadding(s, padding)) {
            this.addText(s + s2, padding);
        } else {
            this.addText(s).BR()
                    .addBlank(padding).addText(s2, padding);
        }
        return this;
    }

    /**
     * 写入文字后换行
     *
     * @param s
     * @return
     */
    default PrintContext addTextAndBR(String s) {
        this.addText(s).BR();
        return this;
    }

    /**
     * 添加空格
     *
     * @param size
     * @return
     */
    default PrintContext addBlank(int size) {
        this.addText(getBlankString(size));
        return this;
    }

    /**
     * 添加二维码
     *
     * @param s
     * @return
     */
    PrintContext addQRcode(String s);

    PrintContext addSystemSound(String fileName, boolean... b);

    PrintContext addSound(String base64, boolean... b);

    default PrintContext addSound(File file, boolean... b){
        if(file!=null&&file.exists()){
            try {
                return this.addSound(new FileInputStream(file),b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    default PrintContext addSound(InputStream in, boolean... bb){
        if(in==null){
            return this;
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(1000)){

            byte[] b = new byte[1000];
            int n;
            while ((n = in.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            return this.addSound(new String(Base64.getEncoder().encode(bos.toByteArray())),bb);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this;
    }

    /*****************************************************************************/
    /*************************          表格          ****************************/
    /*****************************************************************************/

    /**
     * 注意注意 width为比例 不是字数
     *
     * @param widths
     * @return
     */
    default PrintContext tableWidth(int... widths) {
        PrintTableConfig printTableConfig = getTableConfig();
        printTableConfig.tableWidth.clear();
        if (widths.length > 0) {
            for (int width : widths) {
                printTableConfig.tableWidth.add(width);
            }
        }
        return this;
    }

    default PrintContext tableAlign(int... aligns) {
        PrintTableConfig printTableConfig = getTableConfig();
        printTableConfig.tableAlign.clear();
        if (aligns.length > 0) {
            for (int align : aligns) {
                printTableConfig.tableAlign.add(align);
            }
        }
        return this;
    }

    default PrintContext tablePadding(int padding) {
        PrintTableConfig printTableConfig = getTableConfig();
        printTableConfig.tablePadding = padding;
        return this;
    }

    /**
     * 注意注意 width为比例 不是字数   padding字数 padding 会减去 width[0]的长度中的字数
     *
     * @param width
     * @return
     */
    default PrintContext tableWidthPadding(int padding, int... width) {
        this.tablePadding(padding);
        this.tableWidth(width);
        return this;
    }

    default PrintContext addTable(String... text) {
        if (verifyTable(text)) {
            int maxRow = getMaxRow(text);
            for (int i = 0; i < maxRow; i++) {
                tableColumn(text, i);
                if (i + 1 < maxRow) {
                    this.BR();
                }
            }
        }
        this.BR();
        return this;
    }
    default PrintContext addTableFontBold(String... text) {
        return this.fontBold().addTable(text).fontBoldClear();
    }
    default PrintContext addTableFontHight(String... text) {
        return this.fontHeight().addTable(text).fontHeightClear();
    }


    default int getTableWidthMaxSize(int index) {
        PrintTableConfig printTableConfig = getTableConfig();
        if (index >= printTableConfig.tableWidth.size()) {
            return 1;
        }

        int maxLength = getMaxSize() * printTableConfig.tableWidth.get(index) / 100;
        if (index == 0) {
            maxLength = maxLength - printTableConfig.tablePadding;
        }
        return maxLength;
    }

    default boolean verifyTable(String[] text) {
        PrintTableConfig printTableConfig = getTableConfig();
        if (text == null || (printTableConfig.tableWidth.size() != text.length) || (printTableConfig.tableAlign.size() != text.length)) {
            if (text != null) {
                for (String s : text) {
                    this.addText(s);
                }
            }
            return false;
        }
        return true;
    }

    default int getMaxRow(String[] text) {
        int maxRow = 1;
        for (int i = 0; i < text.length; i++) {
            int maxLength = this.getTableWidthMaxSize(i);
            if (maxLength < 1) {
                maxLength = 1;
            }
            int maxSizeRow = lengthMaxSizeRow(text[i], maxLength);
            maxRow = maxRow < maxSizeRow ? maxSizeRow : maxRow;
        }
        return maxRow;
    }



    default void tableColumn(String[] text, int i) {
        PrintTableConfig printTableConfig = getTableConfig();
        for (int j = 0; j < text.length; j++) {
            int maxLength = this.getTableWidthMaxSize(j);
            if (j == 0) {
                int tablepadding = printTableConfig.tablePadding;
                if (maxLength < 1) {
                    this.addBlank(tablepadding - (1 - maxLength));
                    maxLength = 1;
                } else {
                    this.addBlank(tablepadding);
                }
            }

            String rowString = getRowString(getString(text[j]), maxLength, i + 1);
            rowString = tableColumnAlign(j, maxLength, rowString);
            this.addText(rowString);
        }
    }

    default String tableColumnAlign(int j, int maxLength, String rowString) {
        PrintTableConfig printTableConfig = getTableConfig();
        if (printTableConfig.tableAlign.size() > 0) {
            int ali = printTableConfig.tableAlign.get(j);
            if (ali == TableAlign.center) {
                rowString = getCentetString(rowString, maxLength);
            } else if (ali == TableAlign.right) {
                rowString = getRightString(rowString, maxLength);
            } else {
                rowString = getLeftString(rowString, maxLength);
            }
        }
        return rowString;
    }

     /*  PrintContext appendTableHeightFont(String... text);

    PrintContext appendTableWidthFont(String...text ){
        return appendTable(PrintContext.WidthFont_S,text,PrintContext.WidthFont_E);
    }
    PrintContext appendTableFontBigFont(String...text ){
        return appendTable(PrintContext.BigFont_S,text,PrintContext.BigFont_E);
    }
    PrintContext appendTableFontBoldFont(String...text ){

        return appendTable(PrintContext.BoldFont_S,text,PrintContext.BoldFont_E);
    }*/


    /*****************************************************************************/
    /*************************         对齐方式        ****************************/
    /*****************************************************************************/

    /**
     * 左对齐
     *
     * @return
     */
   /* PrintContext alignLeft();

    PrintContext alignLeftClear();

    default PrintContext alignLeft(String s) {
        this.alignLeft();
        this.addText(s);
        this.alignLeftClear();
        return this;
    }

    default PrintContext alignLeft(Consumer<PrintContext> consumer) {
        this.alignLeft();
        consumer.accept(this);
        this.alignLeftClear();
        return this;
    }*/

    /**
     * 居中对齐
     *
     * @return
     */
    PrintContext alignCenter();

    PrintContext alignCenterClear();

    default PrintContext alignCenter(String s) {
        this.alignCenter();
        this.addText(s);
        this.alignCenterClear();
        return this;
    }

    default PrintContext alignCenter(Consumer<PrintContext> consumer) {
        this.alignCenter();
        consumer.accept(this);
        this.alignCenterClear();
        return this;
    }
    default PrintContext alignCenterFontBig(String s){
        return this.alignCenter().fontBig(s).alignCenterClear();
    }
    default PrintContext alignCenterFontBigBold(String s){
        return this.alignCenter().fontBigBold(s).alignCenterClear();
    }
    default PrintContext alignCenterFontWidth(String s){
        return this.alignCenter().fontWidth(s).alignCenterClear();
    }
    default PrintContext alignCenterFontHeight(String s){
        return this.alignCenter().fontHeight(s).alignCenterClear();
    }

    /**
     * 右对齐
     *
     * @return
     */
    PrintContext alignRight();

    PrintContext alignRightClear();

    default PrintContext alignRight(String s) {
        this.alignRight();
        this.addText(s);
        this.alignRightClear();
        return this;
    }

    default PrintContext alignRight(Consumer<PrintContext> consumer) {
        this.alignRight();
        consumer.accept(this);
        this.alignRightClear();
        return this;
    }

    default PrintContext alignRightFontBig(String s){
        return this.alignRight().fontBig(s).alignRightClear();
    }
    default PrintContext alignRightFontBigBold(String s){
        return this.alignRight().fontBigBold(s).alignRightClear();
    }
    default PrintContext alignRightFontWidth(String s){
        return this.alignRight().fontWidth(s).alignRightClear();
    }
    default PrintContext alignRightFontHeight(String s){
        return this.alignRight().fontHeight(s).alignRightClear();
    }


    /*****************************************************************************/
    /*************************         字体大小        ****************************/
    /*****************************************************************************/
    /**
     * 字体加粗
     * @return
     */
    PrintContext fontBold();

    PrintContext fontBoldClear();

    default PrintContext fontBold(String s) {
        this.fontBold();
        this.addText(s);
        this.fontBoldClear();
        return this;
    }

    default PrintContext fontBold(Consumer<PrintContext> consumer) {
        this.fontBold();
        consumer.accept(this);
        this.fontBoldClear();
        return this;
    }

    /**
     * 倍高字体
     * @return
     */
    PrintContext fontHeight();

    PrintContext fontHeightClear();

    default PrintContext fontHeight(String s) {
        this.fontHeight();
        this.addText(s);
        this.fontHeightClear();
        return this;
    }

    default PrintContext fontHeight(Consumer<PrintContext> consumer) {
        this.fontHeight();
        consumer.accept(this);
        this.fontHeightClear();
        return this;
    }

    /**
     * 倍宽字体
     * @return
     */
    PrintContext fontWidth();

    PrintContext fontWidthClear();

    default PrintContext fontWidth(String s) {
        this.fontWidth();
        this.addText(s);
        this.fontWidthClear();
        return this;
    }

    default PrintContext fontWidth(Consumer<PrintContext> consumer) {
        this.fontWidth();
        consumer.accept(this);
        this.fontWidthClear();
        return this;
    }

    /**
     * 字体加大
     * @return
     */
    PrintContext fontBig();

    PrintContext fontBigClear();

    default PrintContext fontBig(String s) {
        this.fontBig();
        this.addText(s);
        this.fontBigClear();
        return this;
    }

    default PrintContext fontBig(Consumer<PrintContext> consumer) {
        this.fontBig();
        consumer.accept(this);
        this.fontBigClear();
        return this;
    }

    default PrintContext fontBigBold(String s){
        return this.fontBold().fontBig(s).fontBoldClear();
    }





    default String getString(String s) {
        return s == null ? "" : s.replaceAll("\t|\n", "");
    }

    static String getBlankString(int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(Blank);
        }
        return builder.toString();
    }

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

    static int lengthMaxSizeRow(String s, int maxSize) {
        return (length(s) + maxSize - 1) / maxSize;
    }

    default int lengthPaddingRow(String s, int padding) {
        return lengthMaxSizeRow(s, this.getMaxSize() - padding);
    }

    static boolean lengthMaxSize(String s, int maxSize) {
        return length(s) > maxSize;
    }

    default boolean lengthPadding(String s, int padding) {
        return lengthMaxSize(s, this.getMaxSize() - padding);
    }

    static String getLeftString(String s, int maxLength) {
        if (null == s || s.length() == 0) {
            return getBlankString(maxLength);
        }
        int krows = maxLength - length(s);
        return s + getBlankString(krows);
    }

    static String getRightString(String s, int maxLength) {
        if (null == s || s.length() == 0) {
            return getBlankString(maxLength);
        }
        int krows = maxLength - length(s);
        return getBlankString(krows) + s;
    }

    static String getCentetString(String s, int maxLength) {
        if (null == s || s.length() == 0) {
            return getBlankString(maxLength);
        }
        int krows = maxLength - length(s);
        boolean isOu = krows % 2 == 0;
        return getBlankString(krows / 2) + s + getBlankString(isOu ? krows / 2 : krows / 2 + 1);
    }

    static String getRowString(String s, int maxLength, int row) {
        if (length(s) < maxLength * (row - 1)) {
            return "";
        }

        char[] c = s.toCharArray();
        int len = 0;
        int k = 0x80;
        StringBuilder builder = new StringBuilder();
        int srow = 1;
        for (int i = 0; i < c.length; i++) {
            char cc = c[i];

            len++;
            boolean han = !(cc / k == 0 ? true : false);
            if (han) {
                len++;
            }
            if (len > maxLength) {
                len = han ? 2 : 1;
                srow++;
            }
            if (srow > row) {
                return builder.toString();
            }
            if (srow == row) {
                builder.append(new String(new char[]{cc}));
            }
        }
        return builder.toString();
    }


}
