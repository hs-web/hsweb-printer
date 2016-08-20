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

import java.awt.print.PageFormat;
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
    double gehHeight();

    /**
     * 纸张左边距
     * @return
     */
    default double getXpadding(){
        return 5;
    }

    /**
     *  纸张上边距
     * @return
     */
    default double getYpadding(){
        return getXpadding()*3;
    }



}
