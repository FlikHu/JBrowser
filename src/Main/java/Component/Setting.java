package Component;

import Application.Main;
import Application.SessionManager;
import Component.SettingComponent.BookMarkTab;
import Component.SettingComponent.DownloadTab;
import Component.SettingComponent.SettingTab;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;


public class Setting {

    public static Scene createSettingScene(){
        Group root = new Group();

        TabPane settingPane = new TabPane();
        settingPane.prefWidthProperty().bind(Main.getPrimaryStage().widthProperty());
        settingPane.prefHeightProperty().bind(Main.getPrimaryStage().heightProperty());
        settingPane.setId("settingPage");
        settingPane.getStylesheets().add("css/Style.css");

        Tab setting = new Tab("Setting");
        setting.setClosable(false);
        Tab bookmarks = new Tab("Bookmarks");
        bookmarks.setClosable(false);
        Tab downloads = new Tab("Downloads");
        downloads.setClosable(false);

        settingPane.getTabs().addAll(setting, bookmarks, downloads);
        settingPane.tabMinWidthProperty().bind(Main.getPrimaryStage().widthProperty().divide(5));
        settingPane.getSelectionModel().select(setting);

        setting.setContent(new SettingTab(settingPane));
        bookmarks.setContent(new BookMarkTab());
        downloads.setContent(new DownloadTab());

        root.getChildren().addAll(settingPane);
        return new Scene(root, Main.getMainScene().getWidth(), Main.getMainScene().getHeight());
    }
}


