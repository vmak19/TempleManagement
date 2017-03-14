package assignment.database;

import assignment.model.Volunteers;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class VolunteersQueries extends DatabaseQuery {

    PreparedStatement insertVolunteers = null;
    PreparedStatement getAllVolunteers = null;
    PreparedStatement editVolunteers = null;
    PreparedStatement deleteVolunteers = null;
    PreparedStatement importVolunteers = null;

    ResultSet rs = null;
    List<Volunteers> volunteers;

    public List<Volunteers> getVolunteers() {
        volunteers = new ArrayList<Volunteers>();
        openConnection();
        try {
            getAllVolunteers = conn.prepareStatement("select * from app.VOLUNTEERS");
            rs = getAllVolunteers.executeQuery();
            while (rs.next()) {
                volunteers.add(
                        new Volunteers(rs.getInt("MEM_ID"), rs.getDate("DAYS").toLocalDate(),
                                rs.getDate("TIME").toLocalDate(), rs.getString("OTHER"),
                                rs.getString("GROUP_NAME")));
            }
            rs.close();
            getAllVolunteers.close();
        } catch (SQLException ex) {
            System.out.println("getVolunteers() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return volunteers;
    }

    public Volunteers getVolunteer(int memID) {
        Volunteers v = new Volunteers();
        int i = 0;
        openConnection();
        try {
            getAllVolunteers = conn.prepareStatement("select * from app.VOLUNTEERS "
                    + "where MEM_ID=?");
            getAllVolunteers.setInt(1, memID);
            rs = getAllVolunteers.executeQuery();
            while (rs.next()) {
                i++;
                v = new Volunteers(rs.getInt("MEM_ID"), rs.getDate("DAYS").toLocalDate(),
                        rs.getDate("TIME").toLocalDate(), rs.getString("OTHER"),
                        rs.getString("GROUP_NAME"));
            }
            rs.close();
            getAllVolunteers.close();
        } catch (SQLException ex) {
            System.out.println("getVolunteers() error!");
            ex.printStackTrace();
        }
        closeConnection();
        if (i == 0) { //no result return in rs, aka no records of volunteer for this memID
            return null;
        } else {
            return v;
        }
    }

    public int insertVolunteers(Volunteers toInsert) {
        int userGeneratedId = -1;
        openConnection();
        try {/*
            insertVolunteers = conn.prepareStatement("insert into app.volunteers (DAYS, TIME, OTHER, GROUP_NAME)"
                    + "values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertVolunteers.setDate(1, toInsert.getAvailableDaysToDate());
            insertVolunteers.setDate(2, toInsert.getAvailableTimeToDate());
            insertVolunteers.setString(3, toInsert.getOther());
            insertVolunteers.setString(4, toInsert.getGroup());*/

            insertVolunteers = conn.prepareStatement("insert into app.volunteers "
                    + "(MEM_ID, DAYS, TIME, OTHER, GROUP_NAME) "
                    + "values (?, ?, ?, ?, ?)");
            insertVolunteers.setInt(1, toInsert.getMemID());
            insertVolunteers.setDate(2, toInsert.getAvailableDaysToDate());
            insertVolunteers.setDate(3, toInsert.getAvailableTimeToDate());
            insertVolunteers.setString(4, toInsert.getOther());
            insertVolunteers.setString(5, toInsert.getGroup());

            insertVolunteers.executeUpdate();
            insertVolunteers.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! insertVolunteers() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
        return userGeneratedId;
    }

    public void updateVolunteers(Volunteers toInsert) {
        //int userGeneratedId = toInsert.getUserID();
        openConnection();
        try {
            editVolunteers = conn.prepareStatement("update app.volunteers set DAYS=?, TIME=?, OTHER=?, GROUP_NAME=? "
                    + "where MEM_ID=?");
            editVolunteers.setDate(1, toInsert.getAvailableDaysToDate());
            editVolunteers.setDate(2, toInsert.getAvailableTimeToDate());
            editVolunteers.setString(3, toInsert.getOther());
            editVolunteers.setString(4, toInsert.getGroup());
            editVolunteers.setInt(5, toInsert.getMemID());
            editVolunteers.executeUpdate();
            editVolunteers.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! editVolunteers() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
    }

    public void deleteVolunteers(Volunteers toDelete) {
        openConnection();
        try {
            deleteVolunteers = conn.prepareStatement("delete from app.VOLUNTEERS where MEM_ID = ?");
            deleteVolunteers.setInt(1, toDelete.getMemID());
            deleteVolunteers.execute();
        } catch (SQLException ex) {
            System.out.println("ERROR! deleteVolunteers()!");
            ex.printStackTrace();
        }
        closeConnection();
    }

    public List<String> getBlueRows(String file) throws FileNotFoundException, IOException {
        //Open excel file
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //grab blue row (the one that has the column names e.g. volunteersID, chinese name, first name...)
        row = sheet.getRow(0);
        //Put data into arrayList
        List<String> list = new ArrayList<String>();
        int colCounts = row.getLastCellNum();
        DataFormatter df = new DataFormatter();
        for (int j = 0; j < colCounts; j++) {
            list.add(df.formatCellValue(row.getCell(j)));
        }
        return list;
    }

    private List<Integer> newVolunteersIndexList = new ArrayList<>();

    public List<Integer> getNewVolunteersIndexList() {
        return newVolunteersIndexList;
    }

    public List<Integer> getVolunteersID(String file) throws FileNotFoundException, IOException {
        //Open excel file
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //Put data into arrayList
        List<Integer> volunteersIDlist = new ArrayList<>();
        int rowCount = sheet.getPhysicalNumberOfRows();
        DataFormatter df = new DataFormatter();
        for (int i = 1; i < rowCount; i++) {
            row = sheet.getRow(i);
            //Store volunteersID into arraylist
            if (row.getCell(0) != null) { //OR contains a number
                volunteersIDlist.add(Integer.parseInt(df.formatCellValue(row.getCell(0))));
            } else { //else has no volunteersID, toInsertNewVolunteers() later
                newVolunteersIndexList.add(i); //Store excel row index for accessing later
            }
        }

        return volunteersIDlist;
    }

    //Imports each row of excel file, no restrictions
    public int importVolunteers(String file, ArrayList<String> results) throws FileNotFoundException, IOException {
        openConnection();
        //Open excel file
        //FileInputStream fileIn = new FileInputStream(new File("VolunteersImport.xlsx"));
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            importVolunteers = conn.prepareStatement("insert into app.volunteers "
                    + "(C_NAME, F_NAME, L_NAME, GENDER, DOB, H_PHONE, MOB, W_PHONE, "
                    + "FAX, EMAIL, WECHAT, PREF_CONTACT, LANG, SKILL, EDU, EMPLOYMENT, "
                    + "NATION, ADDRESS, SUBURB, POSTCODE, STATE, DHARMA, DATE_REFUGE, "
                    + "NOTE, CREATE_DATE, UPDATE_DATE, CREATE_BY, UPDATE_BY)"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            //Put into drop down list ######==>calls initialise method in DbLinkDialogController.java
            //Start reading data from excel, row by row
            java.sql.Date sqlDate;
            java.sql.Date sqlDate2 = java.sql.Date.valueOf("9999-01-01");
            DataFormatter df = new DataFormatter();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);

                if (results.contains("Chinese Name")) {
                    importVolunteers.setString(1, df.formatCellValue(row.getCell(1)));
                } else {
                    importVolunteers.setString(1, "");
                }
                if (results.contains("First Name")) {
                    importVolunteers.setString(2, df.formatCellValue(row.getCell(2)));
                } else {
                    importVolunteers.setString(2, "");
                }
                if (results.contains("Last Name")) {
                    importVolunteers.setString(3, df.formatCellValue(row.getCell(3)));
                } else {
                    importVolunteers.setString(3, "");
                }
                if (results.contains("Gender")) {
                    importVolunteers.setString(4, df.formatCellValue(row.getCell(4)));
                } else {
                    importVolunteers.setString(4, "");
                }
                if (results.contains("Date of birth")) {
                    sqlDate = new java.sql.Date(row.getCell(5).getDateCellValue().getTime());
                    importVolunteers.setDate(5, sqlDate);
                } else {
                    importVolunteers.setDate(5, sqlDate2);
                }
                if (results.contains("Home Phone")) {
                    importVolunteers.setString(6, df.formatCellValue(row.getCell(6)));
                } else {
                    importVolunteers.setString(6, "");
                }
                if (results.contains("Mobile")) {
                    importVolunteers.setString(7, df.formatCellValue(row.getCell(7)));
                } else {
                    importVolunteers.setString(7, "");
                }
                if (results.contains("Work Phone")) {
                    importVolunteers.setString(8, df.formatCellValue(row.getCell(8)));
                } else {
                    importVolunteers.setString(8, "");
                }
                if (results.contains("Fax")) {
                    importVolunteers.setString(9, df.formatCellValue(row.getCell(9)));
                } else {
                    importVolunteers.setString(9, "");
                }
                if (results.contains("Email")) {
                    importVolunteers.setString(10, df.formatCellValue(row.getCell(10)));
                } else {
                    importVolunteers.setString(10, "");
                }
                if (results.contains("WeChat ID")) {
                    importVolunteers.setString(11, df.formatCellValue(row.getCell(11)));
                } else {
                    importVolunteers.setString(11, "");
                }
                if (results.contains("Preferred Contact Method")) {
                    importVolunteers.setString(12, df.formatCellValue(row.getCell(12)));
                } else {
                    importVolunteers.setString(12, "");
                }
                if (results.contains("Language")) {
                    importVolunteers.setString(13, df.formatCellValue(row.getCell(13)));
                } else {
                    importVolunteers.setString(13, "");
                }
                if (results.contains("Skills/Hobbies")) {
                    importVolunteers.setString(14, df.formatCellValue(row.getCell(14)));
                } else {
                    importVolunteers.setString(14, "");
                }
                if (results.contains("Education")) {
                    importVolunteers.setString(15, df.formatCellValue(row.getCell(15)));
                } else {
                    importVolunteers.setString(15, "");
                }
                if (results.contains("Employment")) {
                    importVolunteers.setString(16, df.formatCellValue(row.getCell(16)));
                } else {
                    importVolunteers.setString(16, "");
                }
                if (results.contains("Nationality")) {
                    importVolunteers.setString(17, df.formatCellValue(row.getCell(17)));
                } else {
                    importVolunteers.setString(17, "");
                }
                if (results.contains("Address")) {
                    importVolunteers.setString(18, df.formatCellValue(row.getCell(18)));
                } else {
                    importVolunteers.setString(18, "");
                }
                if (results.contains("Suburb")) {
                    importVolunteers.setString(19, df.formatCellValue(row.getCell(19)));
                } else {
                    importVolunteers.setString(19, "");
                }
                if (results.contains("Postcode")) {
                    importVolunteers.setString(20, df.formatCellValue(row.getCell(20)));
                } else {
                    importVolunteers.setString(20, "");
                }
                if (results.contains("State")) {
                    importVolunteers.setString(21, df.formatCellValue(row.getCell(21)));
                } else {
                    importVolunteers.setString(21, "");
                }
                if (results.contains("Dharma Name")) {
                    importVolunteers.setString(22, df.formatCellValue(row.getCell(22)));
                } else {
                    importVolunteers.setString(22, "");
                }
                if (results.contains("Date of Taking Refuge")) {
                    sqlDate = new java.sql.Date(row.getCell(23).getDateCellValue().getTime());
                    importVolunteers.setDate(23, sqlDate);
                } else {
                    importVolunteers.setDate(23, sqlDate2);
                }
                if (results.contains("Note")) {
                    importVolunteers.setString(24, df.formatCellValue(row.getCell(24)));
                } else {
                    importVolunteers.setString(24, "");
                }
                if (results.contains("Create Date")) {
                    sqlDate = new java.sql.Date(row.getCell(25).getDateCellValue().getTime());
                    importVolunteers.setDate(25, sqlDate);
                } else {
                    importVolunteers.setDate(25, sqlDate2); //SERT TO CURR DATE
                }
                if (results.contains("Update Date")) {
                    sqlDate = new java.sql.Date(row.getCell(26).getDateCellValue().getTime());
                    importVolunteers.setDate(26, sqlDate);
                } else {
                    importVolunteers.setDate(26, sqlDate2);
                }
                if (results.contains("Created By")) {
                    importVolunteers.setString(27, df.formatCellValue(row.getCell(27)));
                } else {
                    importVolunteers.setString(27, ""); //SET TO USER 
                }
                if (results.contains("Updated By")) {
                    importVolunteers.setString(28, df.formatCellValue(row.getCell(28)));
                } else {
                    importVolunteers.setString(28, "");
                }

                importVolunteers.execute();
            }

            wb.close();
            fileIn.close();
            importVolunteers.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! importVolunteers() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in VolunteersQueries.java/importVolunteers from excel... Most likely from extra null rows in excel");
            return 2;
        }
        closeConnection();
        return 1;
    }

    //Imports only rows that contains deleted volunteersID from db OR rows without volunteersID ===> assigns these rows with new volunteersID
    public int importNewVolunteers(List<Integer> newVolunteersList, List<Integer> newVolunteersIndexList, String file, ArrayList<String> results) throws FileNotFoundException, IOException {
        openConnection();
        //Open excel file
        //FileInputStream fileIn = new FileInputStream(new File("VolunteersImport.xlsx"));
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            importVolunteers = conn.prepareStatement("insert into app.volunteers "
                    + "(C_NAME, F_NAME, L_NAME, GENDER, DOB, H_PHONE, MOB, W_PHONE, "
                    + "FAX, EMAIL, WECHAT, PREF_CONTACT, LANG, SKILL, EDU, EMPLOYMENT, "
                    + "NATION, ADDRESS, SUBURB, POSTCODE, STATE, DHARMA, DATE_REFUGE, "
                    + "NOTE, CREATE_DATE, UPDATE_DATE, CREATE_BY, UPDATE_BY)"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            //Put into drop down list ######==>calls initialise method in DbLinkDialogController.java
            //Start reading data from excel, row by row
            java.sql.Date sqlDate;
            java.sql.Date sqlDate2 = java.sql.Date.valueOf("9999-01-01");
            DataFormatter df = new DataFormatter();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                //For each row...
                row = sheet.getRow(i);
                //**************** See if curr row is relevant ***************** 
                boolean flag, flag2;
                flag = flag2 = false;
                int id = 0;
                //if it is the row index we want... then process row             
                for (int n : newVolunteersIndexList) {
                    if (i == n) {
                        flag = true;
                        break;
                    }
                }

                //OR if it is the volunteersID we want... then process row
                if (row.getCell(0) != null && !flag) {
                    id = Integer.parseInt(df.formatCellValue(row.getCell(0)));

                    for (int n : newVolunteersList) {
                        if (id == n) {
                            flag2 = true;
                            break;
                        }
                    }
                }
                //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                if (flag || flag2) {
                    if (results.contains("Chinese Name")) {
                        importVolunteers.setString(1, df.formatCellValue(row.getCell(1)));
                    } else {
                        importVolunteers.setString(1, "");
                    }
                    if (results.contains("First Name")) {
                        importVolunteers.setString(2, df.formatCellValue(row.getCell(2)));
                    } else {
                        importVolunteers.setString(2, "");
                    }
                    if (results.contains("Last Name")) {
                        importVolunteers.setString(3, df.formatCellValue(row.getCell(3)));
                    } else {
                        importVolunteers.setString(3, "");
                    }
                    if (results.contains("Gender")) {
                        importVolunteers.setString(4, df.formatCellValue(row.getCell(4)));
                    } else {
                        importVolunteers.setString(4, "");
                    }
                    if (results.contains("Date of birth")) {
                        sqlDate = new java.sql.Date(row.getCell(5).getDateCellValue().getTime());
                        importVolunteers.setDate(5, sqlDate);
                    } else {
                        importVolunteers.setDate(5, sqlDate2);
                    }
                    if (results.contains("Home Phone")) {
                        importVolunteers.setString(6, df.formatCellValue(row.getCell(6)));
                    } else {
                        importVolunteers.setString(6, "");
                    }
                    if (results.contains("Mobile")) {
                        importVolunteers.setString(7, df.formatCellValue(row.getCell(7)));
                    } else {
                        importVolunteers.setString(7, "");
                    }
                    if (results.contains("Work Phone")) {
                        importVolunteers.setString(8, df.formatCellValue(row.getCell(8)));
                    } else {
                        importVolunteers.setString(8, "");
                    }
                    if (results.contains("Fax")) {
                        importVolunteers.setString(9, df.formatCellValue(row.getCell(9)));
                    } else {
                        importVolunteers.setString(9, "");
                    }
                    if (results.contains("Email")) {
                        importVolunteers.setString(10, df.formatCellValue(row.getCell(10)));
                    } else {
                        importVolunteers.setString(10, "");
                    }
                    if (results.contains("WeChat ID")) {
                        importVolunteers.setString(11, df.formatCellValue(row.getCell(11)));
                    } else {
                        importVolunteers.setString(11, "");
                    }
                    if (results.contains("Preferred Contact Method")) {
                        importVolunteers.setString(12, df.formatCellValue(row.getCell(12)));
                    } else {
                        importVolunteers.setString(12, "");
                    }
                    if (results.contains("Language")) {
                        importVolunteers.setString(13, df.formatCellValue(row.getCell(13)));
                    } else {
                        importVolunteers.setString(13, "");
                    }
                    if (results.contains("Skills/Hobbies")) {
                        importVolunteers.setString(14, df.formatCellValue(row.getCell(14)));
                    } else {
                        importVolunteers.setString(14, "");
                    }
                    if (results.contains("Education")) {
                        importVolunteers.setString(15, df.formatCellValue(row.getCell(15)));
                    } else {
                        importVolunteers.setString(15, "");
                    }
                    if (results.contains("Employment")) {
                        importVolunteers.setString(16, df.formatCellValue(row.getCell(16)));
                    } else {
                        importVolunteers.setString(16, "");
                    }
                    if (results.contains("Nationality")) {
                        importVolunteers.setString(17, df.formatCellValue(row.getCell(17)));
                    } else {
                        importVolunteers.setString(17, "");
                    }
                    if (results.contains("Address")) {
                        importVolunteers.setString(18, df.formatCellValue(row.getCell(18)));
                    } else {
                        importVolunteers.setString(18, "");
                    }
                    if (results.contains("Suburb")) {
                        importVolunteers.setString(19, df.formatCellValue(row.getCell(19)));
                    } else {
                        importVolunteers.setString(19, "");
                    }
                    if (results.contains("Postcode")) {
                        importVolunteers.setString(20, df.formatCellValue(row.getCell(20)));
                    } else {
                        importVolunteers.setString(20, "");
                    }
                    if (results.contains("State")) {
                        importVolunteers.setString(21, df.formatCellValue(row.getCell(21)));
                    } else {
                        importVolunteers.setString(21, "");
                    }
                    if (results.contains("Dharma Name")) {
                        importVolunteers.setString(22, df.formatCellValue(row.getCell(22)));
                    } else {
                        importVolunteers.setString(22, "");
                    }
                    if (results.contains("Date of Taking Refuge")) {
                        sqlDate = new java.sql.Date(row.getCell(23).getDateCellValue().getTime());
                        importVolunteers.setDate(23, sqlDate);
                    } else {
                        importVolunteers.setDate(23, sqlDate2);
                    }
                    if (results.contains("Note")) {
                        importVolunteers.setString(24, df.formatCellValue(row.getCell(24)));
                    } else {
                        importVolunteers.setString(24, "");
                    }
                    if (results.contains("Create Date")) {
                        sqlDate = new java.sql.Date(row.getCell(25).getDateCellValue().getTime());
                        importVolunteers.setDate(25, sqlDate);
                    } else {
                        importVolunteers.setDate(25, sqlDate2); //SERT TO CURR DATE
                    }
                    if (results.contains("Update Date")) {
                        sqlDate = new java.sql.Date(row.getCell(26).getDateCellValue().getTime());
                        importVolunteers.setDate(26, sqlDate);
                    } else {
                        importVolunteers.setDate(26, sqlDate2);
                    }
                    if (results.contains("Created By")) {
                        importVolunteers.setString(27, df.formatCellValue(row.getCell(27)));
                    } else {
                        importVolunteers.setString(27, ""); //SET TO USER 
                    }
                    if (results.contains("Updated By")) {
                        importVolunteers.setString(28, df.formatCellValue(row.getCell(28)));
                    } else {
                        importVolunteers.setString(28, "");
                    }

                    importVolunteers.execute();
                }
            }

            wb.close();
            fileIn.close();
            importVolunteers.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! importVolunteers() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in VolunteersQueries.java/importNewVolunteers from excel... Most likely from extra null rows in excel");
            return 2;
        }
        closeConnection();
        return 1;
    }

    //Updates existing volunteerss with volunteersID matching in excel
    public int importOldVolunteers(List<Integer> volunteersIDList, String file, ArrayList<String> results) throws FileNotFoundException, IOException {
        openConnection();
        //Open excel file
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            //'REPLACE INTO' Updates existing records and inserts new ones if pkID not found
            importVolunteers = conn.prepareStatement("update app.volunteers set C_NAME = ?, "
                    + "F_NAME = ?, L_NAME = ?, GENDER = ?, DOB = ?, H_PHONE = ?, "
                    + "MOB = ?, W_PHONE = ?, FAX = ?, EMAIL = ?, WECHAT = ?, "
                    + "PREF_CONTACT = ?, LANG = ?, SKILL = ?, EDU = ?, EMPLOYMENT = ?, "
                    + "NATION = ?, ADDRESS = ?, SUBURB = ?, POSTCODE = ?, "
                    + "STATE = ?, DHARMA = ?, DATE_REFUGE = ?, NOTE = ?, "
                    + "CREATE_DATE = ?, UPDATE_DATE = ?, CREATE_BY = ?, UPDATE_BY = ?"
                    + "where MEM_ID=?");
            //Start reading data from excel, row by row
            java.sql.Date sqlDate;
            java.sql.Date sqlDate2 = java.sql.Date.valueOf("9999-01-01");
            DataFormatter df = new DataFormatter();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                //For each row...
                row = sheet.getRow(i);
                //if volunteersID from cell, is the one we want...
                boolean flag = false;
                int id = 0;
                if (row.getCell(0) != null) {
                    id = Integer.parseInt(df.formatCellValue(row.getCell(0)));
                } else {
                    continue;
                }
                for (int n : volunteersIDList) {
                    if (id == n) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    if (results.contains("Chinese Name")) {
                        importVolunteers.setString(1, df.formatCellValue(row.getCell(1)));
                    } else {
                        importVolunteers.setString(1, "");
                    }
                    if (results.contains("First Name")) {
                        importVolunteers.setString(2, df.formatCellValue(row.getCell(2)));
                    } else {
                        importVolunteers.setString(2, "");
                    }
                    if (results.contains("Last Name")) {
                        importVolunteers.setString(3, df.formatCellValue(row.getCell(3)));
                    } else {
                        importVolunteers.setString(3, "");
                    }
                    if (results.contains("Gender")) {
                        importVolunteers.setString(4, df.formatCellValue(row.getCell(4)));
                    } else {
                        importVolunteers.setString(4, "");
                    }
                    if (results.contains("Date of birth")) {
                        sqlDate = new java.sql.Date(row.getCell(5).getDateCellValue().getTime());
                        importVolunteers.setDate(5, sqlDate);
                    } else {
                        importVolunteers.setDate(5, sqlDate2);
                    }
                    if (results.contains("Home Phone")) {
                        importVolunteers.setString(6, df.formatCellValue(row.getCell(6)));
                    } else {
                        importVolunteers.setString(6, "");
                    }
                    if (results.contains("Mobile")) {
                        importVolunteers.setString(7, df.formatCellValue(row.getCell(7)));
                    } else {
                        importVolunteers.setString(7, "");
                    }
                    if (results.contains("Work Phone")) {
                        importVolunteers.setString(8, df.formatCellValue(row.getCell(8)));
                    } else {
                        importVolunteers.setString(8, "");
                    }
                    if (results.contains("Fax")) {
                        importVolunteers.setString(9, df.formatCellValue(row.getCell(9)));
                    } else {
                        importVolunteers.setString(9, "");
                    }
                    if (results.contains("Email")) {
                        importVolunteers.setString(10, df.formatCellValue(row.getCell(10)));
                    } else {
                        importVolunteers.setString(10, "");
                    }
                    if (results.contains("WeChat ID")) {
                        importVolunteers.setString(11, df.formatCellValue(row.getCell(11)));
                    } else {
                        importVolunteers.setString(11, "");
                    }
                    if (results.contains("Preferred Contact Method")) {
                        importVolunteers.setString(12, df.formatCellValue(row.getCell(12)));
                    } else {
                        importVolunteers.setString(12, "");
                    }
                    if (results.contains("Language")) {
                        importVolunteers.setString(13, df.formatCellValue(row.getCell(13)));
                    } else {
                        importVolunteers.setString(13, "");
                    }
                    if (results.contains("Skills/Hobbies")) {
                        importVolunteers.setString(14, df.formatCellValue(row.getCell(14)));
                    } else {
                        importVolunteers.setString(14, "");
                    }
                    if (results.contains("Education")) {
                        importVolunteers.setString(15, df.formatCellValue(row.getCell(15)));
                    } else {
                        importVolunteers.setString(15, "");
                    }
                    if (results.contains("Employment")) {
                        importVolunteers.setString(16, df.formatCellValue(row.getCell(16)));
                    } else {
                        importVolunteers.setString(16, "");
                    }
                    if (results.contains("Nationality")) {
                        importVolunteers.setString(17, df.formatCellValue(row.getCell(17)));
                    } else {
                        importVolunteers.setString(17, "");
                    }
                    if (results.contains("Address")) {
                        importVolunteers.setString(18, df.formatCellValue(row.getCell(18)));
                    } else {
                        importVolunteers.setString(18, "");
                    }
                    if (results.contains("Suburb")) {
                        importVolunteers.setString(19, df.formatCellValue(row.getCell(19)));
                    } else {
                        importVolunteers.setString(19, "");
                    }
                    if (results.contains("Postcode")) {
                        importVolunteers.setString(20, df.formatCellValue(row.getCell(20)));
                    } else {
                        importVolunteers.setString(20, "");
                    }
                    if (results.contains("State")) {
                        importVolunteers.setString(21, df.formatCellValue(row.getCell(21)));
                    } else {
                        importVolunteers.setString(21, "");
                    }
                    if (results.contains("Dharma Name")) {
                        importVolunteers.setString(22, df.formatCellValue(row.getCell(22)));
                    } else {
                        importVolunteers.setString(22, "");
                    }
                    if (results.contains("Date of Taking Refuge")) {
                        sqlDate = new java.sql.Date(row.getCell(23).getDateCellValue().getTime());
                        importVolunteers.setDate(23, sqlDate);
                    } else {
                        importVolunteers.setDate(23, sqlDate2);
                    }
                    if (results.contains("Note")) {
                        importVolunteers.setString(24, df.formatCellValue(row.getCell(24)));
                    } else {
                        importVolunteers.setString(24, "");
                    }
                    if (results.contains("Create Date")) {
                        sqlDate = new java.sql.Date(row.getCell(25).getDateCellValue().getTime());
                        importVolunteers.setDate(25, sqlDate);
                    } else {
                        importVolunteers.setDate(25, sqlDate2); //SERT TO CURR DATE
                    }
                    if (results.contains("Update Date")) {
                        sqlDate = new java.sql.Date(row.getCell(26).getDateCellValue().getTime());
                        importVolunteers.setDate(26, sqlDate);
                    } else {
                        importVolunteers.setDate(26, sqlDate2);
                    }
                    if (results.contains("Created By")) {
                        importVolunteers.setString(27, df.formatCellValue(row.getCell(27)));
                    } else {
                        importVolunteers.setString(27, ""); //SET TO USER 
                    }
                    if (results.contains("Updated By")) {
                        importVolunteers.setString(28, df.formatCellValue(row.getCell(28)));
                    } else {
                        importVolunteers.setString(28, "");
                    }
                    importVolunteers.setInt(29, Integer.parseInt(df.formatCellValue(row.getCell(0))));

                    importVolunteers.execute();
                }
            }

            wb.close();
            fileIn.close();
            importVolunteers.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! importOldVolunteers() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in VolunteersQueries.java/importOldVolunteers() from excel... Most likely from extra null rows in excel");
            e.printStackTrace();
            return 2;
        }
        closeConnection();
        return 1;
    }
}
