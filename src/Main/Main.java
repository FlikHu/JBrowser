package Main;

import DAO.DBUtility;
import Main.Component.BookmarkBar;
import Main.Component.ToolBar;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class Main extends Application {
    private static Stage primaryStage;
    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        TabPane webTabs = new TabPane();
        primaryStage.setTitle("JFX Browser");

        // DBUtility.dropAllTables();
        DBUtility.initiallize();

        Tab plus = new Tab(" + ");
        plus.closableProperty().setValue(false);
        webTabs.getTabs().add(plus);
        webTabs.getStylesheets().add("Style/Style.css");
        webTabs.prefWidthProperty().bind(primaryStage.widthProperty().multiply(1));
        webTabs.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.97));
        createTab(webTabs, plus);

        webTabs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                SessionManager sessionManager = SessionManager.getInstance();
                if (newValue == plus) {
                    createTab(webTabs, plus);
                } else {
                    webTabs.getSelectionModel().select(newValue);
                    Node node = newValue.getContent();

                    WebView view = (WebView) node.lookup("WebView");
                    if(view != null) {
                        VBox box = (VBox) node.lookup("VBox");
                        WebEngine engine = view.getEngine();

                        ObservableList<Node> childrens = FXCollections.observableArrayList(box.getChildren());
                        int size = childrens.size();

                        System.out.println(sessionManager.isEnableBookmarkBar());
                        if(sessionManager.isEnableBookmarkBar()) {
                            if(size == 3) {
                                childrens.add(1, new BookmarkBar(view));
                            }
                        } else {
                            if(size == 4) {
                                childrens.remove(1);
                            }
                        }
                        box.getChildren().setAll(childrens);
                        view.setFontScale((double)(sessionManager.getFontSize())/100);
                        view.setZoom((double)(sessionManager.getPageZoom())/100);
                        engine.setJavaScriptEnabled(sessionManager.isEnableJS());
                    }
                }
            }
        });

        Group root = new Group();
        root.getChildren().addAll(webTabs);
        Scene main = new Scene(root,1000, 800);

        Main.primaryStage = primaryStage;
        Main.mainScene = main;

        primaryStage.setScene(main);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    private void createTab(TabPane webTabs, Tab plus) {
        Tab newTab = new Tab();
        SessionManager sessionManager = SessionManager.getInstance();

        WebView view = new WebView();
        WebEngine engine = view.getEngine();

        // Fix garbled text
        engine.setUserAgent("Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36");
        engine.load(sessionManager.getHomepage());

        engine.titleProperty().addListener(((observableValue, oldName, newName) -> {
            newTab.setText(newName);
        }));

        VBox box = new VBox();

        if(sessionManager.isEnableBookmarkBar()) {
            box.getChildren().addAll(new ToolBar(view, sessionManager.getHomepage()), new BookmarkBar(view), view, new ToolBar(view));
        } else {
            box.getChildren().addAll(new ToolBar(view, sessionManager.getHomepage()), view, new ToolBar(view));
        }
        view.setFontScale((double)(sessionManager.getFontSize())/100);
        view.setZoom((double)(sessionManager.getPageZoom())/100);
        engine.setJavaScriptEnabled(sessionManager.isEnableJS());

        view.prefHeightProperty().bind(webTabs.heightProperty());
        view.getStyleClass().add("view");

        newTab.setContent(box);
        webTabs.getTabs().add(newTab);
        webTabs.getTabs().remove(plus);
        webTabs.getTabs().add(plus);
        webTabs.getSelectionModel().select(newTab);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
