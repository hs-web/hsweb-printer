package org.hsweb.printer.server;

import com.sun.net.httpserver.HttpServer;
import org.hsweb.printer.server.handler.PrintHandler;
import org.hsweb.printer.server.handler.PrintersHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class PrinterHttpServer {
    private HttpServer httpServer=null;
    private Boolean state=false;

    public PrinterHttpServer(){
        try {
            httpServer= HttpServer.create(new InetSocketAddress(22511), 0);
            httpServer.createContext("/prints",new PrintHandler());
            httpServer.createContext("/prints/printers",new PrintersHandler());
            httpServer.start();
            state = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public HttpServer getHttpServer(){
        return httpServer;
    }

    public Boolean getState() {
        return state;
    }
}
