package assignment.database;

//<editor-fold defaultstate="collapsed" desc="Imports">
//OLD ASSIGNMENT
import assignment.model.Employee;
import assignment.model.Log;

//TEMPLE
import assignment.model.Member;
import assignment.model.Volunteers;
import assignment.model.Donation;
import assignment.model.Accommodation;
import assignment.model.PaiWei;

import assignment.util.DateUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
//</editor-fold>

public class DatabaseSetup extends DatabaseQuery {

    //For parsing date strings from CSV into local date
    String pattern = "d/MM/yyyy";
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

    //OLD ASSIGNMENT
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

    //TEMPLE
    PreparedStatement createMemberTable = null;
    PreparedStatement createVolunteersTable = null;
    PreparedStatement createDonationTable = null;
    PreparedStatement createAccommodationTable = null;
    PreparedStatement createPaiWeiTable = null;
    PreparedStatement createEventTable = null;
    ResultSet rs = null;

    public static void setupDatabase() {
        DatabaseSetup dbs = new DatabaseSetup();
        dbs.databaseSetup();
    }

    public void databaseSetup() {
        openConnection();

        createEmployeeTable();
        createLogTable();
        createMemberTable();
        createVolunteersTable();
        createDonationTable();
        createAccommodationTable();
        createPaiWeiTable();

        closeConnection();
    }

    public String generateLatestID(int numID) {
        String ID = "";

        //TODO...
        return ID;
    }

