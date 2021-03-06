package assignment.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseQuery {

    protected Connection conn;

    protected void openConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:derby:"
                        + System.getProperty("user.dir")
                        + System.getProperty("file.separator")
                        + "templeDB;create=true");
            } catch (SQLException ex) {
                System.out.println("openConnection() error!");
            }
        }
    }

    protected void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException ex) {
            System.out.println("closeConnection() error!");
        }
    }
}
