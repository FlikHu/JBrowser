package Main;

import Components.NavBar;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("JFX Browser");
        Group root = new Group();
        Scene main = new Scene(root, 1000, 800);

        VBox box = new VBox();
        box.getChildren().addAll(new NavBar(), new PageView());

        root.getChildren().add(box);

        primaryStage.setScene(main);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
