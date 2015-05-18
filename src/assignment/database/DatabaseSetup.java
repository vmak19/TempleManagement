package assignment.database;

import assignment.model.Booking;
import assignment.model.Employee;
import assignment.model.Room;
import assignment.model.RoomInfo;
import assignment.model.RoomType;
import assignment.util.DateUtil;
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
    PreparedStatement createRoomTable = null;
    PreparedStatement createRoomTypeTable = null;
    PreparedStatement createRoomInfoTable = null;
    PreparedStatement createBillingTable = null;
    PreparedStatement createEmployeeTable = null;
    PreparedStatement createLogTable = null;
    ResultSet rs = null;

    public static void setupDatabase() {
        DatabaseSetup dbs = new DatabaseSetup();
        dbs.databaseSetup();
    }

    private void databaseSetup() {

        openConnection();

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

                getRoomTypesFromFile();
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
                        + "\"ROOMID\" INT not null primary key, "
                        + "\"ROOMTYPEID\" VARCHAR(50),"
                        + "FOREIGN KEY (ROOMTYPEID) REFERENCES ROOMTYPE(ROOMTYPEID))");
                createRoomTable.execute();

                getRoomsFromFile();
                System.out.println("created Table ROOM");
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table ROOM error!");
        }

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
                        + "\"CHECKIN\" DATE, "
                        + "\"CHECKOUT\" DATE, "
                        + "\"AMOUNTPAID\" DOUBLE, "
                        + "\"AMOUNTDUE\" DOUBLE, "
                        + "\"EARLYCHECKIN\" BOOLEAN, "
                        + "\"LATECHECKOUT\" BOOLEAN, "
                        + "FOREIGN KEY (ROOMID) REFERENCES ROOM(ROOMID))");
                createBookingTable.execute();
                getBookingsFromFile();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table BOOKING error!");
        }

        try {

            // Determine if the BILLING table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "BILLING", null);

            if (!rs.next()) {
                // If the BILLING table does not already exist we create it
                createBillingTable = conn.prepareStatement(
                        "CREATE TABLE APP.BILLING ("
                        + "\"BILLINGID\" INT not null primary key "
                        + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "\"AMOUNTPAID\" DOUBLE, "
                        + "\"AMOUNTDUE\" DOUBLE, "
                        + "\"DATE\" DATE)");
                createBillingTable.execute();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table BILLING error!");
        }

        try {

            // Determine if the LOG table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "LOG", null);

            if (!rs.next()) {
                // If the LOG table does not already exist we create it
                createLogTable = conn.prepareStatement(
                        "CREATE TABLE APP.LOG ("
                        + "\"LOGID\" INT not null primary key "
                        + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "\"EMPFIRSTNAME\" VARCHAR(100), "
                        + "\"EMPLASTNAME\" VARCHAR(100), "
                        + "\"DATEMOD\" DATE, "
                        + "\"ITEMMODIFIED\" VARCHAR(200))");
                createLogTable.execute();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table LOG error!");
            ex.printStackTrace();
        }

        closeConnection();
    }

    public void getRoomTypesFromFile() {
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("resources/roomtypes.csv"));

            //for all lines in file
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] roomTypeID = s.split(",");
                String[] description = s.split(",");
                String[] baseRate = s.split(",");
                String[] capacity = s.split(",");

                RoomTypeQueries roomTypeQueries = new RoomTypeQueries();

                roomTypeQueries.insertRoomType(new RoomType(
                        roomTypeID[0],
                        description[1],
                        Double.parseDouble(baseRate[2]),
                        Integer.parseInt(capacity[3])));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getRoomTypesFromFile() Error!");
            ex.printStackTrace();
        }
    }

    public void getRoomsFromFile() {
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("resources/rooms.csv"));

            //for all lines in file
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] roomID = s.split(",");
                String[] roomTypeID = s.split(",");

                RoomQueries roomQueries = new RoomQueries();

                roomQueries.insertRoom(new Room(
                        Integer.parseInt(roomID[0]),
                        roomTypeID[1]));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getRoomsFromFile() Error!");
            ex.printStackTrace();
        }
    }

    public void getBookingsFromFile() {
        //List<Booking> bookings = new ArrayList<Booking>();
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("resources/bookings.csv"));

            //for all lines in file
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] refCode = s.split(",");
                String[] fname = s.split(",");
                String[] lname = s.split(",");
                String[] numPeople = s.split(",");
                String[] roomID = s.split(",");
                String[] createdDate = s.split(",");
                String[] numBreakfast = s.split(",");
                String[] checkIn = s.split(",");
                String[] checkOut = s.split(",");
                String[] amountPaid = s.split(",");
                String[] amountDue = s.split(",");
                String[] earlyCheckIn = s.split(",");
                String[] lateCheckOut = s.split(",");

                BookingQueries bookingQueries = new BookingQueries();

                bookingQueries.insertBooking(new Booking(
                        Integer.parseInt(refCode[0]),
                        fname[1],
                        lname[2],
                        Integer.parseInt(numPeople[3]),
                        Integer.parseInt(roomID[4]),
                        DateUtil.parse(createdDate[5]),
                        Integer.parseInt(numBreakfast[6]),
                        DateUtil.parse(checkIn[7]),
                        DateUtil.parse(checkOut[8]),
                        Boolean.parseBoolean(earlyCheckIn[9]),
                        Boolean.parseBoolean(lateCheckOut[10]),
                        Double.parseDouble(amountPaid[11]),
                        Double.parseDouble(amountDue[12])));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getBookingsFromFile() Error!");
            ex.printStackTrace();
        }
    }

}
