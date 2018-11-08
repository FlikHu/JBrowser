package Component;

import Application.Main;
import Application.SessionManager;
import DAO.BookmarkDAO;
import Downloader.Downloader;
import Downloader.DownloadDialog;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class ToolBar extends HBox {

    // Top tool bar
    public ToolBar(WebView view, String hPage) {
        WebEngine engine = view.getEngine();
        WebHistory history = engine.getHistory();

        // Buttons
        TextField url = new TextField();
        url.setText(engine.getLocation());
        setHgrow(url, Priority.ALWAYS);

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

        Button search = new Button();
        search.setId("search");

        Button bookmark = new Button();
        bookmark.setId("bookmark");

        Button download = new Button();
        download.setId("download");

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

        search.setOnAction((event -> {
            String googleQueryString = "https://www.google.com/search?q="+url.getText();
            engine.load(googleQueryString);
        }));

        bookmark.setOnAction(event -> {
                showBookmarkDialog(engine);
        });
        if (BookmarkDAO.bookmarkExists(hPage)) {
            bookmark.setId("stared");
            bookmark.setDisable(true);
        } else {
            bookmark.setId("bookmark");
            bookmark.setDisable(false);
        }

        download.setOnAction(event -> {
            Downloader downloader = new Downloader(engine.getLocation());
            if (downloader.initialize()) {
                Thread downloadThread = new Thread(downloader);
                new DownloadDialog(downloader);
                downloadThread.start();
            }
        });

        url.setOnKeyPressed((keyEvent) -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER)) {
                String temp = url.getText();
                if(temp.length() == 0) {
                    url.setText("Invalid URL");
                } else {
                    if(!temp.startsWith("http://") && !temp.startsWith("https://")) {
                        String googleQueryString = "https://www.google.com/search?q="+temp;
                        String yahooQueryString = "https://search.yahoo.com/search?p="+temp;
                        String bingQueryString = "https://www.bing.com/search?q="+temp;
                        String duckduckgoQueryString = "https://duckduckgo.com/?q="+temp;
                        switch(SessionManager.getInstance().getSearchEngine()) {
                            case GOOGLE:
                                engine.load(googleQueryString);
                                break;
                            case YAHOO:
                                engine.load(yahooQueryString);
                                break;
                            case BING:
                                engine.load(bingQueryString);
                                break;
                            case DUCK_DUCK_GO:
                                engine.load(duckduckgoQueryString);
                                break;
                        }
                    }

                    try {
                        URL address = new URL(temp);
                        URLConnection connection = address.openConnection();
                        connection.connect();
                        engine.load(temp);
                    } catch (Exception e){
                        String googleQueryString = "https://www.google.com/search?q="+temp;
                        String yahooQueryString = "https://search.yahoo.com/search?p="+temp;
                        String bingQueryString = "https://www.bing.com/search?q="+temp;
                        String duckduckgoQueryString = "https://duckduckgo.com/?q="+temp;
                        switch(SessionManager.getInstance().getSearchEngine()) {
                            case GOOGLE:
                                engine.load(googleQueryString);
                                break;
                            case YAHOO:
                                engine.load(yahooQueryString);
                                break;
                            case BING:
                                engine.load(bingQueryString);
                                break;
                            case DUCK_DUCK_GO:
                                engine.load(duckduckgoQueryString);
                                break;
                        }
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
            if(newURL != null) {
                url.setText(newURL);
                if (BookmarkDAO.bookmarkExists(newURL)) {
                    bookmark.setId("stared");
                    bookmark.setDisable(true);
                } else {
                    bookmark.setId("bookmark");
                    bookmark.setDisable(false);
                }
            }
        });

        this.setFillHeight(true);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getStyleClass().add("bar");
        this.getStylesheets().add("css/Style.css");
        this.getChildren().addAll(back, forward, refresh, homepage, url, search, bookmark, download);
    }

    // Bottom tool bar
    public ToolBar(WebView view) {
        WebEngine engine = view.getEngine();
        Worker worker = engine.getLoadWorker();

        ProgressBar loadingProgress = new ProgressBar(0);

        Label loadingLabel = new Label();
        loadingLabel.setLabelFor(loadingProgress);
        loadingLabel.setText("Loading:");

        Label timeLabel = new Label();

        Button setting = new Button();
        setting.setId("setting");

        Pane placeholder = new Pane();
        placeholder.setPrefHeight(20);
        setHgrow(placeholder, Priority.ALWAYS);


        Counter counter = new Counter();
        worker.progressProperty().addListener(((observable, oldProgress, newProgress) -> {
            double old = oldProgress.doubleValue();
            double progress = newProgress.doubleValue();
            if(progress > 0) {
                loadingProgress.setProgress(progress);
                if(old < 0.1) {
                    counter.start();
                    timeLabel.setText("");
                } else if(progress > 0.95) {
                    counter.end();
                    timeLabel.setText(Double.toString((double)counter.getInterval()/1000)+"s");
                    counter.reset();
                }
            }
        }));

        setting.setOnAction(event -> {
            Stage pStage = Main.getPrimaryStage();
            pStage.setScene(Setting.createSettingScene());
        });

        this.setFillHeight(true);
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("bar");
        this.getStylesheets().add("css/Style.css");
        this.getChildren().addAll(loadingLabel, loadingProgress, timeLabel, placeholder, setting);
    }

    // Display add bookmark dialog
    private void showBookmarkDialog(WebEngine engine){
        Stage dialog = new Stage();
        dialog.initModality(Modality.NONE);
        dialog.initOwner(Main.getPrimaryStage());
        VBox box = new VBox(20);
        Label title = new Label("New Bookmark");
        title.getStyleClass().add("title");

        HBox container = new HBox();
        Label nameLabel = new Label("Name: ");
        TextField name = new TextField(engine.getTitle());
        name.getStyleClass().add("bookmarkName");
        container.getChildren().addAll(nameLabel,name);
        container.setAlignment(Pos.CENTER);

        HBox container2 = new HBox();
        Label urlLabel = new Label("URL Address: ");
        Label bookmarkURL = new Label(engine.getLocation());
        container2.getChildren().addAll(urlLabel,bookmarkURL);
        container2.setAlignment(Pos.CENTER);

        Button submit = new Button("Done");
        submit.getStyleClass().add("dialogButton");

        submit.setOnAction((actionEvent -> {
            String userId = SessionManager.getInstance().getUserId();
            BookmarkDAO bookmarkDAO = new BookmarkDAO(userId);
            bookmarkDAO.addBookmark(name.getText(), engine.getLocation());
            dialog.close();
        }));

        box.getChildren().addAll(title, container, container2, submit);
        box.setAlignment(Pos.CENTER);
        box.getStylesheets().add("css/Style.css");
        Scene dialogScene = new Scene(box, 400, 200);

        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.show();
    }
}

/*To count loading time (cannot directly do so in the lambda expression)*/
class Counter {
    private long startTime;
    private long endTime;

    Counter(){
        startTime=0;
        endTime=0;
    }

    void start() {
        startTime = new Date().getTime();
    }

    void end() {
        endTime = new Date().getTime();
    }

    void reset() {
        startTime = 0;
        endTime = 0;
    }

    long getInterval() {
        return endTime - startTime;
    }
}
