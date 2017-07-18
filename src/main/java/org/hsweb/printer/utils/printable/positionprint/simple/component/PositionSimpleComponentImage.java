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

package org.hsweb.printer.utils.printable.positionprint.simple.component;

import org.hsweb.printer.dtos.PositionSimplePrintDTO;
import org.hsweb.printer.utils.SemacodeTool;
import org.hsweb.printer.utils.printable.positionprint.PositionPrintUnit;
import org.hsweb.printer.utils.printable.positionprint.simple.PositionSimplePrintConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

/**
 * Created by xiong on 2017-07-06.
 */
public class PositionSimpleComponentImage implements PositionSimpleComponent {
    private PositionSimplePrintDTO positionSimplePrintDTO;
    private double height;
    private double width;
    public PositionSimpleComponentImage(PositionSimplePrintDTO positionSimplePrintDTO, double height, double width){
        this.positionSimplePrintDTO=positionSimplePrintDTO;
        this.height=height;
        this.width=width;
    }
    @Override
    public void print(int pageIndex, Graphics graphics, double xpadding, double ypadding) {
        try {
            Image image=null;
            if(PositionSimplePrintConstants.QRCODE.equals(positionSimplePrintDTO.getType())){
                image=ImageIO.read(SemacodeTool.getInputStream(positionSimplePrintDTO.getContext()));
            }else if( positionSimplePrintDTO.getContext().indexOf("http://")==0|| positionSimplePrintDTO.getContext().indexOf("https://")==0){
                image=ImageIO.read(new URL(positionSimplePrintDTO.getContext()));
            }else {
                String[] split = positionSimplePrintDTO.getContext().split(",");
                byte[] decode = Base64.getDecoder().decode(split[split.length-1]);
                image=ImageIO.read(new ByteArrayInputStream(decode));
            }
            graphics.drawImage(image,
                (int) PositionPrintUnit.parsingUnit(positionSimplePrintDTO.getX()),
                    (int)(PositionPrintUnit.parsingUnit(positionSimplePrintDTO.getY())-pageIndex*height),
                    (int)(PositionPrintUnit.parsingUnit(positionSimplePrintDTO.getWidth())),
                    (int)(PositionPrintUnit.parsingUnit(positionSimplePrintDTO.getHeight())),
                    null
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
