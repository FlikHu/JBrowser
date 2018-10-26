package Main;

import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.ArrayList;
import java.util.List;


public class PageView extends Region {
    private WebView view = new WebView();
    private WebEngine engine = view.getEngine();
    private String title;
    private int historyIndex;
    private List<String> pageHistory;

    public PageView(){
        engine.load("https://www.google.com/");
        this.title = engine.getTitle();
        this.pageHistory = new ArrayList<String>();
        historyIndex = 0;
        pageHistory.add("https://www.google.com/");
        engine.locationProperty().addListener((observable, oldValue, newValue) -> {
            if (historyIndex == pageHistory.size()-1) historyIndex++;
            pageHistory.add(newValue);
            System.out.println(historyIndex);
        });

        engine.titleProperty().addListener(((observable, oldValue, newValue) -> {
            title = newValue;
        }));

        this.getChildren().add(view);
        this.getStyleClass().add("page_view");
        this.getStylesheets().add("Style/Style.css");
    }

    public boolean canBack() {
        return historyIndex != 0;
    }

    public void back() {
        historyIndex--;
        engine.load(pageHistory.get(historyIndex));
        System.out.println(historyIndex);
    }

    public boolean canForward() {
        return historyIndex < pageHistory.size()-1;
    }

    public void forward() {
        historyIndex++;
        engine.load(pageHistory.get(historyIndex));
        System.out.println(historyIndex);
    }

    public void refresh() {
        engine.reload();
    }

    public void goTo(String url) {
        engine.load(url);
    }

    public String getTitle() {
        return title;
    }

    public List<String> getPageHistory(){
        return pageHistory;
    }

}
