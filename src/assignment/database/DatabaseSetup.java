package assignment.database;

import assignment.model.Assignment;
import assignment.model.Booking;
import assignment.model.Employee;
import assignment.model.Log;
import assignment.model.Room;
import assignment.model.RoomType;
import assignment.model.Service;
import assignment.util.DateUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseSetup extends DatabaseQuery {

    PreparedStatement createBookingTable = null;
    PreparedStatement createRoomTable = null;
    PreparedStatement createRoomTypeTable = null;
    PreparedStatement createRoomInfoTable = null;
    PreparedStatement createBillingTable = null;
    PreparedStatement createEmployeeTable = null;
    PreparedStatement createAssignmentTable = null;
    PreparedStatement createLogTable = null;
    PreparedStatement createServiceTable = null;
    PreparedStatement createProvidesTable = null;
    ResultSet rs = null;

    public static void setupDatabase() {
        DatabaseSetup dbs = new DatabaseSetup();
        dbs.databaseSetup();
    }

    private void databaseSetup() {

        openConnection();
        
        createEmployeeTable();
        createRoomTypeTable();
        createRoomTable();
        createBookingTable();
        createAssignmentTable();
        createServiceTable();
        createProvidesTable();
        createLogTable();
        
        closeConnection();

    }

    private void createEmployeeTable() {
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
                getEmployeesFromFile();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table EMPLOYEE error!");
        }
    }

    private void createRoomTypeTable() {
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
    }

    private void createRoomTable() {
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
                        + "\"NOOFBEDS\" INT,"
                        + "\"EXTRACHARGE\" DOUBLE,"
                        + "\"TOTALCHARGE\" DOUBLE,"
                        + "FOREIGN KEY (ROOMTYPEID) REFERENCES ROOMTYPE(ROOMTYPEID))");
                createRoomTable.execute();

                getRoomsFromFile();
                System.out.println("created Table ROOM");
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table ROOM error!");
        }
    }

    private void createBookingTable() {
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
                        + "\"CREATEDDATE\" DATE, "
                        + "\"NUMBREAKFAST\" INT, "
                        + "\"CHECKIN\" DATE, "
                        + "\"CHECKOUT\" DATE, "
                        + "\"EARLYCHECKIN\" BOOLEAN, "
                        + "\"LATECHECKOUT\" BOOLEAN, "
                        + "\"AMOUNTPAID\" DOUBLE, "
                        + "\"AMOUNTDUE\" DOUBLE)");
                createBookingTable.execute();
                getBookingsFromFile();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table BOOKING error!");
        }
    }

    private void createAssignmentTable() {
        try {

            // Determine if the ASSIGNMENT table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "ASSIGNMENT", null);

            if (!rs.next()) {
                // If the ASSIGNMENT table does not already exist we create it
                createAssignmentTable = conn.prepareStatement(
                        "CREATE TABLE APP.ASSIGNMENT ("
                        + "\"REFCODE\" INT, "
                        + "\"ROOMID\" INT, "
                        + "FOREIGN KEY (REFCODE) REFERENCES BOOKING(REFCODE), "
                        + "FOREIGN KEY (ROOMID) REFERENCES ROOM(ROOMID))");
                createAssignmentTable.execute();
                getAssignmentsFromFile();
            }

        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table ASSIGNMENT error!");
            ex.printStackTrace();
        }
    }

    private void createServiceTable() {
        try {

            // Determine if the SERVICE table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "SERVICE", null);

            if (!rs.next()) {
                // If the SERVICE table does not already exist we create it
                createServiceTable = conn.prepareStatement(
                        "CREATE TABLE APP.SERVICE ("
                        + "\"SERVICEID\" INT not null primary key "
                        + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "\"SERVICEDESC\" VARCHAR(200), "
                        + "\"COST\" DOUBLE)");
                createServiceTable.execute();
                getServicesFromFile();
            }

        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table SERVICE error!");
            ex.printStackTrace();
        }
    }

    private void createProvidesTable() {
        try {

            // Determine if the PROVIDES table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "PROVIDES", null);

            if (!rs.next()) {
                // If the PROVIDES table does not already exist we create it
                createProvidesTable = conn.prepareStatement(
                        "CREATE TABLE APP.PROVIDES ("
                        + "\"PROVIDEID\" INT not null primary key "
                        + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "\"REFCODE\" INT, "
                        + "\"ROOMID\" INT, "
                        + "\"SERVICEID\" INT, "
                        + "\"CREATEDDATE\" DATE, "
                        + "FOREIGN KEY (REFCODE) REFERENCES BOOKING(REFCODE), "
                        + "FOREIGN KEY (SERVICEID) REFERENCES SERVICE(SERVICEID))");
                createProvidesTable.execute();
            }

        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table PROVIDES error!");
            ex.printStackTrace();
        }
    }

    private void createLogTable() {
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
                        + "\"USERID\" INT, "
                        + "\"EMPFIRSTNAME\" VARCHAR(100), "
                        + "\"EMPLASTNAME\" VARCHAR(100), "
                        + "\"DATEMOD\" DATE, "
                        + "\"ITEMMODIFIED\" VARCHAR(200))");
                        //+ "\"ITEMMODIFIED\" VARCHAR(200), "
                        //+ "FOREIGN KEY (USERID) REFERENCES EMPLOYEE(USERID))");
                createLogTable.execute();
                getLogsFromFile();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table LOG error!");
            ex.printStackTrace();
        }
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
                String[] noOfBeds = s.split(",");
                String[] extraCharge = s.split(",");
                String[] totalCharge = s.split(",");

                RoomQueries roomQueries = new RoomQueries();

                roomQueries.insertRoom(new Room(
                        Integer.parseInt(roomID[0]),
                        roomTypeID[1],
                        Integer.parseInt(noOfBeds[2]),
                        Double.parseDouble(extraCharge[3]),
                        Double.parseDouble(totalCharge[4])));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getRoomsFromFile() Error!");
            ex.printStackTrace();
        }
    }

    public void getEmployeesFromFile() {
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("resources/employees.csv"));

            //for all lines in file
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] userID = s.split(",");
                String[] password = s.split(",");
                String[] fname = s.split(",");
                String[] lname = s.split(",");
                String[] admin = s.split(",");

                EmployeeQueries employeeQueries = new EmployeeQueries();

                employeeQueries.insertEmployee(new Employee(
                        Integer.parseInt(userID[0]),
                        password[1],
                        fname[2],
                        lname[3],
                        Boolean.parseBoolean(admin[4])));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getEmployeesFromFile() Error!");
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
                String[] createdDate = s.split(",");
                String[] numBreakfast = s.split(",");
                String[] checkIn = s.split(",");
                String[] checkOut = s.split(",");
                String[] earlyCheckIn = s.split(",");
                String[] lateCheckOut = s.split(",");
                String[] amountPaid = s.split(",");
                String[] amountDue = s.split(",");

                BookingQueries bookingQueries = new BookingQueries();

                bookingQueries.insertBooking(new Booking(
                        Integer.parseInt(refCode[0]),
                        fname[1],
                        lname[2],
                        DateUtil.parse(createdDate[3]),
                        Integer.parseInt(numBreakfast[4]),
                        DateUtil.parse(checkIn[5]),
                        DateUtil.parse(checkOut[6]),
                        Boolean.parseBoolean(earlyCheckIn[7]),
                        Boolean.parseBoolean(lateCheckOut[8]),
                        Double.parseDouble(amountPaid[9]),
                        Double.parseDouble(amountDue[10])));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getBookingsFromFile() Error!");
            ex.printStackTrace();
        }
    }

    public void getAssignmentsFromFile() {
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("resources/assignment.csv"));

            // For all lines in file
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] refCode = s.split(",");
                String[] roomID = s.split(",");

                AssignmentQueries assignmentQueries = new AssignmentQueries();

                assignmentQueries.insertAssignment(new Assignment(
                        Integer.parseInt(refCode[0]),
                        Integer.parseInt(roomID[1])));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getAssignmentFromFile() Error!");
            ex.printStackTrace();
        }
    }
    public void getServicesFromFile() {
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("resources/services.csv"));

            // For all lines in file
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] serviceID = s.split(",");
                String[] serviceDesc = s.split(",");
                String[] cost = s.split(",");

                ServiceQueries serviceQueries = new ServiceQueries();

                serviceQueries.insertService(new Service(
                        Integer.parseInt(serviceID[0]),
                        serviceDesc[1],
                        Double.parseDouble(cost[2])));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getServicesFromFile() Error!");
            ex.printStackTrace();
        }
    }
    
    public void getLogsFromFile() {
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("resources/logs.csv"));

            //for all lines in file
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] logID = s.split(",");
                String[] userID = s.split(",");
                String[] fname = s.split(",");
                String[] lname = s.split(",");
                String[] date = s.split(",");
                String[] item = s.split(",");

                LogQueries logQueries = new LogQueries();

                logQueries.insertLog(new Log(
                        Integer.parseInt(logID[0]),
                        Integer.parseInt(userID[1]),
                        fname[2],
                        lname[3],
                        DateUtil.parse(date[4]),
                        item[5]));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getLogsFromFile() Error!");
            ex.printStackTrace();
        }
    }

}
