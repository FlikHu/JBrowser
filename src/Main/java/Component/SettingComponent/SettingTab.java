package Component.SettingComponent;

import Application.Main;
import Application.SessionManager;
import Component.UIComponent.ToggleSwitch;
import DAO.SettingDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

// Tab content for setting
public class SettingTab extends ListView<Node> {
    public SettingTab() {
        // Todo
        // Toggle on/off bookmark bar
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

        ObservableList<Node> settingChildrens = FXCollections.observableArrayList();
        settingChildrens.add(isBookmarkBarOn);
        settingChildrens.add(new HBox());
        settingChildrens.add(back);
        this.setItems(settingChildrens);
    }
}