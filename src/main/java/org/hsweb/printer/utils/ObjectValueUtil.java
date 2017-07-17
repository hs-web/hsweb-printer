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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by xiong on 2017-07-17.
 */
public class ObjectValueUtil {
    public static Object getObjectValue(Object obj, String code){
        if(obj==null||code==null||code.length()==0){
            return null;
        }
        if(obj instanceof Map){
           return getMapVaule((Map<String, Object>) obj,code);
        }else {
            return getObjVaule(obj,code);
        }

    }

    private static Object getObjVaule(Object obj, String code) {
        if(obj==null||code==null||code.length()==0){
            return null;
        }

        String[] split = code.split("\\.");

        if(split.length==1){
            return getValue(obj,code);
        }

        Object returnValue = getObject(obj, split);

        return returnValue;
    }
    private static Object getMapVaule(Map<String,Object> obj, String code) {
        if(obj==null||code==null||code.length()==0){
            return null;
        }

        String[] split = code.split("\\.");

        if(split.length==1){
            return obj.get(code);
        }

        Object returnValue=getObject(obj, split);
        return returnValue;
    }

    private static Object getObject(Object obj, String[] split) {
        Object returnValue=null;
        Object upObj=obj;
        for (int i=0;i<split.length;i++) {
            String  name = split[i];
            if(upObj==null||name==null||name.length()==0){
                break;
            }
            Object value =null;
            if(upObj instanceof Map){
                value=((Map) upObj).get(name);
            }else {
                value=getValue(upObj, name);
            }

            if(value==null){
                break;
            }
            upObj=value;
            if(i+1==split.length){
                returnValue=upObj;
            }
        }
        return returnValue;
    }


    private static Object getValue(Object obj,String code) {
        if(obj==null||code==null||code.length()==0){
            return null;
        }
        Class<Object> objclass= (Class<Object>) obj.getClass();
        String methodName="get" + code.substring(0, 1).toUpperCase() + code.substring(1);

        boolean noSuchMethod=false;
        try {
            Method objm =objclass.getDeclaredMethod(methodName);
            objm.setAccessible(true);
            Object objvalue=objm.invoke(obj);
            return objvalue;
        } catch (NoSuchMethodException e) {
            noSuchMethod=true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if(!noSuchMethod) {
            return null;
        }
        try {
            Method objm = objclass.getMethod(methodName);
            Object objvalue = objm.invoke(obj);
            return objvalue;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return null;
    }
}
