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

package org.hsweb.printer.utils;

import org.hsweb.printer.dtos.PrintInputDTO;
import org.hsweb.printer.dtos.PrintResultDTO;
import org.hsweb.printer.dtos.PrinterDTO;
import org.hsweb.printer.utils.printable.BasePrintable;
import org.hsweb.printer.utils.printable.LabelPrintable;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by xiongchuang on 2016/8/18 .
 */
public class PrintUtil {
    public static PrintService getDefaultPrintService(){
        return PrintServiceLookup.lookupDefaultPrintService();
    }
    public static PrintService getPrintServiceByNameOrDefault(String printerName){
        PrintService printService = getPrintService(printerName);
        if(printService==null){
            printService=getDefaultPrintService();
        }
        return printService;
    }

    public static PrintService getPrintService(String printerName){
        Map<String, PrintService> printServices = getPrintServices();
        return printServices.get(printerName);
    }
    public static Map<String, PrintService> getPrintServices(){
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        Map<String, PrintService> printServiceMap = Arrays.asList(printServices).stream().collect(Collectors.toMap(PrintService::getName, (o) -> o));
        return printServiceMap;
    }

    public static List<PrinterDTO> getPrinters(){
        List<PrinterDTO> printerDTOs=new ArrayList<PrinterDTO>();

        PrintService[] printServices = PrinterJob.lookupPrintServices();
        if(printServices!=null&&printServices.length>0){
            for (PrintService printService : printServices) {

                PrinterDTO printerDTO=new PrinterDTO();
                printerDTO.setPrinterName(printService.getName());
                printerDTO.setPrinterPort("");

                printerDTOs.add(printerDTO);
            }
        }
        return printerDTOs;
    }

    public static PrintResultDTO print(String printerName, BasePrintable basePrintable){
        PrintService printService = getPrintServiceByNameOrDefault(printerName);


        PrintResultDTO printVo=new PrintResultDTO();
        printVo.setSuccess(true);

        if(printService==null){
            printVo.setSuccess(false);
            printVo.setMessage("打印机不存在");
        }


        //    通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper p = new Paper();
        p.setSize(basePrintable.getWidth(),basePrintable.gehHeight());//纸张大小
        p.setImageableArea(basePrintable.getXpadding(),basePrintable.getYpadding(), basePrintable.getWidth(),basePrintable.gehHeight());//A4(595 X 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72

        //    设置成竖打
        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(p);

        //    通俗理解就是书、文档
        Book book = new Book();
        //    把 PageFormat 和 Printable 添加到书中，组成一个页面
        book.append(basePrintable, pf);

        //获取打印服务对象
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName(basePrintable.getPrintDocName());
        // 设置打印类
        job.setPageable(book);

        try {
            //可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
            // boolean a=job.printDialog();

            job.setPrintService(printService);
            job.print();
            printVo.setSuccess(true);
            printVo.setMessage("打印机成功");

        } catch (PrinterException e) {
            e.printStackTrace();
            printVo.setSuccess(false);
            printVo.setMessage(e.getMessage());
        }
        return printVo;
    }



    public static PrintResultDTO print(PrintInputDTO printInputDTO){
        LabelPrintable labelPrintable=new LabelPrintable(printInputDTO.getPrintDocName(),printInputDTO.getPageWidth(),printInputDTO.getPrintText());
        return print(printInputDTO.getPrinterName(),labelPrintable);
    }



}
