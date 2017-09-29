package org.hswebframework.printer.executor.job;


import org.hswebframework.printer.designer.Color;
import org.hswebframework.printer.designer.Line;
import org.hswebframework.printer.designer.Pager;
import org.hswebframework.printer.designer.PrintCommand;
import org.hswebframework.printer.executor.DefaultPagerGraphicsService;
import org.hswebframework.printer.executor.PagerGraphicsService;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Arrays;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public class DefaultPrintableTest {
    static PagerGraphicsService graphicsService = new DefaultPagerGraphicsService();

    public static void main(String[] args) throws PrinterException {
        testPrint();
    }

    public static void testPrint() throws PrinterException {
        Line line = new Line();
        line.setColor(new Color());
        line.setLength(100);
        line.setSize(1);
        line.setX(10);
        line.setY(100);

        Pager pager = new Pager();

        pager.setName("test");
        pager.setPrintObjects(Arrays.asList(line));

        PrintCommand command = new PrintCommand();
        command.setPagers(Arrays.asList(pager));

        DefaultPrintable defaultPrintable = new DefaultPrintable(command, graphicsService);
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();


        //获取打印服务对象
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("test");
        // 设置打印类
        job.setPageable(defaultPrintable.getBook());

        //可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
        boolean a = job.printDialog();

        job.setPrintService(printService);
        job.print();
    }

}