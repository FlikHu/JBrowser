package Component.SettingComponent;


import Application.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class DownloadTab extends BorderPane {

    public DownloadTab() {
        TableView tableView = new TableView();
        TableColumn taskNameCol = new TableColumn("Task Name");
        TableColumn urlCol = new TableColumn("URL");
        TableColumn sizeCol =  new TableColumn("Task Size");
        TableColumn timeCol = new TableColumn("Date Completed");
        TableColumn destCol = new TableColumn("Destination");
        TableColumn actionCol = new TableColumn("Actions");

        taskNameCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        urlCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        sizeCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        timeCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        destCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        actionCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));

        ObservableList<Download> items = FXCollections.observableArrayList();
        List<String[]> downloadHistory = SessionManager.getInstance().getDownloads();

        for(String[] download : downloadHistory) {
            String id = download[0];
            String name = download[1];
            String url = download[2];
            String size = download[3];
            String time = download[4];
            String dest = download[5];
            items.add(new Download(id,name,url,size,time,dest,items));
        }

        taskNameCol.setCellValueFactory(new PropertyValueFactory<Download,String>("name"));
        urlCol.setCellValueFactory(new PropertyValueFactory<Download,String>("url"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<Download,String>("size"));
        timeCol.setCellValueFactory(new PropertyValueFactory<Download,String>("time"));
        destCol.setCellValueFactory(new PropertyValueFactory<Download,String>("destination"));
        actionCol.setCellValueFactory(new PropertyValueFactory<Download,HBox>("buttonGroup"));

        tableView.getColumns().addAll(taskNameCol,urlCol,sizeCol,timeCol,destCol,actionCol);
        tableView.setItems(items);
        this.setCenter(tableView);
    }
}
