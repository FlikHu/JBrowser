package Component.SettingComponent;

import Application.SessionManager;
import Constant.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class HistoryTab extends BorderPane {
    public HistoryTab(){
        TableView tableView = new TableView();
        TableColumn nameCol = new TableColumn("Name");
        TableColumn urlCol = new TableColumn("URL");
        TableColumn timeCol = new TableColumn("Date");
        TableColumn actionCol = new TableColumn("Action");
        tableView.getColumns().addAll(nameCol,urlCol,timeCol,actionCol);

        nameCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        urlCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));
        timeCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        actionCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));

        ObservableList<History> items = FXCollections.observableArrayList();
        List<String[]> historyList = SessionManager.getInstance().getHistory();
        System.out.println(historyList.size());

        for(String[] bookmark : historyList) {
            String id = bookmark[0];
            String name = bookmark[1];
            String url = bookmark[2];
            String timeAdded = bookmark[3];

            items.add(new History(id,name,url,timeAdded,items));
        }

        nameCol.setCellValueFactory(new PropertyValueFactory<Bookmark,String>("name"));
        urlCol.setCellValueFactory(new PropertyValueFactory<Bookmark,String>("url"));
        timeCol.setCellValueFactory(new PropertyValueFactory<Bookmark,String>("timeAdded"));
        actionCol.setCellValueFactory(new PropertyValueFactory<Bookmark,Button>("deleteBtn"));
        tableView.setItems(items);

        Pane borderLeft = new Pane();
        Pane borderRight = new Pane();
        borderLeft.prefWidthProperty().bind(this.widthProperty().multiply(Constants.SETTING_BORDER_MARGIN));
        borderRight.prefWidthProperty().bind(this.widthProperty().multiply(Constants.SETTING_BORDER_MARGIN));

        Button clearHistory = new Button("Clear Browsing Data");
        clearHistory.getStyleClass().add("dialogButton");
        clearHistory.setStyle("-fx-pref-width: 150px !important;");
        clearHistory.setOnAction((actionEvent -> {
            ClearHistoryDialog dialog = new ClearHistoryDialog();
            ObservableList<History> newItems = dialog.showAndWaitForResponse();
            if (newItems != null) tableView.setItems(newItems);
        }));

        HBox bottomBar = new HBox();
        bottomBar.getChildren().add(clearHistory);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPrefHeight(100);
        this.setBottom(bottomBar);
        this.setCenter(tableView);
        this.setLeft(borderLeft);
        this.setRight(borderRight);
    }
}
