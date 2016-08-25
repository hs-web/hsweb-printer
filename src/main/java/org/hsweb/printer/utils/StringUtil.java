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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiongchuang on 2016/8/23 .
 */
public class StringUtil {
    /**
     * 获取字符串长度  汉字为2
     * @param s
     * @return
     */
    public static int length(String s) {

        if (s == null)
            return 0;

        int len = 0;
        for (char c:s.toCharArray()) {
            len+=charLength(c);
        }
        return len;
    }

    /**
     * 获取char长度  汉字为2
     * @param c
     * @return
     */
    public static int charLength(char c){
        int len=1;
        int k = 0x80;
        if (!(c / k == 0 ? true : false)) {
            len++;
        }
        return len;
    }

    /**
     * 字符串按长度分组 汉字为2
     * @param s
     * @param max
     * @return
     */
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

}
