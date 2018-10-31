package Main.Component;

import DAO.SettingDAO;
import Main.Main;
import Main.SessionManager;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Setting {

    public static Scene createSettingScene(){
        Group root = new Group();

        TabPane settingPane = new TabPane();
        settingPane.prefWidthProperty().bind(Main.getPrimaryStage().widthProperty());
        settingPane.prefHeightProperty().bind(Main.getPrimaryStage().heightProperty());
        settingPane.setId("settingPage");
        settingPane.getStylesheets().add("Style/Style.css");

        Tab setting = new Tab("Setting");
        setting.setClosable(false);
        Tab bookmarks = new Tab("Bookmarks");
        bookmarks.setClosable(false);
        Tab account = new Tab("Account");
        account.setClosable(false);

        settingPane.getTabs().addAll(setting, bookmarks, account);
        settingPane.tabMinWidthProperty().bind(Main.getPrimaryStage().widthProperty().divide(4));
        settingPane.getSelectionModel().select(setting);

        setting.setContent(new SettingTab());
        bookmarks.setContent(new BookmarkTab());
        account.setContent(new AccountTab());

        root.getChildren().addAll(settingPane);
        return new Scene(root, 1000, 800);
    }
}

class SettingTab extends ListView<Node> {
    SettingTab() {

        // Toggle on/off bookmark bar
        ToggleSwitch isBookmarkBarOn = new ToggleSwitch(60, 30, SessionManager.getInstance().isEnableBookmarkBar());
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

class BookmarkTab extends ListView<Node>{
    BookmarkTab() {
        ListView<Node> bookmarksList = new ListView<Node>();
        ObservableList<Node> bookmarksChildrens = FXCollections.observableArrayList();
        bookmarksChildrens.add(new HBox());
        bookmarksList.setItems(bookmarksChildrens);
    }
}

class AccountTab extends VBox {
    AccountTab() {

    }
}

// Helper class for creating a toggle switch, which is not presented in JavaFX
class ToggleSwitch extends Parent {

    private BooleanProperty isOn;

    ToggleSwitch(int width, int height, boolean toggled) {
        isOn = new SimpleBooleanProperty(toggled);

        TranslateTransition translation = new TranslateTransition(Duration.seconds(0.3));
        FillTransition fill = new FillTransition(Duration.seconds(0.3));
        ParallelTransition animation = new ParallelTransition(translation, fill);

        Rectangle rect = new Rectangle();
        rect.setArcWidth(width/2);
        rect.setArcHeight(width/2);
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setFill(isOn.getValue() ? Color.valueOf("#4285f4") : Color.WHITE);
        rect.setStroke(Color.LIGHTGRAY);

        Circle toggle = new Circle();
        toggle.setRadius(width/4);
        toggle.setCenterX(isOn.getValue() ? width*3/4 : width/4);
        toggle.setCenterY(height/2);
        toggle.setFill(Color.valueOf("#efefef"));
        toggle.setStroke(Color.LIGHTGRAY);
        getChildren().addAll(rect, toggle);

        translation.setNode(toggle);
        fill.setShape(rect);

        if(!isOn.getValue()) {
            setOnMouseClicked(event -> {
                if(!isOn.getValue()) {
                    translation.setToX(width/2);
                    fill.setFromValue(Color.WHITE);
                    fill.setToValue(Color.valueOf("#4285f4"));
                } else {
                    translation.setToX(0);
                    fill.setFromValue(Color.valueOf("#4285f4"));
                    fill.setToValue(Color.WHITE);
                }
                animation.play();
                isOn.setValue(!isOn.getValue());
            });
        } else {
            setOnMouseClicked(event -> {
                if(!isOn.getValue()) {
                    translation.setToX(0);
                    fill.setFromValue(Color.WHITE);
                    fill.setToValue(Color.valueOf("#4285f4"));
                } else {
                    translation.setToX(-width/2);
                    fill.setFromValue(Color.valueOf("#4285f4"));
                    fill.setToValue(Color.WHITE);
                }
                animation.play();
                isOn.setValue(!isOn.getValue());
            });
        }
    }

    BooleanProperty isOnProperty() {
        return isOn;
    }
}

