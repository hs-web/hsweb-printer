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

package org.hsweb.printer.utils.printable.positionprint.simple;

import org.hsweb.printer.dtos.PositionSimplePrintDTO;
import org.hsweb.printer.dtos.PositionSimplePrintFontDTO;
import org.hsweb.printer.dtos.PositionSimplePrintStyleDTO;
import org.hsweb.printer.utils.printable.positionprint.PositionPrintUnit;
import org.hsweb.printer.utils.printable.positionprint.simple.component.PositionSimpleComponent;
import org.hsweb.printer.utils.printable.positionprint.simple.component.PositionSimpleComponentImage;
import org.hsweb.printer.utils.printable.positionprint.simple.component.PositionSimpleComponentString;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiong on 2017-07-06.
 */
public class PositionSimplePrint {
    //基础数据
    private double height;
    private double width;
    private List<PositionSimplePrintDTO> positionSimplePrintDTOS;
    private final String prefix="page";

    //转化数据
    private List<PositionSimplePrintStyleDTO> styleList=new ArrayList<>();
    private List<PositionSimplePrintFontDTO> fontList=new ArrayList<>();
    //转换结果数据
    private Map<String,List<PositionSimpleComponent>> pagePositionSimpleComponent=new HashMap<>();


    //1in = 2.54cm = 25.4mm = 72pt = 96px     =7.14375使用单位
    public PositionSimplePrint(double height, double width, List<PositionSimplePrintDTO> printDTOList) {
        this.height=height;
        this.width=width;
        this.positionSimplePrintDTOS=printDTOList;
        this.init();

    }

    private void init() {
        Map<String, List<PositionSimplePrintDTO>> pagePositionSimplePrintList = this.initPagePositionSimplePrintList();
        this.initPagePositionSimpleComponent(pagePositionSimplePrintList);
    }
    private Map<String,List<PositionSimplePrintDTO>> initPagePositionSimplePrintList(){
        PositionSimplePrintStyleDTO positionSimplePrintStyleDTO=new PositionSimplePrintStyleDTO();
        positionSimplePrintStyleDTO.setId(-1);
        positionSimplePrintStyleDTO.setAlign(0);
        positionSimplePrintStyleDTO.setColor( Color.decode("#ffffff"));
        styleList.add(positionSimplePrintStyleDTO);

        PositionSimplePrintFontDTO positionSimplePrintFontDTO=new PositionSimplePrintFontDTO();
        positionSimplePrintFontDTO.setId(-1);
        positionSimplePrintFontDTO.setFont(new Font("微软雅黑",Font.PLAIN,8));
        fontList.add(positionSimplePrintFontDTO);
        //临时数据
        Map<String,List<PositionSimplePrintDTO>> pagePositionSimplePrintList=new HashMap<>();
        int i=0;
        for (PositionSimplePrintDTO positionPrintDTO : positionSimplePrintDTOS) {
            positionPrintDTO.setId(i++);
            int thisPage=0;
            int thisPage2=0;
            if(PositionSimplePrintConstants.NO_HEIGHT.contains(positionPrintDTO.getType())){
                this.noHeightePositionSimplePrint(positionPrintDTO);
                continue;
            }else {
                thisPage =(int)(PositionPrintUnit.parsingUnit(positionPrintDTO.getY()) / height);
                thisPage2 =(int)((PositionPrintUnit.parsingUnit(positionPrintDTO.getY())+PositionPrintUnit.parsingUnit(positionPrintDTO.getHeight())) / height);
            }
            this.addPagePositionSimplePrintDTO(positionPrintDTO,pagePositionSimplePrintList,thisPage);
            if(thisPage!=thisPage2){
                this.addPagePositionSimplePrintDTO(positionPrintDTO,pagePositionSimplePrintList,thisPage2);
            }
        }

        return pagePositionSimplePrintList;
    }

    private void noHeightePositionSimplePrint(PositionSimplePrintDTO positionPrintDTO) {
        if(PositionSimplePrintConstants.FONT.equals(positionPrintDTO.getType())){
            Font font = new Font(positionPrintDTO.getFontName(),positionPrintDTO.getFontStyle(),positionPrintDTO.getFontSize());
            PositionSimplePrintFontDTO positionSimplePrintFontDTO=new PositionSimplePrintFontDTO();
            positionSimplePrintFontDTO.setFont(font);
            positionSimplePrintFontDTO.setId(positionPrintDTO.getId());
            fontList.add(positionSimplePrintFontDTO);
        }else if(PositionSimplePrintConstants.STYLE.equals(positionPrintDTO.getType())){
            Color decode = Color.decode(positionPrintDTO.getColor());
            PositionSimplePrintStyleDTO positionSimplePrintStyleDTO=new PositionSimplePrintStyleDTO();
            positionSimplePrintStyleDTO.setColor(decode);
            positionSimplePrintStyleDTO.setAlign(positionPrintDTO.getAlign());
            positionSimplePrintStyleDTO.setId(positionPrintDTO.getId());
            styleList.add(positionSimplePrintStyleDTO);
        }
    }

