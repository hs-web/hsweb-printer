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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hsweb.printer.fx.components.BasicComponent;
import org.hsweb.printer.fx.components.Component;
import org.hsweb.printer.fx.components.ImageComponent;
import org.hsweb.printer.fx.dtos.PropertyDTO;
import org.hsweb.printer.utils.printable.templateptint.TemplatePrintConstants;
import org.hsweb.printer.utils.printable.templateptint.dtos.*;

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

    @FXML
    private TitledPane propertyFont;
    @FXML
    private VBox propertyFontName;
    @FXML
    private VBox propertyFontValue;

    private Component nowComponent;


    public List<Component> basicComponents=new ArrayList<>();


    private PropertyDTO pubPropertyDTO;
    private PropertyDTO fontPropertyDTO;
    private TemplateComponentDTO contentComponentDTO;



    @Override
    public void initData(Stage nowStage, Object userData, Object sendData) {
        content.setPrefWidth(400);
        content.setPrefHeight(400);
        pubPropertyDTO=new PropertyDTO(propertyPubName,propertyPubValue);
        fontPropertyDTO=new PropertyDTO(propertyFontName,propertyFontValue);
        nowStage.show();
       /* content.setOnMousePressed((e) -> {
            if(content.equals(e.getTarget())){
                basicComponents.forEach(basicComponent -> basicComponent.hiddenBoder());
            }
        });*/
        contentComponentDTO=new TemplateComponentDTO();
        contentComponentDTO.setWidth(400D);
        contentComponentDTO.setHeight(400D);
        contentComponentDTO.setContext("打印模板");

        content.setOnMousePressed((e)->{
            if (e.getTarget().equals(content)) {
                contentProperty();
            }
        });
        content.setOnKeyReleased((e)->{
            KeyCode code = e.getCode();
            if(nowComponent!=null&&KeyCode.DELETE.equals(code)){
                basicComponents.remove(nowComponent);
                content.getChildren().remove(nowComponent.getThisNode());
            }
        });
        contentProperty();
    }

    private void contentProperty() {
        nowComponent=null;
        this.propertyFont.setVisible(false);
        pubPropertyDTO.clear();
        pubPropertyDTO.add("文档标题", contentComponentDTO.getContext(),(s,f) ->{
            contentComponentDTO.setContext(s);
        });

        pubPropertyDTO.add("高度", contentComponentDTO.getHeight() + "", (s,f) ->{
            try {
                double height = Double.parseDouble(s);
                contentComponentDTO.setHeight(height);
                content.setPrefHeight(height);
                for (Component basicComponent : basicComponents) {
                    basicComponent.getComponent().setHeight(height);
                    basicComponent.changeProperty(basicComponent.getComponent());
                }
            }catch (NumberFormatException e2){
                f.setText(content.getHeight()+"");
                f.requestFocus();
            }
        });
        pubPropertyDTO.add("宽度", contentComponentDTO.getWidth() + "", (s,f) ->{
            try {
                double width = Double.parseDouble(s);
                contentComponentDTO.setWidth(width);
                content.setPrefWidth(width);

                for (Component basicComponent : basicComponents) {
                    basicComponent.getComponent().setWidth(width);
                    basicComponent.changeProperty(basicComponent.getComponent());
                }
            }catch (NumberFormatException e2){
                f.setText(contentComponentDTO.getWidth()+"");
                f.requestFocus();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void add(TemplateComponentDTO baseComponentDTO){
        if(TemplatePrintConstants.TEXT.equals(baseComponentDTO.getType())
                ||TemplatePrintConstants.VARIABLE.equals(baseComponentDTO.getType())) {
            BasicComponent printText = new BasicComponent(baseComponentDTO, this);
            basicComponents.add(printText);
            content.getChildren().add(printText);
        }else if(TemplatePrintConstants.IMAGE.equals(baseComponentDTO.getType())
                ||TemplatePrintConstants.QRCODE.equals(baseComponentDTO.getType())){
            ImageComponent imageComponent= new ImageComponent(baseComponentDTO, this);
            basicComponents.add(imageComponent);
            content.getChildren().add(imageComponent);
        }
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
    public void inserImage(ActionEvent actionEvent) {
        ImageComponentDTO baseComponentDTO=new ImageComponentDTO();
        baseComponentDTO.setType(TemplatePrintConstants.IMAGE);
        baseComponentDTO.setWindowHeight(content.getHeight());
        baseComponentDTO.setWindowWidth(content.getWidth());
        baseComponentDTO.setContext("");
        baseComponentDTO.setWidth(100D);
        baseComponentDTO.setHeight(100D);

        this.add(baseComponentDTO);
    }
    public void inserQrcode(ActionEvent actionEvent) {
        QrcodeComponentDTO baseComponentDTO=new QrcodeComponentDTO();
        baseComponentDTO.setType(TemplatePrintConstants.QRCODE);
        baseComponentDTO.setWindowHeight(content.getHeight());
        baseComponentDTO.setWindowWidth(content.getWidth());
        baseComponentDTO.setContext("");
        baseComponentDTO.setWidth(100D);
        baseComponentDTO.setHeight(100D);

        this.add(baseComponentDTO);
    }

    @Override
    public void  property(Component basicComponent,TemplateComponentDTO baseComponentDTO){
        nowComponent=basicComponent;
        this.propertyFont.setVisible(false);

        if(TemplatePrintConstants.TEXT.equals(baseComponentDTO.getType())){
            this.textProperty(basicComponent,(TextComponentDTO)baseComponentDTO);
        }else  if(TemplatePrintConstants.VARIABLE.equals(baseComponentDTO.getType())){
            this.variableProperty(basicComponent,(VariableComponentDTO)baseComponentDTO);
        }else  if(TemplatePrintConstants.IMAGE.equals(baseComponentDTO.getType())){
            this.imageProperty(basicComponent,(ImageComponentDTO)baseComponentDTO);
        }else  if(TemplatePrintConstants.QRCODE.equals(baseComponentDTO.getType())){
            this.qrcodeProperty(basicComponent,(ImageComponentDTO)baseComponentDTO);
        }
    }



    private void pubProperty(Component basicComponent,TemplateComponentDTO baseComponentDTO,String contentName){
        pubPropertyDTO.clear();
        pubPropertyDTO.add(contentName, baseComponentDTO.getContext() + "",(s,f) ->{
            baseComponentDTO.setContext(s);
            basicComponent.changeProperty(baseComponentDTO);
        });
        pubPropertyDTO.add("坐标x", baseComponentDTO.getX() + "", (s,f) ->{
            try {
                baseComponentDTO.setX(Double.parseDouble(s));
                basicComponent.changeProperty(baseComponentDTO);
            }catch (NumberFormatException e){
                f.setText(baseComponentDTO.getX()+"");
                f.requestFocus();
            }
        });
        pubPropertyDTO.add("坐标y", baseComponentDTO.getY() + "", (s,f) ->{
            try {
                baseComponentDTO.setY(Double.parseDouble(s));
                basicComponent.changeProperty(baseComponentDTO);
            }catch (NumberFormatException e){
                f.setText(baseComponentDTO.getY()+"");
                f.requestFocus();
            }
        });
        pubPropertyDTO.add("高度", baseComponentDTO.getHeight() + "", (s,f) ->{
            try {
                baseComponentDTO.setHeight(Double.parseDouble(s));
                basicComponent.changeProperty(baseComponentDTO);
            }catch (NumberFormatException e){
                f.setText(baseComponentDTO.getHeight()+"");
                f.requestFocus();
            }
        });
        pubPropertyDTO.add("宽度", baseComponentDTO.getWidth() + "", (s,f) ->{
            try {
                baseComponentDTO.setWidth(Double.parseDouble(s));
                basicComponent.changeProperty(baseComponentDTO);
            }catch (NumberFormatException e){
                f.setText(baseComponentDTO.getWidth()+"");
                f.requestFocus();
            }
        });


    }
    private void textProperty(Component basicComponent, TextComponentDTO baseComponentDTO) {
        this.pubProperty(basicComponent,baseComponentDTO,"内容");
        this.fontProperty(basicComponent,baseComponentDTO);

    }

    private void fontProperty(Component basicComponent, TextComponentDTO baseComponentDTO) {
        this.propertyFont.setVisible(true);
        fontPropertyDTO.clear();
        fontPropertyDTO.add("字体名", baseComponentDTO.getFontName() + "",(s,f) ->{
            if(s==null||s.length()==0){
                f.setText(baseComponentDTO.getFontName());
            }else {
                baseComponentDTO.setFontName(s);
                basicComponent.changeProperty(baseComponentDTO);
            }
        });
        fontPropertyDTO.add("字体大小", baseComponentDTO.getFontSize() + "", (s,f) ->{
            try {
                baseComponentDTO.setFontSize(Integer.parseInt(s));
                basicComponent.changeProperty(baseComponentDTO);
            }catch (NumberFormatException e){
                f.setText(baseComponentDTO.getX()+"");
                f.requestFocus();
            }
        });

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.valueOf(baseComponentDTO.getColor()));
        colorPicker.setOnAction(event -> {
            Color color = colorPicker.getValue();
            Color desaturate = color.desaturate();
            String s = desaturate.toString();
            baseComponentDTO.setColor(s);
            basicComponent.changeProperty(baseComponentDTO);
        });
        fontPropertyDTO.add("颜色", colorPicker);
       /* fontPropertyDTO.add("字体样式", baseComponentDTO.getY() + "", (s,f) ->{
            try {
                baseComponentDTO.setY(Double.parseDouble(s));
                basicComponent.changeProperty(baseComponentDTO);
            }catch (NumberFormatException e){
                f.setText(baseComponentDTO.getY()+"");
                f.requestFocus();
            }
        });*/
    }

    private void variableProperty(Component basicComponent, VariableComponentDTO baseComponentDTO) {
        this.pubProperty(basicComponent,baseComponentDTO,"变量名");
        this.fontProperty(basicComponent,baseComponentDTO);
    }

    private void imageProperty(Component basicComponent, ImageComponentDTO baseComponentDTO) {
        this.pubProperty(basicComponent,baseComponentDTO,"变量名");
    }
    private void qrcodeProperty(Component basicComponent, ImageComponentDTO baseComponentDTO) {
        this.pubProperty(basicComponent,baseComponentDTO,"变量名");
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
        for (Component basicComponent : basicComponents) {
            baseComponentDTOS.add(basicComponent.getComponent());
        }

        TemplateDTO exportDTO = new TemplateDTO();
        exportDTO.setPrintName(contentComponentDTO.getContext());
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
