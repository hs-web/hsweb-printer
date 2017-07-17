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

import com.alibaba.fastjson.JSON;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hsweb.printer.fx.components.BasicComponent;
import org.hsweb.printer.fx.dtos.PubPropertyDTO;
import org.hsweb.printer.utils.printable.templateptint.TemplatePrintConstants;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.TextComponentDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.VariableComponentDTO;

import java.io.*;
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
    private String printName="打印模板";



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

    private void add(TemplateComponentDTO baseComponentDTO){
        BasicComponent printText = new BasicComponent(baseComponentDTO,this);
        basicComponents.add(printText);
        content.getChildren().add(printText);
    }

    public void insertLable(ActionEvent actionEvent) {
        TextComponentDTO baseComponentDTO=new TextComponentDTO();
        baseComponentDTO.setType(TemplatePrintConstants.TEXT);
        baseComponentDTO.setWindowHeight(content.getHeight());
        baseComponentDTO.setWindowWidth(content.getWidth());
        baseComponentDTO.setContext("插入文本");

        this.add(baseComponentDTO);
    }
    public void inserObj(ActionEvent actionEvent) {
        VariableComponentDTO baseComponentDTO=new VariableComponentDTO();
        baseComponentDTO.setType(TemplatePrintConstants.VARIABLE);
        baseComponentDTO.setWindowHeight(content.getHeight());
        baseComponentDTO.setWindowWidth(content.getWidth());
        baseComponentDTO.setContext("插入变量");

        this.add(baseComponentDTO);
    }

    @Override
    public void  property(BasicComponent basicComponent,TemplateComponentDTO baseComponentDTO){

        pubProperty(basicComponent,baseComponentDTO);
    }

    private void pubProperty(BasicComponent basicComponent,TemplateComponentDTO baseComponentDTO){
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
    public String getFileUrl(String title){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage s = new Stage();
        File file = fileChooser.showOpenDialog(s);
        if (file == null) return null;
        String exportFilePath = file.getAbsolutePath().replaceAll(".json", "") + ".json.";
        return exportFilePath;
    }

    public void importFile(ActionEvent actionEvent) {
        String exportFilePath =getFileUrl("选择文件" );
        if(exportFilePath==null){
            return;
        }
        StringBuilder json=new StringBuilder();
        try (BufferedReader file1 = new BufferedReader(new FileReader(new File(exportFilePath)))) {
            while(file1.ready()){
                json.append(file1.readLine());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(json.length()==0){
            return;
        }
        TemplateDTO baseComponentDTOS = JSON.parseObject(json.toString(), TemplateDTO.class);

        content.getChildren().clear();
        for (TemplateComponentDTO baseComponentDTO : baseComponentDTOS.getComponentDTOS()) {
            TemplateComponentDTO baseComponentDTO1 = JSON.parseObject(JSON.toJSONBytes(baseComponentDTO), TemplateComponentDTO.class);
            baseComponentDTO1.setWindowWidth(baseComponentDTOS.getWindowWidth());
            baseComponentDTO1.setWindowHeight(baseComponentDTOS.getWindowHeight());
            this.add(baseComponentDTO1);
        }

    }
    public void exportFile(ActionEvent actionEvent) {
        String exportFilePath =getFileUrl("选择要保存的位置" );
        if(exportFilePath==null){
            return;
        }
        List<TemplateComponentDTO> baseComponentDTOS = new ArrayList<>();
        for (BasicComponent basicComponent : basicComponents) {
            baseComponentDTOS.add(basicComponent.getBaseComponentDTO());
        }

        TemplateDTO exportDTO = new TemplateDTO();
        exportDTO.setPrintName(printName);
        exportDTO.setWindowHeight(content.getHeight());
        exportDTO.setWindowWidth(content.getWidth());
        exportDTO.setComponentDTOS(baseComponentDTOS);

        try (FileWriter file1 = new FileWriter(new File(exportFilePath))) {
            file1.write(JSON.toJSONString(exportDTO));
            file1.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
