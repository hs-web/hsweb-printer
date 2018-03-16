package org.hswebframework.printer.builder;

import org.apache.avalon.framework.configuration.DefaultConfiguration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.hswebframework.printer.*;
import org.hswebframework.printer.executor.DefaultPrintable;
import org.junit.Assert;
import org.junit.Test;

import javax.print.*;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrintQuality;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author zhouhao
 * @since 2.0
 */
public class PrinterTests {
    static String json = "{\"layers\":[{\"type\":\"rect\",\"rp\":\"rp72\",\"x\":20,\"y\":78,\"width\":559,\"height\":181,\"fill\":\"rgba(0,0,0,0)\",\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":20,\"y1\":100,\"x2\":580,\"y2\":100,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":20,\"y1\":120,\"x2\":579,\"y2\":120,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":81,\"y1\":140,\"x2\":299,\"y2\":140,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":138,\"y1\":78,\"x2\":138,\"y2\":200,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":300,\"y1\":77,\"x2\":300,\"y2\":179,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":419,\"y1\":79,\"x2\":419,\"y2\":179,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":82,\"y1\":160,\"x2\":299,\"y2\":160,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":20,\"y1\":180,\"x2\":580,\"y2\":180,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":362,\"y1\":140,\"x2\":580,\"y2\":140,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":362,\"y1\":160,\"x2\":579,\"y2\":160,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":20,\"y1\":200,\"x2\":581,\"y2\":200,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":299,\"y1\":201,\"x2\":299,\"y2\":259,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":81,\"y1\":121,\"x2\":81,\"y2\":181,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":361,\"y1\":120,\"x2\":361,\"y2\":180,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"fontSize\":22,\"type\":\"text\",\"rp\":\"rp72\",\"x\":202,\"y\":13,\"originalY\":7.40625,\"text\":\"转账交易电子回单\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":14,\"type\":\"text\",\"rp\":\"rp72\",\"x\":15,\"y\":54.796875,\"originalY\":51.609375,\"text\":\"入账日期:20180315\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":35,\"y\":77,\"originalY\":72.609375,\"text\":\"电子回单号\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":23,\"y\":137,\"originalY\":132.609375,\"text\":\"付款人\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":304,\"y\":136,\"originalY\":131.609375,\"text\":\"收款人\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":142,\"y\":77,\"originalY\":72.609375,\"text\":\"张三\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":316,\"y\":77,\"originalY\":72.609375,\"text\":\"交易类型\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":423,\"y\":76,\"originalY\":71.609375,\"text\":\"测试\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":37,\"y\":96.75,\"originalY\":92.359375,\"text\":\"交易流水\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":319,\"y\":97,\"originalY\":92.609375,\"text\":\"交易渠道\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":82,\"y\":118,\"originalY\":113.609375,\"text\":\"全称\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":82,\"y\":138,\"originalY\":133.609375,\"text\":\"帐号\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":82,\"y\":158,\"originalY\":153.609375,\"text\":\"开户行\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":362,\"y\":117,\"originalY\":112.609375,\"text\":\"全称\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":361,\"y\":138,\"originalY\":133.609375,\"text\":\"帐号\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":364,\"y\":158,\"originalY\":153.609375,\"text\":\"开户行\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":40,\"y\":178,\"originalY\":173.609375,\"text\":\"交易金额\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":144,\"y\":178,\"originalY\":173.609375,\"text\":\"大写：\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":18,\"type\":\"text\",\"rp\":\"rp72\",\"x\":378,\"y\":179,\"originalY\":174.609375,\"text\":\"小写：\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":13,\"type\":\"text\",\"rp\":\"rp72\",\"x\":21,\"y\":199,\"originalY\":196.109375,\"text\":\"摘要\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":13,\"type\":\"text\",\"rp\":\"rp72\",\"x\":21,\"y\":214,\"originalY\":211.109375,\"text\":\"附言\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":13,\"type\":\"text\",\"rp\":\"rp72\",\"x\":21,\"y\":229,\"originalY\":226.109375,\"text\":\"其他信息\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":15,\"type\":\"text\",\"rp\":\"rp72\",\"x\":23,\"y\":260.3125,\"originalY\":256.8125,\"text\":\"打印机构：\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":15,\"type\":\"text\",\"rp\":\"rp72\",\"x\":239,\"y\":259,\"originalY\":255.5,\"text\":\"打印次数：\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"fontSize\":15,\"type\":\"text\",\"rp\":\"rp72\",\"x\":380,\"y\":259,\"originalY\":255.5,\"text\":\"打印时间：\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\",\"position\":\"left\",\"maxWidth\":\"\"},{\"type\":\"img\",\"rp\":\"rp72\",\"x\":510,\"y\":2,\"width\":79,\"height\":61,\"fill\":\"rgba(184,184,184,1)\",\"imgData\":\"http://www.hsweb.me\",\"imgType\":\"qrCode\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"}]}";

    static JsonPageBuilder builder = new JsonPageBuilder();

    @Test
    public void testParse() {
        List<Pager> pagers = builder.build(json);
        assertNotNull(pagers);
        assertFalse(pagers.isEmpty());
        assertFalse(pagers.get(0).getLayers().isEmpty());
    }

    @Test
    public void testPrintSvg() {
        List<Pager> pagers = builder.build(json);
        List<String> svgs = PrinterUtils.printToSvg(pagers);
        assertFalse(svgs.isEmpty());
        System.out.println(svgs.get(0));
    }

    @Test
    public void testPrintPdf() throws Exception {
        new File("./target").mkdirs();
        List<Pager> pagers = builder.build(json);
        PrinterUtils
                .printToPdf(pagers
                        , new PixelPaper(72, Paper.A4)
                        , new FileOutputStream("./target/test.pdf")
                        , new DefaultConfigurationBuilder().build("./config/fop-configuration.xml"));
        assertTrue(new File("./target/test.pdf").exists());
        assertTrue(new File("./target/test.pdf").length() > 0);
    }

    public static void main(String[] args) throws PrintException {
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        //获取打印服务对象
        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        //弹出提示,选择打印机
        PrintService printService = ServiceUI.printDialog(null, 200, 200, printServices,
                defaultService, flavor, pras);
        if (printService == null) return;

        List<Pager> pagers = builder.build(json);
        DocPrintJob job = printService.createPrintJob();
        // pras.add(new PrinterName("test", Locale.getDefault()));
        pras.add(PrintQuality.HIGH);
        pras.add(MediaSizeName.ISO_A4);
        //pras.add(OrientationRequested.LANDSCAPE);
        PrintCommand command = new PrintCommand();
        command.setPagers(pagers);
        command.setPaper(new PixelPaper(72, Paper.A4));

        DefaultPrintable defaultPrintable = new DefaultPrintable(command);
        pras.add(new MediaPrintableArea(20, 20, Paper.A4.getWidth(), Paper.A4.getHeight(), MediaPrintableArea.MM));
        Doc doc = new SimpleDoc(defaultPrintable, flavor, null);
        job.print(doc, pras);

    }
}