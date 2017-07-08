package org.hsweb.printer.fx.utils.userdata;

import javafx.stage.Stage;

public  class UserDataShow implements UserData{

        @Override
        public String getKEY() {
            return "show";
        }

        @Override
        public void apply(Stage stage, String value) {
            if("true".equals(value)){
                stage.show();
            }else {
                stage.hide();
            }
        }
    }