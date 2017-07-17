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

package org.hsweb.printer.utils.printable;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;

/**
 * Created by xiongchuang on 2016/8/20 .
 */
public interface BasePrintable extends Printable {
    /**
     * 打印文档名称
     * @return
     */
    String getPrintDocName();

    /**
     * 获取打印页
     * @return
     */
    default Paper getPaper(){
        //    通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper p = new Paper();
        p.setSize(getWidth()+getWidth()*0.1,getHeight());//纸张大小
        p.setImageableArea(0,0, getWidth()+getWidth()*0.1,getHeight());//A4(595 X 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
        return p;
    }

    /**
     * 获取打印页样式
     * @return
     */
    default PageFormat getPageFormat(){
        Paper p=getPaper();

        //    设置成竖打
        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(p);
        return pf;
    }

    /**
     * 获取打印文档
     * @return
     */
    default Book getBook(){
        PageFormat pageFormat=getPageFormat();

        //    通俗理解就是书、文档
        Book book = new Book();
        //    把 PageFormat 和 Printable 添加到书中，组成一个页面
        for ( int i=0;i<this.getPageSize();i++) {
            book.append(this, pageFormat);
        }
        return book;
    }
    default int getPageSize(){
        return 1;
    }
    /**
     * 打印文档方向
     * @return
     */
    default int getDirection() {
        return PageFormat.PORTRAIT;
    }

    /**
     * 纸张宽度
     * @return
     */
    double getWidth();
    /**
     * 纸张高度
     * @return
     */
    double getHeight();

    /**
     * 纸张左边距
     * @return
     */
    default double getXpadding(){
        return 0;
    }

    /**
     *  纸张上边距
     * @return
     */
    default double getYpadding(){
        return getWidth()*0.1;
    }



}
