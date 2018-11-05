package Component.SettingComponent;

import Application.Main;
import Application.SessionManager;
import Component.UIComponent.PercentageButton;
import Component.UIComponent.ToggleSwitch;
import DAO.SettingDAO;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


// Tab content for setting
public class SettingTab extends VBox {
    public SettingTab() {
        // Todo

        // Change font size
        Label fontSize = new Label("Font Size");
        PercentageButton size = new PercentageButton(50,25, SessionManager.getInstance().getFontSize());
        size.getPercentage().addListener(((observableValue, oldValue, newValue) -> {
            SessionManager sessionManager = SessionManager.getInstance();
            SettingDAO settingDAO = new SettingDAO(sessionManager.getUserId());
            settingDAO.updateFontSize(newValue.intValue());
            sessionManager.setFontSize(newValue.intValue());
        }));

        // Change page zoom
        Label pageZoom = new Label("Page Zoom");
        PercentageButton zoom = new PercentageButton(50,25, SessionManager.getInstance().getPageZoom());
        zoom.getPercentage().addListener(((observableValue, oldValue, newValue) -> {
            SessionManager sessionManager = SessionManager.getInstance();
            SettingDAO settingDAO = new SettingDAO(sessionManager.getUserId());
            settingDAO.updatePageZoom(newValue.intValue());
            sessionManager.setPageZoom(newValue.intValue());
        }));

        // Toggle on/off Javascript
        Label JS = new Label("Enable Javascript");
        ToggleSwitch enableJS = new ToggleSwitch(50,25, SessionManager.getInstance().isEnableJS());
        enableJS.isOnProperty().addListener(((observable, oldValue, newValue) -> {
            SessionManager sessionManager = SessionManager.getInstance();
            SettingDAO settingDAO = new SettingDAO(sessionManager.getUserId());
            settingDAO.updateEnableJS(newValue);
            sessionManager.setEnableJS(newValue);
        }));

        // Toggle on/off bookmark bar
        Label bookmark = new Label("Enable Bookmark Bar");
        ToggleSwitch isBookmarkBarOn = new ToggleSwitch(50, 25, SessionManager.getInstance().isEnableBookmarkBar());
        isBookmarkBarOn.isOnProperty().addListener(((observable, oldValue, newValue) -> {
            SessionManager sessionManager = SessionManager.getInstance();
            SettingDAO settingDAO = new SettingDAO(sessionManager.getUserId());
            settingDAO.updateEnableBookmarkBar(newValue);
            sessionManager.setEnableBookmarkBar(newValue);
        }));

        // Go back to main page
        Button back = new Button();
        back.setText("Back");
        back.getStyleClass().add("settingButton");
        back.setOnAction((event -> {
            Stage pStage = Main.getPrimaryStage();
            pStage.setScene(Main.getMainScene());
        }));

        this.getChildren().addAll(fontSize, size, pageZoom, zoom, JS, enableJS, bookmark, isBookmarkBarOn, back);
        this.setSpacing(15);
        this.setAlignment(Pos.BASELINE_CENTER);
    }
}