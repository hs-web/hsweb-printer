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
import javax.print.attribute.standard.ColorSupported;
import java.awt.print.PrinterAbortException;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by xiongchuang on 2016/8/18 .
 */
public class PrintUtil {
    /**
     * 获取默认打印机
     * @return
     */
    public static PrintService getDefaultPrintService(){
        return PrintServiceLookup.lookupDefaultPrintService();
    }

    /**
     * 根据打印机名称获取打印机，如果不存在返回默认打印机
     * @param printerName
     * @return
     */
    public static PrintService getPrintServiceByNameOrDefault(String printerName){
        PrintService printService = getPrintService(printerName);
        if(printService==null){
            printService=getDefaultPrintService();
        }
        return printService;
    }
    /**
     * 根据打印机名称获取打印机
     * @param printerName
     * @return
     */
    public static PrintService getPrintService(String printerName){
        Map<String, PrintService> printServices = getPrintServices();
        return printServices.get(printerName);
    }

    /**
     * 获取所有打印机
     * @return
     */
    public static Map<String, PrintService> getPrintServices(){
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        Map<String, PrintService> printServiceMap = Arrays.asList(printServices).stream().collect(Collectors.toMap(PrintService::getName, (o) -> o));
        return printServiceMap;
    }
    /*
   * 获取所有打印机
    * @return
    */
    public static List<PrinterDTO> getPrinters() {
        List<PrinterDTO> printerDTOs=new ArrayList<PrinterDTO>();

        PrintService[] printServices = PrinterJob.lookupPrintServices();
        if(printServices!=null&&printServices.length>0){
            for (PrintService printService : printServices) {

                PrinterDTO printerDTO=new PrinterDTO();
                printerDTO.setPrinterName(printService.getName());
                printerDTO.setPrinterPort("");

                ColorSupported attribute = printService.getAttribute(ColorSupported.class);
                Class<?> printServiceImplClass = getPrintServiceImplClass();
                Field port = null;
                try {
                    port = printServiceImplClass.getDeclaredField("port");
                    String printPort = (String) port.get(printService);
                    port.setAccessible(true);
                    printerDTO.setPrinterPort(printPort);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


//                if(printService instanceof Win32PrintService){
//                    try {
//                        ColorSupported attribute = printService.getAttribute(ColorSupported.class);
//                        Win32PrintService win32PrintService= (Win32PrintService)printService;
//                        Class<? extends Win32PrintService> aClass = win32PrintService.getClass();
//                        Field port = aClass.getDeclaredField("port");
//                        port.setAccessible(true);
//                        printerDTO.setPrinterPort((String) port.get(win32PrintService));
//                    } catch (NoSuchFieldException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//
//                }
                printerDTOs.add(printerDTO);
            }
        }
        return printerDTOs;
    }

    private static Class<?> getPrintServiceImplClass(){
        Class<?> printServiceClass = null;
        try {
            Class<?>  win32PrintService= Class.forName("sun.print.Win32PrintService");
            printServiceClass = win32PrintService;
        } catch (ClassNotFoundException e) {
            try {
                Class<?>  unixPrintService= Class.forName("sun.print.UnixPrintService");
                printServiceClass = unixPrintService;
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        return printServiceClass;
    }

    /**
     * 打印
     * @param printerName
     * @param basePrintable
     * @return
     */
    public static PrintResultDTO print(String printerName, BasePrintable basePrintable){
        PrintService printService = getPrintServiceByNameOrDefault(printerName);

        PrintResultDTO printVo=new PrintResultDTO();
        printVo.setSuccess(true);
        printVo.setPrintDateTime(DateUtil.timeToString(System.currentTimeMillis(),"MM-dd HH:mm:ss"));
        if(printService==null){
            printVo.setSuccess(false);
            printVo.setMessage("打印机不存在");
            return printVo;
        }

        if(!printerName.equals(printService.getName())) {
            printVo.setPrinterName(printService.getName());
            printVo.setMessage("未找到"+printerName+"打印机,使用"+printService.getName()+"打印机打印");
        }

        try {
            //获取打印服务对象
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName(basePrintable.getPrintDocName());
            // 设置打印类
            job.setPageable(basePrintable.getBook());

            //可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
            // boolean a=job.printDialog();

            job.setPrintService(printService);
            job.print();
            printVo.setSuccess(true);
        }catch (PrinterAbortException e){
            printVo.setSuccess(false);
            printVo.setMessage("主动取消打印");
        }catch (PrinterException e) {
            e.printStackTrace();
            printVo.setSuccess(false);
            printVo.setMessage(e.getMessage());
        }
        return printVo;
    }


    /**
     * 打印
     * @param printInputDTO
     * @return
     */
    public static PrintResultDTO print(PrintInputDTO printInputDTO){
        try {
            LabelPrintable labelPrintable=new LabelPrintable(printInputDTO.getPrintDocName(),printInputDTO.getPageWidth(),printInputDTO.getFontSize(),printInputDTO.getPrintText());
            PrintResultDTO resultDTO = print(printInputDTO.getPrinterName(), labelPrintable);
            return resultDTO;
        } catch (Exception e){
            PrintResultDTO resultDTO=new PrintResultDTO();
            resultDTO.setSuccess(false);
            resultDTO.setMessage(e.getMessage());
            resultDTO.setPrintDateTime(DateUtil.timeToString(System.currentTimeMillis(),"MM-dd HH:mm:ss"));
            return resultDTO;
        }
    }

    public static void main(String[] args) {
        List<PrinterDTO> printers = getPrinters();
        for (PrinterDTO printer : printers) {
            System.out.println(printer);
        }
    }



}
