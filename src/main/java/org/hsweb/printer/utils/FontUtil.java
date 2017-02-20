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
    private static Font sourceCodePro ;//= new Font("SourceCodePro-Medium.ttf",Font.PLAIN,12);

    private static Font getFont(String fileName) {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
        BufferedInputStream fb = new BufferedInputStream(inputStream);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, fb);
            return font;
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream!=null)
                    inputStream.close();
                if(fb!=null)
                    fb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Font printFont(int style, int size) {
        return new Font("微软雅黑",style,size);
    }
    public static Font printLetterFont(int style, int size) {
        return deriveSourceCodeProFont(style,size);
    }
    /**
     * 获取雅黑字体
     *
     * @param style
     * @param size
     * @return
     */
  /*  public static Font deriveYaheiMonoFont(int style, int size) {
        if (yaheiMono==null){
            yaheiMono=getFont("yahei_mono.ttf");
        }
        return yaheiMono.deriveFont(style, size);
    }*/
    public static Font deriveSourceCodeProFont(int style, int size) {
        if (sourceCodePro==null){
            sourceCodePro=getFont("SourceCodePro-Medium.ttf");
            if(sourceCodePro==null){
                sourceCodePro=new Font("courier new",style,size);
            }
        }
        return sourceCodePro.deriveFont(style, size);
    }
/*    public static Font deriveMutterFont(int style, int size) {
        if (mutter==null){
            mutter=getFont("mutter_font.ttf");
        }
        return mutter.deriveFont(style, size);
    }*/
    public static void main(String[] args) {
        FontUtil.deriveSourceCodeProFont(Font.PLAIN,12);
    }
}
