package Components.Subcomponents;

import Main.PageView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.util.HashMap;

// Multitab
public class Multitab extends HBox{
    private HashMap<Button, PageView> tab = new HashMap<>();

    public Multitab() {

    }
}
