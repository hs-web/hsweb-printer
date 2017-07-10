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

import javafx.scene.text.Text;
import org.hsweb.printer.fx.PropertyController;
import org.hsweb.printer.fx.components.dtos.BaseComponentDTO;

/**
 * Created by xiong on 2017-07-08.
 */
public class BasicComponent  extends Text {
    private double windowWidth;
    private double windowHeight;

    private boolean pressed=false;

    private double pressedX=0;
    private double pressedY=0;


    public void changeProperty(BaseComponentDTO baseComponentDTO){
        this.windowHeight=baseComponentDTO.getWindowHeight();
        this.windowWidth=baseComponentDTO.getWindowWidth();
        this.setText(baseComponentDTO.getContext());
        this.setX(baseComponentDTO.getX());
        this.setY(baseComponentDTO.getY());
    }
    public BasicComponent(BaseComponentDTO baseComponentDTO, PropertyController propertyController) {
        changeProperty(baseComponentDTO);
        propertyController.property(this,baseComponentDTO);
        this.setOnMousePressed((e)->{
          // this.showBoder();
            propertyController.property(this,baseComponentDTO);
            System.out.println(1);
        });
        this.setOnMouseClicked((e)->{
            e.getEventType();
            System.out.println(2);
        });
        this.setOnMouseMoved((e)->{
            if(this.pressed){
                System.out.println(e.getX());
                System.out.println(e.getY());
                //pressedX-e.getX();
                //pressedY-e.getY();
                System.out.println(3);
            }
        });
        this.setOnMouseDragEntered((e)->{
            System.out.println(4);
        });
        this.setOnMouseDragExited((e)->{
            System.out.println(5);
        });
        this.setOnMouseDragged((e)->{
            if(!this.pressed){
                this.pressed=true;

                this.pressedX=e.getX()-this.getX();
                this.pressedY=e.getY()-this.getY();
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

            baseComponentDTO.setX(x);
            baseComponentDTO.setY(y);
            changeProperty(baseComponentDTO);
            propertyController.property(this,baseComponentDTO);
        });
        this.setOnMouseDragOver((e)->{
            System.out.println(7);
        });
        this.setOnMouseDragReleased((e)->{
            System.out.println(8);
        });
        this.setOnMouseEntered((e)->{
            System.out.println(9);
        });
        this.setOnMouseExited((e)->{
            System.out.println(10);
        });
        this.setOnMouseReleased((e)->{
            System.out.println(11);
            this.pressed=false;
        });

    }


}
