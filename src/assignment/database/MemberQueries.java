package assignment.database;

//<editor-fold defaultstate="collapsed" desc="Imports">
import assignment.model.Log;
import assignment.model.Member;
import assignment.view.HotelOverviewController;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javafx.scene.control.Alert;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
//</editor-fold>

public class MemberQueries extends DatabaseQuery {

    HotelOverviewController hotelOverview;

    PreparedStatement insertMember = null;
    PreparedStatement getAllMembers = null;
    PreparedStatement editMember = null;
    PreparedStatement deleteMember = null;
    PreparedStatement exportMember = null;
    PreparedStatement importMember = null;
    PreparedStatement importOldMember = null;
    PreparedStatement importNewMember = null;
    PreparedStatement getSessionInfo = null;
    PreparedStatement getAllMIDs = null;

    ResultSet rs = null;
    List<Member> members;
    LogQueries logQueries = new LogQueries();
    Member member = new Member(); //NOT NECCESSARY?

    public List<Member> getMembers() {
        members = new ArrayList<Member>();
        openConnection();
        try {
            getAllMembers = conn.prepareStatement("select * from app.MEMBER");
            rs = getAllMembers.executeQuery();
            while (rs.next()) {
                members.add(
                        new Member(rs.getInt("MEM_ID"), rs.getString("M_ID"),
                                rs.getString("C_NAME"), rs.getString("F_NAME"),
                                rs.getString("L_NAME"), rs.getString("GENDER"),
                                rs.getString("DOB"), rs.getString("H_PHONE"),
                                rs.getString("MOB"), rs.getString("W_PHONE"),
                                rs.getString("FAX"), rs.getString("EMAIL"),
                                rs.getString("WECHAT"), rs.getString("PREF_CONTACT"),
                                rs.getString("LANG1"), rs.getString("LANG2"),
                                rs.getString("EDU_EMP"), rs.getString("NATION"),
                                rs.getString("ADDRESS"), rs.getString("SUBURB"),
                                rs.getString("POSTCODE"), rs.getString("STATE"),
                                rs.getString("DHARMA"), rs.getDate("DATE_REFUGE").toLocalDate(),
                                rs.getString("NOTE"), rs.getDate("CREATE_DATE").toLocalDate(),
                                rs.getDate("UPDATE_DATE").toLocalDate(), rs.getString("CREATE_BY"),
                                rs.getString("UPDATE_BY")));
            }
            rs.close();
            getAllMembers.close();
        } catch (SQLException ex) {
            System.out.println("getMember() error!");
        }
        closeConnection();
        return members;
    }

    int latestIDForYear = 0;
    private String makeMID() {
        //already added extra ?, TODO remove MID from bot, add to here :
        //Generates mID: formatted PK (YYYY-00001)
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = now.get(Calendar.YEAR);
        String mID = year + "-";
        //Get latest id for that event combination
        latestIDForYear = latestIDForYear(mID)-1;

        return mID;
    }

