package org.hsweb.printer.dtos;

import java.io.Serializable;

/**
 * Created by xiong on 2016/8/20.
 */
public class PrinterDTO implements Serializable {
    private String printerName;
    private String printerPort;

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrinterPort() {
        return printerPort;
    }

    public void setPrinterPort(String printerPort) {
        this.printerPort = printerPort;
    }

    @Override
    public String toString() {
        return "PrinterDTO{" +
                "printerName='" + printerName + '\'' +
                ", printerPort='" + printerPort + '\'' +
                '}';
    }
}
