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

package org.hsweb.print;

import org.hsweb.printer.dtos.PrintInputDTO;
import org.hsweb.printer.dtos.PrintResultDTO;
import org.hsweb.printer.utils.PrintUtil;
import org.hsweb.utils.ClientPrintContext;
import org.hsweb.utils.PrintContext;

/**
 * Created by xiong on 2017-02-20.
 */
public class LablePrintTest {
    public static void main(String[] args) {
        int pageWidth=800;
        String printText=getPrintText(pageWidth);
       // System.out.println(printText);

        PrintInputDTO printInputDTO=new PrintInputDTO();
        printInputDTO.setPageWidth(pageWidth+0D);
        printInputDTO.setPrintDocName("测试");
        printInputDTO.setPrintType("labelPrint");
        printInputDTO.setPrinterName("打印机");
        printInputDTO.setPrintText(printText);

        PrintResultDTO print = PrintUtil.print(printInputDTO);
        System.out.println(print);
    }

    private static String getPrintText(int pageWidth) {
        PrintContext printContext = new ClientPrintContext(pageWidth).appendCenterBigFont("例子吧").BR()
                .appendBlank().append("订单号：").append("123").BR();

        printContext.append("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十").BR()
                .append("订单来源：").append("xsxsxsxs").BR()
                .append("出餐时间：").append("121212").BR()
                .append("出餐门店：").append("xxxx").BR()
                .appendDecollator()
                .appendBlank().append("收货人：").append("xxxx").BR()
                .append("收货电话：").append("32132322323").BR()
                .append("用餐时间：").append("2017").BR()
                .append("配送地址：").append("sadsadasdsadas", 10, "asdasdasd").BR()
                .append("客户备注：").append("sadsadasdasdasdsad", 10).BR()
                .appendDecollator();


        printContext.tableWidth(17, 33, 17, 33)
                .tableAlign(ClientPrintContext.align_center, ClientPrintContext.align_center, ClientPrintContext.align_center, ClientPrintContext.align_center)
                .appendTable("品名", "单价(￥)", "数量", "金额(￥)")
                .appendDecollator();


        printContext.append("套餐").BR();

        printContext.tableWidthPadding(3,100)
                .tableAlign(ClientPrintContext.align_left)
                .appendTable("【含】下撒旦飞洒x5");
        printContext.tableWidth(17, 32, 17, 32)
                .tableAlign(ClientPrintContext.align_left, ClientPrintContext.align_right, ClientPrintContext.align_center, ClientPrintContext.align_right);
        printContext.appendTable("", "500", "" + 1, "500");


        printContext.BR()
                .appendRightFont("菜品：" + PrintContext.getRightString("500", 14))
                .appendRightFont("快递：" + PrintContext.getRightString("500", 14));
        printContext.appendRightFont("优惠：" + PrintContext.getRightString("500", 14));
        printContext.appendRightFont("实付：" + PrintContext.getRightString("500", 14))
                //.appendQRcode(orderPrintDTO.getSemacodeStr()).BR()
                .appendCenterFont("请您在收餐后尽快食用，祝您用餐愉快！")
                .appendCenterFont("如果有任何疑问，请拨打客服电话：121212")
        .systemSound(ClientPrintContext.SystemSound.newPrint)
        .sound(ClassLoader.getSystemClassLoader().getResourceAsStream("alarm_new_order.wav"));

        return printContext.getContext();
    }
}
