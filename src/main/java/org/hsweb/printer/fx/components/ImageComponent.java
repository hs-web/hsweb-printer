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

package org.hsweb.printer.fx.components;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;

/**
 * Created by xiong on 2017-07-18.
 */
public class ImageComponent  extends ImageView implements Component{
    private TemplateComponentDTO baseComponentDTO;
    private ComponentEvent componentEvent;

    public ImageComponent(TemplateComponentDTO baseComponentDTO, PropertyController propertyController) {

        this.componentEvent= new ComponentEvent(this,baseComponentDTO,propertyController);

        this.changeProperty(baseComponentDTO);

        propertyController.property(this,baseComponentDTO);

    }
    @Override
    public TemplateComponentDTO getComponent() {
        return baseComponentDTO;
    }
    @Override
    public void changeProperty(TemplateComponentDTO baseComponentDTO){
        this.baseComponentDTO=baseComponentDTO;

        this.componentEvent.changeTemplateComponent(baseComponentDTO);
        Image image=new Image("image.png",baseComponentDTO.getWidth(),baseComponentDTO.getHeight(),false,false);
        this.setImage(image);
        //this.setText(baseComponentDTO.getContext());
        this.setX(baseComponentDTO.getX());
        this.setY(baseComponentDTO.getY());
        this.prefHeight(baseComponentDTO.getHeight());
        this.prefWidth(baseComponentDTO.getWidth());

      /*  this.setWrappingWidth(baseComponentDTO.getWidth());
        if(TemplatePrintConstants.TEXT.equals(baseComponentDTO.getType())){
            this.changeTextProperty((TextComponentDTO)baseComponentDTO);
        }else if(TemplatePrintConstants.VARIABLE.equals(baseComponentDTO.getType())){
            this.changeVariableProperty((VariableComponentDTO)baseComponentDTO);
        }*/
    }

    @Override
    public Node getThisNode() {
        return this;
    }


}
