package org.hsweb.printer.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class PrinterHttpServer {

    public void start() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(2511), 0);
        httpServer.createContext("/print", httpExchange -> {
            byte[] data = "{\"message\":\"乱码？\"}".getBytes();
            httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin","*");
            httpExchange.sendResponseHeaders(200, data.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(data);
            httpExchange.close();
        });
        httpServer.start();
    }

    public static void main(String[] args) throws IOException {
        new PrinterHttpServer().start();
    }
}
