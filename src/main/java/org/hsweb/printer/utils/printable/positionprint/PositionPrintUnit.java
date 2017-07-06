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

package org.hsweb.printer.utils.printable.positionprint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiong on 2017-02-24.
 */
public class PositionPrintUnit {
    //1in = 2.54cm = 25.4mm = 72pt = 96px     =7.14375使用单位
    private static final float printProportion=7.14375F;
    private static final float cmProportion=2.54F/printProportion;
    private static final float mmProportion=25.4F/printProportion;
    private static final float ptProportion=72F/printProportion;
    private static final float pxProportion=96F/printProportion;

    private final static  Pattern pattern = Pattern.compile("(\\d+.?\\d+)([a-zA-z]{2})?");
    public static double parsingUnit(String s){
        Matcher matcher = pattern.matcher(s);
        try {
            if(matcher.matches()){
                double group2 = Double.parseDouble(matcher.group(1));
                String group3 = matcher.group(2);
                if(null==group3){
                    return group2;
                }
                if("xp".equals(group3)){
                    return pxToPinrt(group2);
                }
                if("mm".equals(group3)){
                    return mmToPinrt(group2);
                }

                if("pt".equals(group3)){
                    return ptToPinrt(group2);
                }
                if("cm".equals(group3)){
                    return cmToPinrt(group2);
                }
                if("in".equals(group3)){
                    return inToPinrt(group2);
                }


            }
        }catch (NumberFormatException e){
        }

        return -1;
    }


    public static double pxToPinrt(double px){
        return px/pxProportion;
    }
    private static double ptToPinrt(double pt) {
        return pt/ptProportion;
    }
    private static double mmToPinrt(double mm) {
        return mm/mmProportion;
    }
    private static double cmToPinrt(double cm) {
        return cm/cmProportion;
    }
    public static double inToPinrt(double in){
        return in*printProportion;
    }

    public static void main(String[] args) {
        System.out.println(parsingUnit("1111111111.1cm"));
    }
}
