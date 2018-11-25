package DAO;

import Constant.Constants;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class DownloadDAO {
    private String userId;
    private List<String[]> downloadHistory;

    public DownloadDAO(String userId) {
        this.userId = userId;
        downloadHistory = new ArrayList<>();
    }

    public void newDownload(String name, String url, long size, String dest) {
        Connection conn = null;
        try {
            Class.forName(Constants.DB_DRIVER);
            conn = DriverManager.getConnection(Constants.DB_ADDRESS);
            if (conn != null) {
                String sqlQueryString = "INSERT INTO downloads (id, name, url, size, time, dest, user_id) VALUES(?,?,?,?,?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, DBUtility.generateId());
                stmt.setString(2, name);
                stmt.setString(3, url);
                stmt.setLong(4, size);
                stmt.setLong(5, new Date().getTime());
                stmt.setString(6, dest);
                stmt.setString(7,this.userId);
                stmt.executeUpdate();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void getDownloads() {
        Connection conn = null;
        try {
            Class.forName(Constants.DB_DRIVER);
            conn = DriverManager.getConnection(Constants.DB_ADDRESS);
            if(conn != null) {
                String sqlQueryString = "SELECT * FROM downloads WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, this.userId);
                ResultSet set = stmt.executeQuery();
                while(set.next()) {
                    String[] item = new String[6];
                    // Download id
                    item[0] = set.getString(1);
                    // Task name
                    item[1] = set.getString(2);
                    // URL
                    item[2] = set.getString(3);
                    // Size
                    DecimalFormat df = new DecimalFormat("#.##");
                    String size = df.format((double)set.getLong(4)/1000000) + " MB";
                    item[3] = size;
                    // Time
                    Date date = new Date(set.getLong(5));
                    SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                    String time = formatter.format(date);
                    item[4] = time;
                    // Destination
                    item[5] = set.getString(6);
                    this.downloadHistory.add(item);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteDownload(String downloadId) {
        Connection conn = null;
        try {
            Class.forName(Constants.DB_DRIVER);
            conn = DriverManager.getConnection(Constants.DB_ADDRESS);
            if(conn != null) {
                String sqlQueryString = "DELETE FROM downloads WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, downloadId);
                stmt.executeUpdate();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getUserId() {
        return userId;
    }

    public List<String[]> getDownloadHistory() {
        return downloadHistory;
    }
}
