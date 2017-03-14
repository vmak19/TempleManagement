package assignment.database;

import assignment.model.Accommodation;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AccommodationQueries extends DatabaseQuery {

    PreparedStatement insertAccommodation = null;
    PreparedStatement getAllAccommodations = null;
    PreparedStatement editAccommodation = null;
    PreparedStatement deleteAccommodation = null;
    PreparedStatement importAccommodation = null;

    ResultSet rs = null;
    List<Accommodation> accommodations;

    public List<Accommodation> getAccommodation() {
        accommodations = new ArrayList<Accommodation>();
        openConnection();
        try {
            getAllAccommodations = conn.prepareStatement("select * from app.ACCOMMODATION");
            rs = getAllAccommodations.executeQuery();
            while (rs.next()) {
                accommodations.add(
                        new Accommodation(rs.getInt("A_ID"), rs.getInt("MEM_ID"),
                                rs.getDate("CHECK_IN_DATE").toLocalDate(),
                                rs.getDate("CHECK_OUT_DATE").toLocalDate(), rs.getString("NOTE"),
                                rs.getString("ROOM"), rs.getString("GENDER"),
                                rs.getString("VERIFICATION_TYPE"), rs.getString("VERIFICATION_ID"),
                                rs.getString("REASON"), rs.getString("EMG_PERSON"),
                                rs.getString("EMG_NO"), rs.getString("RELATIONSHIP")));
            }
            rs.close();
            getAllAccommodations.close();
        } catch (SQLException ex) {
            System.out.println("getAccommodation() error!");
        }
        closeConnection();
        return accommodations;
    }
    
    public List<Accommodation> getAccordionAccommodation(int memID) {
        accommodations = new ArrayList<Accommodation>();
        openConnection();
        try {
            getAllAccommodations = conn.prepareStatement("select * from app.ACCOMMODATION "
                    + "where MEM_ID = ?");
            getAllAccommodations.setInt(1, memID);
            rs = getAllAccommodations.executeQuery();
            while (rs.next()) {
                accommodations.add(
                        new Accommodation(rs.getInt("A_ID"), rs.getInt("MEM_ID"),
                                rs.getDate("CHECK_IN_DATE").toLocalDate(),
                                rs.getDate("CHECK_OUT_DATE").toLocalDate(), rs.getString("NOTE"),
                                rs.getString("ROOM"), rs.getString("GENDER"),
                                rs.getString("VERIFICATION_TYPE"), rs.getString("VERIFICATION_ID"),
                                rs.getString("REASON"), rs.getString("EMG_PERSON"),
                                rs.getString("EMG_NO"), rs.getString("RELATIONSHIP")));
            }
            rs.close();
            getAllAccommodations.close();
        } catch (SQLException ex) {
            System.out.println("getAccommodation() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return accommodations;
    }

    public int insertAccommodation(Accommodation toInsert) {
        int userGeneratedId = -1;
        openConnection();
        try {
            insertAccommodation = conn.prepareStatement("insert into app.accommodation "
                    + "(MEM_ID, CHECK_IN_DATE, CHECK_OUT_DATE, NOTE, ROOM, GENDER, "
                    + "VERIFICATION_TYPE, VERIFICATION_ID, REASON, EMG_PERSON, EMG_NO, RELATIONSHIP) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertAccommodation.setInt(1, toInsert.getMemID());
            insertAccommodation.setDate(2, toInsert.getCheckInDateToDate());
            insertAccommodation.setDate(3, toInsert.getCheckOutDateToDate());
            insertAccommodation.setString(4, toInsert.getNote());
            insertAccommodation.setString(5, toInsert.getRoom());
            insertAccommodation.setString(6, toInsert.getGender());
            insertAccommodation.setString(7, toInsert.getTypeOfID());
            insertAccommodation.setString(8, toInsert.getIDNum());
            insertAccommodation.setString(9, toInsert.getReason());
            insertAccommodation.setString(10, toInsert.getEmgContactPerson());
            insertAccommodation.setString(11, toInsert.getEmgContactNum());
            insertAccommodation.setString(12, toInsert.getRelationship());
            insertAccommodation.executeUpdate();

            rs = insertAccommodation.getGeneratedKeys();
            rs.next();
            userGeneratedId = rs.getInt(1);
            toInsert.setMemID(userGeneratedId);
            rs.close();
            insertAccommodation.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! insertAccommodation() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
        return userGeneratedId;
    }

    public void updateAccommodation(Accommodation toInsert) {
        openConnection();
        try {
            editAccommodation = conn.prepareStatement("update app.accommodation set "
                    + "MEM_ID=?, CHECK_IN_DATE =?, CHECK_OUT_DATE =?, NOTE =?, ROOM =?, GENDER =?, "
                    + "VERIFICATION_TYPE =?, VERIFICATION_ID =?, REASON =?, EMG_PERSON =?, EMG_NO =?, RELATIONSHIP =?, "
                    + "where A_ID=?");
            editAccommodation.setInt(1, toInsert.getMemID());
            editAccommodation.setDate(2, toInsert.getCheckInDateToDate());
            editAccommodation.setDate(3, toInsert.getCheckOutDateToDate());
            editAccommodation.setString(4, toInsert.getNote());
            editAccommodation.setString(5, toInsert.getRoom());
            editAccommodation.setString(6, toInsert.getGender());
            editAccommodation.setString(7, toInsert.getTypeOfID());
            editAccommodation.setString(8, toInsert.getIDNum());
            editAccommodation.setString(9, toInsert.getReason());
            editAccommodation.setString(10, toInsert.getEmgContactPerson());
            editAccommodation.setString(11, toInsert.getEmgContactNum());
            editAccommodation.setString(12, toInsert.getRelationship());
            editAccommodation.setInt(13, toInsert.getAID());

            editAccommodation.executeUpdate();
            editAccommodation.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! editAccommodation() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
    }

    public void deleteAccommodation(Accommodation toDelete) {
        openConnection();
        try {
            deleteAccommodation = conn.prepareStatement("delete from app.ACCOMMODATION where A_ID = ?");
            deleteAccommodation.setInt(1, toDelete.getAID());
            deleteAccommodation.execute();
        } catch (SQLException ex) {
            System.out.println("ERROR! deleteAccommodation()!");
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

        //grab blue row (the one that has the column names e.g. accommodationID, chinese name, first name...)
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

    private List<Integer> newAccommodationIndexList = new ArrayList<>();

    public List<Integer> getNewAccommodationIndexList() {
        return newAccommodationIndexList;
    }

    public List<Integer> getAccommodationID(String file) throws FileNotFoundException, IOException {
        //Open excel file
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //Put data into arrayList
        List<Integer> accommodationIDlist = new ArrayList<>();
        int rowCount = sheet.getPhysicalNumberOfRows();
        DataFormatter df = new DataFormatter();
        for (int i = 1; i < rowCount; i++) {
            row = sheet.getRow(i);
            //Store accommodationID into arraylist
            if (row.getCell(0) != null) { //OR contains a number
                accommodationIDlist.add(Integer.parseInt(df.formatCellValue(row.getCell(0))));
            } else { //else has no accommodationID, toInsertNewAccommodation() later
                newAccommodationIndexList.add(i); //Store excel row index for accessing later
            }
        }

        return accommodationIDlist;
    }

    //Imports each row of excel file, no restrictions
    public int importAccommodation(String file, ArrayList<String> results) throws FileNotFoundException, IOException {
        openConnection();
        //Open excel file
        //FileInputStream fileIn = new FileInputStream(new File("AccommodationImport.xlsx"));
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            importAccommodation = conn.prepareStatement("insert into app.accommodation "
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
                    importAccommodation.setString(1, df.formatCellValue(row.getCell(1)));
                } else {
                    importAccommodation.setString(1, "");
                }
                if (results.contains("First Name")) {
                    importAccommodation.setString(2, df.formatCellValue(row.getCell(2)));
                } else {
                    importAccommodation.setString(2, "");
                }
                if (results.contains("Last Name")) {
                    importAccommodation.setString(3, df.formatCellValue(row.getCell(3)));
                } else {
                    importAccommodation.setString(3, "");
                }
                if (results.contains("Gender")) {
                    importAccommodation.setString(4, df.formatCellValue(row.getCell(4)));
                } else {
                    importAccommodation.setString(4, "");
                }
                if (results.contains("Date of birth")) {
                    sqlDate = new java.sql.Date(row.getCell(5).getDateCellValue().getTime());
                    importAccommodation.setDate(5, sqlDate);
                } else {
                    importAccommodation.setDate(5, sqlDate2);
                }
                if (results.contains("Home Phone")) {
                    importAccommodation.setString(6, df.formatCellValue(row.getCell(6)));
                } else {
                    importAccommodation.setString(6, "");
                }
                if (results.contains("Mobile")) {
                    importAccommodation.setString(7, df.formatCellValue(row.getCell(7)));
                } else {
                    importAccommodation.setString(7, "");
                }
                if (results.contains("Work Phone")) {
                    importAccommodation.setString(8, df.formatCellValue(row.getCell(8)));
                } else {
                    importAccommodation.setString(8, "");
                }
                if (results.contains("Fax")) {
                    importAccommodation.setString(9, df.formatCellValue(row.getCell(9)));
                } else {
                    importAccommodation.setString(9, "");
                }
                if (results.contains("Email")) {
                    importAccommodation.setString(10, df.formatCellValue(row.getCell(10)));
                } else {
                    importAccommodation.setString(10, "");
                }
                if (results.contains("WeChat ID")) {
                    importAccommodation.setString(11, df.formatCellValue(row.getCell(11)));
                } else {
                    importAccommodation.setString(11, "");
                }
                if (results.contains("Preferred Contact Method")) {
                    importAccommodation.setString(12, df.formatCellValue(row.getCell(12)));
                } else {
                    importAccommodation.setString(12, "");
                }
                if (results.contains("Language")) {
                    importAccommodation.setString(13, df.formatCellValue(row.getCell(13)));
                } else {
                    importAccommodation.setString(13, "");
                }
                if (results.contains("Skills/Hobbies")) {
                    importAccommodation.setString(14, df.formatCellValue(row.getCell(14)));
                } else {
                    importAccommodation.setString(14, "");
                }
                if (results.contains("Education")) {
                    importAccommodation.setString(15, df.formatCellValue(row.getCell(15)));
                } else {
                    importAccommodation.setString(15, "");
                }
                if (results.contains("Employment")) {
                    importAccommodation.setString(16, df.formatCellValue(row.getCell(16)));
                } else {
                    importAccommodation.setString(16, "");
                }
                if (results.contains("Nationality")) {
                    importAccommodation.setString(17, df.formatCellValue(row.getCell(17)));
                } else {
                    importAccommodation.setString(17, "");
                }
                if (results.contains("Address")) {
                    importAccommodation.setString(18, df.formatCellValue(row.getCell(18)));
                } else {
                    importAccommodation.setString(18, "");
                }
                if (results.contains("Suburb")) {
                    importAccommodation.setString(19, df.formatCellValue(row.getCell(19)));
                } else {
                    importAccommodation.setString(19, "");
                }
                if (results.contains("Postcode")) {
                    importAccommodation.setString(20, df.formatCellValue(row.getCell(20)));
                } else {
                    importAccommodation.setString(20, "");
                }
                if (results.contains("State")) {
                    importAccommodation.setString(21, df.formatCellValue(row.getCell(21)));
                } else {
                    importAccommodation.setString(21, "");
                }
                if (results.contains("Dharma Name")) {
                    importAccommodation.setString(22, df.formatCellValue(row.getCell(22)));
                } else {
                    importAccommodation.setString(22, "");
                }
                if (results.contains("Date of Taking Refuge")) {
                    sqlDate = new java.sql.Date(row.getCell(23).getDateCellValue().getTime());
                    importAccommodation.setDate(23, sqlDate);
                } else {
                    importAccommodation.setDate(23, sqlDate2);
                }
                if (results.contains("Note")) {
                    importAccommodation.setString(24, df.formatCellValue(row.getCell(24)));
                } else {
                    importAccommodation.setString(24, "");
                }
                if (results.contains("Create Date")) {
                    sqlDate = new java.sql.Date(row.getCell(25).getDateCellValue().getTime());
                    importAccommodation.setDate(25, sqlDate);
                } else {
                    importAccommodation.setDate(25, sqlDate2); //SERT TO CURR DATE
                }
                if (results.contains("Update Date")) {
                    sqlDate = new java.sql.Date(row.getCell(26).getDateCellValue().getTime());
                    importAccommodation.setDate(26, sqlDate);
                } else {
                    importAccommodation.setDate(26, sqlDate2);
                }
                if (results.contains("Created By")) {
                    importAccommodation.setString(27, df.formatCellValue(row.getCell(27)));
                } else {
                    importAccommodation.setString(27, ""); //SET TO USER 
                }
                if (results.contains("Updated By")) {
                    importAccommodation.setString(28, df.formatCellValue(row.getCell(28)));
                } else {
                    importAccommodation.setString(28, "");
                }

                importAccommodation.execute();
            }

            wb.close();
            fileIn.close();
            importAccommodation.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! importAccommodation() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in AccommodationQueries.java/importAccommodation from excel... Most likely from extra null rows in excel");
            return 2;
        }
        closeConnection();
        return 1;
    }

    //Imports only rows that contains deleted accommodationID from db OR rows without accommodationID ===> assigns these rows with new accommodationID
    public int importNewAccommodation(List<Integer> newAccommodationList, List<Integer> newAccommodationIndexList, String file, ArrayList<String> results) throws FileNotFoundException, IOException {
        openConnection();
        //Open excel file
        //FileInputStream fileIn = new FileInputStream(new File("AccommodationImport.xlsx"));
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            importAccommodation = conn.prepareStatement("insert into app.accommodation "
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
                for (int n : newAccommodationIndexList) {
                    if (i == n) {
                        flag = true;
                        break;
                    }
                }

                //OR if it is the accommodationID we want... then process row
                if (row.getCell(0) != null && !flag) {
                    id = Integer.parseInt(df.formatCellValue(row.getCell(0)));

                    for (int n : newAccommodationList) {
                        if (id == n) {
                            flag2 = true;
                            break;
                        }
                    }
                }
                //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                if (flag || flag2) {
                    if (results.contains("Chinese Name")) {
                        importAccommodation.setString(1, df.formatCellValue(row.getCell(1)));
                    } else {
                        importAccommodation.setString(1, "");
                    }
                    if (results.contains("First Name")) {
                        importAccommodation.setString(2, df.formatCellValue(row.getCell(2)));
                    } else {
                        importAccommodation.setString(2, "");
                    }
                    if (results.contains("Last Name")) {
                        importAccommodation.setString(3, df.formatCellValue(row.getCell(3)));
                    } else {
                        importAccommodation.setString(3, "");
                    }
                    if (results.contains("Gender")) {
                        importAccommodation.setString(4, df.formatCellValue(row.getCell(4)));
                    } else {
                        importAccommodation.setString(4, "");
                    }
                    if (results.contains("Date of birth")) {
                        sqlDate = new java.sql.Date(row.getCell(5).getDateCellValue().getTime());
                        importAccommodation.setDate(5, sqlDate);
                    } else {
                        importAccommodation.setDate(5, sqlDate2);
                    }
                    if (results.contains("Home Phone")) {
                        importAccommodation.setString(6, df.formatCellValue(row.getCell(6)));
                    } else {
                        importAccommodation.setString(6, "");
                    }
                    if (results.contains("Mobile")) {
                        importAccommodation.setString(7, df.formatCellValue(row.getCell(7)));
                    } else {
                        importAccommodation.setString(7, "");
                    }
                    if (results.contains("Work Phone")) {
                        importAccommodation.setString(8, df.formatCellValue(row.getCell(8)));
                    } else {
                        importAccommodation.setString(8, "");
                    }
                    if (results.contains("Fax")) {
                        importAccommodation.setString(9, df.formatCellValue(row.getCell(9)));
                    } else {
                        importAccommodation.setString(9, "");
                    }
                    if (results.contains("Email")) {
                        importAccommodation.setString(10, df.formatCellValue(row.getCell(10)));
                    } else {
                        importAccommodation.setString(10, "");
                    }
                    if (results.contains("WeChat ID")) {
                        importAccommodation.setString(11, df.formatCellValue(row.getCell(11)));
                    } else {
                        importAccommodation.setString(11, "");
                    }
                    if (results.contains("Preferred Contact Method")) {
                        importAccommodation.setString(12, df.formatCellValue(row.getCell(12)));
                    } else {
                        importAccommodation.setString(12, "");
                    }
                    if (results.contains("Language")) {
                        importAccommodation.setString(13, df.formatCellValue(row.getCell(13)));
                    } else {
                        importAccommodation.setString(13, "");
                    }
                    if (results.contains("Skills/Hobbies")) {
                        importAccommodation.setString(14, df.formatCellValue(row.getCell(14)));
                    } else {
                        importAccommodation.setString(14, "");
                    }
                    if (results.contains("Education")) {
                        importAccommodation.setString(15, df.formatCellValue(row.getCell(15)));
                    } else {
                        importAccommodation.setString(15, "");
                    }
                    if (results.contains("Employment")) {
                        importAccommodation.setString(16, df.formatCellValue(row.getCell(16)));
                    } else {
                        importAccommodation.setString(16, "");
                    }
                    if (results.contains("Nationality")) {
                        importAccommodation.setString(17, df.formatCellValue(row.getCell(17)));
                    } else {
                        importAccommodation.setString(17, "");
                    }
                    if (results.contains("Address")) {
                        importAccommodation.setString(18, df.formatCellValue(row.getCell(18)));
                    } else {
                        importAccommodation.setString(18, "");
                    }
                    if (results.contains("Suburb")) {
                        importAccommodation.setString(19, df.formatCellValue(row.getCell(19)));
                    } else {
                        importAccommodation.setString(19, "");
                    }
                    if (results.contains("Postcode")) {
                        importAccommodation.setString(20, df.formatCellValue(row.getCell(20)));
                    } else {
                        importAccommodation.setString(20, "");
                    }
                    if (results.contains("State")) {
                        importAccommodation.setString(21, df.formatCellValue(row.getCell(21)));
                    } else {
                        importAccommodation.setString(21, "");
                    }
                    if (results.contains("Dharma Name")) {
                        importAccommodation.setString(22, df.formatCellValue(row.getCell(22)));
                    } else {
                        importAccommodation.setString(22, "");
                    }
                    if (results.contains("Date of Taking Refuge")) {
                        sqlDate = new java.sql.Date(row.getCell(23).getDateCellValue().getTime());
                        importAccommodation.setDate(23, sqlDate);
                    } else {
                        importAccommodation.setDate(23, sqlDate2);
                    }
                    if (results.contains("Note")) {
                        importAccommodation.setString(24, df.formatCellValue(row.getCell(24)));
                    } else {
                        importAccommodation.setString(24, "");
                    }
                    if (results.contains("Create Date")) {
                        sqlDate = new java.sql.Date(row.getCell(25).getDateCellValue().getTime());
                        importAccommodation.setDate(25, sqlDate);
                    } else {
                        importAccommodation.setDate(25, sqlDate2); //SERT TO CURR DATE
                    }
                    if (results.contains("Update Date")) {
                        sqlDate = new java.sql.Date(row.getCell(26).getDateCellValue().getTime());
                        importAccommodation.setDate(26, sqlDate);
                    } else {
                        importAccommodation.setDate(26, sqlDate2);
                    }
                    if (results.contains("Created By")) {
                        importAccommodation.setString(27, df.formatCellValue(row.getCell(27)));
                    } else {
                        importAccommodation.setString(27, ""); //SET TO USER 
                    }
                    if (results.contains("Updated By")) {
                        importAccommodation.setString(28, df.formatCellValue(row.getCell(28)));
                    } else {
                        importAccommodation.setString(28, "");
                    }

                    importAccommodation.execute();
                }
            }

            wb.close();
            fileIn.close();
            importAccommodation.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! importAccommodation() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in AccommodationQueries.java/importNewAccommodation from excel... Most likely from extra null rows in excel");
            return 2;
        }
        closeConnection();
        return 1;
    }

    //Updates existing accommodations with accommodationID matching in excel
    public int importOldAccommodation(List<Integer> accommodationIDList, String file, ArrayList<String> results) throws FileNotFoundException, IOException {
        openConnection();
        //Open excel file
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            //'REPLACE INTO' Updates existing records and inserts new ones if pkID not found
            importAccommodation = conn.prepareStatement("update app.accommodation set C_NAME = ?, "
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
                //if accommodationID from cell, is the one we want...
                boolean flag = false;
                int id = 0;
                if (row.getCell(0) != null) {
                    id = Integer.parseInt(df.formatCellValue(row.getCell(0)));
                } else {
                    continue;
                }
                for (int n : accommodationIDList) {
                    if (id == n) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    if (results.contains("Chinese Name")) {
                        importAccommodation.setString(1, df.formatCellValue(row.getCell(1)));
                    } else {
                        importAccommodation.setString(1, "");
                    }
                    if (results.contains("First Name")) {
                        importAccommodation.setString(2, df.formatCellValue(row.getCell(2)));
                    } else {
                        importAccommodation.setString(2, "");
                    }
                    if (results.contains("Last Name")) {
                        importAccommodation.setString(3, df.formatCellValue(row.getCell(3)));
                    } else {
                        importAccommodation.setString(3, "");
                    }
                    if (results.contains("Gender")) {
                        importAccommodation.setString(4, df.formatCellValue(row.getCell(4)));
                    } else {
                        importAccommodation.setString(4, "");
                    }
                    if (results.contains("Date of birth")) {
                        sqlDate = new java.sql.Date(row.getCell(5).getDateCellValue().getTime());
                        importAccommodation.setDate(5, sqlDate);
                    } else {
                        importAccommodation.setDate(5, sqlDate2);
                    }
                    if (results.contains("Home Phone")) {
                        importAccommodation.setString(6, df.formatCellValue(row.getCell(6)));
                    } else {
                        importAccommodation.setString(6, "");
                    }
                    if (results.contains("Mobile")) {
                        importAccommodation.setString(7, df.formatCellValue(row.getCell(7)));
                    } else {
                        importAccommodation.setString(7, "");
                    }
                    if (results.contains("Work Phone")) {
                        importAccommodation.setString(8, df.formatCellValue(row.getCell(8)));
                    } else {
                        importAccommodation.setString(8, "");
                    }
                    if (results.contains("Fax")) {
                        importAccommodation.setString(9, df.formatCellValue(row.getCell(9)));
                    } else {
                        importAccommodation.setString(9, "");
                    }
                    if (results.contains("Email")) {
                        importAccommodation.setString(10, df.formatCellValue(row.getCell(10)));
                    } else {
                        importAccommodation.setString(10, "");
                    }
                    if (results.contains("WeChat ID")) {
                        importAccommodation.setString(11, df.formatCellValue(row.getCell(11)));
                    } else {
                        importAccommodation.setString(11, "");
                    }
                    if (results.contains("Preferred Contact Method")) {
                        importAccommodation.setString(12, df.formatCellValue(row.getCell(12)));
                    } else {
                        importAccommodation.setString(12, "");
                    }
                    if (results.contains("Language")) {
                        importAccommodation.setString(13, df.formatCellValue(row.getCell(13)));
                    } else {
                        importAccommodation.setString(13, "");
                    }
                    if (results.contains("Skills/Hobbies")) {
                        importAccommodation.setString(14, df.formatCellValue(row.getCell(14)));
                    } else {
                        importAccommodation.setString(14, "");
                    }
                    if (results.contains("Education")) {
                        importAccommodation.setString(15, df.formatCellValue(row.getCell(15)));
                    } else {
                        importAccommodation.setString(15, "");
                    }
                    if (results.contains("Employment")) {
                        importAccommodation.setString(16, df.formatCellValue(row.getCell(16)));
                    } else {
                        importAccommodation.setString(16, "");
                    }
                    if (results.contains("Nationality")) {
                        importAccommodation.setString(17, df.formatCellValue(row.getCell(17)));
                    } else {
                        importAccommodation.setString(17, "");
                    }
                    if (results.contains("Address")) {
                        importAccommodation.setString(18, df.formatCellValue(row.getCell(18)));
                    } else {
                        importAccommodation.setString(18, "");
                    }
                    if (results.contains("Suburb")) {
                        importAccommodation.setString(19, df.formatCellValue(row.getCell(19)));
                    } else {
                        importAccommodation.setString(19, "");
                    }
                    if (results.contains("Postcode")) {
                        importAccommodation.setString(20, df.formatCellValue(row.getCell(20)));
                    } else {
                        importAccommodation.setString(20, "");
                    }
                    if (results.contains("State")) {
                        importAccommodation.setString(21, df.formatCellValue(row.getCell(21)));
                    } else {
                        importAccommodation.setString(21, "");
                    }
                    if (results.contains("Dharma Name")) {
                        importAccommodation.setString(22, df.formatCellValue(row.getCell(22)));
                    } else {
                        importAccommodation.setString(22, "");
                    }
                    if (results.contains("Date of Taking Refuge")) {
                        sqlDate = new java.sql.Date(row.getCell(23).getDateCellValue().getTime());
                        importAccommodation.setDate(23, sqlDate);
                    } else {
                        importAccommodation.setDate(23, sqlDate2);
                    }
                    if (results.contains("Note")) {
                        importAccommodation.setString(24, df.formatCellValue(row.getCell(24)));
                    } else {
                        importAccommodation.setString(24, "");
                    }
                    if (results.contains("Create Date")) {
                        sqlDate = new java.sql.Date(row.getCell(25).getDateCellValue().getTime());
                        importAccommodation.setDate(25, sqlDate);
                    } else {
                        importAccommodation.setDate(25, sqlDate2); //SERT TO CURR DATE
                    }
                    if (results.contains("Update Date")) {
                        sqlDate = new java.sql.Date(row.getCell(26).getDateCellValue().getTime());
                        importAccommodation.setDate(26, sqlDate);
                    } else {
                        importAccommodation.setDate(26, sqlDate2);
                    }
                    if (results.contains("Created By")) {
                        importAccommodation.setString(27, df.formatCellValue(row.getCell(27)));
                    } else {
                        importAccommodation.setString(27, ""); //SET TO USER 
                    }
                    if (results.contains("Updated By")) {
                        importAccommodation.setString(28, df.formatCellValue(row.getCell(28)));
                    } else {
                        importAccommodation.setString(28, "");
                    }
                    importAccommodation.setInt(29, Integer.parseInt(df.formatCellValue(row.getCell(0))));

                    importAccommodation.execute();
                }
            }

            wb.close();
            fileIn.close();
            importAccommodation.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! importOldAccommodation() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in AccommodationQueries.java/importOldAccommodation() from excel... Most likely from extra null rows in excel");
            e.printStackTrace();
            return 2;
        }
        closeConnection();
        return 1;
    }
}
