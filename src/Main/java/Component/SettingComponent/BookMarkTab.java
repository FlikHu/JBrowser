package Component.SettingComponent;

import Application.SessionManager;
import Constant.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookMarkTab extends BorderPane {
    public BookMarkTab() {
        // Table View
        // Name + URL + Time added + delete button
        TableView tableView = new TableView();
        TableColumn nameCol = new TableColumn("Name");
        TableColumn urlCol = new TableColumn("URL");
        TableColumn timeCol = new TableColumn("Date Added");
        TableColumn actionCol = new TableColumn("Actions");
        tableView.getColumns().addAll(nameCol,urlCol,timeCol,actionCol);

        nameCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        urlCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));
        timeCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        actionCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));

        ObservableList<Bookmark> items = FXCollections.observableArrayList();
        List<String[]> bookmarks = SessionManager.getInstance().getBookmarks();

        for(String[] bookmark : bookmarks) {
            String id = bookmark[0];
            String name = bookmark[2];
            String url = bookmark[1];
            Long timestamp = Long.parseLong(bookmark[3]);
            Date date = new Date(timestamp);
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
            String timeAdded = formatter.format(date);

            items.add(new Bookmark(id,name,url,timeAdded, items));
        }

        nameCol.setCellValueFactory(new PropertyValueFactory<Bookmark,String>("name"));
        urlCol.setCellValueFactory(new PropertyValueFactory<Bookmark,String>("url"));
        timeCol.setCellValueFactory(new PropertyValueFactory<Bookmark,String>("timeAdded"));
        actionCol.setCellValueFactory(new PropertyValueFactory<Bookmark,HBox>("buttonGroup"));
        tableView.setItems(items);

        Pane borderLeft = new Pane();
        Pane borderRight = new Pane();
        borderLeft.prefWidthProperty().bind(this.widthProperty().multiply(Constants.SETTING_BORDER_MARGIN));
        borderRight.prefWidthProperty().bind(this.widthProperty().multiply(Constants.SETTING_BORDER_MARGIN));
        this.setCenter(tableView);
        this.setLeft(borderLeft);
        this.setRight(borderRight);
    }
}
