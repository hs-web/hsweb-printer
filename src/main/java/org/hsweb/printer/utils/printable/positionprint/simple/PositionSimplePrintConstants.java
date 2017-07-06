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

package org.hsweb.printer.utils.printable.positionprint.simple;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xiong on 2017-07-06.
 */
public interface PositionSimplePrintConstants {
    String TEXT="TEXT";
    String IMAGE="IMAGE";
    String FONT="FONT";
    String STYLE="STYLE";


    Set<String> NO_HEIGHT=new HashSet<String>(){
        {
            add(FONT);
            add(STYLE);
        }
    };
}
