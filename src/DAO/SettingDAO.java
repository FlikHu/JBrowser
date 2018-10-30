package DAO;

import java.sql.*;

public class SettingDAO {
    private String userId;
    private String homepage;
    private int fontSize;
    private int pageZoom;
    private SearchEngines searchEngine;
    private boolean enableJS;
    private boolean enableBookmarkBar;

    public SettingDAO() {
        this.userId = "0";
        this.homepage = "https://stackoverflow.com/";
        this.fontSize = 100;
        this.pageZoom = 100;
        this.searchEngine = SearchEngines.GOOGLE;
        this.enableJS = true;
        this.enableBookmarkBar = false;
    }

    public SettingDAO(String userId, String homepage, int fontSize, int pageZoom, SearchEngines searchEngine, boolean enableJS, boolean enableBookmarkBar) {
        this.userId = userId;
        this.homepage = homepage;
        this.fontSize = fontSize;
        this.pageZoom = pageZoom;
        this.searchEngine = searchEngine;
        this.enableJS = enableJS;
        this.enableBookmarkBar = enableBookmarkBar;
    }

    public void createSetting(String userId) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "INSERT INTO setting (user_id, homepage, font_size, page_zoom, search_engine, javascript_enable, bookmark_bar_enable) "+
                                        "VALUES (?,?,?,?,?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, userId);
                stmt.setString(2, this.homepage);
                stmt.setInt(3, this.fontSize);
                stmt.setInt(4, this.pageZoom);
                switch(searchEngine) {
                    case GOOGLE:
                        stmt.setInt(5, 0);
                        break;
                    case BING:
                        stmt.setInt(5, 1);
                        break;
                    case YAHOO:
                        stmt.setInt(5, 2);
                        break;
                    case DUCK_DUCK_GO:
                        stmt.setInt(5, 3);
                        break;
                }
                stmt.setInt(6, this.enableJS ? 1 : 0);
                stmt.setInt(7, this.enableBookmarkBar ? 1 : 0);
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

    public boolean getSetting(String userId) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "SELECT * FROM setting WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, userId);
                ResultSet res = stmt.executeQuery();
                if(res.next()) {
                    String id = res.getString(1);
                    String hPage = res.getString(2);
                    int fSize = res.getInt(3);
                    int zoom = res.getInt(4);
                    int se = res.getInt(5);
                    boolean js = res.getInt(6) == 1;
                    boolean bookmark = res.getInt(7) == 1;

                    this.userId = id;
                    this.homepage = hPage;
                    this.fontSize = fSize;
                    this.pageZoom = zoom;
                    switch(se) {
                        case 0:
                            this.searchEngine = SearchEngines.GOOGLE;
                            break;
                        case 1:
                            this.searchEngine = SearchEngines.BING;
                            break;
                        case 2 :
                            this.searchEngine = SearchEngines.YAHOO;
                            break;
                        case 3:
                            this.searchEngine = SearchEngines.DUCK_DUCK_GO;
                            break;
                    }
                    this.enableJS = js;
                    this.enableBookmarkBar = bookmark;
                    return true;
                }
                return false;
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
        return false;
    }

    public void updateHomepage(String homepage) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "UPDATE setting SET homepage = ? WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, homepage);
                stmt.setString(2, this.userId);
                stmt.executeUpdate();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateFontSize(int fontSize) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "UPDATE setting SET font_size = ? WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setInt(1, fontSize);
                stmt.setString(2, this.userId);
                stmt.executeUpdate();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePageZoom(int pageZoom) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "UPDATE setting SET page_zoom = ? WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setInt(1, pageZoom);
                stmt.setString(2, this.userId);
                stmt.executeUpdate();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateSearchEngine(SearchEngines searchEngine) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "UPDATE setting SET search_engine = ? WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                switch(searchEngine) {
                    case GOOGLE:
                        stmt.setInt(1, 0);
                        break;
                    case BING:
                        stmt.setInt(1, 1);
                        break;
                    case YAHOO:
                        stmt.setInt(1, 2);
                        break;
                    case DUCK_DUCK_GO:
                        stmt.setInt(1, 3);
                        break;
                }
                stmt.setString(2, this.userId);
                stmt.executeUpdate();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateEnableJS(boolean enableJS) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "UPDATE setting SET javascript_enable = ? WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setInt(1, enableJS ? 1 : 0);
                stmt.setString(2, this.userId);
                stmt.executeUpdate();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateEnableBookmarkBar(boolean enableBookmarkBar) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "UPDATE setting SET bookmark_bar_enable = ? WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setInt(1, enableBookmarkBar ? 1 : 0);
                stmt.setString(2, this.userId);
                stmt.executeUpdate();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteSetting() {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "DELETE from setting WHERE user_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, this.userId);
                stmt.executeUpdate();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getPageZoom() {
        return pageZoom;
    }

    public SearchEngines getSearchEngine() {
        return searchEngine;
    }

    public boolean isEnableJS() {
        return enableJS;
    }

    public boolean isEnableBookmarkBar() {
        return enableBookmarkBar;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setPageZoom(int pageZoom) {
        this.pageZoom = pageZoom;
    }

    public void setSearchEngine(SearchEngines searchEngine) {
        this.searchEngine = searchEngine;
    }

    public void setEnableJS(boolean enableJS) {
        this.enableJS = enableJS;
    }

    public void setEnableBookmarkBar(boolean enableBookmarkBar) {
        this.enableBookmarkBar = enableBookmarkBar;
    }
}
