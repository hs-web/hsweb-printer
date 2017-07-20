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

package org.hsweb.printer.fx.component.propertys.elements;

import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import org.hsweb.printer.fx.component.components.Component;
import org.hsweb.printer.fx.component.propertys.dtos.PropertysDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.TextViewComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public abstract class TextViewElementComponentProperty<T extends Component,A extends TextViewComponentDTO>  extends BaseElementComponentProperty<T,A>  {


    @Override
    public void elementComponentProperty(PropertysDTO propertys, T basicComponent, A baseComponentDTO){
        propertys.getPropertyFont().setVisible(true);
        this.textComponentProperty(propertys,basicComponent,baseComponentDTO);
    }
    public abstract void textComponentProperty(PropertysDTO propertys, T basicComponent, A baseComponentDTO);

    protected void fontFontName(){
        this.getPropertys().getFontProperty().add("字体名",  this.getBaseComponent().getFontName() + "",(s,f) ->{
            if(s==null||s.length()==0){
                f.setText(this.getBaseComponent().getFontName());
            }else {
                this.getBaseComponent().setFontName(s);
                this.getBbasicComponent().changeProperty(this.getBaseComponent());
            }
        });
    }

    protected void fontFontSize(){
        this.getPropertys().getFontProperty().add("字体大小",  this.getBaseComponent().getFontSize() + "",(s,f) ->{
            try {
                this.getBaseComponent().setFontSize(Integer.parseInt(s));
                this.getBbasicComponent().changeProperty(this.getBaseComponent());
            }catch (NumberFormatException e){
                f.setText(this.getBaseComponent().getX()+"");
                f.requestFocus();
            }
        });
    }

    protected void fontColor(){
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.valueOf(this.getBaseComponent().getColor()));
        colorPicker.setOnAction(event -> {
            Color color = colorPicker.getValue();
            Color desaturate = color.desaturate();
            String s = desaturate.toString();
            this.getBaseComponent().setColor(s);
            this.getBbasicComponent().changeProperty(this.getBaseComponent());
        });
        this.getPropertys().getFontProperty().add("颜色", colorPicker);
    }
    protected void fontAll(){
        this.fontFontName();
        this.fontFontSize();
        this.fontColor();
    }


}
