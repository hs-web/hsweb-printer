package org.hswebframework.printer;

import lombok.extern.slf4j.Slf4j;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.ext.awt.RenderingHintsKeyExt;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.XMLAbstractTranscoder;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.fop.svg.PDFDocumentGraphics2D;
import org.apache.fop.svg.PDFDocumentGraphics2DConfigurator;
import org.apache.fop.svg.PDFTranscoder;
import org.apache.xmlgraphics.java2d.GraphicContext;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import sun.font.FontManagerFactory;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.batik.transcoder.SVGAbstractTranscoder.KEY_HEIGHT;
import static org.apache.batik.transcoder.SVGAbstractTranscoder.KEY_WIDTH;
import static org.apache.fop.svg.AbstractFOPTranscoder.KEY_AUTO_FONTS;

/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public class PrinterUtils {
    public static void draw(List<Layer> layers, Graphics2D g2) {
        initGraphics2D(g2);
        layers.forEach(layer -> layer.draw(g2));
    }

    static {
        log.info("加载字体...");
        File fontDir = new File("./config/font");
        if (fontDir.exists()) {
            File[] fonts = fontDir.listFiles((dir, name) -> name.endsWith("ttf") || name.endsWith("TTF"));
            if (null != fonts) {
                for (File file : fonts) {
                    try {
                        Font font = Font.createFont(Font.TRUETYPE_FONT, file);
                        FontManagerFactory.getInstance().registerFont(font);
                        log.info("load font {} success",font);
                    } catch (Exception e) {
                        log.warn("load font error", e);
                    }
                }
            }

        }
    }

    public static void initGraphics2D(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    public static void svg2pdf(List<String> svg, OutputStream out) throws Exception {
        TranscoderOutput output = new TranscoderOutput(out);
        PDFPrinter transcoder = new PDFPrinter(svg);
        transcoder.addTranscodingHint(KEY_WIDTH, 800f);
        transcoder.addTranscodingHint(KEY_HEIGHT, 1200f);
        transcoder.addTranscodingHint(KEY_AUTO_FONTS, true);
        final int dpi = 300;
        transcoder.addTranscodingHint(ImageTranscoder.KEY_PIXEL_UNIT_TO_MILLIMETER, 25.4f / dpi);
        transcoder.addTranscodingHint(XMLAbstractTranscoder.KEY_XML_PARSER_VALIDATING, Boolean.FALSE);
        transcoder.addTranscodingHint(PDFTranscoder.KEY_STROKE_TEXT, Boolean.FALSE);
        transcoder.transcode(output);
    }

    public static void printToPdf(List<Pager> pagers, PixelPaper pixelPaper, OutputStream outputStream) throws Exception {
        printToPdf(pagers, pixelPaper, outputStream, new DefaultConfigurationBuilder().build("./config/fop-configuration.xml"));
    }

    public static void printToPdf(List<Pager> pagers, PixelPaper pixelPaper, OutputStream outputStream, Configuration configuration) throws Exception {
        PDFDocumentGraphics2D graphics2D = new PDFDocumentGraphics2D(false);
        GraphicContext context = new GraphicContext();
        graphics2D.setGraphicContext(context);
        initGraphics2D(graphics2D);
        PDFDocumentGraphics2DConfigurator configurator = new PDFDocumentGraphics2DConfigurator();

        configurator.configure(graphics2D, configuration, false);

        graphics2D.setupDocument(outputStream, pixelPaper.getWidth(), pixelPaper.getHeight());
        graphics2D.setupDefaultFontInfo();
        graphics2D.setRenderingHint(
                RenderingHintsKeyExt.KEY_TRANSCODING,
                RenderingHintsKeyExt.VALUE_TRANSCODING_VECTOR);
        for (Pager pager : pagers) {
            // TODO: 18-3-14 旋转错误,不能只旋转一页
            if (pager.getOrientation() != 0) {
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.rotate(pager.getOrientation() * (Math.PI / 2), pixelPaper.getWidth() / 2D, pixelPaper.getHeight() / 2D);
                graphics2D.setTransform(affineTransform);
            }
            for (Layer layer : pager.getLayers()) {
                layer.draw((Graphics2D) graphics2D.create());
            }
            graphics2D.nextPage(pixelPaper.getWidth(), pixelPaper.getHeight());
        }
        graphics2D.finish();
    }

    public static List<String> printToSvg(List<Pager> pagers) {
        DOMImplementation domImpl =
                GenericDOMImplementation.getDOMImplementation();
        return pagers.stream().map(pager -> {
            String svgNS = "http://www.w3.org/2000/svg";
            Document document = domImpl.createDocument(svgNS, "svg", null);
            SVGGraphics2D svgGraphics2D = new SVGGraphics2D(document);
            // TODO: 18-3-14 旋转无效
            if (pager.getOrientation() != 0) {
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.rotate(pager.getOrientation() * (Math.PI / 2), 800 / 2D, 500 / 2D);
                svgGraphics2D.setTransform(affineTransform);
            }
            pager.getLayers().forEach(layer -> layer.draw(svgGraphics2D));
            StringWriter writer = new StringWriter();
            try {
                svgGraphics2D.stream(writer);
            } catch (SVGGraphics2DIOException e) {
                throw new RuntimeException(e);
            }
            return writer.toString();
        }).collect(Collectors.toList());
    }

    public static BufferedImage printToImage(List<Pager> pagers, PixelPaper pixelPaper) {
        BufferedImage image = new BufferedImage(pixelPaper.getWidth(), (pixelPaper.getHeight() + 10) * pagers.size(), BufferedImage.TYPE_INT_ARGB);
        int tempY = 0;

        Graphics2D g2 = ((Graphics2D) image.getGraphics());
        initGraphics2D(g2);
        for (Pager pager : pagers) {
            BufferedImage preview = new BufferedImage(pixelPaper.getWidth(), pixelPaper.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = ((Graphics2D) preview.getGraphics());
            initGraphics2D(graphics2D);
            // TODO: 18-3-14 优化旋转，当前存在问题: 90度或者270度选择时,位置不对
            if (pager.getOrientation() != 0) {
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.rotate(pager.getOrientation() * (Math.PI / 2), pixelPaper.getWidth() / 2D, pixelPaper.getHeight() / 2D);
                graphics2D.setTransform(affineTransform);
            }

            graphics2D.setBackground(Color.white);
            graphics2D.setColor(Color.white);
            graphics2D.fillRect(0, 0, pixelPaper.getWidth(), pixelPaper.getHeight());
            pager.getLayers().forEach(layer -> layer.draw(graphics2D));

            image.getGraphics()
                    .drawImage(preview, 0, tempY, pixelPaper.getWidth(), pixelPaper.getHeight(), (img, infoflags, x, y, width, height) -> false);
            tempY += pixelPaper.getHeight() + 10;
        }
        return image;
    }
}
