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

package org.hsweb.printer.utils.printable.labelprint;

import org.hsweb.printer.utils.SemacodeTool;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by xiongchuang on 2016/8/23 .
 */
public class LablePrintLineQrcode implements LablePrintLine{
    private int x;
    private int size;
    private String qrcodeString;

    private BufferedImage bufferedImage;

    public LablePrintLineQrcode(int x, int size, String qrcodeString) {
        this.x = x;
        this.size = size;
        this.qrcodeString = qrcodeString;
        image();
    }

    private void image(){
        this.bufferedImage=SemacodeTool.toBufferedImage(qrcodeString,size);
    }


    @Override
    public float getHeight() {
        return size+size*0.4f;
    }

    @Override
    public void print(float xpadding,float y,Graphics2D g2){
        g2.drawImage(bufferedImage,x,(int)(y+size*0.2f),size,size,null);
    }
}
