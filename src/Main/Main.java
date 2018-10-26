package Main;

import Components.ToolBar;
import javafx.application.Application;
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
        welcomeTab(webTabs);
        webTabs.setStyle("-fx-pref-width: 1000px");


        primaryStage.setTitle("Welcome to JFX Browser");
        Group root = new Group();
        Scene main = new Scene(root,1000, 800);
        WebView view = new WebView();
        WebEngine engine = view.getEngine();
        engine.load(homepage);
        view.setStyle("-fx-pref-width: 1000px");


        VBox box = new VBox();
        box.getChildren().addAll(new ToolBar(), view);

        root.getChildren().add(box);

        primaryStage.setScene(main);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    private void welcomeTab(TabPane webTabs) {
        Tab tab = new Tab("Welcome");
        webTabs.getTabs().add(tab);
        webTabs.getSelectionModel().select(tab);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
