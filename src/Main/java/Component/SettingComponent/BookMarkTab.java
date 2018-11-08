package Component.SettingComponent;

import Application.SessionManager;
import DAO.BookmarkDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// Tab content for bookmark
public class BookMarkTab extends ListView<Node> {
    public BookMarkTab() {
        ObservableList<Node> bookmarksChildren = FXCollections.observableArrayList();

        BookmarkDAO bookmarkDAO = new BookmarkDAO(SessionManager.getInstance().getUserId());
        bookmarkDAO.getBookmarks();
        List<String[]> bookmarks = bookmarkDAO.getBookmarkList();

        for(String[] bookmark : bookmarks) {
            Label name = new Label(bookmark[2]);

            Label url = new Label(bookmark[1]);
            Label time = new Label();
            Long timestamp = Long.parseLong(bookmark[3]);
            Date date = new Date(timestamp);
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
            time.setText(formatter.format(date));

            HBox box = new HBox();
            box.setHgrow(time, Priority.ALWAYS);
            box.getChildren().addAll(name, url, time);
            bookmarksChildren.add(box);
        }
        this.setItems(bookmarksChildren);
    }
}