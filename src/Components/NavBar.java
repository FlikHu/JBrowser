package Components;

import Components.Subcomponents.Multitab;
import Components.Subcomponents.ToolBar;
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
        this.getChildren().addAll(new Multitab(), new ToolBar());
    }
}
