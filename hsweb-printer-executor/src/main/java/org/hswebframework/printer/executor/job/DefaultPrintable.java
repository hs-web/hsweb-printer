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

package org.hswebframework.printer.executor.job;

import org.hswebframework.printer.designer.Pager;
import org.hswebframework.printer.designer.PrintCommand;
import org.hswebframework.printer.executor.PagerGraphicsService;

import java.awt.*;
import java.awt.print.*;
import java.util.List;

/**
 * @author zhouhao
 * @author xiongchuang
 */
public class DefaultPrintable implements Printable {

    private double width = 595;

    private double height = 842;

    private PrintCommand printCommand;

    private PagerGraphicsService pagerGraphicsService;

    public DefaultPrintable(PrintCommand printCommand, PagerGraphicsService pagerGraphicsService) {
        this.printCommand = printCommand;
        this.pagerGraphicsService = pagerGraphicsService;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        List<Pager> pagerList = printCommand.getPagers();
        if (pagerList.size() <= pageIndex) {
            return NO_SUCH_PAGE;
        }
        pagerGraphicsService.doGraphics(pagerList.get(pageIndex), graphics);

        return PAGE_EXISTS;
    }

    /**
     * 获取打印页
     *
     * @return
     */
    public Paper getPaper() {
        //    通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper paper = new Paper();
        paper.setSize(getWidth() + getWidth() * 0.1, getHeight());//纸张大小
        paper.setImageableArea(0, 0, getWidth() + getWidth() * 0.1, getHeight());//A4(595 X 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
        return paper;
    }

    /**
     * 获取打印页样式
     *
     * @return
     */
    public PageFormat getPageFormat() {
        Paper paper = getPaper();
        //    设置成竖打
        PageFormat format = new PageFormat();
        format.setOrientation(getDirection());
        format.setPaper(paper);
        return format;
    }

    /**
     * 获取打印文档
     *
     * @return
     */
    public Book getBook() {
        PageFormat pageFormat = getPageFormat();
        //    通俗理解就是书、文档
        Book book = new Book();
        //    把 PageFormat 和 Printable 添加到书中，组成一个页面
        for (int i = 0; i < printCommand.getPageSize(); i++) {
            book.append(this, pageFormat);
        }
        return book;
    }

    /**
     * 打印文档方向
     *
     * @return
     */
    int getDirection() {
        return PageFormat.PORTRAIT;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 纸张左边距
     *
     * @return
     */
    double getXPadding() {
        return 0;
    }

    /**
     * 纸张上边距
     *
     * @return
     */
    double getYPadding() {
        return getWidth() * 0.1;
    }

}
