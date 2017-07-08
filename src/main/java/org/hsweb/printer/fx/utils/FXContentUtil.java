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

package org.hsweb.printer.fx.utils;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hsweb.printer.fx.ControllerDataInitializable;
import org.hsweb.printer.fx.utils.userdata.UserData;
import org.hsweb.printer.fx.utils.userdata.UserDataResizable;
import org.hsweb.printer.fx.utils.userdata.UserDataShow;
import org.hsweb.printer.fx.utils.userdata.UserDataTitle;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiong on 2017-02-16.
 */
public class FXContentUtil {
    public static final String CodeLoginFX="fx/CodeLoginFX.fxml";
    public static final String PrintHistoryFX="fx/PrintHistoryFX.fxml";
    public static final String PrintHistoryItemFX="fx/PrintHistoryItemFX.fxml";


    private static Map<String,UserData> userDataMap=new HashMap<>();
    private static void addUserData(UserData userData){
        userDataMap.put(userData.getKEY(),userData);
    }
    static {
        addUserData(new UserDataTitle());
        addUserData(new UserDataShow());
        addUserData(new UserDataResizable());
    }

    private FXContentUtil(){
    }

    public static  void sceneContent(Stage stage, String fxml){
        sceneContent(stage,fxml,null,null);
    }
    public static  void sceneContent(Stage stage, String fxml,Object sendData){
        sceneContent(stage,fxml,sendData,null);
    }
    public static  void sceneContent(Stage stage, String fxml,Runnable callback){
        sceneContent(stage,fxml,null,callback);
    }
    public static void sceneContent(Stage stage, String fxml,Object sendData,Runnable callback){
            try {
                URL resource = ClassLoader.getSystemClassLoader().getResource(fxml);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(resource);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                Pane root = fxmlLoader.load();
                stage.setWidth(root.getPrefWidth());
                stage.setHeight(root.getPrefHeight());

                Object userData = root.getUserData();
                if (userData != null) {
                    String[] split = userData.toString().split(";");
                    for (String s : split) {
                        String[] split1 = s.split(":", 2);
                        UserData userData1 = userDataMap.get(split1[0].trim().toLowerCase());
                        if (userData1 != null) {
                            userData1.apply(stage, split1[1].trim().toLowerCase());
                        }
                    }
                }
                Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
                //stage.getIcons().add(DataCenterFx.fx_icon);
                stage.setScene(scene);
                Object controller = fxmlLoader.getController();
                if (controller instanceof ControllerDataInitializable) {
                    ((ControllerDataInitializable) controller).initData(stage, userData, sendData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(callback!=null){
                    new Thread(callback).start();
                }
            }
    }






}
