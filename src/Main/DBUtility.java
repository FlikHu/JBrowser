package Main;

import java.sql.*;

public class DBUtility {

    public static void initiallize() {
        String dbAddress = "jdbc:sqlite:data.db";
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(dbAddress);
            if(conn != null) {
                System.out.println("database created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveLogin() {

    }
}
