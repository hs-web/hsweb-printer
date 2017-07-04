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

package org.hsweb.printer.dtos;

import java.io.Serializable;

/**
 * Created by xiongchuang on 2016/8/18 .
 */
public class PrintInputDTO implements Serializable{
    private String printerName;
    private String printDocName="java打印";
    private String printType;
    private Double pageWidth;
    private String printText;
    private Integer fontSize;

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrintDocName() {
        return printDocName;
    }

    public void setPrintDocName(String printDocName) {
        this.printDocName = printDocName;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public Double getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(Double pageWidth) {
        this.pageWidth = pageWidth;
    }

    public String getPrintText() {
        return printText;
    }

    public void setPrintText(String printText) {
        this.printText = printText;
    }
}
