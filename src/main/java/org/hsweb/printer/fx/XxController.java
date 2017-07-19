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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hsweb.printer.fx.components.ComponentFactory;
import org.hsweb.printer.fx.components.ComponentPropertyFactory;
import org.hsweb.printer.fx.components.components.Component;
import org.hsweb.printer.fx.components.components.PanelComponent;
import org.hsweb.printer.fx.components.propertys.dtos.PropertyDTO;
import org.hsweb.printer.fx.components.propertys.dtos.PropertysDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateDTO;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by xiong on 2017-02-25.
 */
public class XxController  implements ControllerDataInitializable,PropertyController{
    /**
     * doc
     */
    @FXML
    private VBox doc;

    private TemplateComponentDTO docComponentDTO;

    private List<PanelComponent> panelComponents=new ArrayList<>();

    /**
     * menu
     */
    @FXML
    private Menu elementMenu;


    /**
     * property
     */
    private PropertysDTO  propertys=new PropertysDTO();
    //propertyPub
    @FXML
    private VBox propertyPubName;
    @FXML
    private VBox propertyPubValue;
    //propertyFont
    @FXML
    private TitledPane propertyFont;
    @FXML
    private VBox propertyFontName;
    @FXML
    private VBox propertyFontValue;


    /**
     * now
     */
    private PanelComponent nowPanelComponent;
    private Component nowComponent;



    @Override
    public void initData(Stage nowStage, Object userData, Object sendData) {
        this.propertys.setElementMenu(elementMenu);
        this.propertys.setPubProperty(new PropertyDTO(propertyPubName, propertyPubValue));
        this.propertys.setPropertyFont(propertyFont);
        this.propertys.setFontProperty(new PropertyDTO(propertyFontName, propertyFontValue));


        nowStage.show();

        this.initDoc();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void initDoc() {
        this.elementMenu.setVisible(false);

        this.docComponentDTO=new TemplateComponentDTO();
        this.docComponentDTO.setWidth(400D);
        this.docComponentDTO.setHeight(400D);
        this.docComponentDTO.setContext("打印模板");
        doc.setOnMousePressed((e)->{
            if (e.getTarget().equals(doc)) {

                docProperty();
            }
        });
        doc.setOnKeyReleased((e)->{
           /* KeyCode code = e.getCode();
            if(nowComponent!=null&&KeyCode.DELETE.equals(code)){
                basicComponents.remove(nowComponent);
               // content.getChildren().remove(nowComponent.getThisNode());
            }*/
        });
        docProperty();
    }

    private void docProperty() {
        nowComponent=null;
        nowPanelComponent=null;
        this.elementMenu.setVisible(false);
        this.propertyFont.setVisible(false);
        PropertyDTO pubPropertyDTO = this.propertys.getPubProperty();
        pubPropertyDTO.clear();
        pubPropertyDTO.add("文档标题", docComponentDTO.getContext(),(s,f) ->{
            docComponentDTO.setContext(s);
        });

        pubPropertyDTO.add("纸张高度", docComponentDTO.getHeight() + "", (s,f) ->{
            try {
                double height = Double.parseDouble(s);
                docComponentDTO.setHeight(height);
            }catch (NumberFormatException e2){
                f.setText(docComponentDTO.getHeight()+"");
                f.requestFocus();
            }
        });

        pubPropertyDTO.add("纸张宽度", docComponentDTO.getWidth() + "", (s,f) ->{
            try {
                double width = Double.parseDouble(s);
                docComponentDTO.setWidth(width);
            }catch (NumberFormatException e2){
                f.setText(docComponentDTO.getWidth()+"");
                f.requestFocus();
            }
        });
    }

    @Override
    public void  property(Component basicComponent,TemplateComponentDTO baseComponentDTO){
        this.nowComponent=basicComponent;
        this.nowPanelComponent=basicComponent.getParentComponent();

        ComponentPropertyFactory.property(propertys,basicComponent,baseComponentDTO);
    }

    @Override
    public void property(PanelComponent basicComponent, TemplateComponentDTO baseComponentDTO) {
        this.nowPanelComponent=basicComponent;
        this.nowComponent=null;
        ComponentPropertyFactory.property(propertys,basicComponent,baseComponentDTO);
    }

    public void insertpanel(ActionEvent actionEvent) {
        if(!(actionEvent.getSource() instanceof MenuItem)){
            return;
        }
        this.elementMenu.setVisible(false);
        MenuItem menuItem = (MenuItem) actionEvent.getSource();

        PanelComponent builder = ComponentFactory.builder(menuItem.getId(), docComponentDTO, this);
        if(builder!=null) {
            panelComponents.add(builder);
            doc.getChildren().add(builder.getThisNode());
        }
        // System.out.println(1);
    }

    public void insertelement(ActionEvent actionEvent) {
        if(!(actionEvent.getSource() instanceof MenuItem)||nowPanelComponent==null){
            return;
        }

        MenuItem menuItem = (MenuItem) actionEvent.getSource();

        nowPanelComponent.add(menuItem.getId());
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

     /*   content.getChildren().clear();
        for (TemplateComponentDTO baseComponentDTO : baseComponentDTOS.getComponentDTOS()) {
            TemplateComponentDTO baseComponentDTO1 = JSON.parseObject(JSON.toJSONBytes(baseComponentDTO), TemplateComponentDTO.class);
            baseComponentDTO1.setWindowWidth(baseComponentDTOS.getWindowWidth());
            baseComponentDTO1.setWindowHeight(baseComponentDTOS.getWindowHeight());
            this.add(baseComponentDTO1);
        }*/

    }
    public void exportFile(ActionEvent actionEvent) {
        // TODO: 2017-07-19 15:17  熊闯   修改
        String exportFilePath =getFileUrl("选择要保存的位置" );
        if(exportFilePath==null){
            return;
        }
        List<TemplateComponentDTO> baseComponentDTOS = new ArrayList<>();
        for (Component basicComponent : panelComponents) {
            baseComponentDTOS.add(basicComponent.getComponent());
        }

        TemplateDTO exportDTO = new TemplateDTO();
       /* exportDTO.setPrintName(contentComponentDTO.getContext());
        exportDTO.setWindowHeight(content.getHeight());
        exportDTO.setWindowWidth(content.getWidth());
        exportDTO.setComponentDTOS(baseComponentDTOS);*/

        try (FileWriter file1 = new FileWriter(new File(exportFilePath))) {
            file1.write(JSON.toJSONString(exportDTO));
            file1.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


/*







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
       */
/* fontPropertyDTO.add("字体样式", baseComponentDTO.getY() + "", (s,f) ->{
            try {
                baseComponentDTO.setY(Double.parseDouble(s));
                basicComponent.changeProperty(baseComponentDTO);
            }catch (NumberFormatException e){
                f.setText(baseComponentDTO.getY()+"");
                f.requestFocus();
            }
        });*//*

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




*/


}
