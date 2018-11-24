package DAO;

import Constant.Constants;

import java.sql.*;
import java.util.UUID;

public class DBUtility {

    private static void statementExecutor(String sqlQueryString) {
        Connection conn = null;
        try {
            Class.forName(Constants.DB_DRIVER);
            conn = DriverManager.getConnection(Constants.DB_ADDRESS);
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
        Connection conn = null;
        try {
            Class.forName(Constants.DB_DRIVER);
            conn = DriverManager.getConnection(Constants.DB_ADDRESS);
            if(conn != null) {
                createSaveLoginTable();
                createUserTable();
                createBookMarksTable();
                createSettingTable();
                createDownloadHistoryTable();
                createHistoryTable();
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

    static String generateId() {
        return UUID.randomUUID().toString();
    }

    private static void createSaveLoginTable() {
        String queryString = "CREATE TABLE IF NOT EXISTS loginData (username text PRIMARY KEY)";
        statementExecutor(queryString);
    }

    private static void createUserTable() {
        String queryString = "CREATE TABLE IF NOT EXISTS userData (id text PRIMARY KEY, username, password text)";
        statementExecutor(queryString);
    }

    private static void createBookMarksTable() {
        String queryString = "CREATE TABLE IF NOT EXISTS bookmarks (id text PRIMARY KEY, url text, name text, user_id text, time_added integer)";
        statementExecutor(queryString);
    }

    private static void createSettingTable() {
        String queryString = "CREATE TABLE IF NOT EXISTS setting (user_id text PRIMARY KEY, " +
                "homepage text, font_size integer, page_zoom integer, search_engine integer, " +
                "javascript_enable integer, bookmark_bar_enable integer)";
        statementExecutor(queryString);
    }

    private static void createDownloadHistoryTable() {
        String queryString = "CREATE TABLE IF NOT EXISTS downloads (id text PRIMARY KEY, name text, url text, size integer," +
                "time integer, dest text, user_id text)";
        statementExecutor(queryString);
    }

    private static void createHistoryTable() {
        String queryString = "CREATE TABLE IF NOT EXISTS history (id text PRIMARY KEY, name text, url text, time integer, user_id text)";
        statementExecutor(queryString);
    }

    public static void dropAllTables() {
        String queryString1 = "DROP TABLE IF EXISTS loginData";
        String queryString2 = "DROP TABLE IF EXISTS userData";
        String queryString3 = "DROP TABLE IF EXISTS bookmarks";
        String queryString4 = "DROP TABLE IF EXISTS setting";
        String queryString5 = "DROP TABLE IF EXISTS downloads";
        String queryString6 = "DROP TABLE IF EXISTS history";
        statementExecutor(queryString1);
        statementExecutor(queryString2);
        statementExecutor(queryString3);
        statementExecutor(queryString4);
        statementExecutor(queryString5);
        statementExecutor(queryString6);
    }
}
