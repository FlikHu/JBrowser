package Main;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Setting {

    public static Scene createSettingScene(){
        Group root = new Group();

        TabPane settingPane = new TabPane();
        settingPane.prefWidthProperty().bind(Main.getPrimaryStage().widthProperty());
        settingPane.prefHeightProperty().bind(Main.getPrimaryStage().heightProperty().multiply(0.94));
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

        Button back = new Button();
        back.setText("Back");
        back.setOnAction((event -> {
            Stage pStage = Main.getPrimaryStage();
            pStage.setScene(Main.getMainScene());
        }));

        VBox box = new VBox();
        box.getChildren().addAll(settingPane, back);
        root.getChildren().addAll(box);
        return new Scene(root, 1000, 800);
    }
}
