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

package org.hsweb.printer.dtos;

import java.io.Serializable;

/**
 * Created by xiongchuang on 2016/8/27 .
 */
public class PrintHistoryDTO implements Serializable {
    private PrintInputDTO printInputDTO;
    private PrintResultDTO printResultDTO;

    public PrintInputDTO getPrintInputDTO() {
        return printInputDTO;
    }

    public void setPrintInputDTO(PrintInputDTO printInputDTO) {
        this.printInputDTO = printInputDTO;
    }

    public PrintResultDTO getPrintResultDTO() {
        return printResultDTO;
    }

    public void setPrintResultDTO(PrintResultDTO printResultDTO) {
        this.printResultDTO = printResultDTO;
    }
}
