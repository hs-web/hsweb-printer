package org.hsweb.printer;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Worker.State;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * JavaFX 8  打印，将
 */
public class FxPrinter extends Application {
    Stage primaryStage;

    static {
        SvgImageLoaderFactory.install();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        final TextField urlTextField = new TextField();
        final Button printButton = new Button("Print");
        //web 浏览器
        final WebView webPage = new WebView();
        final WebEngine webEngine = webPage.getEngine();
        webPage.setVisible(true);
        webEngine.loadContent(
                "<svg width=\"210mm\" height=\"297mm\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                        " <!-- Created with Method Draw - http://github.com/duopixel/Method-Draw/ -->\n" +
                        " <g>\n" +
                        "  <title>background</title>\n" +
                        "  <rect fill=\"#ffffff\" id=\"canvas_background\" height=\"597\" width=\"844\" y=\"-1\" x=\"-1\"/>\n" +
                        "  <g display=\"none\" overflow=\"visible\" y=\"0\" x=\"0\" height=\"100%\" width=\"100%\" id=\"canvasGrid\">\n" +
                        "   <rect fill=\"url(#gridpattern)\" stroke-width=\"0\" y=\"0\" x=\"0\" height=\"100%\" width=\"100%\"/>\n" +
                        "  </g>\n" +
                        " </g>\n" +
                        " <g>\n" +
                        "  <title>Layer 1</title>\n" +
                        "  <rect stroke=\"#000\" id=\"svg_1\" height=\"31\" width=\"137\" y=\"15.5\" x=\"90.5\" stroke-width=\"1.5\" fill=\"#fff\"/>\n" +
                        "  <text stroke=\"#000\" transform=\"matrix(1,0,0,0.9259259104728699,0,0.037037044763565063) \" xml:space=\"preserve\" text-anchor=\"start\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"24\" id=\"svg_2\" y=\"41.94\" x=\"25.5\" stroke-width=\"0\" fill=\"#000000\">姓名</text>\n" +
                        "  <text xml:space=\"preserve\" text-anchor=\"start\" font-family=\"Helvetica, Arial, sans-serif\" font-size=\"24\" id=\"svg_3\" y=\"36.5\" x=\"98.5\" fill-opacity=\"null\" stroke-opacity=\"null\" stroke-width=\"0\" stroke=\"#000\" fill=\"#000000\">${name}</text>\n" +
                        " </g>\n" +
                        "</svg>");
        HBox hbox = new HBox();
        hbox.getChildren().addAll(
                //urlTextField,
                printButton);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hbox);
        borderPane.setCenter(webPage);
        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setTitle("Print Demo");
        primaryStage.setScene(scene);

        // print button pressed, page loaded
        final BooleanProperty printButtonClickedProperty = new SimpleBooleanProperty(false);
        final BooleanProperty pageLoadedProperty = new SimpleBooleanProperty(false);

        // when the a page is loaded and the button was pressed call the print() method.
        final BooleanProperty printActionProperty = new SimpleBooleanProperty(false);
        printActionProperty.bind(pageLoadedProperty.and(printButtonClickedProperty));

        // WebEngine updates flag when finished loading web page.
        webEngine.getLoadWorker()
                .stateProperty()
                .addListener((obsValue, oldState, newState) -> {
                    if (newState == State.SUCCEEDED) {
                        pageLoadedProperty.set(true);
                    }
                });

        // When user enters a url and hits the enter key.
        urlTextField.setOnAction(aEvent -> {
            pageLoadedProperty.set(false);
            printButtonClickedProperty.set(false);
//            webEngine.load(urlTextField.getText());
        });

        // When the user clicks the print button the webview node is printed
        printButton.setOnAction(aEvent -> {
            //  printButtonClickedProperty.set(true);
            PrinterJob job = PrinterJob.createPrinterJob();
            job.showPrintDialog(primaryStage.getOwner());
            webEngine.print(job);
           // print(webPage);
        });

        // Once the print action hears a true go print the WebView node.
        printActionProperty.addListener((obsValue, oldState, newState) -> {
            if (newState) {
                // print(webPage);
            }
        });

        primaryStage.show();
        primaryStage.close();
    }

    /**
     * Scales the node based on the standard letter, portrait paper to be printed.
     *
     * @param node The scene node to be printed.
     */
    public void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        //node.getTransforms().add(new Scale(scaleX, scaleY));
        PrinterJob job = PrinterJob.createPrinterJob();
        job.setPrinter(printer);
        if (job != null) {
            job.showPrintDialog(primaryStage.getOwner());

            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}