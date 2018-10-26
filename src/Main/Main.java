package Main;

import Components.ToolBar;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        String homepage = "https://stackoverflow.com/";
        TabPane webTabs = new TabPane();
        primaryStage.setTitle("Welcome to JFX Browser");

        welcomeTab(webTabs);
        Tab plus = new Tab(" + ");
        plus.closableProperty().setValue(false);
        webTabs.getTabs().add(plus);
        webTabs.getStyleClass().add("web_tabs");
        webTabs.getStylesheets().add("Style/Style.css");
        webTabs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                if (newValue == plus) {
                    Tab newTab = new Tab("newnewnwe");
                    webTabs.getTabs().remove(plus);
                    webTabs.getTabs().addAll(newTab, plus);
                    webTabs.getSelectionModel().select(newTab);
                } else {

                }
            }
        });

        Group root = new Group();

        WebView view = new WebView();
        WebEngine engine = view.getEngine();
        engine.load(homepage);
        view.setStyle("-fx-pref-width: 1000px");

        VBox box = new VBox();
        box.getChildren().addAll(new ToolBar(), view);


        root.getChildren().addAll(webTabs);
        Scene main = new Scene(root,1000, 800);
        primaryStage.setScene(main);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    private void welcomeTab(TabPane webTabs) {
        Tab tab = new Tab("Welcome");
        webTabs.getTabs().addAll(tab);
        webTabs.getSelectionModel().select(tab);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
