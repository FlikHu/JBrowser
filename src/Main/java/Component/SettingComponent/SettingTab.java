package Component.SettingComponent;

import Application.Main;
import Application.SessionManager;
import Component.UIComponent.PercentageButton;
import Component.UIComponent.ToggleSwitch;
import Constant.Constants;
import DAO.SearchEngines;
import DAO.SettingDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.net.URLConnection;


// Tab content for setting
public class SettingTab extends BorderPane {
    public SettingTab(TabPane settingPane) {

        // Todo
        // Set homepage
        Label homepage = new Label("Homepage");
        homepage.getStyleClass().add("settingLabel");
        TextField hp = new TextField(SessionManager.getInstance().getHomepage());
        hp.setId("homepageField");
        hp.setOnKeyPressed(KeyEvent ->{
            if(KeyEvent.getCode().equals(KeyCode.ENTER)) {
                try {
                    URL address = new URL(hp.getText());
                    URLConnection connection = address.openConnection();
                    connection.connect();
                } catch (Exception e){
                    hp.setText("Invalid url");
                    return;
                }
                SettingDAO settingDAO = new SettingDAO(SessionManager.getInstance().getUserId());
                SessionManager.getInstance().setHomepage(hp.getText());
                settingDAO.updateHomepage(hp.getText());
            }
        });
        HBox container1 = new HBox();
        Pane placeholder1 = new Pane();
        placeholder1.setPrefHeight(Constants.SETTING_ITEM_HEIGHT);
        hp.setPrefWidth(400);
        hp.setPrefHeight(Constants.SETTING_ITEM_HEIGHT);
        container1.getChildren().addAll(homepage,placeholder1,hp);
        container1.setHgrow(placeholder1, Priority.ALWAYS);
        container1.getStyleClass().add("container");

        // Pick default search engine
        Label searchEngine = new Label("Default Search Engine");
        searchEngine.getStyleClass().add("settingLabel");
        ComboBox<String> se = new ComboBox<>();
        se.setId("searchEngineChoices");
        se.setPrefSize(200, Constants.SETTING_ITEM_HEIGHT);
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Google");
        items.add("Yahoo");
        items.add("Bing");
        items.add("Duck Duck Go");
        se.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldVal, selected) -> {
            SearchEngines searchEngines = SearchEngines.GOOGLE;
            switch(selected) {
                case "Google":
                    searchEngines = SearchEngines.GOOGLE;
                    break;
                case "Yahoo":
                    searchEngines = SearchEngines.YAHOO;
                    break;
                case "Bing":
                    searchEngines = SearchEngines.BING;
                    break;
                case "Duck Duck Go":
                    searchEngines = SearchEngines.DUCK_DUCK_GO;
                    break;
            }
            SettingDAO settingDAO = new SettingDAO(SessionManager.getInstance().getUserId());
            SessionManager.getInstance().setSearchEngine(searchEngines);
            settingDAO.updateSearchEngine(searchEngines);
        }));
        se.setItems(items);
        switch(SessionManager.getInstance().getSearchEngine()) {
            case GOOGLE:
                se.getSelectionModel().select(0);
                break;
            case YAHOO:
                se.getSelectionModel().select(1);
                break;
            case BING:
                se.getSelectionModel().select(2);
                break;
            case DUCK_DUCK_GO:
                se.getSelectionModel().select(3);
                break;
        }
        HBox container2 = new HBox();
        Pane placeholder2 = new Pane();
        placeholder2.setPrefHeight(Constants.SETTING_ITEM_HEIGHT);
        container2.getChildren().addAll(searchEngine, placeholder2,se);
        container2.setHgrow(placeholder2, Priority.ALWAYS);
        container2.getStyleClass().add("container");

        // Change font size
        Label fontSize = new Label("Font Size");
        fontSize.getStyleClass().add("settingLabel");
        PercentageButton size = new PercentageButton(Constants.SETTING_ITEM_WIDTH, Constants.SETTING_ITEM_HEIGHT, SessionManager.getInstance().getFontSize());
        size.getPercentage().addListener(((observableValue, oldValue, newValue) -> {
            SessionManager sessionManager = SessionManager.getInstance();
            SettingDAO settingDAO = new SettingDAO(sessionManager.getUserId());
            settingDAO.updateFontSize(newValue.intValue());
            sessionManager.setFontSize(newValue.intValue());
        }));
        HBox container3 = new HBox();
        Pane placeholder3 = new Pane();
        placeholder3.setPrefHeight(Constants.SETTING_ITEM_HEIGHT);
        container3.getChildren().addAll(fontSize, placeholder3,size);
        container3.setHgrow(placeholder3, Priority.ALWAYS);
        container3.getStyleClass().add("container");

        // Change page zoom
        Label pageZoom = new Label("Page Zoom");
        pageZoom.getStyleClass().add("settingLabel");
        PercentageButton zoom = new PercentageButton(Constants.SETTING_ITEM_WIDTH, Constants.SETTING_ITEM_HEIGHT, SessionManager.getInstance().getPageZoom());
        zoom.getPercentage().addListener(((observableValue, oldValue, newValue) -> {
            SessionManager sessionManager = SessionManager.getInstance();
            SettingDAO settingDAO = new SettingDAO(sessionManager.getUserId());
            settingDAO.updatePageZoom(newValue.intValue());
            sessionManager.setPageZoom(newValue.intValue());
        }));
        HBox container4 = new HBox();
        Pane placeholder4 = new Pane();
        placeholder4.setPrefHeight(Constants.SETTING_ITEM_HEIGHT);
        container4.getChildren().addAll(pageZoom, placeholder4, zoom);
        container4.setHgrow(placeholder4, Priority.ALWAYS);
        container4.getStyleClass().add("container");

        // Toggle on/off Javascript
        Label JS = new Label("Enable Javascript");
        JS.getStyleClass().add("settingLabel");
        ToggleSwitch enableJS = new ToggleSwitch(Constants.SETTING_ITEM_WIDTH, Constants.SETTING_ITEM_HEIGHT, SessionManager.getInstance().isEnableJS());
        enableJS.isOnProperty().addListener(((observable, oldValue, newValue) -> {
            SessionManager sessionManager = SessionManager.getInstance();
            SettingDAO settingDAO = new SettingDAO(sessionManager.getUserId());
            settingDAO.updateEnableJS(newValue);
            sessionManager.setEnableJS(newValue);
        }));
        HBox container5 = new HBox();
        Pane placeholder5 = new Pane();
        placeholder5.setPrefHeight(Constants.SETTING_ITEM_HEIGHT);
        container5.getChildren().addAll(JS, placeholder5, enableJS);
        container5.setHgrow(placeholder5, Priority.ALWAYS);
        container5.getStyleClass().add("container");

        // Toggle on/off bookmark bar
        Label bookmark = new Label("Show Bookmark Bar");
        bookmark.getStyleClass().add("settingLabel");
        ToggleSwitch isBookmarkBarOn = new ToggleSwitch(Constants.SETTING_ITEM_WIDTH, Constants.SETTING_ITEM_HEIGHT, SessionManager.getInstance().isEnableBookmarkBar());
        isBookmarkBarOn.isOnProperty().addListener(((observable, oldValue, newValue) -> {
            SessionManager sessionManager = SessionManager.getInstance();
            SettingDAO settingDAO = new SettingDAO(sessionManager.getUserId());
            settingDAO.updateEnableBookmarkBar(newValue);
            sessionManager.setEnableBookmarkBar(newValue);
        }));
        HBox container6 = new HBox();
        Pane placeholder6 = new Pane();
        placeholder6.setPrefHeight(Constants.SETTING_ITEM_HEIGHT);
        container6.getChildren().addAll(bookmark, placeholder6, isBookmarkBarOn);
        container6.setHgrow(placeholder6, Priority.ALWAYS);
        container6.getStyleClass().add("container");

        // Go back to main page
        Button back = new Button();
        back.setText("Back");
        back.getStyleClass().add("dialogButton");
        back.setOnAction((event -> {
            Stage pStage = Main.getPrimaryStage();
            Scene mainScene = Main.getMainScene();
            TabPane webTabs = (TabPane) mainScene.lookup("TabPane");
            Main.refresh(webTabs.getSelectionModel().getSelectedItem());

            pStage.setScene(mainScene);
            pStage.setWidth(settingPane.getWidth());
            pStage.setHeight(settingPane.getHeight());
        }));

        VBox settingContainer = new VBox();
        settingContainer.setId("settingContainer");
        settingContainer.setSpacing(Constants.SETTING_ITEM_HEIGHT);
        settingContainer.setAlignment(Pos.BASELINE_CENTER);
        settingContainer.getChildren().addAll(container1, container2, container3, container4, container5, container6, back);

        //Border around central panel
        Pane borderTop = new Pane();
        Pane borderLeft = new Pane();
        Pane borderRight = new Pane();
        borderTop.prefHeightProperty().bind(this.heightProperty().multiply(0.02));
        borderLeft.prefWidthProperty().bind(this.widthProperty().multiply(0.05));
        borderRight.prefWidthProperty().bind(this.widthProperty().multiply(0.05));
        this.setTop(borderTop);
        this.setCenter(settingContainer);
        this.setLeft(borderLeft);
        this.setRight(borderRight);
    }
}