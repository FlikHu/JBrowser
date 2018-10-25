package Components.Subcomponents;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ToolBar extends VBox {
    public ToolBar() {

        HBox urlField = new HBox();
        TextField url = new TextField();
        urlField.getStyleClass().add("special");

        Button back = new Button();
        Button forward = new Button();
        Button refresh = new Button();
        back.setText("Back");
        forward.setText("Forward");
        refresh.setText("Refresh");
        urlField.getChildren().addAll(back,forward, refresh,url);
        url.setStyle("-fx-pref-width: 400px");

        this.getChildren().addAll(new BookMark(), urlField);
    }
}
