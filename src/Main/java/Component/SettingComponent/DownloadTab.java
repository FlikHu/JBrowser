package Component.SettingComponent;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class DownloadTab extends BorderPane {
    public DownloadTab() {
        TableView tableView = new TableView();
        TableColumn taskNameCol = new TableColumn("Task Name");
        TableColumn urlCol = new TableColumn("URL");
        TableColumn sizeCol =  new TableColumn("Task Size");
        TableColumn timeCol = new TableColumn("Time");
        TableColumn destCol = new TableColumn("Destination");
        TableColumn actionCol = new TableColumn("Actions");
        ObservableList items = FXCollections.observableArrayList();


        tableView.getColumns().addAll(taskNameCol,urlCol,sizeCol,timeCol,destCol,actionCol);
        tableView.setItems(items);
        this.setCenter(tableView);
    }
}
