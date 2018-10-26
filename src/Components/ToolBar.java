package Components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ToolBar extends VBox {

    public ToolBar() {
        HBox urlField = new HBox();
        TextField url = new TextField();

        Button back = new Button();
        Button forward = new Button();
        Button refresh = new Button();
        Button go = new Button();

        back.setText("<-");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        forward.setText("->");
        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        refresh.setText("*");
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        go.setText("Go");
        go.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        urlField.getChildren().addAll(back, forward, refresh, url, go);
        this.getStylesheets().add("Style/Style.css");
        this.getChildren().addAll(new BookMark(), urlField);
    }
}
