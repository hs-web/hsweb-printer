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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hsweb.printer.fx.x.PrintText;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by xiong on 2017-02-25.
 */
public class XxController  implements ControllerDataInitializable{
    @FXML
    private AnchorPane content;


    @Override
    public void initData(Stage nowStage, Object userData, Object sendData) {
        content.setPrefWidth(400);
        content.setPrefHeight(400);
        nowStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void insertText(ActionEvent actionEvent) {
        PrintText printText = new PrintText(content.getWidth(),content.getHeight());
        content.getChildren().add(printText);
        //Event.fireEvent(printText, );
    }

}
