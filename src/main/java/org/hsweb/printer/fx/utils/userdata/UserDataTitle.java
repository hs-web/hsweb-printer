package org.hsweb.printer.fx.utils.userdata;

import javafx.stage.Stage;

public class UserDataTitle implements UserData{

        @Override
        public String getKEY() {
            return "title";
        }

        @Override
        public void apply(Stage stage, String value) {
            stage.setTitle(value);
        }
    }