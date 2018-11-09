package Downloader;

import Application.Main;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

// Dialog displaying download progress
public class DownloadDialog extends Stage {

    public DownloadDialog(Downloader downloader) {
        Label nameLabel = new Label("File Name");
        nameLabel.getStyleClass().add("title");
        Label name = new Label(downloader.getName());

        Label destLabel = new Label("Destination");
        destLabel.getStyleClass().add("title");
        Label dest = new Label(downloader.getDest());

        Label sizeLabel = new Label("File Size");
        sizeLabel.getStyleClass().add("title");
        Label size = new Label((double)downloader.getTaskSize()/1000000 + " MB");
        Label percentage = new Label("--%");

        ProgressBar bar = new ProgressBar();
        bar.progressProperty().bind(downloader.getProgress());

        Button openFile = new Button("Open");
        openFile.setDisable(true);
        openFile.getStyleClass().add("dialogButton");
        openFile.setOnAction((actionEvent -> {
            try {
                Desktop desktop = Desktop.getDesktop();
                File file = new File(downloader.getDest());
                desktop.open(file);

            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                openFile.setDisable(true);
            } catch (Exception e) {
                openFile.setDisable(true);
            }
        }));

        Button openFolder = new Button("Open folder");
        openFolder.setDisable(true);
        openFolder.getStyleClass().add("dialogButton");
        openFolder.setOnAction((actionEvent -> {
            try {
                Desktop desktop = Desktop.getDesktop();
                File file = new File(downloader.getDest()).getParentFile();
                if(file == null) {
                    openFolder.setDisable(true);
                } else {
                    desktop.open(file);
                }
            } catch (Exception e) {
                openFolder.setDisable(true);
                e.printStackTrace();
            }
        }));

        Thread downloadThread = new Thread(downloader);
        try {
            downloadThread.start();
        } catch (DownloadException e) {
            this.setTitle("Download Error");
            percentage.setText("--%");
        }

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("dialogButton");
        cancel.setId("cancelButton");
        cancel.setOnAction((actionEvent -> {
            downloader.kill();
            this.setTitle("Canceled");
        }));

        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(openFile, openFolder, cancel);
        buttonGroup.setSpacing(5);
        buttonGroup.setAlignment(Pos.CENTER);


        downloader.getProgress().addListener(((observableValue, o, n) -> {
            Platform.runLater(() -> {
                DecimalFormat df = new DecimalFormat("#.##");
                String formattedPercentage = df.format(n.doubleValue()*100)+"%";
                percentage.setText(formattedPercentage);
                if(n.doubleValue() >= 1.0) {
                    this.setTitle("Done!");
                    percentage.setText("Download Completed");
                    openFile.setDisable(false);
                    openFolder.setDisable(false);
                    cancel.setDisable(true);
                }
            });
        }));

        VBox box = new VBox();
        box.getChildren().addAll(nameLabel, name, sizeLabel, size, destLabel, dest, bar, percentage, buttonGroup);
        box.setSpacing(5);
        box.setAlignment(Pos.CENTER);
        box.getStylesheets().add("css/Style.css");
        Scene dialogScene = new Scene(box, 400, 270);

        this.initModality(Modality.NONE);
        this.initOwner(Main.getPrimaryStage());
        this.setTitle("Downloading...");
        this.setScene(dialogScene);
        this.setResizable(false);
        this.show();
    }
}