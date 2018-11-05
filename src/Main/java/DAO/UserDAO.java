package DAO;

import java.sql.*;

public class UserDAO {
    private String id;
    private String username;
    private String password;

    public UserDAO(String username, String password) {
        this.id =  DBUtility.generateId();
        this.username = username;
        this.password = password;
    }

    // For update
    public UserDAO(String id) {
        this.id = id;
    }

    private UserDAO(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public void createUser() {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "INSERT INTO userData (id, username, password) VALUES(?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, id);
                stmt.setString(2, username);
                stmt.setString(3, password);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
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

    public boolean auth() {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "SELECT * FROM userData WHERE username = ? AND password = ? ";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet res = stmt.executeQuery();
                if(res.next()) {
                    this.id = res.getString(1);
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
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
        return false;
    }

    public UserDAO getUser(String username, String password) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "SELECT * FROM userData WHERE username = ? AND password = ? ";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet res = stmt.executeQuery();
                if(res.next()) {
                    return new UserDAO(res.getString(1), res.getString(2),res.getString(3));
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
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
        return null;
    }

    public void updateUsername(String newUsername) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "UPDATE userData SET username = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, newUsername);
                stmt.setString(2, id);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
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

    public void updatePassword(String newPassword) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "UPDATE userData SET password = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, newPassword);
                stmt.setString(2, id);
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

    public void deleteUser() {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                String sqlQueryString = "DELETE from userData WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sqlQueryString);
                stmt.setString(1, id);
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

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
