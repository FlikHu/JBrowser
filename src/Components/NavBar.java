package Components;

import Main.PageView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class NavBar extends VBox {
    private HashMap<Button, PageView> tab = new HashMap<>();

    public NavBar() {
        HBox tabButtons = new HBox();
        for(Button btn : tab.keySet()) {
            tabButtons.getChildren().add(btn);
        }

        // Multitab
        Button add = new Button();
        add.setText("+");
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button tab = new Button();
                tabButtons.getChildren().add(tab);
            }
        });


        // ToolBar
        tabButtons.getChildren().add(add);
        HBox urlField = new HBox();
        TextField url = new TextField();
        urlField.getStyleClass().add("special");


        Button back = new Button();
        Button forward = new Button();
        Button refresh = new Button();
        back.setText("Back");
        forward.setText("Forward");
        refresh.setText("Refresh");
        urlField.getChildren().addAll(back,forward, url, refresh);
        this.getChildren().addAll(tabButtons, urlField);
    }
}
