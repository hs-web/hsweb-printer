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

package org.hsweb.printer.server.handler;


import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.hsweb.printer.dtos.PrintInputDTO;
import org.hsweb.printer.dtos.PrintResultDTO;
import org.hsweb.printer.utils.PrintUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by xiongchuang on 2016/8/19 .
 */
public class PrintHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");


        InputStream requestBody = httpExchange.getRequestBody();
        String s = inputStream2String(requestBody);
        PrintInputDTO printInputDTO= JSON.parseObject(s,PrintInputDTO.class);

        PrintResultDTO printResultVo = PrintUtil.print(printInputDTO);

        byte[] data =JSON.toJSONString(printResultVo).getBytes();
        httpExchange.sendResponseHeaders(200, data.length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(data);
        httpExchange.close();
    }

    public static String inputStream2String(InputStream   is)   throws   IOException{
        ByteArrayOutputStream baos   =   new   ByteArrayOutputStream();
        int   i=-1;
        while((i=is.read())!=-1){
            baos.write(i);
        }
        return   baos.toString();
    }


}
