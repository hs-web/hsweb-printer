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

package org.hswebframework.printer.executor;



import org.hswebframework.printer.Pager;
import org.hswebframework.printer.PrintCommand;

import java.awt.*;
import java.awt.print.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhouhao
 * @author xiongchuang
 */
public class DefaultPrintable implements Printable {

    private double width;

    private double height;

    private PrintCommand printCommand;

    private Pager noPrintPager;

    private int xPadding = 0;

    private int yPadding = 0;

    private Map<Integer, AtomicInteger> renderCounter = new HashMap<>();

    public DefaultPrintable(PrintCommand printCommand) {
        this.printCommand = printCommand;
        this.width = printCommand.getPaper().getWidth();
        this.height = printCommand.getPaper().getHeight();
        noPrintPager = printCommand.getPagers().get(0);
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        AtomicInteger counter = renderCounter.computeIfAbsent(pageIndex, p -> new AtomicInteger());
        List<Pager> pagerList = printCommand.getPagers();
        if (pagerList.size() <= pageIndex) {
            return NO_SUCH_PAGE;
        }
        if (counter.get() == 1) {
            ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            noPrintPager = pagerList.get(pageIndex);
            noPrintPager.getLayers().forEach(layer -> layer.draw(((Graphics2D) graphics)));
        }
        counter.incrementAndGet();
        return PAGE_EXISTS;
    }

    /**
     * 获取打印页
     *
     * @return 1.0
     */
    public Paper getPaper() {
        //通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper paper = new Paper();
        //纸张大小
        paper.setSize(getWidth(), getHeight());
        paper.setImageableArea(getXPadding(), getYPadding(), getWidth(), getHeight());
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
        format.setOrientation(noPrintPager.getOrientation());
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
        int pageSize = printCommand.getPagers().size();

        for (int i = 0; i < pageSize; i++) {
            book.append(this, pageFormat);
        }
        return book;
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
        return xPadding;
    }

    /**
     * 纸张上边距
     *
     * @return
     */
    double getYPadding() {
        return yPadding;
    }

}