    //************************************************
    //************************************************
    //TEMPLE (creating tables)
    //<editor-fold defaultstate="collapsed" desc="creating tables">
    //<editor-fold defaultstate="collapsed" desc="createMemberTable">
    private void createMemberTable() {
        try {

            // Determine if the MEMBER table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "MEMBER", null);

            if (!rs.next()) {
                // If the MEMBER table does not already exist we create it
                createMemberTable = conn.prepareStatement(
                        "CREATE TABLE APP.MEMBER ("
                        + "\"MEM_ID\" INT not null primary key "
                        + "GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "\"M_ID\" VARCHAR(35), " //The beautified string version of MEM_ID
                        + "\"C_NAME\" VARCHAR(100), "
                        + "\"F_NAME\" VARCHAR(50), "
                        + "\"L_NAME\" VARCHAR(50), "
                        + "\"GENDER\" VARCHAR(6), "
                        + "\"DOB\" VARCHAR(4), "
                        + "\"H_PHONE\" VARCHAR(40), "
                        + "\"MOB\" VARCHAR(40), "
                        + "\"W_PHONE\" VARCHAR(40), "
                        + "\"FAX\" VARCHAR(40), "
                        + "\"EMAIL\" VARCHAR(100), "
                        + "\"WECHAT\" VARCHAR(100), "
                        + "\"PREF_CONTACT\" VARCHAR(200), "
                        + "\"LANG1\" VARCHAR(100), "
                        + "\"LANG2\" VARCHAR(100), "
                        + "\"EDU_EMP\" VARCHAR(100), "
                        + "\"NATION\" VARCHAR(100), "
                        + "\"ADDRESS\" VARCHAR(100), "
                        + "\"SUBURB\" VARCHAR(100), "
                        + "\"POSTCODE\" VARCHAR(20), "
                        + "\"STATE\" VARCHAR(40), "
                        + "\"DHARMA\" VARCHAR(70), "
                        + "\"DATE_REFUGE\" DATE, "
                        + "\"NOTE\" VARCHAR(1500), "
                        + "\"CREATE_DATE\" DATE, "
                        + "\"UPDATE_DATE\" DATE, "
                        + "\"CREATE_BY\" VARCHAR(50), "
                        + "\"UPDATE_BY\" VARCHAR(50))");
                createMemberTable.execute();
                getMembersFromFile();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table MEMBER error!");
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createVolunteersTable">
    private void createVolunteersTable() {
        try {

            // Determine if the VOLUNTEERS table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "VOLUNTEERS", null);

            if (!rs.next()) {
                // If the VOLUNTEERS table does not already exist we create it
                createVolunteersTable = conn.prepareStatement(
                        "CREATE TABLE APP.VOLUNTEERS ("
                        + "\"MEM_ID\" INT, "
                        + "\"DAYS\" DATE, "
                        + "\"TIME\" DATE, "
                        + "\"OTHER\" VARCHAR(100), "
                        + "\"GROUP_NAME\" VARCHAR(100), "
                        + "FOREIGN KEY (MEM_ID) REFERENCES MEMBER(MEM_ID))");
                createVolunteersTable.execute();
                getVolunteersFromFile();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table VOLUNTEERS error!");
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createDonationTable">
    private void createDonationTable() {
        try {

            // Determine if the DONATION table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "DONATION", null);

            if (!rs.next()) {
                // If the DONATION table does not already exist we create it
                createDonationTable = conn.prepareStatement(
                        "CREATE TABLE APP.DONATION ("
                        + "\"DON_ID\" INT not null primary key "
                        + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "\"MEM_ID\" INT, "
                        + "\"DON_TYPE\" VARCHAR(35), "
                        + "\"DHARMA_SERVICE\" VARCHAR(35), "
                        + "\"DATE\" DATE, "
                        + "\"PAY_METHOD\" VARCHAR(100), "
                        + "\"AMT_PAID\" DOUBLE, "
                        + "\"TOTAL_DON\" DOUBLE, "
                        + "\"PAY_DATE\" DATE, "
                        + "\"BAL\" DOUBLE, "
                        + "\"NOTE\" VARCHAR(1500), "
                        + "\"CREATE_BY\" VARCHAR(100), "
                        + "\"CREATE_DATE\" DATE, "
                        + "\"UPDATE_BY\" VARCHAR(100), "
                        + "\"UPDATE_DATE\" DATE, "
                        + "FOREIGN KEY (MEM_ID) REFERENCES MEMBER(MEM_ID))");
                createDonationTable.execute();
                getDonationsFromFile();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table DONATION error!");
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createAccommodationTable">
    private void createAccommodationTable() {
        try {
            // Determine if the ACCOMMODATION table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "ACCOMMODATION", null);

            if (!rs.next()) {
                // If the ACCOMMODATION table does not already exist we create it
                createAccommodationTable = conn.prepareStatement(
                        "CREATE TABLE APP.ACCOMMODATION ("
                        + "\"A_ID\" INT not null primary key "
                        + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "\"MEM_ID\" INT, "
                        + "\"CHECK_IN_DATE\" DATE, "
                        + "\"CHECK_OUT_DATE\" DATE, "
                        + "\"NOTE\" VARCHAR(1500), "
                        + "\"ROOM\" VARCHAR(35), "
                        + "\"GENDER\" VARCHAR(100), "
                        + "\"VERIFICATION_TYPE\" VARCHAR(100), "
                        + "\"VERIFICATION_ID\" VARCHAR(100), "
                        + "\"REASON\" VARCHAR(100), "
                        + "\"EMG_PERSON\" VARCHAR(100), "
                        + "\"EMG_NO\" VARCHAR(100), "
                        + "\"RELATIONSHIP\" VARCHAR(100), "
                        + "FOREIGN KEY (MEM_ID) REFERENCES MEMBER(MEM_ID))");
                createAccommodationTable.execute();
                getAccommodationsFromFile();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table ACCOMMODATION error!");
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createPaiWeiTable">
    private void createPaiWeiTable() {
        try {

            // Determine if the PAIWEI table already exists or not
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "PAIWEI", null);

            if (!rs.next()) {
                // If the PAIWEI table does not already exist we create it
                createPaiWeiTable = conn.prepareStatement(
                        "CREATE TABLE APP.PAIWEI ("
                        + "\"PAI_WEI_ID\" INT not null primary key "
                        + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "\"PW_ID\" VARCHAR(35), " //Generated from events selection choices. Visible to user
                        + "\"MEM_ID\" INT, " //FK
                        + "\"TYPE\" VARCHAR(35), "
                        + "\"SIZE\" VARCHAR(35), "
                        + "\"DHARMA_SERVICE\" VARCHAR(35), "
                        + "\"SUB_EVENT_TYPE\" VARCHAR(35), "
                        + "\"DATE\" DATE, "
                        + "\"PAY_METHOD\" VARCHAR(100), "
                        + "\"AMT_PAID\" DOUBLE, "
                        + "\"TOTAL_DON\" DOUBLE, "
                        + "\"PAY_DATE\" DATE, "
                        + "\"BAL\" DOUBLE, "
                        + "\"NOTE\" VARCHAR(1500), "
                        + "\"CREATE_BY\" VARCHAR(100), "
                        + "\"CREATE_DATE\" DATE, "
                        + "\"UPDATE_BY\" VARCHAR(100), "
                        + "\"UPDATE_DATE\" DATE, "
                        + "\"MIS1\" VARCHAR(35), "
                        + "\"MIS2\" VARCHAR(35), "
                        + "\"MIS3\" VARCHAR(35), "
                        + "\"MIS4\" VARCHAR(35), "
                        + "\"MIS5\" VARCHAR(35), "
                        + "\"MIS6\" VARCHAR(35), "
                        + "\"MIS7\" VARCHAR(35), "
                        + "\"MIS8\" VARCHAR(35), "
                        + "\"MIS9\" VARCHAR(35), "
                        + "\"MIS10\" VARCHAR(35), " //
                        + "\"YANG1\" VARCHAR(35), "
                        + "\"YANG2\" VARCHAR(35), "
                        + "\"YANG3\" VARCHAR(35), "
                        + "\"YANG4\" VARCHAR(35), "
                        + "\"YANG5\" VARCHAR(35), "
                        + "\"YANG6\" VARCHAR(35), "
                        + "\"YANG7\" VARCHAR(35), "
                        + "\"YANG8\" VARCHAR(35), "
                        + "\"YANG9\" VARCHAR(35), "
                        + "\"YANG10\" VARCHAR(35), " //
                        + "\"DREN_TYPE\" VARCHAR(35), "
                        + "\"CLAN1\" VARCHAR(35), "
                        + "\"CLAN2\" VARCHAR(35), "
                        + "\"CLAN3\" VARCHAR(35), "
                        + "\"DECEASED1\" VARCHAR(35), "
                        + "\"DECEASED2\" VARCHAR(35), "
                        + "\"DECEASED3\" VARCHAR(35), "
                        + "\"DECEASED4\" VARCHAR(35), "
                        + "\"DECEASED5\" VARCHAR(35), "
                        + "\"DECEASED6\" VARCHAR(35), "
                        + "\"DECEASED7\" VARCHAR(35), "
                        + "\"DECEASED8\" VARCHAR(35), "
                        + "\"DECEASED9\" VARCHAR(35), "
                        + "\"DECEASED10\" VARCHAR(35), " //
                        + "\"OLD_OWNERS1\" VARCHAR(35), "
                        + "\"OLD_OWNERS2\" VARCHAR(35), "
                        + "FOREIGN KEY (MEM_ID) REFERENCES MEMBER(MEM_ID))");
                createPaiWeiTable.execute();
                getPaiWeisFromFile();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table PAIWEI error!");
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createEmployeeTable">
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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="createLogTable">
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
                        //+ "\"DATEMOD\" DATE, "
                        + "\"DATEMOD\" VARCHAR(100), "
                        + "\"ITEMMODIFIED\" VARCHAR(200))");
                createLogTable.execute();
                getLogsFromFile();
            }
        } catch (SQLException ex) {
            System.out.println("databaseSetup() for Table LOG error!");
            ex.printStackTrace();
        }
    }
//</editor-fold>
//</editor-fold>

    //*****************************************************************************************************************
    //TEMPLE (getting from files aka csv)
    //<editor-fold defaultstate="collapsed" desc="Getting from files">
    //<editor-fold defaultstate="collapsed" desc="getMembersFromFile">
    public void getMembersFromFile() {
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("./resources/members.csv"));

            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] memID = s.split(",");
                String[] mID = s.split(",");
                String[] cName = s.split(",");
                String[] fName = s.split(",");
                String[] lName = s.split(",");
                String[] gender = s.split(",");
                String[] dob = s.split(",");
                String[] hPhone = s.split(",");
                String[] mob = s.split(",");
                String[] wPhone = s.split(",");
                String[] fax = s.split(",");
                String[] email = s.split(",");
                String[] wechat = s.split(",");
                String[] prefContact = s.split(",");
                String[] lang1 = s.split(",");
                String[] lang2 = s.split(",");
                String[] eduEmp = s.split(",");
                String[] nation = s.split(",");
                String[] address = s.split(",");
                String[] suburb = s.split(",");
                String[] postcode = s.split(",");
                String[] state = s.split(",");
                String[] dharma = s.split(",");
                String[] dateRefuge = s.split(",");
                String[] note = s.split(",");
                String[] createDate = s.split(",");
                String[] updateOn = s.split(",");
                String[] createBy = s.split(",");
                String[] updateBy = s.split(",");

                //Parse dates before inserting
                LocalDate dateRefuge2, createDate2, updateOn2;
                if ((dateRefuge2 = DateUtil.parse(dateRefuge[23])) == null) {
                    dateRefuge2 = LocalDate.parse(dateRefuge[23], dateFormatter);
                }
                if ((createDate2 = DateUtil.parse(createDate[25])) == null) {
                    createDate2 = LocalDate.parse(createDate[25], dateFormatter);
                }
                if ((updateOn2 = DateUtil.parse(updateOn[26])) == null) {
                    updateOn2 = LocalDate.parse(updateOn[26], dateFormatter);
                }

                MemberQueries memberQueries = new MemberQueries();
                memberQueries.insertMember(new Member(
                        Integer.parseInt(memID[0]),
                        mID[1],
                        cName[2],
                        fName[3],
                        lName[4],
                        gender[5],
                        dob[6],
                        hPhone[7],
                        mob[8],
                        wPhone[9],
                        fax[10],
                        email[11],
                        wechat[12],
                        prefContact[13],
                        lang1[14],
                        lang2[15],
                        eduEmp[16],
                        nation[17],
                        address[18],
                        suburb[19],
                        postcode[20],
                        state[21],
                        dharma[22],
                        dateRefuge2,
                        note[24],
                        createDate2,
                        updateOn2,
                        createBy[27],
                        updateBy[28]));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getMembersFromFile() Error!");
            ex.printStackTrace();
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getVolunteersFromFile">
    public void getVolunteersFromFile() {
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("./resources/volunteers.csv"));

            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] memID = s.split(",");
                String[] days = s.split(",");
                String[] time = s.split(",");
                String[] other = s.split(",");
                String[] group = s.split(",");

                //Parse dates before inserting
                LocalDate days2, time2;
                if ((days2 = DateUtil.parse(days[1])) == null) {
                    days2 = LocalDate.parse(days[1], dateFormatter);
                }
                if ((time2 = DateUtil.parse(time[2])) == null) {
                    time2 = LocalDate.parse(time[2], dateFormatter);
                }

                VolunteersQueries volunteersQueries = new VolunteersQueries();
                volunteersQueries.insertVolunteers(new Volunteers(
                        Integer.parseInt(memID[0]),
                        days2,
                        time2,
                        other[3],
                        group[4]));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getVolunteersFromFile() Error!");
            ex.printStackTrace();
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getDonationsFromFile">
    public void getDonationsFromFile() {
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("./resources/donations.csv"));

            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] memID = s.split(",");
                String[] donID = s.split(",");
                String[] donType = s.split(",");
                String[] contributor = s.split(",");
                String[] dharmaService = s.split(",");
                String[] date = s.split(",");
                String[] payMethod = s.split(",");
                String[] amtPaid = s.split(",");
                String[] totalDon = s.split(",");
                String[] payDate = s.split(",");
                String[] bal = s.split(",");
                String[] note = s.split(",");
                String[] createDate = s.split(",");
                String[] updateOn = s.split(",");
                String[] createBy = s.split(",");
                String[] updateBy = s.split(",");

                //Parse dates before inserting
                LocalDate date2, payDate2, createDate2, updateOn2;
                if ((date2 = DateUtil.parse(date[4])) == null) {
                    date2 = LocalDate.parse(date[4], dateFormatter);
                }
                if ((payDate2 = DateUtil.parse(payDate[8])) == null) {
                    payDate2 = LocalDate.parse(payDate[8], dateFormatter);
                }
                if ((createDate2 = DateUtil.parse(createDate[11])) == null) {
                    createDate2 = LocalDate.parse(createDate[11], dateFormatter);
                }
                if ((updateOn2 = DateUtil.parse(updateOn[12])) == null) {
                    updateOn2 = LocalDate.parse(updateOn[12], dateFormatter);
                }

                DonationQueries donationsQueries = new DonationQueries();
                donationsQueries.insertDonations(new Donation(
                        Integer.parseInt(memID[0]),
                        Integer.parseInt(donID[1]),
                        donType[2],
                        dharmaService[3],
                        date2,
                        payMethod[5],
                        Double.parseDouble(amtPaid[6]),
                        Double.parseDouble(totalDon[7]),
                        payDate2,
                        Double.parseDouble(bal[9]),
                        note[10],
                        createDate2,
                        updateOn2,
                        createBy[13],
                        updateBy[14]));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getDonationsFromFile() Error!");
            ex.printStackTrace();
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getAccommodationsFromFile">
    public void getAccommodationsFromFile() {
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("./resources/accommodations.csv"));

            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] aID = s.split(",");
                String[] memID = s.split(",");
                String[] checkInDate = s.split(",");
                String[] checkOutDate = s.split(",");
                String[] note = s.split(",");
                String[] room = s.split(",");
                String[] gender = s.split(",");
                String[] typeOfID = s.split(",");
                String[] IDNum = s.split(",");
                String[] reason = s.split(",");
                String[] emgContactPerson = s.split(",");
                String[] emgContactNum = s.split(",");
                String[] relationship = s.split(",");

                //Parse dates before inserting
                LocalDate checkInDate2, checkOutDate2;
                if ((checkInDate2 = DateUtil.parse(checkInDate[2])) == null) {
                    checkInDate2 = LocalDate.parse(checkInDate[2], dateFormatter);
                }
                if ((checkOutDate2 = DateUtil.parse(checkOutDate[3])) == null) {
                    checkOutDate2 = LocalDate.parse(checkOutDate[3], dateFormatter);
                }

                AccommodationQueries accommodationQueries = new AccommodationQueries();
                accommodationQueries.insertAccommodation(new Accommodation(
                        Integer.parseInt(aID[0]),
                        Integer.parseInt(memID[1]),
                        checkInDate2,
                        checkOutDate2,
                        note[4],
                        room[5],
                        gender[6],
                        typeOfID[7],
                        IDNum[8],
                        reason[9],
                        emgContactPerson[10],
                        emgContactNum[11],
                        relationship[12]));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getAccommodationsFromFile() Error!");
            ex.printStackTrace();
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getPaiWeisFromFile">
    public void getPaiWeisFromFile() {
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("./resources/paiweis.csv"));

            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] paiWeiID = s.split(",");
                String[] memID = s.split(",");
                String[] type = s.split(",");
                String[] size = s.split(",");
                String[] dharmaService = s.split(",");
                String[] subEventType = s.split(",");
                String[] date = s.split(",");
                String[] payMethod = s.split(",");
                String[] amtPaid = s.split(",");
                String[] totalDon = s.split(",");
                String[] payDate = s.split(",");
                String[] bal = s.split(",");
                String[] note = s.split(",");
                String[] createdBy = s.split(",");
                String[] createdOn = s.split(",");
                String[] updateBy = s.split(",");
                String[] updateOn = s.split(",");
                String[] mis1 = s.split(",");
                String[] mis2 = s.split(",");
                String[] mis3 = s.split(",");
                String[] mis4 = s.split(",");
                String[] mis5 = s.split(",");
                String[] mis6 = s.split(",");
                String[] mis7 = s.split(",");
                String[] mis8 = s.split(",");
                String[] mis9 = s.split(",");
                String[] mis10 = s.split(",");   //10 mis
                String[] yang1 = s.split(",");
                String[] yang2 = s.split(",");
                String[] yang3 = s.split(",");
                String[] yang4 = s.split(",");
                String[] yang5 = s.split(",");
                String[] yang6 = s.split(",");
                String[] yang7 = s.split(",");
                String[] yang8 = s.split(",");
                String[] yang9 = s.split(",");
                String[] yang10 = s.split(",");   //10 yang
                String[] drenType = s.split(",");   //dren
                String[] clan1 = s.split(",");
                String[] clan2 = s.split(",");
                String[] clan3 = s.split(",");   //clan
                String[] deceased1 = s.split(",");
                String[] deceased2 = s.split(",");
                String[] deceased3 = s.split(",");
                String[] deceased4 = s.split(",");
                String[] deceased5 = s.split(",");
                String[] deceased6 = s.split(",");
                String[] deceased7 = s.split(",");
                String[] deceased8 = s.split(",");
                String[] deceased9 = s.split(",");
                String[] deceased10 = s.split(",");   //10 deceased
                String[] oldOwners1 = s.split(",");
                String[] oldOwners2 = s.split(",");   //oldOwners

                //Parse dates before inserting
                LocalDate date2, payDate2, createdOn2, updateOn2;
                if ((date2 = DateUtil.parse(date[7])) == null) {
                    date2 = LocalDate.parse(date[7], dateFormatter);
                }
                if ((payDate2 = DateUtil.parse(payDate[11])) == null) {
                    payDate2 = LocalDate.parse(payDate[11], dateFormatter);
                }
                if ((createdOn2 = DateUtil.parse(createdOn[15])) == null) {
                    createdOn2 = LocalDate.parse(createdOn[15], dateFormatter);
                }
                if ((updateOn2 = DateUtil.parse(updateOn[17])) == null) {
                    updateOn2 = LocalDate.parse(updateOn[17], dateFormatter);
                }

                PaiWeiQueries paiWeisQueries = new PaiWeiQueries();
                paiWeisQueries.insertPaiWeis(new PaiWei(
                        Integer.parseInt(paiWeiID[0]),
                        size[1],
                        Integer.parseInt(memID[2]),
                        type[3],
                        size[4],
                        dharmaService[5],
                        dharmaService[6],
                        date2,
                        payMethod[8],
                        Double.parseDouble(amtPaid[9]),
                        Double.parseDouble(totalDon[10]),
                        payDate2,
                        Double.parseDouble(bal[12]),
                        note[13],
                        createdBy[14],
                        createdOn2,
                        updateBy[16],
                        updateOn2,
                        mis1[18],
                        mis2[19],
                        mis3[20],
                        mis4[21],
                        mis5[22],
                        mis6[23],
                        mis7[24],
                        mis8[25],
                        mis9[26],
                        mis10[27], //10    mis
                        yang1[28],
                        yang2[29],
                        yang3[30],
                        yang4[31],
                        yang5[32],
                        yang6[33],
                        yang7[34],
                        yang8[35],
                        yang9[36],
                        yang10[37], //10    yang
                        drenType[38], //      dren
                        clan1[39],
                        clan2[40],
                        clan3[41], //  clan 3
                        deceased1[42],
                        deceased2[43],
                        deceased3[44],
                        deceased4[45],
                        deceased5[46],
                        deceased6[47],
                        deceased7[48],
                        deceased8[49],
                        deceased9[50],
                        deceased10[51], //10    deceased
                        oldOwners1[52],
                        oldOwners2[53]));   // old owners
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getPaiWeisFromFile() Error!");
            ex.printStackTrace();
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getEmployeesFromFile">
    public void getEmployeesFromFile() {
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("./resources/employees.csv"));

            //InputStream in = getClass().getResourceAsStream("/resources/filename.csv");
            //BufferedReader input = new BufferedReader(new InputStreamReader(in));
            //Scanner scanner = new Scanner(new File(input));
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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getLogsFromFile">
    public void getLogsFromFile() {
        try {
            // Open the file
            Scanner scanner = new Scanner(new File("./resources/logs.csv"));

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

                logQueries.insertLogFromFile(new Log(
                        Integer.parseInt(logID[0]),
                        Integer.parseInt(userID[1]),
                        fname[2],
                        lname[3],
                        date[4],
                        item[5]));
            }

            // Close the file
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println("getLogsFromFile() Error!");
            ex.printStackTrace();
        }
    }
//</editor-fold>
//</editor-fold>
}
