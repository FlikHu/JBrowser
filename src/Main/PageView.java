package Main;

import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class PageView extends Region {
    final WebView view = new WebView();
    final WebEngine engine = view.getEngine();
    private String title;
    private String currentUrl;


    public PageView(){
        engine.load("https://www.google.com/");
        this.title = "1";
        this.getChildren().add(view);
    }

    public String getTitle() {
        return title;
    }
}
