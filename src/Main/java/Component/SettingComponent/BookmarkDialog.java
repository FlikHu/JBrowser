package Component.SettingComponent;

import Application.Main;
import Application.SessionManager;
import DAO.BookmarkDAO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BookmarkDialog extends Stage {
    private String editedName;

    public BookmarkDialog(WebEngine engine, Button bookmark){
        VBox box = new VBox(20);
        Label title = new Label("New Bookmark");
        title.getStyleClass().add("title");

        HBox container = new HBox();
        Label nameLabel = new Label("Name: ");
        TextField name = new TextField(engine.getTitle());
        name.getStyleClass().add("bookmarkName");
        container.getChildren().addAll(nameLabel,name);
        container.setAlignment(Pos.CENTER);

        HBox container2 = new HBox();
        Label urlLabel = new Label("URL Address: ");
        Label bookmarkURL = new Label(engine.getLocation());
        container2.getChildren().addAll(urlLabel,bookmarkURL);
        container2.setAlignment(Pos.CENTER);

        Button submit = new Button("Done");
        submit.getStyleClass().add("dialogButton");

        submit.setOnAction((actionEvent -> {
            String userId = SessionManager.getInstance().getUserId();
            BookmarkDAO bookmarkDAO = new BookmarkDAO(userId);
            bookmarkDAO.addBookmark(name.getText(), engine.getLocation());
            SessionManager.getInstance().updateBookmarkList();
            bookmark.setId("stared");
            this.close();
        }));

        box.getChildren().addAll(title, container, container2, submit);
        box.setAlignment(Pos.CENTER);
        box.getStylesheets().add("css/Style.css");
        Scene dialogScene = new Scene(box, 400, 200);

        this.initModality(Modality.NONE);
        this.initOwner(Main.getPrimaryStage());
        this.setScene(dialogScene);
        this.setResizable(false);
        this.showAndWait();
    }

    public BookmarkDialog(String bookmarkId, String bookmarkName) {
        VBox box = new VBox(20);
        Label title = new Label("Edit Bookmark");
        title.getStyleClass().add("title");

        HBox container = new HBox();
        Label nameLabel = new Label("Name: ");
        TextField name = new TextField(bookmarkName);
        name.getStyleClass().add("bookmarkName");
        container.getChildren().addAll(nameLabel,name);
        container.setAlignment(Pos.CENTER);

        Button submit = new Button("Done");
        submit.getStyleClass().add("dialogButton");

        submit.setOnAction((actionEvent -> {
            String userId = SessionManager.getInstance().getUserId();
            BookmarkDAO bookmarkDAO = new BookmarkDAO(userId);
            bookmarkDAO.updateBookmarkName(name.getText(), bookmarkId);
            SessionManager.getInstance().updateBookmarkList();
            this.editedName = name.getText();
            this.close();
        }));

        box.getChildren().addAll(title, container, submit);
        box.setAlignment(Pos.CENTER);
        box.getStylesheets().add("css/Style.css");
        Scene dialogScene = new Scene(box, 400, 150);

        this.initModality(Modality.NONE);
        this.initOwner(Main.getPrimaryStage());
        this.setScene(dialogScene);
        this.setResizable(false);
    }

    public String showAndWaitForResponse() {
        super.showAndWait();
        return editedName;
    }
}
