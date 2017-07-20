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

package org.hsweb.printer.fx.component.components.elements;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.component.components.Component;
import org.hsweb.printer.fx.component.components.ElementComponentEvent;
import org.hsweb.printer.fx.component.components.PanelComponent;
import org.hsweb.printer.utils.printable.templateptint.TemplatePrintConstants;
import org.hsweb.printer.utils.printable.templateptint.dtos.TextComponentDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.VariableComponentDTO;

/**
 * Created by xiong on 2017-07-08.
 */
public class TextViewComponent extends Text implements Component<TextComponentDTO,Text> {

    private TextComponentDTO baseComponentDTO;
    private ElementComponentEvent<TextViewComponent,TextComponentDTO> componentEvent;
    private PanelComponent parentComponent;
    public TextViewComponent(TextComponentDTO baseComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {

        this.parentComponent= parentComponent;
        this.componentEvent= new ElementComponentEvent(this,baseComponentDTO,propertyController);

        this.changeProperty(baseComponentDTO);

        propertyController.property(this,baseComponentDTO);

    }

    @Override
    public PanelComponent getParentComponent() {
        return parentComponent;
    }

    @Override
    public TextComponentDTO getComponent() {
        return baseComponentDTO;
    }
    @Override
    public void changeProperty(TextComponentDTO baseComponentDTO){
        this.baseComponentDTO=baseComponentDTO;

        this.componentEvent.changeTemplateComponent(baseComponentDTO);

        this.setText(baseComponentDTO.getContext());
        this.setX(baseComponentDTO.getX());
        this.setY(baseComponentDTO.getY());
        this.prefHeight(baseComponentDTO.getHeight());
        this.prefWidth(baseComponentDTO.getWidth());
        this.setWrappingWidth(baseComponentDTO.getWidth());
        if(TemplatePrintConstants.TEXT.equals(baseComponentDTO.getType())){
            this.changeTextProperty(baseComponentDTO);
        }else if(TemplatePrintConstants.VARIABLE.equals(baseComponentDTO.getType())){
            this.changeVariableProperty((VariableComponentDTO)baseComponentDTO);
        }
    }

    @Override
    public Text getThisNode() {
        return this;
    }


    private void changeTextProperty(TextComponentDTO baseComponentDTO) {
        Font font =  Font.font(baseComponentDTO.getFontName(),baseComponentDTO.getFontPosture(),baseComponentDTO.getFontSize());
        this.setFont(font);
        this.setTextAlignment(TextAlignment.LEFT);
        if(baseComponentDTO.getAlign()==1){
            this.setTextAlignment(TextAlignment.CENTER);
        }else if(baseComponentDTO.getAlign()==2){
            this.setTextAlignment(TextAlignment.RIGHT);
        }
        this.setFill(Color.valueOf(baseComponentDTO.getColor()));

    }
    private void changeVariableProperty(VariableComponentDTO baseComponentDTO) {
        this.changeTextProperty(baseComponentDTO);
    }




}
