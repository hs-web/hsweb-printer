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

import javafx.scene.control.SplitPane;
import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.component.components.Component;
import org.hsweb.printer.fx.component.components.PanelComponent;
import org.hsweb.printer.fx.component.components.PanelComponentEvent;
import org.hsweb.printer.utils.printable.templateptint.TemplatePrintConstants;
import org.hsweb.printer.utils.printable.templateptint.dtos.AnchorPaneComponentDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.SplitPaneComponentDTO;

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
    private PanelComponentEvent<AnchorPaneComponent,AnchorPaneComponentDTO> panelComponentEvent;


    public SplitPaneComponent(SplitPaneComponentDTO templateComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {
        //this.componentEvent= new ElementComponentEvent(this,baseComponentDTO,propertyController);
        this.propertyController=propertyController;
        this.parentComponent=parentComponent;

        this.changeProperty(templateComponentDTO);
        this.panelComponentEvent = new PanelComponentEvent(this,baseComponentDTO,propertyController);
        propertyController.property(this,baseComponentDTO);

        this.add(TemplatePrintConstants.TEXT);
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

        this.setHeight(baseComponentDTO.getWindowWidth());
        this.setMinHeight(baseComponentDTO.getWindowWidth());
        this.setMaxHeight(baseComponentDTO.getWindowWidth());
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
        this.getChildren().add(component.getThisNode());
    }

    @Override
    public List<Component> getComponents() {
        return components;
    }

}
