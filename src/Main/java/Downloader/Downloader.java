package Downloader;

import Application.Main;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class Downloader implements Runnable {
    private String url;
    private String dest;
    private String name;
    private long taskSize;
    private long downloadedSize;
    private DoubleProperty progress;
    private volatile boolean running = true;

    public Downloader(String url) {
        this.url = url;
        this.taskSize = 0;
        this.downloadedSize = 0;
        this.progress = new SimpleDoubleProperty(0);
    }

    public boolean initialize() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        int dot = this.url.lastIndexOf(".");
        String extension;
        if(dot > 0) {
            extension = this.url.substring(dot);
        } else {
            extension = ".f";
        }

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("File (*"+extension+")","*"+extension);
        fileChooser.getExtensionFilters().add(filter);
        File selectedDirectory = fileChooser.showSaveDialog(Main.getPrimaryStage());

        if(selectedDirectory == null) {
            return false;
        } else {
            HttpURLConnection conn = null;
            try {
                URL downloadURL = new URL(this.url);
                conn = (HttpURLConnection) new URL(this.url).openConnection();
                conn.setConnectTimeout(2000);

                taskSize = (conn.getContentLengthLong());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("Malformed URL");
                return false;

            } catch (SocketTimeoutException e) {
                System.out.println("Connection timeout");
                e.printStackTrace();
                return false;

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            this.name = selectedDirectory.getName();
            this.dest = selectedDirectory.getAbsolutePath();
            return true;
        }
    }

    @Override
    public void run() {
            try {
                URL conn = new URL(this.url);
                BufferedInputStream inputStream = new BufferedInputStream(conn.openStream());
                FileOutputStream outputStream = new FileOutputStream(this.dest);
                byte[] buffer = new byte[2048];

                int bytes;
                long read = 0;

                while((bytes = inputStream.read(buffer, 0, 2048)) != -1 && running) {
                    read+=bytes;
                    downloadedSize = read;
                    progress.setValue((double)downloadedSize/taskSize);
                    outputStream.write(buffer, 0, bytes);
                }

                inputStream.close();
                outputStream.flush();
                outputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new DownloadException("Invalid download url");

            } catch (IOException e) {
                e.printStackTrace();
                throw new DownloadException("Unexpected download error");
            }
    }

    long getTaskSize() {
        return taskSize;
    }

    String getDest() {
        return dest;
    }

    String getName() {
        return name;
    }

    DoubleProperty getProgress() {
        return progress;
    }

    void kill() {
        this.running = false;
    }
}

class DownloadException extends RuntimeException {
    DownloadException(String message) {
        super(message);
    }
}