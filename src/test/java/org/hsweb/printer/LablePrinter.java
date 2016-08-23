package org.hsweb.printer;

import org.hsweb.printer.dtos.PrintInputDTO;
import org.hsweb.printer.dtos.PrintResultDTO;
import org.hsweb.printer.utils.PrintUtil;

/**
 * Created by xiong on 2016/8/20.
 */
public class LablePrinter {
    public static void main(String[] args) {
        PrintInputDTO printInputDTO=new PrintInputDTO();
        printInputDTO.setPrinterName("打印机名称 找不到用默认打印机");
        printInputDTO.setPrintDocName("测试打印");
        printInputDTO.setPageWidth(200d);
        printInputDTO.setPrintText("<G>11111111111111111111111111111111111111111111111111111111111111111111111111111</G>\n<GB>2</GB>\n3\n<B>4</B><qrcod>xxxxx</qrcod>");

        PrintResultDTO printResultVo = PrintUtil.print(printInputDTO);
        System.out.println(printResultVo);
    }
}
