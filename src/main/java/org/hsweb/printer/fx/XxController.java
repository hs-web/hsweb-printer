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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hsweb.printer.fx.components.BasicComponent;
import org.hsweb.printer.fx.components.dtos.BaseComponentDTO;
import org.hsweb.printer.fx.dtos.PubPropertyDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by xiong on 2017-02-25.
 */
public class XxController  implements ControllerDataInitializable,PropertyController{
    @FXML
    private AnchorPane content;
    @FXML
    private VBox propertyPubName;
    @FXML
    private VBox propertyPubValue;

    public static List<BasicComponent> basicComponents=new ArrayList<>();


    private PubPropertyDTO pubPropertyDTO;



    @Override
    public void initData(Stage nowStage, Object userData, Object sendData) {
        content.setPrefWidth(400);
        content.setPrefHeight(400);
        pubPropertyDTO=new PubPropertyDTO(propertyPubName,propertyPubValue);
        nowStage.show();
       /* content.setOnMousePressed((e) -> {
            if(content.equals(e.getTarget())){
                basicComponents.forEach(basicComponent -> basicComponent.hiddenBoder());
            }
        });*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void insertLable(ActionEvent actionEvent) {
        BaseComponentDTO baseComponentDTO=new BaseComponentDTO();
        baseComponentDTO.setWindowHeight(content.getHeight());
        baseComponentDTO.setWindowWidth(content.getWidth());
        baseComponentDTO.setContext("插入文本");
        BasicComponent printText = new BasicComponent(baseComponentDTO,this);
        basicComponents.add(printText);
        content.getChildren().add(printText);
        //Event.fireEvent(printText, );
    }
    public void inserObj(ActionEvent actionEvent) {
        BaseComponentDTO baseComponentDTO=new BaseComponentDTO();
        baseComponentDTO.setWindowHeight(content.getHeight());
        baseComponentDTO.setWindowWidth(content.getWidth());
        baseComponentDTO.setContext("插入变量");
        BasicComponent printText = new BasicComponent(baseComponentDTO,this);
        basicComponents.add(printText);
        content.getChildren().add(printText);
        //Event.fireEvent(printText, );
    }

    @Override
    public void  property(BasicComponent basicComponent,BaseComponentDTO baseComponentDTO){

        pubProperty(basicComponent,baseComponentDTO);




    }
    private void pubProperty(BasicComponent basicComponent,BaseComponentDTO baseComponentDTO){
        pubPropertyDTO.clear();
        pubPropertyDTO.add("内容", baseComponentDTO.getContext() + "",(s,f) ->{
            baseComponentDTO.setContext(s);
            basicComponent.changeProperty(baseComponentDTO);
        });
        pubPropertyDTO.add("x", baseComponentDTO.getX() + "", (s,f) ->{
            try {
                baseComponentDTO.setX(Double.parseDouble(s));
                basicComponent.changeProperty(baseComponentDTO);
            }catch (NumberFormatException e){
                f.setText(baseComponentDTO.getX()+"");
                f.requestFocus();
            }
        });
        pubPropertyDTO.add("y", baseComponentDTO.getY() + "", (s,f) ->{
            try {
                baseComponentDTO.setY(Double.parseDouble(s));
                basicComponent.changeProperty(baseComponentDTO);
            }catch (NumberFormatException e){
                f.setText(baseComponentDTO.getY()+"");
                f.requestFocus();
            }
        });


    }


}
