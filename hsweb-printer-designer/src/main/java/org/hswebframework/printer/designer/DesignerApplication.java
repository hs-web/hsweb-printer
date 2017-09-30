package org.hswebframework.printer.designer;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.print.PrinterJob;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.util.function.Function;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public class DesignerApplication extends Application {

    private String title = "hsweb printer designer";

    private Scene scene;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(title);
        StackPane root = new StackPane();
        root.setPrefHeight(600);

        Text text = new Text();

        text.setX(200);
        text.setY(20);
        text.setCursor(Cursor.HAND);
        text.setText("呵呵");
        text.setFont(new Font(24));
        root.getChildren().add(text);

        text.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double oldStartX = text.getX();
                double oldStartY = text.getY();

                double scenex = event.getSceneX();
                double sceney = event.getSceneY();
                text.setX(scenex);
                text.setY(sceney);
                System.out.println(oldStartX+":"+oldStartY);

            }
        });

        scene = new Scene(root, 900, 600, Color.web("#666970"));
        primaryStage.setScene(scene);

        WebView webView = new WebView();
        webView.setFontSmoothingType(FontSmoothingType.LCD);
        webView.getEngine().getLoadWorker().stateProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if ("SUCCEEDED".equals(String.valueOf(observable.getValue()))) {
                        JSObject jsObject = (JSObject) webView.getEngine().executeScript("window");
                        jsObject.setMember("app", new Test());
                        webView.getEngine().executeScript("alert(app.test('1234'))");
                    }
                });

        final WebEngine engine = webView.getEngine();
        engine.load("http://www.baidu.com");
        engine.setOnAlert(event -> System.out.println(event.getData()));

        //root.getChildren().add(webView);


        primaryStage.show();
    }

    public static class Test {
        public String test(String s) {
            System.out.println(s);
            return s + "test";
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
