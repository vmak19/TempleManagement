package assignment.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseSetup extends DatabaseQuery {
   
    PreparedStatement createBookingTable = null;
    ResultSet rs = null;
    
    public static void setupDatabase() {
        DatabaseSetup dbs = new DatabaseSetup();
        dbs.databaseSetup();
    }
    
    private void databaseSetup() {
        
        openConnection();
        
        try {

            // Determine if the booking table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "BOOKING", null);

            if (!rs.next()) {
                // If the BOOKING table does not already exist we create it
                createBookingTable = conn.prepareStatement(
                        "CREATE TABLE APP.BOOKING ("
                        + "\"REFCODE\" INT not null primary key "
                        + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "\"CUSTFIRSTNAME\" VARCHAR(100), "
                        + "\"CUSTLASTNAME\" VARCHAR(100), "
                        + "\"NUMPEOPLE\" INT, "
                        + "\"ROOMNUM\" INT, "
                        + "\"CREATEDDATE\" DATE, "
                        + "\"NUMBREAKFAST\" INT, "
                        + "\"CHECKIN\" TIMESTAMP, "
                        + "\"CHECKOUT\" TIMESTAMP, "
                        + "\"AMOUNTPAID\" DOUBLE, "
                        + "\"AMOUNTDUE\" DOUBLE, "
                        + "\"EARLYCHECKIN\" BOOLEAN, "
                        + "\"LATECHECKOUT\" BOOLEAN)");
                createBookingTable.execute();
            }
        } catch (SQLException ex) {
            System.out.println("datebaseSetup() error!");
        }

        closeConnection();
    }
    
    
}
