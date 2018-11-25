package Component.SettingComponent;

import Application.Main;
import Application.SessionManager;
import DAO.BookmarkDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Set;

// Data class for bookmark table
public class Bookmark {
    private StringProperty id;
    private StringProperty name;
    private StringProperty url;
    private StringProperty timeAdded;
    private HBox buttonGroup;
    private ObservableList<Bookmark> items;

    public Bookmark(String id, String name, String url, String timeAdded, ObservableList<Bookmark> items) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.url = new SimpleStringProperty(url);
        this.timeAdded = new SimpleStringProperty(timeAdded);
        this.items = items;
        this.buttonGroup = new HBox();

        Button deleteBtn = new Button();
        deleteBtn.getStyleClass().add("delete");
        deleteBtn.getStylesheets().add("css/Style.css");
        deleteBtn.setOnAction((actionEvent -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this bookmark?");
            confirm.showAndWait().ifPresent((response) -> {
                if(response == ButtonType.OK) {
                    BookmarkDAO bookmarkDAO = new BookmarkDAO(SessionManager.getInstance().getUserId());
                    bookmarkDAO.deleteBookmark(id);
                    SessionManager.getInstance().updateBookmarkList();
                    items.remove(this);
                }
            });
        }));

        Button editButton = new Button();
        editButton.getStyleClass().add("edit");
        editButton.getStyleClass().add("css/Style.css");
        editButton.setOnAction((actionEvent -> {
            for(int index = 0; index < items.size(); index++) {
                Bookmark curr = items.get(index);
                if(curr == this){
                    String newName = new BookmarkDialog(curr.getId(),curr.getName()).showAndWaitForResponse();
                    if(newName != null) {
                        curr.setName(newName);
                        items.set(index, curr);
                    }
                    break;
                }
            }
        }));

        // Todo
        Button gotoButton = new Button();
        editButton.getStyleClass().add("??");
        editButton.getStyleClass().add("css/Style.css");
        gotoButton.setOnAction((actionEvent -> {
            Stage pStage = Main.getPrimaryStage();
            Scene mainScene = Main.getMainScene();
            TabPane webTabs = Main.getWebTabsView();
            Tab plus = Main.getPlusTab();
            Main.createTab(webTabs,plus, this.getUrl());
            pStage.setScene(mainScene);
        }));

        this.buttonGroup.getChildren().addAll(deleteBtn, editButton, gotoButton);
        this.buttonGroup.setAlignment(Pos.CENTER);
        this.buttonGroup.setSpacing(10);

    }

    public String getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getUrl() {
        return url.get();
    }

    public String getTimeAdded() {
        return timeAdded.get();
    }

    public HBox getButtonGroup() {
        return buttonGroup;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
