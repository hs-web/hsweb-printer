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

package org.hsweb.printer.utils.printable.positionprint.full;

import org.hsweb.printer.dtos.PositionPrintDTO;
import org.hsweb.printer.utils.printable.positionprint.full.component.PositionComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xiong on 2017-02-24.
 */
public class PositionPrint {

    private List<List<PositionComponent>> pagePositionComponent=new ArrayList<>();
    private Font font=new Font("微软雅黑",Font.PLAIN,8);
    private Color color= Color.decode("#ffffff");


    //1in = 2.54cm = 25.4mm = 72pt = 96px     =7.14375使用单位
    public PositionPrint(double height, double width, List<PositionPrintDTO> printDTOList) {
        for (PositionPrintDTO positionPrintDTO : printDTOList) {

        }
    }
    public int getPageSize() {
        return pagePositionComponent.size();
    }

    public void print(int pageIndex, Graphics graphics, double xpadding, double ypadding) {

    }



}
