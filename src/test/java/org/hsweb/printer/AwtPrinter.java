package org.hsweb.printer;


import java.awt.*;
import java.awt.print.*;

/**
 * AWT 方式打印
 *
 * @author zhouhao
 */
public class AwtPrinter implements Printable {

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex >= 1)
            return Printable.NO_SUCH_PAGE;
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setPaint(Color.black);
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        //绘制打印
        g2.drawString("测试打印", 10, 50);
        return Printable.PAGE_EXISTS;
    }

    public static void main(String[] args) {
        PrinterJob myPrtJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = myPrtJob.defaultPage();
        pageFormat.setPaper(new Paper());
        // 设置自定义Printable和页面格式
        myPrtJob.setPrintable(new AwtPrinter(), pageFormat);
        try {
            if (myPrtJob.printDialog()) {
                myPrtJob.print();
            }
        } catch (PrinterException pe) {
            pe.printStackTrace();
        }

    }
}
