package Component.SettingComponent;

import Application.SessionManager;
import DAO.BookmarkDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

// Data class for bookmark table
public class Bookmark {
    private StringProperty id;
    private StringProperty name;
    private StringProperty url;
    private StringProperty timeAdded;
    private Button deleteBtn;
    private ObservableList<Bookmark> items;

    public Bookmark(String id, String name, String url, String timeAdded, ObservableList<Bookmark> items) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.url = new SimpleStringProperty(url);
        this.timeAdded = new SimpleStringProperty(timeAdded);
        this.items = items;
        this.deleteBtn = new Button();
        this.deleteBtn.getStyleClass().add("delete");
        this.deleteBtn.getStylesheets().add("css/Style.css");
        this.deleteBtn.setOnAction((actionEvent -> {
            BookmarkDAO bookmarkDAO = new BookmarkDAO(SessionManager.getInstance().getUserId());
            bookmarkDAO.deleteBookmark(id);
            SessionManager.getInstance().updateBookmarkList();
            items.remove(this);
        }));

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

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
