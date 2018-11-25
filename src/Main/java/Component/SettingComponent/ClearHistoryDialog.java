package Component.SettingComponent;

import Application.Main;
import Application.SessionManager;
import DAO.HistoryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;


public class ClearHistoryDialog extends Stage {
    private ObservableList<History> items;

    public ClearHistoryDialog() {
        VBox container = new VBox();
        Label title = new Label("Time Range");
        title.getStyleClass().add("title");

        ToggleGroup choices = new ToggleGroup();

        RadioButton clearPastDay = new RadioButton("Last 24 hours");
        clearPastDay.setToggleGroup(choices);
        clearPastDay.setUserData(0);
        clearPastDay.setSelected(true);

        RadioButton clearPastWeek = new RadioButton("Last 7 days");
        clearPastWeek.setToggleGroup(choices);
        clearPastWeek.setUserData(1);

        RadioButton clearPastMonth = new RadioButton("Last 30 days");
        clearPastMonth.setToggleGroup(choices);
        clearPastMonth.setUserData(2);

        RadioButton clearAll = new RadioButton("All time");
        clearAll.setToggleGroup(choices);
        clearAll.setUserData(3);

        Button clear = new Button("Clear");
        clear.getStyleClass().add("dialogButton");
        clear.setOnAction((actionEvent -> {
            Toggle toggle = choices.getSelectedToggle();
            int option = (int) toggle.getUserData();
            HistoryDAO historyDAO = new HistoryDAO(SessionManager.getInstance().getUserId());
            historyDAO.clearHistory(option);
            SessionManager.getInstance().updateHistoryList();

            ObservableList<History> items = FXCollections.observableArrayList();
            List<String[]> historyList = SessionManager.getInstance().getHistory();

            for(String[] bookmark : historyList) {
                String id = bookmark[0];
                String name = bookmark[2];
                String url = bookmark[1];
                String timeAdded = bookmark[3];

                items.add(new History(id,name,url,timeAdded,items));
            }
            this.items = items;
            this.close();
        }));

        container.getChildren().addAll(title, clearPastDay, clearPastWeek, clearPastMonth, clearAll, clear);
        container.setAlignment(Pos.BASELINE_CENTER);
        container.setSpacing(10);
        container.getStylesheets().add("css/Style.css");
        Scene scene = new Scene(container,300,200);
        this.initModality(Modality.NONE);
        this.initOwner(Main.getPrimaryStage());
        this.setTitle("Clear History");
        this.setResizable(false);
        this.setScene(scene);
    }

    public ObservableList<History> showAndWaitForResponse() {
        this.showAndWait();
        return this.items;
    }
}
