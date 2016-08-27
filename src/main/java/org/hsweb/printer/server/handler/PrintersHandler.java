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
import org.hsweb.printer.dtos.PrinterDTO;
import org.hsweb.printer.utils.PrintUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by xiongchuang on 2016/8/19 .
 */
public class PrintersHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        List<PrinterDTO> printers = PrintUtil.getPrinters();
        String s = JSON.toJSONString(printers);
        byte[] data = s.getBytes();
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");
        httpExchange.sendResponseHeaders(200, data.length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(data);
        httpExchange.close();
    }

    class Pnv{
        private String name;
        private String port;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }
    }
}
