package Main;

import java.sql.*;

public class DBUtility {

    private static void statementExecutor(String sqlQueryString) {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            Statement stmt = conn.createStatement();
            stmt.execute(sqlQueryString);
        } catch (SQLException e) {
            System.out.println("database error");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void initiallize() {
        String dbAddress = "jdbc:sqlite:data.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                System.out.println("conncected to database");
                createSaveLoginTable();
                createUserTable();
                createBookMarksTable();
                createSettingTable();
            }
        } catch (SQLException e) {
            System.out.println("Cannot connect to database");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private static void createSaveLoginTable() {
        String queryString = "CREATE TABLE IF NOT EXISTS loginData (username text PRIMARY KEY)";
        statementExecutor(queryString);
    }

    private static void createUserTable() {
        String queryString = "CREATE TABLE IF NOT EXISTS userData (username text PRIMARY KEY, password text)";
        statementExecutor(queryString);
    }

    private static void createBookMarksTable() {
        String queryString = "CREATE TABLE IF NOT EXISTS bookmarks (username text PRIMARY KEY, url text, name text)";
        statementExecutor(queryString);
    }

    private static void createSettingTable() {
        String queryString = "CREATE TABLE IF NOT EXISTS setting (username text PRIMARY KEY, " +
                "homepage text, font_size integer, page_zoom integer, search_engine integer, javascript_enable integer)";
        statementExecutor(queryString);
    }

    public static void dropAllTables() {
        String queryString1 = "DROP TABLE IF EXISTS loginData";
        String queryString2 = "DROP TABLE IF EXISTS userData";
        String queryString3 = "DROP TABLE IF EXISTS bookmarks";
        String queryString4 = "DROP TABLE IF EXISTS setting";
        statementExecutor(queryString1);
        statementExecutor(queryString2);
        statementExecutor(queryString3);
        statementExecutor(queryString4);
    }
}
