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
import org.hsweb.printer.fx.components.BasicComponent;
import org.hsweb.printer.fx.components.dtos.BaseComponentDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by xiong on 2017-02-25.
 */
public class XxController  implements ControllerDataInitializable{
    @FXML
    private AnchorPane content;

    public static List<BasicComponent> basicComponents=new ArrayList<>();


    @Override
    public void initData(Stage nowStage, Object userData, Object sendData) {
        content.setPrefWidth(400);
        content.setPrefHeight(400);
        nowStage.show();
        content.setOnMousePressed((e) -> {
            if(content.equals(e.getTarget())){
                basicComponents.forEach(basicComponent -> basicComponent.hiddenBoder());
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void insertLable(ActionEvent actionEvent) {
        BaseComponentDTO baseComponentDTO=new BaseComponentDTO();
        baseComponentDTO.setWindowHeight(content.getHeight());
        baseComponentDTO.setWindowWidth(content.getWidth());
        baseComponentDTO.setContext("插入文本");
        BasicComponent printText = new BasicComponent(baseComponentDTO);
        basicComponents.add(printText);
        content.getChildren().add(printText);
        //Event.fireEvent(printText, );
    }
    public void inserObj(ActionEvent actionEvent) {
        BaseComponentDTO baseComponentDTO=new BaseComponentDTO();
        baseComponentDTO.setWindowHeight(content.getHeight());
        baseComponentDTO.setWindowWidth(content.getWidth());
        baseComponentDTO.setContext("插入变量");
        BasicComponent printText = new BasicComponent(baseComponentDTO);
        basicComponents.add(printText);
        content.getChildren().add(printText);
        //Event.fireEvent(printText, );
    }


}
