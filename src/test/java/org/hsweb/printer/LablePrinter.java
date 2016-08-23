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
        //printInputDTO.setPrintText("<G>11111111111111111111111111111111111111111111111111111111111111111111111111111</G><G>菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜</G>\n<GB>2</GB>\n3\n<B>4</B><QR>xxxxx</QR>");
        printInputDTO.setPrintText("XXXX<B><G>111111</G>XXXXXXXXX</B>");
        PrintResultDTO printResultVo = PrintUtil.print(printInputDTO);
        System.out.println(printResultVo);

       /* System.out.println("菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜菜".length());*/

      /*  String s="d你";
        System.out.println(length(s));*/
    }
    /*public static int length(String s) {
        int k = 0x80;
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!(c[i] / k == 0 ? true : false)) {
                len++;
            }
        }
        return len;
    }*/
}
