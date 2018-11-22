package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class BookmarkDAO {
    private String userId;
    private List<String[]> bookmarkList;

    public BookmarkDAO(String userId) {
        this.userId = userId;
        bookmarkList = new ArrayList<>();
    }

    public void getBookmarks() {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "SELECT * FROM bookmarks WHERE user_id = ? ORDER BY time_added DESC";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, this.userId);
                ResultSet res = stmt.executeQuery();
                while(res.next()) {
                    String[] item = new String[4];
                    item[0] = res.getString(1);
                    item[1] = res.getString(2);
                    item[2] = res.getString(3);
                    item[3] = Long.toString(res.getLong(5));
                    bookmarkList.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void addBookmark(String bookmarkName, String url) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "INSERT INTO bookmarks (id, url, name, user_id, time_added) VALUES(?,?,?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, DBUtility.generateId());
                stmt.setString(2, url);
                stmt.setString(3, bookmarkName);
                stmt.setString(4, this.userId);
                stmt.setLong(5, new Date().getTime());
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateBookmarkName(String bookmarkName, String bookmarkId) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "UPDATE bookmarks SET name = ?, time_added = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, bookmarkName);
                stmt.setLong(2, new Date().getTime());
                stmt.setString(3, bookmarkId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteBookmark(String bookmarkId) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "DELETE FROM bookmarks WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, bookmarkId);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean bookmarkExists(String url) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if (conn != null) {
                String sqlQueryString = "SELECT * FROM bookmarks WHERE url = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1,url);
                ResultSet set = stmt.executeQuery();
                boolean res = false;
                if(set.next()) res = true;
                return res;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public String getUserId() {
        return userId;
    }

    public List<String[]> getBookmarkList() {
        return bookmarkList;
    }
}
