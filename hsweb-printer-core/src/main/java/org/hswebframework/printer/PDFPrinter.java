package org.hswebframework.printer;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.batik.bridge.UnitProcessor;
import org.apache.batik.ext.awt.RenderingHintsKeyExt;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.fop.Version;
import org.apache.fop.svg.PDFDocumentGraphics2D;
import org.apache.fop.svg.PDFDocumentGraphics2DConfigurator;
import org.apache.fop.svg.PDFTranscoder;
import org.apache.fop.svg.font.FOPFontFamilyResolverImpl;
import org.w3c.dom.Document;
import org.w3c.dom.svg.SVGLength;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;

/**
 * @author zhouhao
 * @since 1.0
 */
public class PDFPrinter extends PDFTranscoder {
    private List<String> svg;

    public PDFPrinter(List<String> svg) throws TranscoderException {
        this.svg = svg;
        graphics = new PDFDocumentGraphics2D(isTextStroked());
        graphics.getPDFDocument().getInfo().setProducer("Apache FOP Version "
                + Version.getVersion()
                + ": PDF Transcoder for Batik");
        if (hints.containsKey(KEY_DEVICE_RESOLUTION)) {
            graphics.setDeviceDPI(getDeviceResolution());
        }
        try {
            Configuration effCfg = getEffectiveConfiguration();

            if (effCfg != null) {
                PDFDocumentGraphics2DConfigurator configurator
                        = new PDFDocumentGraphics2DConfigurator();
                boolean useComplexScriptFeatures = false; //TODO - FIX ME
                configurator.configure(graphics, effCfg, useComplexScriptFeatures);
            } else {
                graphics.setupDefaultFontInfo();
            }
            ((FOPTranscoderUserAgent) userAgent).setFontFamilyResolver(
                    new FOPFontFamilyResolverImpl(graphics.getFontInfo()));
        } catch (Exception e) {
            throw new TranscoderException(
                    "Error while setting up PDFDocumentGraphics2D", e);
        }

    }

    public void transcode(TranscoderOutput output) throws Exception {
        OutputStream out = output.getOutputStream();
        if (!(out instanceof BufferedOutputStream)) {
            out = new BufferedOutputStream(out);
        }
        graphics.setupDocument(out, 800, 1000);
        graphics.setSVGDimension(width, height);

        if (hints.containsKey(ImageTranscoder.KEY_BACKGROUND_COLOR)) {
            graphics.setBackgroundColor(
                    (Color) hints.get(ImageTranscoder.KEY_BACKGROUND_COLOR));
        }
        graphics.setGraphicContext(
                new org.apache.xmlgraphics.java2d.GraphicContext());
        graphics.setRenderingHint(
                RenderingHintsKeyExt.KEY_TRANSCODING,
                RenderingHintsKeyExt.VALUE_TRANSCODING_VECTOR);
        for (String s : svg) {
            transcode(new TranscoderInput(new StringReader(s)), output);
            graphics.nextPage();
        }
        graphics.finish();
    }

    protected void transcode(Document document, String uri,
                             TranscoderOutput output)
            throws TranscoderException {

        setupImageInfrastructure(uri);
        super.transcode(document, uri, output);
        if (getLogger().isTraceEnabled()) {
            getLogger().trace("document size: " + width + " x " + height);
        }
        // prepare the image to be painted
        UnitProcessor.Context uctx = UnitProcessor.createContext(ctx,
                document.getDocumentElement());
        float widthInPt = UnitProcessor.userSpaceToSVG(width, SVGLength.SVG_LENGTHTYPE_PT,
                UnitProcessor.HORIZONTAL_LENGTH, uctx);
        int w = (int) (widthInPt + 0.5);
        float heightInPt = UnitProcessor.userSpaceToSVG(height, SVGLength.SVG_LENGTHTYPE_PT,
                UnitProcessor.HORIZONTAL_LENGTH, uctx);
        int h = (int) (heightInPt + 0.5);
        if (getLogger().isTraceEnabled()) {
            getLogger().trace("document size: " + w + "pt x " + h + "pt");
        }
        this.root.paint(graphics);
    }
}
