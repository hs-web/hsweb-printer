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

import javafx.application.Application;
import javafx.stage.Stage;
import org.hsweb.printer.fx.utils.FXContentUtil;

/**
 * Created by xiong on 2017-02-25.
 */
public class M extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXContentUtil.sceneContent(primaryStage,"fxml/X.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}
