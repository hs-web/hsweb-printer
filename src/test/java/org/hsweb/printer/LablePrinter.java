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
        printInputDTO.setPrintText("<G>1234567890123456789012345678901234567890123456789012345678901234567890</G>\n<GB>12345678901234567890123456789012345678901234567890123456789012345678901234567890</GB>\n1234567890123456789012345678901234567890123456789012345678901234567890\n<B>1234567890123456789012345678901234567890123456789012345678901234567890</B>");

        PrintResultDTO printResultVo = PrintUtil.print(printInputDTO);
        System.out.println(printResultVo);
    }
}
