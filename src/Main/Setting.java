package Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class Setting {

    static Scene createSettingScene(){
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

        Button back = new Button();
        back.setText("Back");
        back.getStyleClass().add("settingButton");
        back.setOnAction((event -> {
            Stage pStage = Main.getPrimaryStage();
            pStage.setScene(Main.getMainScene());
        }));

        Button test1 = new Button();
        test1.setText("Test1");
        test1.getStyleClass().add("settingButton");
        test1.setOnAction((event -> {
            SessionManager.getInstance().setEnableBookmarkBar(true);
        }));

        Button test2 = new Button();
        test2.setText("Test2");
        test2.getStyleClass().add("settingButton");
        test2.setOnAction((event -> {
            SessionManager.getInstance().setEnableBookmarkBar(false);
        }));

        ListView<Node> settingOptions = new ListView<Node>();
        ObservableList<Node> settingChildrens = FXCollections.observableArrayList();
        settingChildrens.add(test1);
        settingChildrens.add(new HBox());
        settingChildrens.add(test2);
        settingChildrens.add(new HBox());
        settingChildrens.add(back);
        settingOptions.setItems(settingChildrens);

        ListView<Node> bookmarksList = new ListView<Node>();
        ObservableList<Node> bookmarksChildrens = FXCollections.observableArrayList();
        bookmarksChildrens.add(new HBox());
        bookmarksList.setItems(bookmarksChildrens);

        setting.setContent(settingOptions);
        bookmarks.setContent(bookmarksList);

        root.getChildren().addAll(settingPane);
        return new Scene(root, 1000, 800);
    }
}
