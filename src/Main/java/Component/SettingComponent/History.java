package Component.SettingComponent;

import Application.SessionManager;
import DAO.HistoryDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

// Data class for history table
public class History {
    private StringProperty id;
    private StringProperty name;
    private StringProperty url;
    private StringProperty timeAdded;
    private Button deleteBtn;
    private ObservableList<History> items;

    public History(String id, String name, String url, String timeAdded,ObservableList<History> items) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.url = new SimpleStringProperty(url);
        this.timeAdded = new SimpleStringProperty(timeAdded);
        this.items = items;

        this.deleteBtn = new Button();
        deleteBtn.getStyleClass().add("delete");
        deleteBtn.getStylesheets().add("css/Style.css");
        deleteBtn.setOnAction((actionEvent -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this history?");
            confirm.showAndWait().ifPresent((response) -> {
                if(response == ButtonType.OK) {
                    HistoryDAO historyDAO = new HistoryDAO(SessionManager.getInstance().getUserId());
                    historyDAO.deleteHistory(this.getId());
                    SessionManager.getInstance().updateHistoryList();
                    items.remove(this);
                }
            });
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

    public ObservableList<History> getItems() {
        return items;
    }
}
