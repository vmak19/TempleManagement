package assignment.database;

import assignment.model.Donation;
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

public class DonationQueries extends DatabaseQuery {

    PreparedStatement insertDonation = null;
    PreparedStatement getAllDonations = null;
    PreparedStatement editDonation = null;
    PreparedStatement deleteDonation = null;
    PreparedStatement importDonation = null;

    ResultSet rs = null;
    List<Donation> donations;

    public List<Donation> getDonations() {
        donations = new ArrayList<Donation>();
        openConnection();
        try {
            getAllDonations = conn.prepareStatement("select * from app.DONATION");
            rs = getAllDonations.executeQuery();
            while (rs.next()) {
                donations.add(
                        new Donation(rs.getInt("DON_ID"), rs.getInt("MEM_ID"),
                                rs.getString("DON_TYPE"),
                                rs.getString("DHARMA_SERVICE"), rs.getDate("DATE").toLocalDate(),
                                rs.getString("PAY_METHOD"), rs.getDouble("AMT_PAID"),
                                rs.getDouble("TOTAL_DON"), rs.getDate("PAY_DATE").toLocalDate(),
                                rs.getDouble("BAL"), rs.getString("NOTE"),
                                rs.getDate("CREATE_DATE").toLocalDate(),
                                rs.getDate("UPDATE_DATE").toLocalDate(), rs.getString("CREATE_BY"),
                                rs.getString("UPDATE_BY")));
            }
            rs.close();
            getAllDonations.close();
        } catch (SQLException ex) {
            System.out.println("getDonation() error!");
        }
        closeConnection();
        return donations;
    }

