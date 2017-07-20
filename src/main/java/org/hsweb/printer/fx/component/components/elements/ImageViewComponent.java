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

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.component.components.Component;
import org.hsweb.printer.fx.component.components.ElementComponentEvent;
import org.hsweb.printer.fx.component.components.PanelComponent;
import org.hsweb.printer.utils.printable.templateptint.TemplatePrintConstants;
import org.hsweb.printer.utils.printable.templateptint.dtos.ImageComponentDTO;

/**
 * Created by xiong on 2017-07-18.
 */
public class ImageViewComponent extends ImageView implements Component<ImageComponentDTO,ImageView> {
    private ImageComponentDTO baseComponentDTO;
    private ElementComponentEvent<ImageViewComponent,ImageComponentDTO> componentEvent;
    private PanelComponent parentComponent;

    public ImageViewComponent(ImageComponentDTO baseComponentDTO, PropertyController propertyController, PanelComponent parentComponent) {

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
    public ImageComponentDTO getComponent() {
        return baseComponentDTO;
    }
    @Override
    public void changeProperty(ImageComponentDTO baseComponentDTO){
        this.baseComponentDTO=baseComponentDTO;

        this.componentEvent.changeTemplateComponent(baseComponentDTO);
        String url="image.png";
        if(TemplatePrintConstants.QRCODE.equals(baseComponentDTO.getType())){
            url="qrcode.png";
        }
        Image image=new Image(url,baseComponentDTO.getWidth(),baseComponentDTO.getHeight(),false,false);
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
    public ImageView getThisNode() {
        return this;
    }


}
