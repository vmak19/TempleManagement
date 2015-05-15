package assignment.database;

import assignment.model.Booking;
import assignment.model.Employee;
import assignment.model.Room;
import assignment.model.RoomInfo;
import assignment.model.RoomType;
import assignment.view.TabRoomController;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseSetup extends DatabaseQuery {

    PreparedStatement createBookingTable = null;
    PreparedStatement createEmployeeTable = null;
    PreparedStatement createRoomTable = null;
    PreparedStatement createRoomTypeTable = null;
    PreparedStatement createRoomInfoTable = null;
    ResultSet rs = null;

    private ObservableList<Booking> bookingData = FXCollections.observableArrayList(); //Sim edit
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    private ObservableList<RoomType> roomTypeData = FXCollections.observableArrayList();
    private ObservableList<RoomInfo> roomInfoData = FXCollections.observableArrayList();
    private BookingQueries bookingQueries;
    private EmployeeQueries employeeQueries;
    private RoomTypeQueries roomTypeQueries;

    public ObservableList<Booking> getBookingData() {
        return bookingData;
    }

    public ObservableList<Employee> getEmployeeData() {
        return employeeData;
    }

    public ObservableList<RoomType> getRoomTypeData() {
        return roomTypeData;
    }

    public ObservableList<RoomInfo> getRoomInfoData() {
        return roomInfoData;
    }

    public BookingQueries getBookingQueries() {
        return bookingQueries;
    }

    public static void setupDatabase() {
        DatabaseSetup dbs = new DatabaseSetup();
        dbs.databaseSetup();
    }

    private void databaseSetup() {

        bookingQueries = new BookingQueries();
        employeeQueries = new EmployeeQueries();
        roomTypeQueries = new RoomTypeQueries();

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
                        + "\"ROOMID\" INT, "
                        + "\"CREATEDDATE\" DATE, "
                        + "\"NUMBREAKFAST\" INT, "
                        + "\"CHECKIN\" TIMESTAMP, "
                        + "\"CHECKOUT\" TIMESTAMP, "
                        + "\"AMOUNTPAID\" DOUBLE, "
                        + "\"AMOUNTDUE\" DOUBLE, "
                        + "\"EARLYCHECKIN\" BOOLEAN, "
                        + "\"LATECHECKOUT\" BOOLEAN, "
                        + "FOREIGN KEY (ROOMID) REFERENCES ROOM(ROOMID))");
                createBookingTable.execute();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table BOOKING error!");
        }

        try {

            // Determine if the EMPLOYEE table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "EMPLOYEE", null);

            if (!rs.next()) {
                // If the EMPLOYEE table does not already exist we create it
                createEmployeeTable = conn.prepareStatement(
                        "CREATE TABLE APP.EMPLOYEE ("
                        + "\"USERID\" INT not null primary key "
                        + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "\"PASSWORD\" VARCHAR(100), "
                        + "\"EMPFIRSTNAME\" VARCHAR(100), "
                        + "\"EMPLASTNAME\" VARCHAR(100), "
                        + "\"ISADMINISTRATOR\" BOOLEAN)");
                createEmployeeTable.execute();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table EMPLOYEE error!");
        }

        try {

            // Determine if the ROOMTYPE table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "ROOMTYPE", null);

            if (!rs.next()) {
                // If the ROOM_TYPE table does not already exist we create it
                createRoomTypeTable = conn.prepareStatement(
                        "CREATE TABLE APP.ROOMTYPE ("
                        + "\"ROOMTYPEID\" VARCHAR(50) not null primary key, "
                        + "\"DESCRIPTION\" VARCHAR(200), "
                        + "\"BASERATE\" DOUBLE, "
                        + "\"CAPACITY\" INT)");
                createRoomTypeTable.execute();

                System.out.println("created Table ROOMTYPE");
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table ROOMTYPE error!");
        }

        try {

            // Determine if the ROOM table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "ROOM", null);

            if (!rs.next()) {
                // If the ROOM table does not already exist we create it
                createRoomTable = conn.prepareStatement(
                        "CREATE TABLE APP.ROOM ("
                        + "\"ROOMID\" INT not null primary key "
                        + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "\"ROOMTYPEID\" VARCHAR(50),"
                        + "FOREIGN KEY (ROOMTYPEID) REFERENCES ROOMTYPE(ROOMTYPEID))");
                createRoomTable.execute();
                System.out.println("created Table ROOM");
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table ROOM error!");
        }
        closeConnection();

    }
}
