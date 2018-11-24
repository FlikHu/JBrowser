package Component.SettingComponent;

import Application.SessionManager;
import DAO.DownloadDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.File;

// Data class for download table
public class Download {
    private StringProperty id;
    private StringProperty name;
    private StringProperty url;
    private StringProperty size;
    private StringProperty time;
    private StringProperty destination;
    private HBox buttonGroup;

    public Download(String id, String name, String url, String size, String time, String destination, ObservableList<Download> items){
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.url = new SimpleStringProperty(url);
        this.size = new SimpleStringProperty(size);
        this.time = new SimpleStringProperty(time);
        this.destination = new SimpleStringProperty(destination);


        Button deleteBtn = new Button();
        deleteBtn.getStyleClass().add("delete");
        deleteBtn.setOnAction((actionEvent -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this download record?");
            confirm.showAndWait().ifPresent((response) -> {
                if(response == ButtonType.OK) {
                    DownloadDAO downloadDAO = new DownloadDAO(SessionManager.getInstance().getUserId());
                    downloadDAO.deleteDownload(this.id.get());
                    SessionManager.getInstance().updateDownloadHistoryList();
                    items.remove(this);
                }
            });
        }));

        Button openFolder = new Button();
        openFolder.getStyleClass().add("openFolder");
        openFolder.setOnAction((actionEvent -> {
            try {
                Desktop desktop = Desktop.getDesktop();
                File fileDir = new File(this.getDestination());
                if(fileDir.exists()) {
                    File folderDir = fileDir.getParentFile();
                    desktop.open(folderDir);
                } else {
                    Alert error = new Alert(Alert.AlertType.INFORMATION, "The file cannot be found");
                    error.show();
                }
            } catch (Exception e) {
                openFolder.setDisable(true);
                e.printStackTrace();
            }
        }));

        this.buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(deleteBtn, openFolder);
        buttonGroup.setSpacing(10);
        buttonGroup.setAlignment(Pos.CENTER);
    }

    public String getName() {
        return name.get();
    }

    public String getUrl() {
        return url.get();
    }

    public String getSize() {
        return size.get();
    }

    public String getTime() {
        return time.get();
    }

    public String getDestination() {
        return destination.get();
    }

    public HBox getButtonGroup() {
        return buttonGroup;
    }
}
