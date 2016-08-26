package org.hsweb.printer.server;

import com.sun.net.httpserver.HttpServer;
import org.hsweb.printer.server.handler.PrintHandler;
import org.hsweb.printer.server.handler.PrintersHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class PrinterHttpServer {
    private HttpServer httpServer=null;
    private Boolean state=false;

    public PrinterHttpServer() throws IOException {
        httpServer= HttpServer.create(new InetSocketAddress(22511), 0);
        httpServer.createContext("/prints",new PrintHandler());
        httpServer.createContext("/prints/printers",new PrintersHandler());
    }

    public void start(){
        httpServer.start();
        state=true;
        System.out.println("打印服务启动成功！");
    }

    public HttpServer getHttpServer(){
        return httpServer;
    }

    public Boolean getState() {
        return state;
    }
}
