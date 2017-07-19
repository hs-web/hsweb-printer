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

package org.hsweb.printer.fx.components.propertys;

import org.hsweb.printer.fx.components.components.Component;
import org.hsweb.printer.fx.components.propertys.dtos.PropertysDTO;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;

/**
 * Created by xiong on 2017-07-19.
 */
public abstract class BaseComponentProperty<T extends Component, A extends TemplateComponentDTO> implements ComponentProperty<T,A> {
    private PropertysDTO propertys;
    private T basicComponent;
    private A baseComponentDTO;

    protected PropertysDTO getPropertys(){
        return propertys;
    }

    protected  T getBbasicComponent(){
        return basicComponent;
    }

    protected  A getBaseComponent(){
        return baseComponentDTO;
    }

    @Override
    public void property(PropertysDTO propertys, T basicComponent, A baseComponentDTO) {
        this.propertys = propertys;
        this.basicComponent = basicComponent;
        this.baseComponentDTO = baseComponentDTO;

        this.componentProperty(propertys, basicComponent, baseComponentDTO);
    }

    public abstract void componentProperty(PropertysDTO propertys, T basicComponent, A baseComponentDTO);

    protected void pubContextProperty(String content) {
        this.getPropertys().getPubProperty().add(content, this.getBaseComponent().getContext() + "", (s, f) -> {
            this.getBaseComponent().setContext(s);
            this.getBbasicComponent().changeProperty(this.getBaseComponent());
        });
    }

    protected void pubXProperty() {
        this.getPropertys().getPubProperty().add("坐标x", this.getBaseComponent().getX() + "", (s, f) -> {
            try {
                this.getBaseComponent().setX(Double.parseDouble(s));
                this.getBbasicComponent().changeProperty(this.getBaseComponent());
            } catch (NumberFormatException e) {
                f.setText(this.getBaseComponent().getX() + "");
                f.requestFocus();
            }
        });
    }

    protected void pubYProperty() {
        this.getPropertys().getPubProperty().add("坐标y", this.getBaseComponent().getY() + "", (s, f) -> {
            try {
                this.getBaseComponent().setY(Double.parseDouble(s));
                this.getBbasicComponent().changeProperty(this.getBaseComponent());
            } catch (NumberFormatException e) {
                f.setText(this.getBaseComponent().getY() + "");
                f.requestFocus();
            }
        });
    }

    protected void pubHeightProperty() {
        this.getPropertys().getPubProperty().add("高度", this.getBaseComponent().getHeight() + "", (s, f) -> {
            try {
                this.getBaseComponent().setHeight(Double.parseDouble(s));
                this.getBbasicComponent().changeProperty(this.getBaseComponent());
            } catch (NumberFormatException e) {
                f.setText(this.getBaseComponent().getHeight() + "");
                f.requestFocus();
            }
        });
    }

    protected void pubWidthProperty() {
        this.getPropertys().getPubProperty().add("宽度", this.getBaseComponent().getWidth() + "", (s, f) -> {
            try {
                this.getBaseComponent().setWidth(Double.parseDouble(s));
                this.getBbasicComponent().changeProperty(this.getBaseComponent());
            } catch (NumberFormatException e) {
                f.setText(this.getBaseComponent().getWidth() + "");
                f.requestFocus();
            }
        });
    }

    protected void pubAll(String content) {
        this.pubContextProperty(content);
        this.pubXProperty();
        this.pubYProperty();
        this.pubHeightProperty();
        this.pubWidthProperty();
    }


}
