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

package org.hsweb.print;

import com.alibaba.fastjson.JSON;
import org.hsweb.printer.dtos.PrintResultDTO;
import org.hsweb.printer.utils.PrintUtil;
import org.hsweb.printer.utils.printable.TemplatePrintable;
import org.hsweb.printer.utils.printable.templateptint.dtos.PrintTemplateDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiong on 2017-07-06.
 */
public class TemplatePrintTest {
    public static void main(String[] args) {
        String temp="{\"componentDTOS\":[{\"align\":0,\"color\":\"#000000\",\"context\":\"插入文本\",\"fontName\":\"微软雅黑\",\"fontSize\":8,\"height\":20,\"type\":\"TEXT\",\"width\":100,\"windowHeight\":400,\"windowWidth\":400,\"x\":67,\"y\":92},{\"align\":0,\"color\":\"#000000\",\"context\":\"name\",\"fontName\":\"微软雅黑\",\"fontSize\":8,\"height\":20,\"type\":\"VARIABLE\",\"width\":100,\"windowHeight\":400,\"windowWidth\":400,\"x\":100,\"y\":92}],\"printName\":\"打印模板\",\"windowHeight\":400,\"windowWidth\":400}";
        PrintTemplateDTO templateDTO = JSON.parseObject(temp, PrintTemplateDTO.class);
        Map<String,String> map=new HashMap<>();
        map.put("name","张三");

        TemplatePrintable positionSimplePrintable=new TemplatePrintable(templateDTO,map);
        PrintResultDTO xxx = PrintUtil.print("xxx", positionSimplePrintable);

    }
}
