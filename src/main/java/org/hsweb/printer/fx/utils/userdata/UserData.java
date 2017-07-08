package org.hsweb.printer.fx.utils.userdata;

import javafx.stage.Stage;

public interface UserData{
    String getKEY();
    void apply(Stage stage, String value);
}