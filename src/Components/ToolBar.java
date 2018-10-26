package Components;

import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.net.URL;
import java.net.URLConnection;

public class ToolBar extends HBox {

    // Top tool bar
    public ToolBar(WebView view, String hPage) {
        WebEngine engine = view.getEngine();
        WebHistory history = engine.getHistory();

        // Buttons
        TextField url = new TextField();
        url.setText(engine.getLocation());
        this.setHgrow(url, Priority.ALWAYS);

        Button back = new Button();
        back.setDisable(true);
        back.setId("back");

        Button forward = new Button();
        forward.setDisable(true);
        forward.setId("forward");

        Button refresh = new Button();
        refresh.setId("refresh");

        Button homepage = new Button();
        homepage.setId("homepage");

        back.setOnAction((event-> {
                history.go(-1);
        }));

        forward.setOnAction((event->{
                history.go(1);
        }));

        refresh.setOnAction((event -> {
            engine.reload();
        }));

        homepage.setOnAction((event -> {
            engine.load(hPage);
        }));

        url.setOnKeyPressed((keyEvent) -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                String temp = url.getText();
                if(temp.length() == 0) {
                    url.setText("Invalid URL");
                } else {
                    if(!temp.startsWith("http://") && !temp.startsWith("https://")) {
                        temp = "https://"+temp;
                    }
                    try {
                        URL address = new URL(temp);
                        URLConnection connection = address.openConnection();
                        connection.connect();
                        engine.load(temp);
                    } catch (Exception e){
                        String googleQueryString = "https://www.google.com/search?q="+temp;
                        engine.load(googleQueryString);
                    }
                }
            }
        });

        history.currentIndexProperty().addListener((observable, oldValue, newValue) -> {
            int historyIndex = newValue.intValue();
            if(historyIndex <= 0) {
                back.setDisable(true);
            } else {
                back.setDisable(false);
            }

            if(historyIndex >= history.getEntries().size()-1) {
                forward.setDisable(true);
            } else {
                forward.setDisable(false);
            }
        });

        engine.locationProperty().addListener((observable, oldURL, newURL) -> {
            url.setText(newURL);
        });

        this.setFillHeight(true);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getStyleClass().add("bar");
        this.getStylesheets().add("Style/Style.css");
        this.getChildren().addAll(back, forward, refresh, homepage, url);
    }

    // Bottom tool bar
    public ToolBar(WebView view) {
        WebEngine engine = view.getEngine();
        Worker worker = engine.getLoadWorker();

        ProgressBar loadingProgress = new ProgressBar(0);

        Label loadingLabel = new Label();
        loadingLabel.setLabelFor(loadingProgress);
        loadingLabel.setText("Loading:");

        Button setting = new Button();
        setting.setText("Setting");
        setting.setId("setting");

        Pane placeholder = new Pane();
        placeholder.setPrefHeight(20);
        this.setHgrow(placeholder, Priority.ALWAYS);

        worker.progressProperty().addListener(((observable, oldProgress, newProgress) -> {
            double progress = newProgress.doubleValue();
            if(progress > 0) {
                loadingProgress.setProgress(progress);
            }
        }));

        this.setFillHeight(true);
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("bar");
        this.getStylesheets().add("Style/Style.css");
        this.getChildren().addAll(loadingLabel, loadingProgress, placeholder, setting);
    }
}