    private void addPagePositionSimplePrintDTO(PositionSimplePrintDTO positionPrintDTO, Map<String,List<PositionSimplePrintDTO>> pagePositionSimplePrintList,int thisPage){
        List<PositionSimplePrintDTO> positionSimplePrintDTOS=pagePositionSimplePrintList.get(prefix+thisPage);
        if(positionSimplePrintDTOS==null){
            positionSimplePrintDTOS=new ArrayList<>();
            pagePositionSimplePrintList.put(prefix+thisPage,positionSimplePrintDTOS);
        }
        positionSimplePrintDTOS.add(positionPrintDTO);
    }
    private void initPagePositionSimpleComponent(Map<String, List<PositionSimplePrintDTO>> pagePositionSimplePrintList){
        styleList.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
        fontList.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
        for (Map.Entry<String,List<PositionSimplePrintDTO>> stringListEntry : pagePositionSimplePrintList.entrySet()) {
            String key = stringListEntry.getKey();
            List<PositionSimplePrintDTO> positionSimplePrintDTOS = stringListEntry.getValue();

            for (PositionSimplePrintDTO positionSimplePrintDTO: positionSimplePrintDTOS) {
                PositionSimpleComponent positionSimpleComponent=null;
                if(PositionSimplePrintConstants.TEXT.equals(positionSimplePrintDTO.getType())){
                    positionSimpleComponent=this.getPositionSimpleComponentString(positionSimplePrintDTO);
                }else if(PositionSimplePrintConstants.IMAGE.equals(positionSimplePrintDTO.getType())){
                    positionSimpleComponent=this.getPositionSimpleComponentImage(positionSimplePrintDTO);
                }else {
                    continue;
                }

                List<PositionSimpleComponent> positionSimpleComponents = pagePositionSimpleComponent.get(key);
                if(positionSimpleComponents==null){
                    positionSimpleComponents=new ArrayList<>();
                    pagePositionSimpleComponent.put(key,positionSimpleComponents);
                }
                positionSimpleComponents.add(positionSimpleComponent);
            }
        }
    }

    private PositionSimpleComponentString getPositionSimpleComponentString(PositionSimplePrintDTO positionSimplePrintDTO) {
        PositionSimplePrintStyleDTO positionSimplePrintStyleDTO = styleList.stream().filter(positionSimplePrintDTO1 -> positionSimplePrintDTO1.getId() < positionSimplePrintDTO.getId()).findFirst().get();
        PositionSimplePrintFontDTO positionSimplePrintFontDTO = fontList.stream().filter(positionSimplePrintDTO1 -> positionSimplePrintDTO1.getId() < positionSimplePrintDTO.getId()).findFirst().get();

        PositionSimpleComponentString positionSimpleComponent=new PositionSimpleComponentString(positionSimplePrintDTO,positionSimplePrintFontDTO,positionSimplePrintStyleDTO,height,width);
        return positionSimpleComponent;
    }

    private PositionSimpleComponentImage getPositionSimpleComponentImage(PositionSimplePrintDTO positionSimplePrintDTO) {
        PositionSimplePrintStyleDTO positionSimplePrintStyleDTO = styleList.stream().filter(positionSimplePrintDTO1 -> positionSimplePrintDTO1.getId() < positionSimplePrintDTO.getId()).findFirst().get();
        PositionSimpleComponentImage positionSimpleComponent=new PositionSimpleComponentImage(positionSimplePrintDTO,positionSimplePrintStyleDTO,height,width);
        return positionSimpleComponent;
    }

    public int getPageSize() {
        return pagePositionSimpleComponent.size();
    }

    public void print(int pageIndex, Graphics graphics, double xpadding, double ypadding) {
        List<PositionSimpleComponent> positionSimplePrintList = pagePositionSimpleComponent.get(prefix+pageIndex);
        if(positionSimplePrintList!=null) {
            for (PositionSimpleComponent positionSimplePrintDTO : positionSimplePrintList) {
                positionSimplePrintDTO.print(pageIndex, graphics, xpadding, ypadding);
            }
        }
    }

}
