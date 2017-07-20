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

package org.hsweb.printer.fx.component.components;

import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.utils.printable.templateptint.dtos.TemplateComponentDTO;

/**
 * Created by xiong on 2017-07-18.
 */
public  class ElementComponentEvent {
    private double windowWidth;
    private double windowHeight;
    private boolean pressed=false;

    private double pressedX=0;

    private double pressedY=0;


    private Component component;
    private PropertyController propertyController;
    private TemplateComponentDTO baseComponentDTO;

    public ElementComponentEvent(Component component, TemplateComponentDTO baseComponentDTO, PropertyController propertyController) {
        this.component = component;
        this.propertyController = propertyController;
        this.baseComponentDTO = baseComponentDTO;
        this.changeTemplateComponent(baseComponentDTO);
        init();
    }
    public void changeTemplateComponent(TemplateComponentDTO baseComponentDTO){
        this.windowHeight=baseComponentDTO.getWindowHeight();
        this.windowWidth=baseComponentDTO.getWindowWidth();
    }

    public void init(){
        this.component.getThisNode().setOnMousePressed((e)->{

            this.propertyController.property(this.component,this.baseComponentDTO);
            System.out.println(1);
            return;
        });
        this.component.getThisNode().setOnMouseClicked((e)->{
            e.getEventType();
            System.out.println(2);
        });
        this.component.getThisNode().setOnMouseMoved((e)->{
            if(this.pressed){
                System.out.println(e.getX());
                System.out.println(e.getY());
                //pressedX-e.getX();
                //pressedY-e.getY();
                System.out.println(3);
            }
        });
        this.component.getThisNode().setOnMouseDragEntered((e)->{
            System.out.println(4);
        });
        this.component.getThisNode().setOnMouseDragExited((e)->{
            System.out.println(5);
        });
        this.component.getThisNode().setOnMouseDragged((e)->{
            if(!this.pressed){
                this.pressed=true;

                this.pressedX=e.getX()-this.component.getX();
                this.pressedY=e.getY()-this.component.getY();
            }
            double x=e.getX()-this.pressedX;
            double y=e.getY()-this.pressedY;

            if(x<=0){
                x=0;
            }
            if(x>=windowWidth-10){
                x=windowWidth-10;
            }

            if(y<=10){
                y=10;
            }
            if(y>=windowHeight){
                y=windowHeight;
            }

            this.baseComponentDTO.setX(x);
            this.baseComponentDTO.setY(y);
            this.component.changeProperty(baseComponentDTO);
            this.propertyController.property(this.component,baseComponentDTO);
        });
        this.component.getThisNode().setOnMouseDragOver((e)->{
            System.out.println(7);
        });
        this.component.getThisNode().setOnMouseDragReleased((e)->{
            System.out.println(8);
        });
        this.component.getThisNode().setOnMouseEntered((e)->{
            System.out.println(9);
        });
        this.component.getThisNode().setOnMouseExited((e)->{
            System.out.println(10);
        });
        this.component.getThisNode().setOnMouseReleased((e)->{
            System.out.println(11);
            this.pressed=false;
        });
    }
}
