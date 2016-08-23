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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiongchuang on 2016/8/23 .
 */
public class StringUtil {
    /**
     * 处理输入的字符串，将字符串分割成以byteLength为宽度的多行字符串。
     * 根据需要，英文字符的空格长度为0.5，汉字的长度为2（GBK编码下，UTF-8下为3），数字英文字母宽度为1.05。
     *
     * @param inputString 输入字符串
     * @param byteLength  以byteLength的长度进行分割（一行显示多宽）
     * @return 处理过的字符串
     */
    public static String getChangedString(String inputString, int byteLength, String charset) {
        String outputString = inputString;
        try {

            char[] chars = inputString.toCharArray();
            char[] workChars = new char[chars.length * 2];

            // i为工作数组的角标，length为工作过程中长度,stringLength为字符实际长度,j为输入字符角标
            int i = 0, stringLength = 0;
            float length = 0;
            for (int j = 0; j < chars.length - 1; i++, j++) {

                // 如果源字符串中有换行符，此处要将工作过程中计算的长度清零
                if (chars[j] == ' ') {
                    length = 0;
                }
                try {
                    workChars[i] = chars[j];
                    //对汉字字符进行处理
                    if (new Character(chars[j]).toString().getBytes(charset).length == 2 /*&& chars[j] != '”' && chars[j] != '“'*/) {
                        length++;
                        if (length >= byteLength) {
                            if (chars[j + 1] != ' ') {
                                i++;
                                stringLength++;
                                workChars[i] = ' ';
                            }
                            length = 0;
                        }
                    } else if (new Character(chars[j]).toString().getBytes(charset).length == 1) {
                        //对空格何应为字符和数字进行处理。
                        if (chars[j] == ' ') {
                            length -= 0.5;
                        } else {
                            length += 0.05;
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                stringLength++;
                length++;
                //长度超过给定的长度，插入

                if (length >= byteLength) {
                    if (chars[j + 1] != ' ') {
                        i++;
                        stringLength++;
                        workChars[i] = ' ';
                    }
                    length = 0;
                }
            }
            outputString = new String(workChars).substring(0, stringLength)/* .trim() */;
            System.out.println(outputString);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return outputString;
    }
    public static int length(String s) {

        if (s == null)
            return 0;

        int len = 0;
        for (char c:s.toCharArray()) {
            len+=charLength(c);
        }
        return len;
    }
    public static int charLength(char c){
        int len=1;
        int k = 0x80;
        if (!(c / k == 0 ? true : false)) {
            len++;
        }
        return len;
    }
    public static List<String> lengthSplit(String s,int max){
        List<String> strings=new ArrayList<String>();
        if(StringUtil.length(s)<=max||max<2){
            strings.add(s);
            return strings;
        }

        int stringLength=0;
        StringBuilder newString=new StringBuilder();
        for (char c:s.toCharArray()){
            int cLength=charLength(c);
            stringLength+=cLength;

            if(stringLength<=max){
                newString.append(c);
                continue;
            }

            strings.add(newString.toString());
            stringLength=cLength;
            newString=new StringBuilder();
            newString.append(c);
        }
        strings.add(newString.toString());
        return strings;
    }

    public static void main(String[] args) {
        List<String> strings = lengthSplit("你好1你11下1", 2);
        System.out.println(strings.size());
    }

}
