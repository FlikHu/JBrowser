package Component.SettingComponent;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// Tab content for account
public class AccountTab extends VBox {
    // Login view
    public AccountTab() {
        TextField username = new TextField();
        TextField password = new TextField();

        HBox buttonGroup = new HBox();
        Button login = new Button();
        Button signUp = new Button();
        buttonGroup.getChildren().addAll(login, signUp);
        this.getChildren().addAll(username, password, buttonGroup);
    }

    // Logged in view
    public AccountTab(String userId) {
        this.getChildren().addAll();
    }
}

// Sign up view
class Signup extends VBox {
    public Signup() {
        TextField username = new TextField();
        TextField password = new TextField();
        TextField passwordConfirm = new TextField();
        Button submit = new Button();
        this.getChildren().addAll(username, password, passwordConfirm, submit);
    }

    private boolean validate(String username, String password, String passwordConfirm) {
        return false;
    }
}