package Downloader;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.Arrays;
import java.util.HashSet;


// Automatically download files with following extensions
public class DownloadDetector implements ChangeListener<String> {
    public static HashSet<String> downloadable = new HashSet<>(
                Arrays.asList("pkg", "rar", "zip", "exe", "dmg",
                        "iso", "apk", "bin", "jar", "py", "mp3",
                        "mp4", "txt", "pdf", "doc", "docx", "deb",
                        "rpm", "msi", "avi", "gz"
            ));

    // ChangeListener for web engine location property
    @Override
    public void changed(ObservableValue observableValue, String oldValue, String newValue) {
        int dot = newValue.lastIndexOf(".");
        String extension = newValue.substring(dot+1);

        if (downloadable.contains(extension)) {
            Downloader downloader = new Downloader(newValue);
            if (downloader.initialize()) {
                new DownloadDialog(downloader);
            }
        }
    }
}
