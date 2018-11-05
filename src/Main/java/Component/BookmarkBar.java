package Component;

import Application.SessionManager;
import DAO.BookmarkDAO;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

import java.util.List;

public class BookmarkBar extends HBox {

    public BookmarkBar(WebView view) {
        BookmarkDAO bookmarkDAO = new BookmarkDAO(SessionManager.getInstance().getUserId());
        bookmarkDAO.getBookmarks();
        List<String[]> bookmarks = bookmarkDAO.getBookmarkList();

        for(int i = 0; i < 8 && i < bookmarks.size(); i++) {
            String[] bookmark = bookmarks.get(i);
            Button redirect = new Button();
            redirect.setText(bookmark[2]);

            redirect.setOnAction((event -> {
                view.getEngine().load(bookmark[1]);
            }));

            redirect.getStyleClass().add("bookmark");

            Button separator = new Button("|");
            separator.setDisable(true);
            separator.getStyleClass().add("separator");

            this.getChildren().addAll(redirect, separator);
        }

        this.getStyleClass().add("bookmarkBar");
    }

}
