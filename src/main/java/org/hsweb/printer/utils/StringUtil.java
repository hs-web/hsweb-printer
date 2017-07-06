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
    public static boolean isEmpty(CharSequence charSequence){
        if(charSequence==null||charSequence.length()==0){
            return true;
        }
        return false;
    }
    public static boolean isNotEmpty(CharSequence charSequence){
        return !isEmpty(charSequence);
    }
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
    public static List<String> lengthSplit(String s, int max){
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
    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        return camelName(name, false, true);
    }

    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     *
     * @param name                 转换前的下划线大写方式命名的字符串
     * @param firstUpperCase       转换后第一个字符是否转换为大写 false 保持原样
     * @param upperCaseToLowerCase 非下划线开头的字符是否转换为小写
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name, boolean firstUpperCase, boolean upperCaseToLowerCase) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name != null && !name.isEmpty()) {
            if (!name.contains("_")) {
                if (firstUpperCase) {
                    return name.substring(0, 1).toLowerCase() + name.substring(1);
                }
                return name;
            }

            char[] chars = upperCaseToLowerCase ? name.toLowerCase().toCharArray() : name.toCharArray();
            result.append(firstUpperCase ? Character.toUpperCase(chars[0]) : chars[0]);
            for (int i = 1; i < chars.length; i++) {
                char c = chars[i];
                if (c == '_') {
                    c = chars[++i];
                    result.append(Character.toUpperCase(c));
                } else {
                    result.append(c);
                }
            }
        }

        return result.toString();
    }
    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     *
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {
        return underscoreName(name, false);
    }

    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     *
     * @param name           转换前的驼峰式命名的字符串
     * @param firstUpperCase 转换后第一个字符是否转换为大写 false 保持原样
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name, boolean firstUpperCase) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            char[] chars = name.toCharArray();
            result.append(firstUpperCase ? Character.toUpperCase(chars[0]) : chars[0]);
            // 循环处理其余字符
            for (int i = 1; i < chars.length; i++) {
                char c = chars[i];
                // 在大写字母前添加下划线
                if (Character.toUpperCase(c) == c && !Character.isDigit(c)) {
                    result.append("_").append(Character.toLowerCase(c));
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }
}
