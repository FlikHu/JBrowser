package Main;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

class BookmarkBar extends HBox {

    BookmarkBar(WebView view) {
        Button dummy = new Button();
        dummy.setText("My Bookmark");
        dummy.setStyle("-fx-pref-width: 150px");
        this.getChildren().addAll(dummy);
    }

}
