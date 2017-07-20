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

package org.hsweb.printer.fx.component.components.panels;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.component.components.Component;
import org.hsweb.printer.fx.component.components.PanelComponent;
import org.hsweb.printer.fx.component.components.elements.TextViewComponent;
import org.hsweb.printer.utils.printable.templateptint.dtos.SplitPaneComponentDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.TextViewComponentDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiong on 2017-07-20.
 */
public class SplitPaneComponent extends SplitPane implements PanelComponent<SplitPaneComponentDTO,SplitPane> {

    private List<Component> components=new ArrayList<>();

    private SplitPaneComponentDTO baseComponentDTO;
    private PropertyController propertyController;
    private  PanelComponent parentComponent;


    public SplitPaneComponent(SplitPaneComponentDTO templateComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {
        //this.componentEvent= new ElementComponentEvent(this,baseComponentDTO,propertyController);
        this.propertyController=propertyController;
        this.parentComponent=parentComponent;
        this.setOrientation(Orientation.HORIZONTAL);

        this.changeProperty(templateComponentDTO);
        this.setOnMousePressed((e)->{
            if (!e.getTarget().equals(this)) {
                return;
            }
            this.propertyController.property(this,this.baseComponentDTO);
            return;
        });
        propertyController.property(this,baseComponentDTO);

        for (int i=0;i<5;i++) {
            TextViewComponentDTO textViewComponentDTO = new TextViewComponentDTO();
            textViewComponentDTO.setWindowHeight(100D);
            textViewComponentDTO.setWindowWidth(100D);
            textViewComponentDTO.setContext("xxxx");
            VBox vBox = new VBox();
            vBox.getChildren().add(new TextViewComponent(textViewComponentDTO,propertyController,this));
            //this.add(vBox);
            this.getItems().add(vBox);
        }
    }


    @Override
    public PanelComponent getParentComponent() {
        return parentComponent;
    }

    @Override
    public SplitPaneComponentDTO getComponent() {
        return baseComponentDTO;
    }

    @Override
    public void changeProperty(SplitPaneComponentDTO baseComponentDTO) {
        this.baseComponentDTO=baseComponentDTO;


        this.setWidth(baseComponentDTO.getWindowWidth());
        this.setMinWidth(baseComponentDTO.getWindowWidth());
        this.setMaxWidth(baseComponentDTO.getWindowWidth());

        this.setHeight(baseComponentDTO.getHeight());
       // this.setMaxHeight(baseComponentDTO.getHeight());
    }

    @Override
    public SplitPane getThisNode() {
        return this;
    }

    @Override
    public double getX() {
        return this.getLayoutX();
    }

    @Override
    public double getY() {
        return this.getLayoutY();
    }


    @Override
    public PropertyController getPropertyController() {
        return propertyController;
    }

    @Override
    public void add(Component component) {
        components.add(component);
        this.getItems().add(component.getThisNode());
    }

    @Override
    public List<Component> getComponents() {
        return components;
    }

}
