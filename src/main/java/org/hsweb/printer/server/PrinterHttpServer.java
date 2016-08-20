package org.hsweb.printer.server;

import com.sun.net.httpserver.HttpServer;
import org.hsweb.printer.server.handler.PrintHandler;
import org.hsweb.printer.server.handler.PrintersHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class PrinterHttpServer {

    public void start() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(22511), 0);
        httpServer.createContext("/prints",new PrintHandler());
        httpServer.createContext("/prints/printers",new PrintersHandler());
        httpServer.start();
    }

    public static void main(String[] args) throws IOException {
        new PrinterHttpServer().start();
    }
}
