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

package org.hsweb.printer.utils;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xiongchuang on 2016/8/24 .
 */
public class FontUtil {
    private static Font yaheiMono =null;
    private static Font getYaheiFont(){
        if(yaheiMono==null) {
            InputStream inputStream = FontUtil.class.getClassLoader().getResourceAsStream("yahei_mono.ttf");
            BufferedInputStream fb = new java.io.BufferedInputStream(inputStream);
            try {
                yaheiMono= Font.createFont(Font.TRUETYPE_FONT, fb);
            } catch (FontFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return yaheiMono;
    }

    public static Font deriveFont(int style, int size){
        Font font=getYaheiFont();
        return font.deriveFont(style,size);
    }
}
