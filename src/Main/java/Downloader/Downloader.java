package Downloader;

public class Downloader implements Runnable {
    private String url;
    private String name;
    private String dest;

    public Downloader(String url, String name, String dest) {
        this.url = url;
        this.name = name;
        this.dest = dest;
    }

    private void download() {

    }

    @Override
    public void run() {
        // Todo
    }
}
