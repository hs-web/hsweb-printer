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

package org.hsweb.printer.fx;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * Created by xiong on 2017-02-21.
 */
public interface ControllerDataInitializable<E> extends Initializable {
    void initData(Stage nowStage, Object userData, E sendData);
}