    public List<Donation> getAccordionDonation(int memID) {
        donations = new ArrayList<Donation>();
        openConnection();
        try {
            getAllDonations = conn.prepareStatement("select * from app.DONATION "
                    + "where MEM_ID = ?");
            getAllDonations.setInt(1, memID);
            rs = getAllDonations.executeQuery();
            while (rs.next()) {
                donations.add(
                        new Donation(rs.getInt("DON_ID"), rs.getInt("MEM_ID"),
                                rs.getString("DON_TYPE"),
                                rs.getString("DHARMA_SERVICE"), rs.getDate("DATE").toLocalDate(),
                                rs.getString("PAY_METHOD"), rs.getDouble("AMT_PAID"),
                                rs.getDouble("TOTAL_DON"), rs.getDate("PAY_DATE").toLocalDate(),
                                rs.getDouble("BAL"), rs.getString("NOTE"),
                                rs.getDate("CREATE_DATE").toLocalDate(),
                                rs.getDate("UPDATE_DATE").toLocalDate(), rs.getString("CREATE_BY"),
                                rs.getString("UPDATE_BY")));
            }
            rs.close();
            getAllDonations.close();
        } catch (SQLException ex) {
            System.out.println("getDonation() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return donations;
    }

    public int insertDonations(Donation toInsert) {
        int userGeneratedId = -1;
        openConnection();
        try {
            insertDonation = conn.prepareStatement("insert into app.donation "
                    + "(MEM_ID, DON_TYPE, DHARMA_SERVICE, DATE, PAY_METHOD, "
                    + "AMT_PAID, TOTAL_DON, PAY_DATE, BAL, NOTE, CREATE_DATE, UPDATE_DATE, CREATE_BY, UPDATE_BY) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertDonation.setInt(1, toInsert.getMemID());
            insertDonation.setString(2, toInsert.getDonType());
            insertDonation.setString(3, toInsert.getDharmaService());
            insertDonation.setDate(4, toInsert.getDateToDate());
            insertDonation.setString(5, toInsert.getPayMethod());
            insertDonation.setDouble(6, toInsert.getAmtPaid());
            insertDonation.setDouble(7, toInsert.getTotalDon());
            insertDonation.setDate(8, toInsert.getPayDateToDate());
            insertDonation.setDouble(9, toInsert.getBal());
            insertDonation.setString(10, toInsert.getNote());
            insertDonation.setDate(11, toInsert.getCreateDateToDate());
            insertDonation.setDate(12, toInsert.getUpdateOnToDate());
            insertDonation.setString(13, toInsert.getCreateBy());
            insertDonation.setString(14, toInsert.getUpdateBy());
            insertDonation.executeUpdate();

            rs = insertDonation.getGeneratedKeys();
            rs.next();
            userGeneratedId = rs.getInt(1);
            toInsert.setDonID(userGeneratedId);
            rs.close();
            insertDonation.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! insertDonation() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
        return userGeneratedId;
    }

    public void updateDonation(Donation toInsert) { //***********UPDATE on don_id OR donation_id *******************************
        openConnection();
        try {
            editDonation = conn.prepareStatement("update app.donation set "
                    + "MEM_ID = ?, DON_TYPE = ?, DHARMA_SERVICE = ?, DATE = ?, PAY_METHOD = ?, "
                    + "AMT_PAID = ?, TOTAL_DON = ?, PAY_DATE = ?, BAL = ?, NOTE = ?, "
                    + "UPDATE_DATE = ?, UPDATE_BY = ? "
                    + "where DON_ID=?");

            editDonation.setInt(1, toInsert.getMemID());
            editDonation.setString(2, toInsert.getDonType());
            editDonation.setString(3, toInsert.getDharmaService());
            editDonation.setDate(4, toInsert.getDateToDate());
            editDonation.setString(5, toInsert.getPayMethod());
            editDonation.setDouble(6, toInsert.getAmtPaid());
            editDonation.setDouble(7, toInsert.getTotalDon());
            editDonation.setDate(8, toInsert.getPayDateToDate());
            editDonation.setDouble(9, toInsert.getBal());
            editDonation.setString(10, toInsert.getNote());
            editDonation.setDate(11, toInsert.getUpdateOnToDate());
            editDonation.setString(12, toInsert.getUpdateBy());
            editDonation.setInt(13, toInsert.getDonID());

            editDonation.executeUpdate();
            editDonation.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! editDonation() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
    }

    public void deleteDonation(Donation toDelete) {
        openConnection();
        try {
            deleteDonation = conn.prepareStatement("delete from app.DONATION where DON_ID = ?");
            deleteDonation.setInt(1, toDelete.getDonID());
            deleteDonation.execute();
        } catch (SQLException ex) {
            System.out.println("ERROR! deleteDonation()!");
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

        //grab blue row (the one that has the column names e.g. donationID, chinese name, first name...)
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

    private List<Integer> newDonationIndexList = new ArrayList<>();

    public List<Integer> getNewDonationIndexList() {
        return newDonationIndexList;
    }

    public List<Integer> getDonationID(String file) throws FileNotFoundException, IOException {
        //Open excel file
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //Put data into arrayList
        List<Integer> donationIDlist = new ArrayList<>();
        int rowCount = sheet.getPhysicalNumberOfRows();
        DataFormatter df = new DataFormatter();
        for (int i = 1; i < rowCount; i++) {
            row = sheet.getRow(i);
            //Store donationID into arraylist
            if (row.getCell(0) != null) { //OR contains a number                
                donationIDlist.add(Integer.parseInt(df.formatCellValue(row.getCell(0))));
            } else { //else has no donationID, toInsertNewDonation() later
                newDonationIndexList.add(i); //Store excel row index for accessing later
            }
        }

        return donationIDlist;
    }

    //Imports each row of excel file, no restrictions
    public int importDonation(String file, ArrayList<String> results) throws FileNotFoundException, IOException {
        openConnection();
        //Open excel file
        //FileInputStream fileIn = new FileInputStream(new File("DonationImport.xlsx"));
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            importDonation = conn.prepareStatement("insert into app.donation "
                    + "(MEM_ID, DON_TYPE, DHARMA_SERVICE, DATE, "
                    + "PAY_METHOD, AMT_PAID, TOTAL_DON, PAY_DATE, BAL, "
                    + "NOTE, CREATE_DATE, UPDATE_DATE, CREATE_BY, UPDATE_BY)"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            //Put into drop down list ######==>calls initialise method in DbLinkDialogController.java
            //Start reading data from excel, row by row
            java.sql.Date sqlDate;
            java.sql.Date sqlDate2 = java.sql.Date.valueOf("9999-01-01");
            DataFormatter df = new DataFormatter();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);

                if (results.contains("Member ID")) {
                    importDonation.setString(1, df.formatCellValue(row.getCell(1)));
                } else {
                    importDonation.setString(1, "");
                }
                if (results.contains("Type of Donation")) {
                    importDonation.setString(2, df.formatCellValue(row.getCell(2)));
                } else {
                    importDonation.setString(2, "");
                }
                if (results.contains("Dharma Service")) {
                    importDonation.setString(3, df.formatCellValue(row.getCell(3)));
                } else {
                    importDonation.setString(3, "");
                }
                if (results.contains("Date of Donation")) {
                    sqlDate = new java.sql.Date(row.getCell(4).getDateCellValue().getTime());
                    importDonation.setDate(4, sqlDate);
                } else {
                    importDonation.setDate(4, sqlDate2);
                }
                if (results.contains("Payment Method")) {
                    importDonation.setString(5, df.formatCellValue(row.getCell(5)));
                } else {
                    importDonation.setString(5, "");
                }
                if (results.contains("Amount Paid")) {
                    importDonation.setString(6, df.formatCellValue(row.getCell(6)));
                } else {
                    importDonation.setString(6, "");
                }
                if (results.contains("Total Donation")) {
                    importDonation.setString(7, df.formatCellValue(row.getCell(7)));
                } else {
                    importDonation.setString(7, "");
                }
                if (results.contains("Paid On")) {
                    sqlDate = new java.sql.Date(row.getCell(8).getDateCellValue().getTime());
                    importDonation.setDate(8, sqlDate);
                } else {
                    importDonation.setDate(8, sqlDate2);
                }
                if (results.contains("Balance")) {
                    importDonation.setString(9, df.formatCellValue(row.getCell(9)));
                } else {
                    importDonation.setString(9, "");
                }
                if (results.contains("Note")) {
                    importDonation.setString(10, df.formatCellValue(row.getCell(10)));
                } else {
                    importDonation.setString(10, "");
                }
                if (results.contains("Create Date")) {
                    sqlDate = new java.sql.Date(row.getCell(11).getDateCellValue().getTime());
                    importDonation.setDate(11, sqlDate);
                } else {
                    importDonation.setDate(11, sqlDate2); //SERT TO CURR DATE
                }
                if (results.contains("Update Date")) {
                    sqlDate = new java.sql.Date(row.getCell(12).getDateCellValue().getTime());
                    importDonation.setDate(12, sqlDate);
                } else {
                    importDonation.setDate(12, sqlDate2);
                }
                if (results.contains("Created By")) {
                    importDonation.setString(13, df.formatCellValue(row.getCell(13)));
                } else {
                    importDonation.setString(13, ""); //SET TO USER 
                }
                if (results.contains("Updated By")) {
                    importDonation.setString(14, df.formatCellValue(row.getCell(14)));
                } else {
                    importDonation.setString(14, "");
                }

                importDonation.execute();
            }

            wb.close();
            fileIn.close();
            importDonation.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! importDonation() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in DonationQueries.java/importDonation from excel... Most likely from extra null rows in excel");
            return 2;
        }
        closeConnection();
        return 1;
    }

    //Imports only rows that contains deleted donationID from db OR rows without donationID ===> assigns these rows with new donationID
    public int importNewDonation(List<Integer> newDonationList, List<Integer> newDonationIndexList, String file, ArrayList<String> results) throws FileNotFoundException, IOException {
        openConnection();
        //Open excel file
        //FileInputStream fileIn = new FileInputStream(new File("DonationImport.xlsx"));
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            importDonation = conn.prepareStatement("insert into app.donation "
                    + "(MEM_ID, DON_TYPE, DHARMA_SERVICE, DATE, "
                    + "PAY_METHOD, AMT_PAID, TOTAL_DON, PAY_DATE, BAL, "
                    + "NOTE, CREATE_DATE, UPDATE_DATE, CREATE_BY, UPDATE_BY)"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

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
                for (int n : newDonationIndexList) {
                    if (i == n) {
                        flag = true;
                        break;
                    }
                }

                //OR if it is the donationID we want... then process row
                if (row.getCell(0) != null && !flag) {
                    id = Integer.parseInt(df.formatCellValue(row.getCell(0)));

                    for (int n : newDonationList) {
                        if (id == n) {
                            flag2 = true;
                            break;
                        }
                    }
                }
                //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                if (flag || flag2) {
                    if (results.contains("Member ID")) {
                        importDonation.setString(1, df.formatCellValue(row.getCell(1)));
                    } else {
                        importDonation.setString(1, "");
                    }
                    if (results.contains("Type of Donation")) {
                        importDonation.setString(2, df.formatCellValue(row.getCell(2)));
                    } else {
                        importDonation.setString(2, "");
                    }
                    if (results.contains("Dharma Service")) {
                        importDonation.setString(3, df.formatCellValue(row.getCell(3)));
                    } else {
                        importDonation.setString(3, "");
                    }
                    if (results.contains("Date of Donation")) {
                        sqlDate = new java.sql.Date(row.getCell(4).getDateCellValue().getTime());
                        importDonation.setDate(4, sqlDate);
                    } else {
                        importDonation.setDate(4, sqlDate2);
                    }
                    if (results.contains("Payment Method")) {
                        importDonation.setString(5, df.formatCellValue(row.getCell(5)));
                    } else {
                        importDonation.setString(5, "");
                    }
                    if (results.contains("Amount Paid")) {
                        importDonation.setString(6, df.formatCellValue(row.getCell(6)));
                    } else {
                        importDonation.setString(6, "");
                    }
                    if (results.contains("Total Donation")) {
                        importDonation.setString(7, df.formatCellValue(row.getCell(7)));
                    } else {
                        importDonation.setString(7, "");
                    }
                    if (results.contains("Paid On")) {
                        sqlDate = new java.sql.Date(row.getCell(8).getDateCellValue().getTime());
                        importDonation.setDate(8, sqlDate);
                    } else {
                        importDonation.setDate(8, sqlDate2);
                    }
                    if (results.contains("Balance")) {
                        importDonation.setString(9, df.formatCellValue(row.getCell(9)));
                    } else {
                        importDonation.setString(9, "");
                    }
                    if (results.contains("Note")) {
                        importDonation.setString(10, df.formatCellValue(row.getCell(10)));
                    } else {
                        importDonation.setString(10, "");
                    }
                    if (results.contains("Create Date")) {
                        sqlDate = new java.sql.Date(row.getCell(11).getDateCellValue().getTime());
                        importDonation.setDate(11, sqlDate);
                    } else {
                        importDonation.setDate(11, sqlDate2); //SERT TO CURR DATE
                    }
                    if (results.contains("Update Date")) {
                        sqlDate = new java.sql.Date(row.getCell(12).getDateCellValue().getTime());
                        importDonation.setDate(12, sqlDate);
                    } else {
                        importDonation.setDate(12, sqlDate2);
                    }
                    if (results.contains("Created By")) {
                        importDonation.setString(13, df.formatCellValue(row.getCell(13)));
                    } else {
                        importDonation.setString(13, ""); //SET TO USER 
                    }
                    if (results.contains("Updated By")) {
                        importDonation.setString(14, df.formatCellValue(row.getCell(14)));
                    } else {
                        importDonation.setString(14, "");
                    }

                    importDonation.execute();
                }
            }

            wb.close();
            fileIn.close();
            importDonation.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! importNewDonation() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in DonationQueries.java/importNewDonation from excel... Most likely from extra null rows in excel");
            return 2;
        }
        closeConnection();
        return 1;
    }

    //Updates existing donations with donationID matching in excel
    public int importOldDonation(List<Integer> donationIDList, String file, ArrayList<String> results) throws FileNotFoundException, IOException {
        openConnection();
        //Open excel file
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            //'REPLACE INTO' Updates existing records and inserts new ones if pkID not found
            importDonation = conn.prepareStatement("update app.donation set MEM_ID = ?, "
                    + "DON_TYPE = ?, DHARMA_SERVICE = ?, DATE = ?, PAY_METHOD = ?, AMT_PAID = ?, "
                    + "TOTAL_DON = ?, PAY_DATE = ?, BAL = ?, NOTE = ?, "
                    + "CREATE_DATE = ?, UPDATE_DATE = ?, CREATE_BY = ?, UPDATE_BY = ?"
                    + "where DON_ID=?");
            //Start reading data from excel, row by row
            java.sql.Date sqlDate;
            java.sql.Date sqlDate2 = java.sql.Date.valueOf("9999-01-01");
            DataFormatter df = new DataFormatter();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                //For each row...
                row = sheet.getRow(i);
                //if donationID from cell, is the one we want...
                boolean flag = false;
                int id = 0;
                if (row.getCell(0) != null) {
                    id = Integer.parseInt(df.formatCellValue(row.getCell(0)));
                } else {
                    continue;
                }
                for (int n : donationIDList) {
                    if (id == n) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    if (results.contains("Member ID")) {
                        importDonation.setString(1, df.formatCellValue(row.getCell(1)));
                    } else {
                        importDonation.setString(1, "");
                    }
                    if (results.contains("Type of Donation")) {
                        importDonation.setString(2, df.formatCellValue(row.getCell(2)));
                    } else {
                        importDonation.setString(2, "");
                    }
                    if (results.contains("Dharma Service")) {
                        importDonation.setString(3, df.formatCellValue(row.getCell(3)));
                    } else {
                        importDonation.setString(3, "");
                    }
                    if (results.contains("Date of Donation")) {
                        sqlDate = new java.sql.Date(row.getCell(4).getDateCellValue().getTime());
                        importDonation.setDate(4, sqlDate);
                    } else {
                        importDonation.setDate(4, sqlDate2);
                    }
                    if (results.contains("Payment Method")) {
                        importDonation.setString(5, df.formatCellValue(row.getCell(5)));
                    } else {
                        importDonation.setString(5, "");
                    }
                    if (results.contains("Amount Paid")) {
                        importDonation.setString(6, df.formatCellValue(row.getCell(6)));
                    } else {
                        importDonation.setString(6, "");
                    }
                    if (results.contains("Total Donation")) {
                        importDonation.setString(7, df.formatCellValue(row.getCell(7)));
                    } else {
                        importDonation.setString(7, "");
                    }
                    if (results.contains("Paid On")) {
                        sqlDate = new java.sql.Date(row.getCell(8).getDateCellValue().getTime());
                        importDonation.setDate(8, sqlDate);
                    } else {
                        importDonation.setDate(8, sqlDate2);
                    }
                    if (results.contains("Balance")) {
                        importDonation.setString(9, df.formatCellValue(row.getCell(9)));
                    } else {
                        importDonation.setString(9, "");
                    }
                    if (results.contains("Note")) {
                        importDonation.setString(10, df.formatCellValue(row.getCell(10)));
                    } else {
                        importDonation.setString(10, "");
                    }
                    if (results.contains("Create Date")) {
                        sqlDate = new java.sql.Date(row.getCell(11).getDateCellValue().getTime());
                        importDonation.setDate(11, sqlDate);
                    } else {
                        importDonation.setDate(11, sqlDate2); //SERT TO CURR DATE
                    }
                    if (results.contains("Update Date")) {
                        sqlDate = new java.sql.Date(row.getCell(12).getDateCellValue().getTime());
                        importDonation.setDate(12, sqlDate);
                    } else {
                        importDonation.setDate(12, sqlDate2);
                    }
                    if (results.contains("Created By")) {
                        importDonation.setString(13, df.formatCellValue(row.getCell(13)));
                    } else {
                        importDonation.setString(13, ""); //SET TO USER 
                    }
                    if (results.contains("Updated By")) {
                        importDonation.setString(14, df.formatCellValue(row.getCell(14)));
                    } else {
                        importDonation.setString(14, "");
                    }
                    importDonation.setInt(15, Integer.parseInt(df.formatCellValue(row.getCell(0))));

                    importDonation.execute();
                }
            }

            wb.close();
            fileIn.close();
            importDonation.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! importOldDonation() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in DonationQueries.java/importOldDonation() from excel... Most likely from extra null rows in excel");
            e.printStackTrace();
            return 2;
        }
        closeConnection();
        return 1;
    }
}
