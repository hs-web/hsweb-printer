package org.hsweb.printer.fx.utils.userdata;

import javafx.stage.Stage;

public class UserDataResizable implements UserData{

        @Override
        public String getKEY() {
            return "resizable";
        }

        @Override
        public void apply(Stage stage, String value) {
            if("true".equals(value)){
                stage.setResizable(true);
            }else {
                stage.setResizable(false);
            }
        }
    }