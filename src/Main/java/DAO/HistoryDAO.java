package DAO;

import Constant.Constants;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryDAO {
    private String userId;
    private List<String[]> historyList;

    public HistoryDAO(String userId) {
        this.userId = userId;
        this.historyList = new ArrayList<>();
    }

    public void newHistory(String name, String url) {
        Connection conn = null;
        try {
            Class.forName(Constants.DB_DRIVER);
            conn = DriverManager.getConnection(Constants.DB_ADDRESS);
            if (conn != null) {
                String sqlQueryString = "INSERT INTO history (id, name, url, time, user_id) VALUES(?,?,?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, DBUtility.generateId());
                stmt.setString(2, name);
                stmt.setString(3, url);
                stmt.setLong(4, new Date().getTime());
                stmt.setString(5, this.userId);
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

    public void getHistory() {
        Connection conn = null;
        try {
            Class.forName(Constants.DB_DRIVER);
            conn = DriverManager.getConnection(Constants.DB_ADDRESS);
            if (conn != null) {
                String sqlQueryString = "SELECT * FROM history WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, this.userId);
                ResultSet set = stmt.executeQuery();
                while(set.next()) {
                    String[] item = new String[4];
                    item[0] = set.getString(1);
                    item[1] = set.getString(2);
                    item[2] = set.getString(3);
                    // Time
                    Date date = new Date(set.getLong(4));
                    SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                    String time = formatter.format(date);
                    item[3] = time;
                    historyList.add(item);
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

    public void deleteHistory(String historyId) {
        Connection conn = null;
        try {
            Class.forName(Constants.DB_DRIVER);
            conn = DriverManager.getConnection(Constants.DB_ADDRESS);
            if (conn != null) {
                String sqlQueryString = "DELETE FROM history WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, historyId);
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

    public List<String[]> getHistoryList() {
        return historyList;
    }
}
