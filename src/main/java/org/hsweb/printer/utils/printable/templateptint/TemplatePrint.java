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

package org.hsweb.printer.utils.printable.templateptint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javafx.scene.text.FontPosture;
import org.hsweb.printer.dtos.PositionSimplePrintDTO;
import org.hsweb.printer.utils.ObjectValueUtil;
import org.hsweb.printer.utils.printable.positionprint.simple.PositionSimplePrint;
import org.hsweb.printer.utils.printable.positionprint.simple.PositionSimplePrintConstants;
import org.hsweb.printer.utils.printable.templateptint.dtos.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by xiong on 2017-07-17.
 */
public class TemplatePrint {
    private PositionSimplePrint positionSimplePrint;

    //1in = 2.54cm = 25.4mm = 72pt = 96px     =7.14375使用单位
    public TemplatePrint(PrintTemplateDTO printDTOList, Object o) {
        List<PositionSimplePrintDTO> positionSimplePrintDTOS=new ArrayList<>();


        for (JSONObject positionSimplePrintDTO : printDTOList.getComponentDTOS()) {

            String type = positionSimplePrintDTO.getString("type");
            if(TemplatePrintConstants.TEXT.equals(type)){

                positionSimplePrintDTOS.addAll(this.getTextPositionPrintDTO( JSON.parseObject(positionSimplePrintDTO.toJSONString(),TextComponentDTO.class)));
            }else if(TemplatePrintConstants.VARIABLE.equals(type)){
                positionSimplePrintDTOS.addAll(this.getVariablePositionPrintDTO( JSON.parseObject(positionSimplePrintDTO.toJSONString(),VariableComponentDTO.class),o));
            }else if(TemplatePrintConstants.IMAGE.equals(type)){
                positionSimplePrintDTOS.addAll(this.getImagePositionPrintDTO( JSON.parseObject(positionSimplePrintDTO.toJSONString(),ImageComponentDTO.class),o));
            }else if(TemplatePrintConstants.QRCODE.equals(type)){
                positionSimplePrintDTOS.addAll(this.getQrcodepositionPrintDTO( JSON.parseObject(positionSimplePrintDTO.toJSONString(),QrcodeComponentDTO.class),o));
            }
        }

        positionSimplePrint =new PositionSimplePrint(printDTOList.getWindowHeight(),printDTOList.getWindowHeight(),positionSimplePrintDTOS);
    }



    private PositionSimplePrintDTO getFontPrintDTO(TextComponentDTO textComponentDTO){
        PositionSimplePrintDTO fontPrintDTO=new PositionSimplePrintDTO();
        fontPrintDTO.setType(PositionSimplePrintConstants.FONT);

        fontPrintDTO.setFontName(textComponentDTO.getFontName());
        fontPrintDTO.setFontStyle(FontPosture.ITALIC.equals(textComponentDTO.getFontPosture())? Font.ITALIC: Font.PLAIN);
        fontPrintDTO.setFontSize(textComponentDTO.getFontSize());
        return fontPrintDTO;
    }
    private PositionSimplePrintDTO getStylePrintDTO(TextComponentDTO textComponentDTO){
        PositionSimplePrintDTO colorPrintDTO=new PositionSimplePrintDTO();
        colorPrintDTO.setType(PositionSimplePrintConstants.STYLE);
        colorPrintDTO.setColor(textComponentDTO.getColor());
        colorPrintDTO.setAlign(textComponentDTO.getAlign());
        return colorPrintDTO;
    }
    private PositionSimplePrintDTO getContextPrintDTO(TemplateComponentDTO templateComponentDTO,String content){
        PositionSimplePrintDTO textPrintDTO=new PositionSimplePrintDTO();
        textPrintDTO.setType(PositionSimplePrintConstants.TEXT);
        textPrintDTO.setContext(content);
        textPrintDTO.setX(templateComponentDTO.getX()+"");
        textPrintDTO.setY(templateComponentDTO.getY()+"");
        textPrintDTO.setWidth(templateComponentDTO.getWidth()+"");
        textPrintDTO.setHeight(templateComponentDTO.getHeight()+"");
        return textPrintDTO;
    }

    private String getVaule(TemplateComponentDTO templateComponentDTO,Object o){
        if(o!=null){
            try {
                Object objectValue = ObjectValueUtil.getObjectValue(o, templateComponentDTO.getContext());
                if(objectValue!=null){
                    return ""+objectValue.toString();
                }
            } catch (Exception e) {
            }
        }
        return "";
    }
    private List<PositionSimplePrintDTO> getVariablePositionPrintDTO(VariableComponentDTO positionSimplePrintDTO,Object o) {
        String content=getVaule(positionSimplePrintDTO,o);
        return Arrays.asList(this.getFontPrintDTO(positionSimplePrintDTO),this.getStylePrintDTO(positionSimplePrintDTO),this.getContextPrintDTO(positionSimplePrintDTO,content));
    }

    private List<PositionSimplePrintDTO> getTextPositionPrintDTO(TextComponentDTO positionSimplePrintDTO) {
        return Arrays.asList(this.getFontPrintDTO(positionSimplePrintDTO),this.getStylePrintDTO(positionSimplePrintDTO),this.getContextPrintDTO(positionSimplePrintDTO,positionSimplePrintDTO.getContext()));
    }

    private List<PositionSimplePrintDTO> getImagePositionPrintDTO(ImageComponentDTO imageComponentDTO, Object o) {
        String content=getVaule(imageComponentDTO,o);

        if(content.length()==0){
            return new ArrayList<>();
        }
        PositionSimplePrintDTO printDTO=new PositionSimplePrintDTO();
        printDTO.setType(PositionSimplePrintConstants.IMAGE);
        printDTO.setContext(content);
        printDTO.setX(imageComponentDTO.getX()+"");
        printDTO.setY(imageComponentDTO.getY()+"");
        printDTO.setWidth(imageComponentDTO.getWidth()+"");
        printDTO.setHeight(imageComponentDTO.getHeight()+"");
        return Arrays.asList(printDTO);
    }
    private Collection<? extends PositionSimplePrintDTO> getQrcodepositionPrintDTO(QrcodeComponentDTO qrcodeComponentDTO, Object o) {
        String content=getVaule(qrcodeComponentDTO,o);

        PositionSimplePrintDTO printDTO=new PositionSimplePrintDTO();
        printDTO.setType(PositionSimplePrintConstants.QRCODE);
        printDTO.setContext(content);
        printDTO.setX(qrcodeComponentDTO.getX()+"");
        printDTO.setY(qrcodeComponentDTO.getY()+"");
        printDTO.setWidth(qrcodeComponentDTO.getWidth()+"");
        printDTO.setHeight(qrcodeComponentDTO.getHeight()+"");
        return Arrays.asList(printDTO);
    }



    public int getPageSize() {
        return positionSimplePrint.getPageSize();
    }

    public void print(int pageIndex, Graphics graphics, double xpadding, double ypadding) {
        positionSimplePrint.print(pageIndex,graphics,xpadding,ypadding);
    }
}
