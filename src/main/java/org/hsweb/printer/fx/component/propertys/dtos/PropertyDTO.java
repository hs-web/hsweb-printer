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

package org.hsweb.printer.fx.component.propertys.dtos;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * Created by xiong on 2017-07-10.
 */
public class PropertyDTO implements Serializable {
    private VBox propertyPubName;
    private VBox propertyPubValue;
    private ObservableList<Node> pubNames;
    private ObservableList<Node> pubValues;
    public PropertyDTO(VBox propertyPubName, VBox propertyPubValue) {
        this.propertyPubName = propertyPubName;
        this.propertyPubValue = propertyPubValue;
        pubNames=propertyPubName.getChildren();
        pubValues=propertyPubValue.getChildren();

    }

    public void clear(){
        pubNames.clear();
        pubValues.clear();
    }

    public void add(Node  pubName,Node pubValue){
        VBox.setMargin(pubName, new Insets(3,0 , 3, 8));
        pubNames.add(pubName);
        VBox.setMargin(pubValue, new Insets(3,0 , 3, 8));
        pubValues.add(pubValue);
    }
    public void add(String  pubName,Node pubValue){
        Label tableColumn=new Label(pubName);
        tableColumn.setStyle("-fx-min-height:25;");


        this.add(tableColumn,pubValue);
    }
    public void add(String  pubName, String pubValue, BiConsumer<String,TextField> stringConsumer){
        Label tableColumn=new Label(pubName);
        tableColumn.setStyle("-fx-min-height:25;");

        TextField tableColumn2=new TextField();
        tableColumn2.setText(pubValue);
        tableColumn2.setEditable(true);
        tableColumn2.setStyle("-fx-min-height:25;");
        tableColumn2.setOnAction(event -> {
            stringConsumer.accept(tableColumn2.getText(),tableColumn2);
        });
        tableColumn2.setOnKeyReleased(event -> {
            stringConsumer.accept(tableColumn2.getText(),tableColumn2);
        });

        this.add(tableColumn,tableColumn2);
    }
}
