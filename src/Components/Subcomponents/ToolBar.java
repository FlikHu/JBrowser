package Components.Subcomponents;

import Main.PageView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ToolBar extends VBox {

    public ToolBar(PageView pageView) {
        List<String> history = pageView.getPageHistory();
        HBox urlField = new HBox();
        TextField url = new TextField();

        Button back = new Button();
        Button forward = new Button();
        Button refresh = new Button();

        back.setText("<-");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(pageView.canBack()) pageView.back();
            }
        });

        forward.setText("->");
        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(pageView.canForward()) pageView.forward();
            }
        });

        refresh.setText("*");
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pageView.refresh();
            }
        });

        urlField.getChildren().addAll(back,forward, refresh,url);
        this.getStylesheets().add("Style/Style.css");

        this.getChildren().addAll(new BookMark(), urlField);
    }
}
