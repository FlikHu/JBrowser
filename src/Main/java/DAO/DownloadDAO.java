package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DownloadDAO {
    private String userId;
    private List<String[]> downloadHistory;

    public DownloadDAO(String userId) {
        this.userId = userId;
        downloadHistory = new ArrayList();
    }

    public void newDownload() {

    }

    public void getDownload() {

    }

    public void deleteDownload() {

    }

    public void clear() {

    }

    public String getUserId() {
        return userId;
    }

    public List<String[]> getDownloadHistory() {
        return downloadHistory;
    }
}
