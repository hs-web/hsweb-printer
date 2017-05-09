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
        PrintContext printContext = new ClientPrintContext(pageWidth) .alignRight(p->p.fontBold(p2->p2.fontBig("优惠：" + PrintContext.getRightString("500", 14)))).fontBig().alignCenter().addText("例子吧").alignCenterClear().fontBigClear().BR()
                .addBlank(2).addText("订单号：").addText("123").BR();

        printContext.addText("一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十").BR()
                .addText("订单来源：").addText("xsxsxsxs").BR()
                .addText("出餐时间：").addText("121212").BR()
                .addText("出餐门店：").addText("xxxx").BR()
                .addDecollator(0)
                .addBlank(2).addText("收货人：").addText("xxxx").BR()
                .addText("收货电话：").addText("32132322323").BR()
                .addText("用餐时间：").addText("2017").BR()
                .addText("配送地址：").addText("sadsadasdsadas", 10, "asdasdasd").BR()
                .addText("客户备注：").addText("sadsadasdasdasdsad", 10).BR()
                .addDecollator(0);


        printContext.tableWidth(17, 33, 17, 33)
                .tableAlign(PrintContext.TableAlign.center, PrintContext.TableAlign.center, PrintContext.TableAlign.center, PrintContext.TableAlign.center)
                .addTable("品名", "单价(￥)", "数量", "金额(￥)")
                .addDecollator(0);


        printContext.addText("套餐").BR();

        printContext.tableWidthPadding(3,100)
                .tableAlign(PrintContext.TableAlign.left)
                .addTable("【含】下撒旦飞洒x5");
        printContext.tableWidth(17, 32, 17, 32)
                .tableAlign(PrintContext.TableAlign.left, PrintContext.TableAlign.right,PrintContext.TableAlign.center, PrintContext.TableAlign.right);
        printContext.addTable("", "500", "" + 1, "500");


        printContext.BR()
                .alignRight().addText("菜品：" + PrintContext.getRightString("500", 14)).alignRightClear()
                .alignRight("菜品：" + PrintContext.getRightString("500", 14))

                .alignRight().fontHeight().addText("快递：" + PrintContext.getRightString("500", 14)).fontHeightClear().alignRightClear()
                .alignRight().fontHeight("快递：" + PrintContext.getRightString("500", 14)).alignRightClear()
                .alignRight(p->p.fontHeight("快递：" + PrintContext.getRightString("500", 14)))


                .alignRight().fontBold().fontBig().addText("优惠：" + PrintContext.getRightString("500", 14)).fontBigClear().fontBoldClear().alignRightClear()
                .alignRight().fontBold().fontBig("优惠：" + PrintContext.getRightString("500", 14)).fontBoldClear().alignRightClear()
                .alignRight(p->p.fontBold(p2->p2.fontBig("优惠：" + PrintContext.getRightString("500", 14))))


                .alignRight().fontHeight("实付：" + PrintContext.getRightString("500", 14)).alignRightClear()
                //.appendQRcode(orderPrintDTO.getSemacodeStr()).BR()
                .alignCenter().addText("请您在收餐后尽快食用，祝您用餐愉快！").alignCenterClear()
                .alignCenter().addText("如果有任何疑问，请拨打客服电话：121212").alignCenterClear()
        .addSystemSound(ClientPrintContext.SystemSound.newPrint)
        .addSound(ClassLoader.getSystemClassLoader().getResourceAsStream("alarm_new_order.wav"));

        System.out.println(new ClientPrintContext(pageWidth).getTableConfig().tableWidth.size());
        return printContext.getContext();
    }
}