    public int insertMember(Member toInsert) {
        int userGeneratedId = -1;
        //already added extra ?, TODO remove MID from bot, add to here :
        //Generates mID: formatted PK (YYYY-00001)
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = now.get(Calendar.YEAR);
        String mID = year + "-";
        //Get latest id for that event combination
        latestIDForYear = latestIDForYear(mID);
        mID = mID + String.format("%05d", latestIDForYear);
        toInsert.setMID(mID);

        openConnection();
        try {
            insertMember = conn.prepareStatement("insert into app.member "
                    + "(M_ID, C_NAME, F_NAME, L_NAME, GENDER, DOB, H_PHONE, MOB, W_PHONE, "
                    + "FAX, EMAIL, WECHAT, PREF_CONTACT, LANG1, LANG2, EDU_EMP, "
                    + "NATION, ADDRESS, SUBURB, POSTCODE, STATE, DHARMA, DATE_REFUGE, "
                    + "NOTE, CREATE_DATE, UPDATE_DATE, CREATE_BY, UPDATE_BY)"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertMember.setString(1, toInsert.getMID());
            insertMember.setString(2, toInsert.getCName());
            insertMember.setString(3, toInsert.getFName());
            insertMember.setString(4, toInsert.getLName());
            insertMember.setString(5, toInsert.getGender());
            insertMember.setString(6, toInsert.getDob());
            insertMember.setString(7, toInsert.getHPhone());
            insertMember.setString(8, toInsert.getMob());
            insertMember.setString(9, toInsert.getWPhone());
            insertMember.setString(10, toInsert.getFax());
            insertMember.setString(11, toInsert.getEmail());
            insertMember.setString(12, toInsert.getWechat());
            insertMember.setString(13, toInsert.getPrefContact());
            insertMember.setString(14, toInsert.getLang1());
            insertMember.setString(15, toInsert.getLang2());
            insertMember.setString(16, toInsert.getEduEmp());
            insertMember.setString(17, toInsert.getNation());
            insertMember.setString(18, toInsert.getAddress());
            insertMember.setString(19, toInsert.getSuburb());
            insertMember.setString(20, toInsert.getPostcode());
            insertMember.setString(21, toInsert.getState());
            insertMember.setString(22, toInsert.getDharma());
            insertMember.setDate(23, toInsert.getDateRefugeToDate());
            insertMember.setString(24, toInsert.getNote());
            insertMember.setDate(25, toInsert.getCreateDateToDate());
            insertMember.setDate(26, toInsert.getUpdateOnToDate());
            insertMember.setString(27, toInsert.getCreateBy());
            insertMember.setString(28, toInsert.getUpdateBy());
            insertMember.executeUpdate();

            //Gets latest memberID from db
            rs = insertMember.getGeneratedKeys();
            rs.next();
            toInsert.setMemID(rs.getInt(1));

            rs.close();
            insertMember.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! insertMember() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
        return userGeneratedId;
    }

    //Counts all records with specific year
    public int latestIDForYear(String mID) { //mID passed in is <year-> atm e.g. 2009-
        ArrayList<String> id = getMID();

        int i = 1;
        //Count number of records in year
        for (String m : id) {
            if (!(m == null) && m.contains(mID)) { //if m (retrieved from db query) not empty and the year match with mID passed in...
                i++;
            }
        }
        return i;
    }

    public ArrayList<String> getMID() {
        ArrayList id = new ArrayList<String>();
        openConnection();
        try {
            getAllMIDs = conn.prepareStatement("select M_ID from app.MEMBER ");
            rs = getAllMIDs.executeQuery();
            while (rs.next()) {
                id.add(rs.getString("M_ID"));
            }
            rs.close();
            getAllMIDs.close();
        } catch (SQLException ex) {
            System.out.println("getMID() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return id;
    }

    public void updateMember(Member toInsert) {
        openConnection();

        try {
            editMember = conn.prepareStatement("update app.member set C_NAME = ?, "
                    + "F_NAME = ?, L_NAME = ?, GENDER = ?, DOB = ?, H_PHONE = ?, "
                    + "MOB = ?, W_PHONE = ?, FAX = ?, EMAIL = ?, WECHAT = ?, "
                    + "PREF_CONTACT = ?, LANG1 = ?, LANG2 = ?, EDU_EMP = ?, "
                    + "NATION = ?, ADDRESS = ?, SUBURB = ?, POSTCODE = ?, "
                    + "STATE = ?, DHARMA = ?, DATE_REFUGE = ?, NOTE = ?, "
                    + "UPDATE_DATE = ?, UPDATE_BY = ?"
                    + "where MEM_ID=?");
            editMember.setString(1, toInsert.getCName());
            editMember.setString(2, toInsert.getFName());
            editMember.setString(3, toInsert.getLName());
            editMember.setString(4, toInsert.getGender());
            editMember.setString(5, toInsert.getDob());
            editMember.setString(6, toInsert.getHPhone());
            editMember.setString(7, toInsert.getMob());
            editMember.setString(8, toInsert.getWPhone());
            editMember.setString(9, toInsert.getFax());
            editMember.setString(10, toInsert.getEmail());
            editMember.setString(11, toInsert.getWechat());
            editMember.setString(12, toInsert.getPrefContact());
            editMember.setString(13, toInsert.getLang1());
            editMember.setString(14, toInsert.getLang2());
            editMember.setString(15, toInsert.getEduEmp());
            editMember.setString(16, toInsert.getNation());
            editMember.setString(17, toInsert.getAddress());
            editMember.setString(18, toInsert.getSuburb());
            editMember.setString(19, toInsert.getPostcode());
            editMember.setString(20, toInsert.getState());
            editMember.setString(21, toInsert.getDharma());
            editMember.setDate(22, toInsert.getDateRefugeToDate());
            editMember.setString(23, toInsert.getNote());
            editMember.setDate(24, toInsert.getUpdateOnToDate());
            editMember.setString(25, toInsert.getUpdateBy());
            editMember.setInt(26, toInsert.getMemID());

            editMember.executeUpdate();
            editMember.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! editMember() ERROR!");
            ex.printStackTrace();
            //Show error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Edit Member Failed");
            alert.setContentText("Failed to save new information.\nDon't use too much characters in the fields.");
            alert.showAndWait();
        }

        closeConnection();
    }

    public void deleteMember(Member toDelete) {
        openConnection();
        try {
            deleteMember = conn.prepareStatement("delete from app.MEMBER where MEM_ID = ?");
            deleteMember.setInt(1, toDelete.getMemID());
            deleteMember.execute();
        } catch (SQLException ex) {
            System.out.println("ERROR! deleteMember()!");
            ex.printStackTrace();
        }
        closeConnection();
    }

    //public void exportMember(List<Member> toExport) throws FileNotFoundException, IOException {
    public int exportMember(ArrayList<String> results, String fileName) throws FileNotFoundException, IOException {
        openConnection();
        try {   //**********CAN try append multiple MEM_ID like toString for selective mem export
            String query = "select * from app.MEMBER";
            exportMember = conn.prepareStatement(query);
            rs = exportMember.executeQuery();

            XSSFWorkbook wb = new XSSFWorkbook(); //for earlier ver. user HSSF
            XSSFSheet sheet = wb.createSheet("Member Details");
            XSSFRow header = sheet.createRow(0);

            //Make header fields
            int i = 0;
            for (String s : results) {
                header.createCell(i).setCellValue(s);
                sheet.autoSizeColumn(i);
                i++;
            }

            //Make following data rows
            int rowindex = 1;
            while (rs.next()) {
                XSSFRow row = sheet.createRow(rowindex);

                int j = 0; //columnIndex
                for (String s : results) {
                    if (s.equals("Database Member ID")) {
                        row.createCell(j).setCellValue(rs.getString("MEM_ID"));
                    } else if (s.equals("Member_ID")) {
                        row.createCell(j).setCellValue(rs.getString("M_ID"));
                    } else if (s.equals("Other Name")) {
                        row.createCell(j).setCellValue(rs.getString("C_NAME"));
                    } else if (s.equals("First Name")) {
                        row.createCell(j).setCellValue(rs.getString("F_NAME"));
                    } else if (s.equals("Last Name")) {
                        row.createCell(j).setCellValue(rs.getString("L_NAME"));
                    } else if (s.equals("Gender")) {
                        row.createCell(j).setCellValue(rs.getString("GENDER"));
                    } else if (s.equals("Date of birth")) {
                        row.createCell(j).setCellValue(rs.getString("DOB"));
                    } else if (s.equals("Home Phone")) {
                        row.createCell(j).setCellValue(rs.getString("H_PHONE"));
                    } else if (s.equals("Mobile")) {
                        row.createCell(j).setCellValue(rs.getString("MOB"));
                    } else if (s.equals("Work Phone")) {
                        row.createCell(j).setCellValue(rs.getString("W_PHONE"));
                    } else if (s.equals("Fax")) {
                        row.createCell(j).setCellValue(rs.getString("FAX"));
                    } else if (s.equals("Email")) {
                        row.createCell(j).setCellValue(rs.getString("EMAIL"));
                    } else if (s.equals("WeChat ID")) {
                        row.createCell(j).setCellValue(rs.getString("WECHAT"));
                    } else if (s.equals("Preferred Contact Method")) {
                        row.createCell(j).setCellValue(rs.getString("PREF_CONTACT"));
                    } else if (s.equals("Language 1")) {
                        row.createCell(j).setCellValue(rs.getString("LANG1"));
                    } else if (s.equals("Skills/Hobbies")) {
                        row.createCell(j).setCellValue(rs.getString("SKILL"));
                    } else if (s.equals("Education")) {
                        row.createCell(j).setCellValue(rs.getString("EDU"));
                    } else if (s.equals("Employment")) {
                        row.createCell(j).setCellValue(rs.getString("EMPLOYMENT"));
                    } else if (s.equals("Nationality")) {
                        row.createCell(j).setCellValue(rs.getString("NATION"));
                    } else if (s.equals("Address")) {
                        row.createCell(j).setCellValue(rs.getString("ADDRESS"));
                    } else if (s.equals("Suburb")) {
                        row.createCell(j).setCellValue(rs.getString("SUBURB"));
                    } else if (s.equals("Postcode")) {
                        row.createCell(j).setCellValue(rs.getString("POSTCODE"));
                    } else if (s.equals("State")) {
                        row.createCell(j).setCellValue(rs.getString("STATE"));
                    } else if (s.equals("Dharma Name")) {
                        row.createCell(j).setCellValue(rs.getString("DHARMA"));
                    } else if (s.equals("Date of Taking Refuge")) {
                        row.createCell(j).setCellValue(rs.getString("DATE_REFUGE"));
                    } else if (s.equals("Note")) {
                        row.createCell(j).setCellValue(rs.getString("NOTE"));
                    } else if (s.equals("Create Date")) {
                        row.createCell(j).setCellValue(rs.getString("CREATE_DATE"));
                    } else if (s.equals("Update Date")) {
                        row.createCell(j).setCellValue(rs.getString("UPDATE_DATE"));
                    } else if (s.equals("Created By")) {
                        row.createCell(j).setCellValue(rs.getString("CREATE_BY"));
                    } else if (s.equals("Updated By")) {
                        row.createCell(j).setCellValue(rs.getString("UPDATE_BY"));
                    }
                    j++;
                }
                rowindex++;
            }

            FileOutputStream fileOut = new FileOutputStream(fileName);
            //FileOutputStream fileOut = new FileOutputStream("MemberDetails.xlsx");
            wb.write(fileOut);
            fileOut.close();
            exportMember.close();
            rs.close();

            //exportMember = conn.prepareStatement("select * from app.MEMBER where MEM_ID = ?");
            //exportMember.setInt(1, toExport.getMemID());
            //exportMember.execute();
        } catch (SQLException ex) {
            System.out.println("ERROR! exportMember()!");
            //ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            return 0;
        }
        closeConnection();
        return 1;
    }

    public List<String> getBlueRows(String file) throws FileNotFoundException, IOException {
        //Open excel file
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //grab blue row (the one that has the column names e.g. memID, chinese name, first name...)
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

    private List<Integer> newMemIndexList = new ArrayList<>();

    public List<Integer> getNewMemIndexList() {
        return newMemIndexList;
    }

    public List<Integer> getMemID(String file) throws FileNotFoundException, IOException {
        //Open excel file
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //Put data into arrayList
        List<Integer> memIDlist = new ArrayList<>();
        int rowCount = sheet.getPhysicalNumberOfRows();
        DataFormatter df = new DataFormatter();
        for (int i = 1; i < rowCount; i++) {
            row = sheet.getRow(i);
            //Store memID into arraylist
            if (row.getCell(0) != null && !(df.formatCellValue(row.getCell(0)).equals(""))) { //OR contains a number, not empty string
                memIDlist.add(Integer.parseInt(df.formatCellValue(row.getCell(0))));
            } else { //else has no memID, toInsertNewMember() later
                newMemIndexList.add(i); //Store excel row index for accessing later
            }
        }

        return memIDlist;
    }

    public List<Log> getSessionDetails(int userID) {
        List<Log> sessionDetail = new ArrayList<Log>();
        openConnection();
        try {
            getSessionInfo = conn.prepareStatement("select USERID, EMPFIRSTNAME, EMPLASTNAME from app.EMPLOYEE "
                    + "where (USERID = ?)");
            getSessionInfo.setInt(1, userID);
            rs = getSessionInfo.executeQuery();
            while (rs.next()) {
                sessionDetail.add(
                        new Log(rs.getInt("userID"), rs.getString("empFirstName"), rs.getString("empLastName")));
            }
            rs.close();
            getSessionInfo.close();
        } catch (SQLException ex) {
            System.out.println("getLoginDetails() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return sessionDetail;
    }

    //Imports each row of excel file, no restrictions
    //So all mID are filled in??
    public int importMember(String file, ArrayList<String> results, String empName) throws FileNotFoundException, IOException {
        String mID = makeMID();
        openConnection();
        //Open excel file
        //FileInputStream fileIn = new FileInputStream(new File("MemberImport.xlsx"));
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            importMember = conn.prepareStatement("insert into app.member "
                    + "(M_ID, C_NAME, F_NAME, L_NAME, GENDER, DOB, H_PHONE, MOB, W_PHONE, "
                    + "FAX, EMAIL, WECHAT, PREF_CONTACT, LANG1, LANG2, EDU_EMP, "
                    + "NATION, ADDRESS, SUBURB, POSTCODE, STATE, DHARMA, DATE_REFUGE, "
                    + "NOTE, CREATE_DATE, UPDATE_DATE, CREATE_BY, UPDATE_BY)"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            //Put into drop down list ######==>calls initialise method in DbLinkDialogController.java
            //Start reading data from excel, row by row
            java.sql.Date sqlDate;
            java.sql.Date sqlDate2 = java.sql.Date.valueOf("9999-01-01");
            //Get current time:
            Timestamp stamp = new Timestamp(System.currentTimeMillis());
            Date date = new Date(stamp.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");   //dd/MM/yyyy 
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+10"));
            String currDate = sdf.format(date);
            java.sql.Date curDate = java.sql.Date.valueOf(currDate);
            DataFormatter df = new DataFormatter();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                if (results.contains("Member ID")) {
                    importMember.setString(1, df.formatCellValue(row.getCell(1)));
                } else { //doesn't have a mID assigned, make new mID
                    importMember.setString(1, mID + String.format("%05d", latestIDForYear+=1));
                }
                if (results.contains("Chinese Name")) {
                    importMember.setString(2, df.formatCellValue(row.getCell(2)));
                } else {
                    importMember.setString(2, "");
                }
                if (results.contains("First Name")) {
                    importMember.setString(3, df.formatCellValue(row.getCell(3)));
                } else {
                    importMember.setString(3, "");
                }
                if (results.contains("Last Name")) {
                    importMember.setString(4, df.formatCellValue(row.getCell(4)));
                } else {
                    importMember.setString(4, "");
                }
                if (results.contains("Gender")) {
                    importMember.setString(5, df.formatCellValue(row.getCell(5)));
                } else {
                    importMember.setString(5, "");
                }
                if (results.contains("Date of birth")) {
                    importMember.setString(6, df.formatCellValue(row.getCell(6)));
                } else {
                    importMember.setString(6, "");
                }
                if (results.contains("Home Phone")) {
                    importMember.setString(7, df.formatCellValue(row.getCell(7)));
                } else {
                    importMember.setString(7, "");
                }
                if (results.contains("Mobile")) {
                    importMember.setString(8, df.formatCellValue(row.getCell(8)));
                } else {
                    importMember.setString(8, "");
                }
                if (results.contains("Work Phone")) {
                    importMember.setString(9, df.formatCellValue(row.getCell(9)));
                } else {
                    importMember.setString(9, "");
                }
                if (results.contains("Fax")) {
                    importMember.setString(10, df.formatCellValue(row.getCell(10)));
                } else {
                    importMember.setString(10, "");
                }
                if (results.contains("Email")) {
                    importMember.setString(11, df.formatCellValue(row.getCell(11)));
                } else {
                    importMember.setString(11, "");
                }
                if (results.contains("WeChat ID")) {
                    importMember.setString(12, df.formatCellValue(row.getCell(12)));
                } else {
                    importMember.setString(12, "");
                }
                if (results.contains("Preferred Contact Method")) {
                    importMember.setString(13, df.formatCellValue(row.getCell(13)));
                } else {
                    importMember.setString(13, "");
                }
                if (results.contains("Language 1")) {
                    importMember.setString(14, df.formatCellValue(row.getCell(14)));
                } else {
                    importMember.setString(14, "");
                }
                if (results.contains("Language 2")) {
                    importMember.setString(15, df.formatCellValue(row.getCell(15)));
                } else {
                    importMember.setString(15, "");
                }
                if (results.contains("Education and Employment")) {
                    importMember.setString(16, df.formatCellValue(row.getCell(16)));
                } else {
                    importMember.setString(16, "");
                }
                if (results.contains("Nationality")) {
                    importMember.setString(17, df.formatCellValue(row.getCell(17)));
                } else {
                    importMember.setString(17, "");
                }
                if (results.contains("Address")) {
                    importMember.setString(18, df.formatCellValue(row.getCell(18)));
                } else {
                    importMember.setString(18, "");
                }
                if (results.contains("Suburb")) {
                    importMember.setString(19, df.formatCellValue(row.getCell(19)));
                } else {
                    importMember.setString(19, "");
                }
                if (results.contains("Postcode")) {
                    importMember.setString(20, df.formatCellValue(row.getCell(20)));
                } else {
                    importMember.setString(20, "");
                }
                if (results.contains("State")) {
                    importMember.setString(21, df.formatCellValue(row.getCell(21)));
                } else {
                    importMember.setString(21, "");
                }
                if (results.contains("Dharma Name")) {
                    importMember.setString(22, df.formatCellValue(row.getCell(22)));
                } else {
                    importMember.setString(22, "");
                }
                if (results.contains("Date of Taking Refuge")) {
                    sqlDate = new java.sql.Date(row.getCell(23).getDateCellValue().getTime());
                    importMember.setDate(23, sqlDate);
                } else {
                    importMember.setDate(23, sqlDate2);
                }
                if (results.contains("Note")) {
                    importMember.setString(24, df.formatCellValue(row.getCell(24)));
                } else {
                    importMember.setString(24, "");
                }
                if (results.contains("Create Date")) {
                    sqlDate = new java.sql.Date(row.getCell(25).getDateCellValue().getTime());
                    importMember.setDate(25, sqlDate);
                } else {
                    importMember.setDate(25, curDate); //SERT TO CURR DATE
                }
                if (results.contains("Update Date")) {
                    sqlDate = new java.sql.Date(row.getCell(26).getDateCellValue().getTime());
                    importMember.setDate(26, sqlDate);
                } else {
                    importMember.setDate(26, sqlDate2);
                }
                if (results.contains("Created By")) {
                    importMember.setString(27, df.formatCellValue(row.getCell(27)));
                } else {
                    importMember.setString(27, empName); //SET TO USER 
                }
                if (results.contains("Updated By")) {
                    importMember.setString(28, df.formatCellValue(row.getCell(28)));
                } else {
                    importMember.setString(28, "");
                }

                importMember.execute();
            }

            wb.close();
            fileIn.close();
            importMember.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! importMember() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in MemberQueries.java/importMember from excel... Most likely from extra null rows in excel");
            return 2;
        }
        closeConnection();
        return 1;
    }

    //Imports only rows that contains deleted memID from db OR rows without memID ===> assigns these rows with new memID
    public int importNewMember(List<Integer> newMemList, List<Integer> newMemIndexList, String file, ArrayList<String> results, String empName) throws FileNotFoundException, IOException {
        String mID = makeMID();
        openConnection();
        //Open excel file
        //FileInputStream fileIn = new FileInputStream(new File("MemberImport.xlsx"));
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            importNewMember = conn.prepareStatement("insert into app.member "
                    + "(M_ID, C_NAME, F_NAME, L_NAME, GENDER, DOB, H_PHONE, MOB, W_PHONE, "
                    + "FAX, EMAIL, WECHAT, PREF_CONTACT, LANG1, LANG2, EDU_EMP, "
                    + "NATION, ADDRESS, SUBURB, POSTCODE, STATE, DHARMA, DATE_REFUGE, "
                    + "NOTE, CREATE_DATE, UPDATE_DATE, CREATE_BY, UPDATE_BY)"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            //Put into drop down list ######==>calls initialise method in DbLinkDialogController.java
            //Start reading data from excel, row by row
            java.sql.Date sqlDate;
            java.sql.Date sqlDate2 = java.sql.Date.valueOf("9999-01-01"); //Used for null date fields, will reload back as ""
            //Get current time:
            Timestamp stamp = new Timestamp(System.currentTimeMillis());
            Date date = new Date(stamp.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");   //dd/MM/yyyy 
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+10"));
            String currDate = sdf.format(date);
            java.sql.Date curDate = java.sql.Date.valueOf(currDate);
            //Dataformatter automatically reads excel cell in correct type
            DataFormatter df = new DataFormatter();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                //For each row...
                row = sheet.getRow(i);
                //**************** See if curr row is relevant ***************** 
                boolean flag, flag2;
                flag = flag2 = false;
                int id = 0;
                //if it is the row index we want... then process row             
                for (int n : newMemIndexList) {
                    if (i == n) {
                        flag = true;
                        break; //blank row found
                    }
                }

                //OR if it is the memID we want... then process row
                if (row.getCell(0) != null && !flag && !(df.formatCellValue(row.getCell(0)).equals(""))) {
                    id = Integer.parseInt(df.formatCellValue(row.getCell(0)));

                    for (int n : newMemList) {
                        if (id == n) {
                            flag2 = true;
                            break;
                        }
                    }
                }
                //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                if (flag || flag2) {

                    if (results.contains("Member ID")) {
                        try {
                            importMember.setString(1, df.formatCellValue(row.getCell(1)));
                        } catch (Exception e) { //'Member ID' field is null
                            importNewMember.setString(1, mID + String.format("%05d", latestIDForYear+=1)); //increment latest ID by 1
                        }
                    } else {
                        importNewMember.setString(1, mID + String.format("%05d", latestIDForYear+=1));
                    }
                    if (results.contains("Other Name")) {
                        importNewMember.setString(2, df.formatCellValue(row.getCell(2)));
                    } else {
                        importNewMember.setString(2, "");
                    }
                    if (results.contains("First Name")) {
                        importNewMember.setString(3, df.formatCellValue(row.getCell(3)));
                    } else {
                        importNewMember.setString(3, "");
                    }
                    if (results.contains("Last Name")) {
                        importNewMember.setString(4, df.formatCellValue(row.getCell(4)));
                    } else {
                        importNewMember.setString(4, "");
                    }
                    if (results.contains("Gender")) {
                        importNewMember.setString(5, df.formatCellValue(row.getCell(5)));
                    } else {
                        importNewMember.setString(5, "");
                    }
                    if (results.contains("Date of birth")) {
                        importNewMember.setString(6, df.formatCellValue(row.getCell(6)));
                    } else {
                        importNewMember.setString(6, "");
                    }
                    if (results.contains("Home Phone")) {
                        importNewMember.setString(7, df.formatCellValue(row.getCell(7)));
                    } else {
                        importNewMember.setString(7, "");
                    }
                    if (results.contains("Mobile")) {
                        importNewMember.setString(8, df.formatCellValue(row.getCell(8)));
                    } else {
                        importNewMember.setString(8, "");
                    }
                    if (results.contains("Work Phone")) {
                        importNewMember.setString(9, df.formatCellValue(row.getCell(9)));
                    } else {
                        importNewMember.setString(9, "");
                    }
                    if (results.contains("Fax")) {
                        importNewMember.setString(10, df.formatCellValue(row.getCell(10)));
                    } else {
                        importNewMember.setString(10, "");
                    }
                    if (results.contains("Email")) {
                        importNewMember.setString(11, df.formatCellValue(row.getCell(11)));
                    } else {
                        importNewMember.setString(11, "");
                    }
                    if (results.contains("WeChat ID")) {
                        importNewMember.setString(12, df.formatCellValue(row.getCell(12)));
                    } else {
                        importNewMember.setString(12, "");
                    }
                    if (results.contains("Preferred Contact Method")) {
                        importNewMember.setString(13, df.formatCellValue(row.getCell(13)));
                    } else {
                        importNewMember.setString(13, "");
                    }
                    if (results.contains("Language 1")) {
                        importNewMember.setString(14, df.formatCellValue(row.getCell(14)));
                    } else {
                        importNewMember.setString(14, "");
                    }
                    if (results.contains("Language 2")) {
                        importNewMember.setString(15, df.formatCellValue(row.getCell(15)));
                    } else {
                        importNewMember.setString(15, "");
                    }
                    if (results.contains("Education and Employment")) {
                        importNewMember.setString(16, df.formatCellValue(row.getCell(16)));
                    } else {
                        importNewMember.setString(16, "");
                    }
                    if (results.contains("Nationality")) {
                        importNewMember.setString(17, df.formatCellValue(row.getCell(17)));
                    } else {
                        importNewMember.setString(17, "");
                    }
                    if (results.contains("Address")) {
                        importNewMember.setString(18, df.formatCellValue(row.getCell(18)));
                    } else {
                        importNewMember.setString(18, "");
                    }
                    if (results.contains("Suburb")) {
                        importNewMember.setString(19, df.formatCellValue(row.getCell(19)));
                    } else {
                        importNewMember.setString(19, "");
                    }
                    if (results.contains("Postcode")) {
                        importNewMember.setString(20, df.formatCellValue(row.getCell(20)));
                    } else {
                        importNewMember.setString(20, "");
                    }
                    if (results.contains("State")) {
                        importNewMember.setString(21, df.formatCellValue(row.getCell(21)));
                    } else {
                        importNewMember.setString(21, "");
                    }
                    if (results.contains("Dharma Name")) {
                        importNewMember.setString(22, df.formatCellValue(row.getCell(22)));
                    } else {
                        importNewMember.setString(22, "");
                    }
                    if (results.contains("Date of Taking Refuge")) {
                        sqlDate = new java.sql.Date(row.getCell(23).getDateCellValue().getTime());
                        importNewMember.setDate(23, sqlDate);
                    } else {
                        importNewMember.setDate(23, sqlDate2);
                    }
                    if (results.contains("Note")) {
                        importNewMember.setString(24, df.formatCellValue(row.getCell(24)));
                    } else {
                        importNewMember.setString(24, "");
                    }
                    if (results.contains("Create Date")) {
                        sqlDate = new java.sql.Date(row.getCell(25).getDateCellValue().getTime());
                        importNewMember.setDate(25, sqlDate);
                    } else {
                        importNewMember.setDate(25, curDate); //SERT TO CURR DATE
                    }
                    if (results.contains("Update Date")) {
                        sqlDate = new java.sql.Date(row.getCell(26).getDateCellValue().getTime());
                        importNewMember.setDate(26, sqlDate);
                    } else {
                        importNewMember.setDate(26, sqlDate2);
                    }
                    if (results.contains("Created By")) {
                        importNewMember.setString(27, df.formatCellValue(row.getCell(27)));
                    } else {
                        importNewMember.setString(27, empName); //SET TO USER 
                    }
                    if (results.contains("Updated By")) {
                        importNewMember.setString(28, df.formatCellValue(row.getCell(28)));
                    } else {
                        importNewMember.setString(28, "");
                    }

                    importNewMember.execute();
                }
            }

            wb.close();
            fileIn.close();
            importNewMember.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! importNewMember() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in MemberQueries.java/importNewMember from excel... Most likely from extra null rows in excel");
            e.printStackTrace();
            return 2;
        }
        closeConnection();
        return 1;
    }

    //Updates existing members with memID matching in excel
    public int importOldMember(List<Integer> memIDList, String file, ArrayList<String> results, String empName) throws FileNotFoundException, IOException {
        String mID = makeMID(); //This is done before openConnection() to prevent losing connection
        openConnection();
        //Open excel file
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            //'REPLACE INTO' Updates existing records and inserts new ones if pkID not found
            importOldMember = conn.prepareStatement("update app.member set "
                    + "M_ID = ?, C_NAME = ?, "
                    + "F_NAME = ?, L_NAME = ?, GENDER = ?, DOB = ?, H_PHONE = ?, "
                    + "MOB = ?, W_PHONE = ?, FAX = ?, EMAIL = ?, WECHAT = ?, "
                    + "PREF_CONTACT = ?, LANG1 = ?, LANG2 = ?, EDU_EMP = ?, "
                    + "NATION = ?, ADDRESS = ?, SUBURB = ?, POSTCODE = ?, "
                    + "STATE = ?, DHARMA = ?, DATE_REFUGE = ?, NOTE = ?, "
                    + "CREATE_DATE = ?, UPDATE_DATE = ?, CREATE_BY = ?, UPDATE_BY = ?"
                    + "where MEM_ID=?");
            //Start reading data from excel, row by row
            java.sql.Date sqlDate;
            java.sql.Date sqlDate2 = java.sql.Date.valueOf("9999-01-01");
            //Get current time:
            Timestamp stamp = new Timestamp(System.currentTimeMillis());
            Date date = new Date(stamp.getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");   //dd/MM/yyyy 
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+10"));
            String currDate = sdf.format(date);
            java.sql.Date curDate = java.sql.Date.valueOf(currDate);
            DataFormatter df = new DataFormatter();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                //For each row...
                row = sheet.getRow(i);
                //if memID from cell, is the one we want...
                boolean flag = false;
                int id = 0;
                if (row.getCell(0) != null && !(df.formatCellValue(row.getCell(0)).equals(""))) {
                    id = Integer.parseInt(df.formatCellValue(row.getCell(0)));
                } else {
                    continue;
                }
                for (int n : memIDList) {
                    if (id == n) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    if (results.contains("Member ID")) {
                        importOldMember.setString(1, df.formatCellValue(row.getCell(1)));
                    } else {
                        importOldMember.setString(1, mID + String.format("%05d", latestIDForYear+=1));
                    }
                    if (results.contains("Chinese Name")) {
                        importOldMember.setString(2, df.formatCellValue(row.getCell(2)));
                    } else {
                        importOldMember.setString(2, "");
                    }
                    if (results.contains("First Name")) {
                        importOldMember.setString(3, df.formatCellValue(row.getCell(3)));
                    } else {
                        importOldMember.setString(3, "");
                    }
                    if (results.contains("Last Name")) {
                        importOldMember.setString(4, df.formatCellValue(row.getCell(4)));
                    } else {
                        importOldMember.setString(4, "");
                    }
                    if (results.contains("Gender")) {
                        importOldMember.setString(5, df.formatCellValue(row.getCell(5)));
                    } else {
                        importOldMember.setString(5, "");
                    }
                    if (results.contains("Date of birth")) {
                        importOldMember.setString(6, df.formatCellValue(row.getCell(6)));
                    } else {
                        importOldMember.setString(6, "");
                    }
                    if (results.contains("Home Phone")) {
                        importOldMember.setString(7, df.formatCellValue(row.getCell(7)));
                    } else {
                        importOldMember.setString(7, "");
                    }
                    if (results.contains("Mobile")) {
                        importOldMember.setString(8, df.formatCellValue(row.getCell(8)));
                    } else {
                        importOldMember.setString(8, "");
                    }
                    if (results.contains("Work Phone")) {
                        importOldMember.setString(9, df.formatCellValue(row.getCell(9)));
                    } else {
                        importOldMember.setString(9, "");
                    }
                    if (results.contains("Fax")) {
                        importOldMember.setString(10, df.formatCellValue(row.getCell(10)));
                    } else {
                        importOldMember.setString(10, "");
                    }
                    if (results.contains("Email")) {
                        importOldMember.setString(11, df.formatCellValue(row.getCell(11)));
                    } else {
                        importOldMember.setString(11, "");
                    }
                    if (results.contains("WeChat ID")) {
                        importOldMember.setString(12, df.formatCellValue(row.getCell(12)));
                    } else {
                        importOldMember.setString(12, "");
                    }
                    if (results.contains("Preferred Contact Method")) {
                        importOldMember.setString(13, df.formatCellValue(row.getCell(13)));
                    } else {
                        importOldMember.setString(13, "");
                    }
                    if (results.contains("Language 1")) {
                        importOldMember.setString(14, df.formatCellValue(row.getCell(14)));
                    } else {
                        importOldMember.setString(14, "");
                    }
                    if (results.contains("Language 2")) {
                        importOldMember.setString(15, df.formatCellValue(row.getCell(15)));
                    } else {
                        importOldMember.setString(15, "");
                    }
                    if (results.contains("Education and Employment")) {
                        importOldMember.setString(16, df.formatCellValue(row.getCell(16)));
                    } else {
                        importOldMember.setString(16, "");
                    }
                    if (results.contains("Nationality")) {
                        importOldMember.setString(17, df.formatCellValue(row.getCell(17)));
                    } else {
                        importOldMember.setString(17, "");
                    }
                    if (results.contains("Address")) {
                        importOldMember.setString(18, df.formatCellValue(row.getCell(18)));
                    } else {
                        importOldMember.setString(18, "");
                    }
                    if (results.contains("Suburb")) {
                        importOldMember.setString(19, df.formatCellValue(row.getCell(19)));
                    } else {
                        importOldMember.setString(19, "");
                    }
                    if (results.contains("Postcode")) {
                        importOldMember.setString(20, df.formatCellValue(row.getCell(20)));
                    } else {
                        importOldMember.setString(20, "");
                    }
                    if (results.contains("State")) {
                        importOldMember.setString(21, df.formatCellValue(row.getCell(21)));
                    } else {
                        importOldMember.setString(21, "");
                    }
                    if (results.contains("Dharma Name")) {
                        importOldMember.setString(22, df.formatCellValue(row.getCell(22)));
                    } else {
                        importOldMember.setString(22, "");
                    }
                    if (results.contains("Date of Taking Refuge")) {
                        sqlDate = new java.sql.Date(row.getCell(23).getDateCellValue().getTime());
                        importOldMember.setDate(23, sqlDate);
                    } else {
                        importOldMember.setDate(23, sqlDate2);
                    }
                    if (results.contains("Note")) {
                        importOldMember.setString(24, df.formatCellValue(row.getCell(24)));
                    } else {
                        importOldMember.setString(24, "");
                    }
                    if (results.contains("Create Date")) {
                        sqlDate = new java.sql.Date(row.getCell(25).getDateCellValue().getTime());
                        importOldMember.setDate(25, sqlDate);
                    } else {
                        importOldMember.setDate(25, curDate); //SET TO CURR DATE
                    }
                    if (results.contains("Update Date")) {
                        sqlDate = new java.sql.Date(row.getCell(26).getDateCellValue().getTime());
                        importOldMember.setDate(26, sqlDate);
                    } else {
                        importOldMember.setDate(26, sqlDate2);
                    }
                    if (results.contains("Created By")) {
                        importOldMember.setString(27, df.formatCellValue(row.getCell(27)));
                    } else {
                        importOldMember.setString(27, empName); //SET TO USER 
                    }
                    if (results.contains("Updated By")) {
                        importOldMember.setString(28, df.formatCellValue(row.getCell(28)));
                    } else {
                        importOldMember.setString(28, "");
                    }
                    importOldMember.setInt(29, Integer.parseInt(df.formatCellValue(row.getCell(0))));

                    importOldMember.execute();
                }
            }

            wb.close();
            fileIn.close();
            importOldMember.close();
            rs.close();
            System.gc();// force garbage collection to unload the EmbeddedDriver so Derby can be restarted

        } catch (SQLException ex) {
            System.out.println("ERROR! importOldMember() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in MemberQueries.java/importOldMember() from excel... Most likely from extra null rows in excel");
            e.printStackTrace();
            return 2;
        }
        closeConnection();
        return 1;
    }
}
