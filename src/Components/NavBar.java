package Components;

import Components.Subcomponents.ToolBar;
import Main.PageView;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class NavBar extends VBox {
    private HashMap<Button, PageView> tab = new HashMap<>();

    public NavBar() {

    }
}
