package assignment.database;

//<editor-fold defaultstate="collapsed" desc="Imports">
import assignment.model.Member;
import assignment.model.PaiWei;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
//</editor-fold>

public class PaiWeiQueries extends DatabaseQuery {

    PreparedStatement insertPaiWei = null;
    PreparedStatement getAllPaiWeis = null;
    PreparedStatement editPaiWei = null;
    PreparedStatement deletePaiWei = null;
    PreparedStatement exportPaiWei = null;
    PreparedStatement importPaiWei = null;
    PreparedStatement findMember = null;

    ResultSet rs = null;
    List<PaiWei> paiWeis;

    public List<PaiWei> getPaiWeis() {
        paiWeis = new ArrayList<PaiWei>();
        openConnection();
        try {
            getAllPaiWeis = conn.prepareStatement("select * from app.PAIWEI");
            rs = getAllPaiWeis.executeQuery();
            while (rs.next()) {
                paiWeis.add(
                        new PaiWei(rs.getInt("PAI_WEI_ID"), rs.getString("PW_ID"), rs.getInt("MEM_ID"),
                                rs.getString("TYPE"), rs.getString("SIZE"),
                                rs.getString("DHARMA_SERVICE"), rs.getString("SUB_EVENT_TYPE"), rs.getDate("DATE").toLocalDate(),
                                rs.getString("PAY_METHOD"), rs.getDouble("AMT_PAID"),
                                rs.getDouble("TOTAL_DON"), rs.getDate("PAY_DATE").toLocalDate(),
                                rs.getDouble("BAL"), rs.getString("NOTE"),
                                rs.getString("CREATE_BY"), rs.getDate("CREATE_DATE").toLocalDate(),
                                rs.getString("UPDATE_BY"), rs.getDate("UPDATE_DATE").toLocalDate(),
                                rs.getString("MIS1"), rs.getString("MIS2"), rs.getString("MIS3"), rs.getString("MIS4"), rs.getString("MIS5"),
                                rs.getString("MIS6"), rs.getString("MIS7"), rs.getString("MIS8"), rs.getString("MIS9"), rs.getString("MIS10"),
                                rs.getString("YANG1"), rs.getString("YANG2"), rs.getString("YANG3"), rs.getString("YANG4"), rs.getString("YANG5"),
                                rs.getString("YANG6"), rs.getString("YANG7"), rs.getString("YANG8"), rs.getString("YANG9"), rs.getString("YANG10"),
                                rs.getString("DREN_TYPE"), rs.getString("CLAN1"), rs.getString("CLAN2"), rs.getString("CLAN3"),
                                rs.getString("DECEASED1"), rs.getString("DECEASED2"), rs.getString("DECEASED3"), rs.getString("DECEASED4"), rs.getString("DECEASED5"),
                                rs.getString("DECEASED6"), rs.getString("DECEASED7"), rs.getString("DECEASED8"), rs.getString("DECEASED9"), rs.getString("DECEASED10"),
                                rs.getString("OLD_OWNERS1"), rs.getString("OLD_OWNERS2")));
            }
            rs.close();
            getAllPaiWeis.close();
        } catch (SQLException ex) {
            System.out.println("getPaiWei() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return paiWeis;
    }

    public ArrayList<String> getPwID() {
        ArrayList id = new ArrayList<String>();
        openConnection();
        try {
            getAllPaiWeis = conn.prepareStatement("select PW_ID from app.PAIWEI ");
            rs = getAllPaiWeis.executeQuery();
            while (rs.next()) {
                id.add(rs.getString("PW_ID"));
            }
            rs.close();
            getAllPaiWeis.close();
        } catch (SQLException ex) {
            System.out.println("getPaiWei() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return id;
    }

    public String getMID(int memID) {
        ArrayList id = new ArrayList<String>();
        openConnection();
        try {
            getAllPaiWeis = conn.prepareStatement("select M_ID from app.MEMBER "
                    + "where MEM_ID=?");
            getAllPaiWeis.setInt(1, memID);
            rs = getAllPaiWeis.executeQuery();
            while (rs.next()) {
                id.add(rs.getString("M_ID"));
            }
            rs.close();
            getAllPaiWeis.close();
        } catch (SQLException ex) {
            System.out.println("getMID() error!");
            ex.printStackTrace();
        }
        closeConnection();
        
        return id.get(0).toString();
    }

    public List<PaiWei> getAccordionPaiWei(int memID) {
        paiWeis = new ArrayList<PaiWei>();
        openConnection();
        try {
            getAllPaiWeis = conn.prepareStatement("select * from app.PAIWEI "
                    + "where MEM_ID=?");
            getAllPaiWeis.setInt(1, memID);
            rs = getAllPaiWeis.executeQuery();
            while (rs.next()) {
                paiWeis.add(
                        new PaiWei(rs.getInt("PAI_WEI_ID"), rs.getString("PW_ID"), rs.getInt("MEM_ID"),
                                rs.getString("TYPE"), rs.getString("SIZE"),
                                rs.getString("DHARMA_SERVICE"), rs.getString("SUB_EVENT_TYPE"), rs.getDate("DATE").toLocalDate(),
                                rs.getString("PAY_METHOD"), rs.getDouble("AMT_PAID"),
                                rs.getDouble("TOTAL_DON"), rs.getDate("PAY_DATE").toLocalDate(),
                                rs.getDouble("BAL"), rs.getString("NOTE"),
                                rs.getString("CREATE_BY"), rs.getDate("CREATE_DATE").toLocalDate(),
                                rs.getString("UPDATE_BY"), rs.getDate("UPDATE_DATE").toLocalDate(),
                                rs.getString("MIS1"), rs.getString("MIS2"), rs.getString("MIS3"), rs.getString("MIS4"), rs.getString("MIS5"),
                                rs.getString("MIS6"), rs.getString("MIS7"), rs.getString("MIS8"), rs.getString("MIS9"), rs.getString("MIS10"),
                                rs.getString("YANG1"), rs.getString("YANG2"), rs.getString("YANG3"), rs.getString("YANG4"), rs.getString("YANG5"),
                                rs.getString("YANG6"), rs.getString("YANG7"), rs.getString("YANG8"), rs.getString("YANG9"), rs.getString("YANG10"),
                                rs.getString("DREN_TYPE"), rs.getString("CLAN1"), rs.getString("CLAN2"), rs.getString("CLAN3"),
                                rs.getString("DECEASED1"), rs.getString("DECEASED2"), rs.getString("DECEASED3"), rs.getString("DECEASED4"), rs.getString("DECEASED5"),
                                rs.getString("DECEASED6"), rs.getString("DECEASED7"), rs.getString("DECEASED8"), rs.getString("DECEASED9"), rs.getString("DECEASED10"),
                                rs.getString("OLD_OWNERS1"), rs.getString("OLD_OWNERS2")));
            }
            rs.close();
            getAllPaiWeis.close();
        } catch (SQLException ex) {
            System.out.println("getPaiWei() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return paiWeis;
    }

    public int insertPaiWeis(PaiWei toInsert, String sessionEmployeeName) {
        int userGeneratedId = -1;
        openConnection();
        try {
            insertPaiWei = conn.prepareStatement("insert into app.paiWei "
                    + "(PW_ID, MEM_ID, TYPE, SIZE, DHARMA_SERVICE, SUB_EVENT_TYPE, DATE, PAY_METHOD, "
                    + "AMT_PAID, TOTAL_DON, PAY_DATE, BAL, NOTE, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, "
                    + "MIS1, MIS2, MIS3, MIS4, MIS5, MIS6, MIS7, MIS8, MIS9, MIS10, "
                    + "YANG1, YANG2, YANG3, YANG4, YANG5, YANG6, YANG7, YANG8, YANG9, YANG10, "
                    + "DREN_TYPE, CLAN1, CLAN2, CLAN3, "
                    + "DECEASED1, DECEASED2, DECEASED3, DECEASED4, DECEASED5, DECEASED6, DECEASED7, DECEASED8, DECEASED9, DECEASED10, "
                    + "OLD_OWNERS1, OLD_OWNERS2) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " //need 36 more
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertPaiWei.setString(1, toInsert.getPwID());
            insertPaiWei.setInt(2, toInsert.getMemID());
            insertPaiWei.setString(3, toInsert.getType());
            insertPaiWei.setString(4, toInsert.getSize());
            insertPaiWei.setString(5, toInsert.getDharmaService());
            insertPaiWei.setString(6, toInsert.getSubEventType());
            insertPaiWei.setDate(7, toInsert.getDateToDate());
            insertPaiWei.setString(8, toInsert.getPayMethod());
            insertPaiWei.setDouble(9, toInsert.getAmtPaid());
            insertPaiWei.setDouble(10, toInsert.getTotalDon());
            insertPaiWei.setDate(11, toInsert.getPayDateToDate());
            insertPaiWei.setDouble(12, toInsert.getBal());
            insertPaiWei.setString(13, toInsert.getNote());
            insertPaiWei.setString(14, sessionEmployeeName); //getCreateBy
            insertPaiWei.setDate(15, toInsert.getCreatedOnToDate());
            insertPaiWei.setString(16, toInsert.getUpdateBy());
            insertPaiWei.setDate(17, toInsert.getUpdateOnToDate());
            insertPaiWei.setString(18, toInsert.getMis1());
            insertPaiWei.setString(19, toInsert.getMis2());
            insertPaiWei.setString(20, toInsert.getMis3());
            insertPaiWei.setString(21, toInsert.getMis4());
            insertPaiWei.setString(22, toInsert.getMis5());
            insertPaiWei.setString(23, toInsert.getMis6());
            insertPaiWei.setString(24, toInsert.getMis7());
            insertPaiWei.setString(25, toInsert.getMis8());
            insertPaiWei.setString(26, toInsert.getMis9());
            insertPaiWei.setString(27, toInsert.getMis10()); //
            insertPaiWei.setString(28, toInsert.getYang1());
            insertPaiWei.setString(29, toInsert.getYang2());
            insertPaiWei.setString(30, toInsert.getYang3());
            insertPaiWei.setString(31, toInsert.getYang4());
            insertPaiWei.setString(32, toInsert.getYang5());
            insertPaiWei.setString(33, toInsert.getYang6());
            insertPaiWei.setString(34, toInsert.getYang7());
            insertPaiWei.setString(35, toInsert.getYang8());
            insertPaiWei.setString(36, toInsert.getYang9());
            insertPaiWei.setString(37, toInsert.getYang10()); //
            insertPaiWei.setString(38, toInsert.getDrenType());
            insertPaiWei.setString(39, toInsert.getClan1());
            insertPaiWei.setString(40, toInsert.getClan2());
            insertPaiWei.setString(41, toInsert.getClan3()); //
            insertPaiWei.setString(42, toInsert.getDeceased1());
            insertPaiWei.setString(43, toInsert.getDeceased2());
            insertPaiWei.setString(44, toInsert.getDeceased3());
            insertPaiWei.setString(45, toInsert.getDeceased4());
            insertPaiWei.setString(46, toInsert.getDeceased5());
            insertPaiWei.setString(47, toInsert.getDeceased6());
            insertPaiWei.setString(48, toInsert.getDeceased7());
            insertPaiWei.setString(49, toInsert.getDeceased8());
            insertPaiWei.setString(50, toInsert.getDeceased9());
            insertPaiWei.setString(51, toInsert.getDeceased10()); //
            insertPaiWei.setString(52, toInsert.getOldOwners1());
            insertPaiWei.setString(53, toInsert.getOldOwners2()); //

            insertPaiWei.executeUpdate();

            rs = insertPaiWei.getGeneratedKeys();
            rs.next();
            userGeneratedId = rs.getInt(1);
            toInsert.setPaiWeiID(userGeneratedId);
            rs.close();
            insertPaiWei.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! insertPaiWei() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
        return userGeneratedId;
    }

    //2nd insert for DatabaseSetup insert PaiWei from initial cvs file
    public int insertPaiWeis(PaiWei toInsert) {
        int userGeneratedId = -1;
        openConnection();
        try {
            insertPaiWei = conn.prepareStatement("insert into app.paiWei "
                    + "(PW_ID, MEM_ID, TYPE, SIZE, DHARMA_SERVICE, SUB_EVENT_TYPE, DATE, PAY_METHOD, "
                    + "AMT_PAID, TOTAL_DON, PAY_DATE, BAL, NOTE, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, "
                    + "MIS1, MIS2, MIS3, MIS4, MIS5, MIS6, MIS7, MIS8, MIS9, MIS10, "
                    + "YANG1, YANG2, YANG3, YANG4, YANG5, YANG6, YANG7, YANG8, YANG9, YANG10, "
                    + "DREN_TYPE, CLAN1, CLAN2, CLAN3, "
                    + "DECEASED1, DECEASED2, DECEASED3, DECEASED4, DECEASED5, DECEASED6, DECEASED7, DECEASED8, DECEASED9, DECEASED10, "
                    + "OLD_OWNERS1, OLD_OWNERS2) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " //need 36 more
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertPaiWei.setString(1, toInsert.getPwID());
            insertPaiWei.setInt(2, toInsert.getMemID());
            insertPaiWei.setString(3, toInsert.getType());
            insertPaiWei.setString(4, toInsert.getSize());
            insertPaiWei.setString(5, toInsert.getDharmaService());
            insertPaiWei.setString(6, toInsert.getSubEventType());
            insertPaiWei.setDate(7, toInsert.getDateToDate());
            insertPaiWei.setString(8, toInsert.getPayMethod());
            insertPaiWei.setDouble(9, toInsert.getAmtPaid());
            insertPaiWei.setDouble(10, toInsert.getTotalDon());
            insertPaiWei.setDate(11, toInsert.getPayDateToDate());
            insertPaiWei.setDouble(12, toInsert.getBal());
            insertPaiWei.setString(13, toInsert.getNote());
            insertPaiWei.setString(14, toInsert.getCreatedBy()); //getCreateBy
            insertPaiWei.setDate(15, toInsert.getCreatedOnToDate());
            insertPaiWei.setString(16, toInsert.getUpdateBy());
            insertPaiWei.setDate(17, toInsert.getUpdateOnToDate());
            insertPaiWei.setString(18, toInsert.getMis1());
            insertPaiWei.setString(19, toInsert.getMis2());
            insertPaiWei.setString(20, toInsert.getMis3());
            insertPaiWei.setString(21, toInsert.getMis4());
            insertPaiWei.setString(22, toInsert.getMis5());
            insertPaiWei.setString(23, toInsert.getMis6());
            insertPaiWei.setString(24, toInsert.getMis7());
            insertPaiWei.setString(25, toInsert.getMis8());
            insertPaiWei.setString(26, toInsert.getMis9());
            insertPaiWei.setString(27, toInsert.getMis10()); //
            insertPaiWei.setString(28, toInsert.getYang1());
            insertPaiWei.setString(29, toInsert.getYang2());
            insertPaiWei.setString(30, toInsert.getYang3());
            insertPaiWei.setString(31, toInsert.getYang4());
            insertPaiWei.setString(32, toInsert.getYang5());
            insertPaiWei.setString(33, toInsert.getYang6());
            insertPaiWei.setString(34, toInsert.getYang7());
            insertPaiWei.setString(35, toInsert.getYang8());
            insertPaiWei.setString(36, toInsert.getYang9());
            insertPaiWei.setString(37, toInsert.getYang10()); //
            insertPaiWei.setString(38, toInsert.getDrenType());
            insertPaiWei.setString(39, toInsert.getClan1());
            insertPaiWei.setString(40, toInsert.getClan2());
            insertPaiWei.setString(41, toInsert.getClan3()); //
            insertPaiWei.setString(42, toInsert.getDeceased1());
            insertPaiWei.setString(43, toInsert.getDeceased2());
            insertPaiWei.setString(44, toInsert.getDeceased3());
            insertPaiWei.setString(45, toInsert.getDeceased4());
            insertPaiWei.setString(46, toInsert.getDeceased5());
            insertPaiWei.setString(47, toInsert.getDeceased6());
            insertPaiWei.setString(48, toInsert.getDeceased7());
            insertPaiWei.setString(49, toInsert.getDeceased8());
            insertPaiWei.setString(50, toInsert.getDeceased9());
            insertPaiWei.setString(51, toInsert.getDeceased10()); //
            insertPaiWei.setString(52, toInsert.getOldOwners1());
            insertPaiWei.setString(53, toInsert.getOldOwners2()); //

            insertPaiWei.executeUpdate();

            rs = insertPaiWei.getGeneratedKeys();
            rs.next();
            userGeneratedId = rs.getInt(1);
            toInsert.setPaiWeiID(userGeneratedId);
            rs.close();
            insertPaiWei.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! insertPaiWei() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
        return userGeneratedId;
    }

    public void updatePaiWei(PaiWei toInsert) {
        openConnection();
        try {
            editPaiWei = conn.prepareStatement("update app.PAIWEI set "
                    + "PW_ID = ?, MEM_ID = ?, TYPE = ?, SIZE = ?, DHARMA_SERVICE = ?, SUB_EVENT_TYPE = ?, DATE = ?, PAY_METHOD = ?, "
                    + "AMT_PAID = ?, TOTAL_DON = ?, PAY_DATE = ?, BAL = ?, NOTE = ?, UPDATE_BY = ?, UPDATE_DATE = ?, "
                    + "MIS1 = ?, MIS2 = ?, MIS3 = ?, MIS4 = ?, MIS5 = ?, MIS6 = ?, MIS7 = ?, MIS8 = ?, MIS9 = ?, MIS10 = ?, "
                    + "YANG1 = ?, YANG2 = ?, YANG3 = ?, YANG4 = ?, YANG5 = ?, YANG6 = ?, YANG7 = ?, YANG8 = ?, YANG9 = ?, YANG10 = ?, "
                    + "DREN_TYPE = ?, CLAN1 = ?, CLAN2 = ?, CLAN3 = ?, "
                    + "DECEASED1 = ?, DECEASED2 = ?, DECEASED3 = ?, DECEASED4 = ?, DECEASED5 = ?, DECEASED6 = ?, DECEASED7 = ?, DECEASED8 = ?, DECEASED9 = ?, DECEASED10 = ?, "
                    + "OLD_OWNERS1 = ?, OLD_OWNERS2 = ? "
                    + "where PAI_WEI_ID=?");

            editPaiWei.setString(1, toInsert.getPwID());
            editPaiWei.setInt(2, toInsert.getMemID());
            editPaiWei.setString(3, toInsert.getType());
            editPaiWei.setString(4, toInsert.getSize());
            editPaiWei.setString(5, toInsert.getDharmaService());
            editPaiWei.setString(6, toInsert.getSubEventType());
            editPaiWei.setDate(7, toInsert.getDateToDate());
            editPaiWei.setString(8, toInsert.getPayMethod());
            editPaiWei.setDouble(9, toInsert.getAmtPaid());
            editPaiWei.setDouble(10, toInsert.getTotalDon());
            editPaiWei.setDate(11, toInsert.getPayDateToDate());
            editPaiWei.setDouble(12, toInsert.getBal());
            editPaiWei.setString(13, toInsert.getNote());
            editPaiWei.setString(14, toInsert.getUpdateBy());
            editPaiWei.setDate(15, toInsert.getUpdateOnToDate());
            editPaiWei.setString(16, toInsert.getMis1());
            editPaiWei.setString(17, toInsert.getMis2());
            editPaiWei.setString(18, toInsert.getMis3());
            editPaiWei.setString(19, toInsert.getMis4());
            editPaiWei.setString(20, toInsert.getMis5());
            editPaiWei.setString(21, toInsert.getMis6());
            editPaiWei.setString(22, toInsert.getMis7());
            editPaiWei.setString(23, toInsert.getMis8());
            editPaiWei.setString(24, toInsert.getMis9());
            editPaiWei.setString(25, toInsert.getMis10()); //
            editPaiWei.setString(26, toInsert.getYang1());
            editPaiWei.setString(27, toInsert.getYang2());
            editPaiWei.setString(28, toInsert.getYang3());
            editPaiWei.setString(29, toInsert.getYang4());
            editPaiWei.setString(30, toInsert.getYang5());
            editPaiWei.setString(31, toInsert.getYang6());
            editPaiWei.setString(32, toInsert.getYang7());
            editPaiWei.setString(33, toInsert.getYang8());
            editPaiWei.setString(34, toInsert.getYang9());
            editPaiWei.setString(35, toInsert.getYang10()); //
            editPaiWei.setString(36, toInsert.getDrenType());
            editPaiWei.setString(37, toInsert.getClan1());
            editPaiWei.setString(38, toInsert.getClan2());
            editPaiWei.setString(39, toInsert.getClan3()); //
            editPaiWei.setString(40, toInsert.getDeceased1());
            editPaiWei.setString(41, toInsert.getDeceased2());
            editPaiWei.setString(42, toInsert.getDeceased3());
            editPaiWei.setString(43, toInsert.getDeceased4());
            editPaiWei.setString(44, toInsert.getDeceased5());
            editPaiWei.setString(45, toInsert.getDeceased6());
            editPaiWei.setString(46, toInsert.getDeceased7());
            editPaiWei.setString(47, toInsert.getDeceased8());
            editPaiWei.setString(48, toInsert.getDeceased9());
            editPaiWei.setString(49, toInsert.getDeceased10()); //
            editPaiWei.setString(50, toInsert.getOldOwners1());
            editPaiWei.setString(51, toInsert.getOldOwners2()); //
            editPaiWei.setInt(52, toInsert.getPaiWeiID());

            editPaiWei.executeUpdate();
            editPaiWei.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! editPaiWei() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
    }

    public void deletePaiWei(PaiWei toDelete) {
        openConnection();
        try {
            deletePaiWei = conn.prepareStatement("delete from app.PAIWEI where PAI_WEI_ID = ?");
            deletePaiWei.setInt(1, toDelete.getPaiWeiID());
            deletePaiWei.execute();
        } catch (SQLException ex) {
            System.out.println("ERROR! deletePaiWei()!");
            ex.printStackTrace();
        }
        closeConnection();
    }

    public int exportPaiWei(ArrayList<String> results, String fileName) throws FileNotFoundException, IOException {
        openConnection();
        try {
            String query = "select * from app.PAIWEI";
            exportPaiWei = conn.prepareStatement(query);
            rs = exportPaiWei.executeQuery();

            XSSFWorkbook wb = new XSSFWorkbook(); //for earlier ver. user HSSF
            XSSFSheet sheet = wb.createSheet("Pai Wei Details");
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
                    if (s.equals("Database Pai Wei ID")) {
                        row.createCell(j).setCellValue(rs.getString("PAI_WEI_ID"));
                    } else if (s.equals("Pai Wei ID")) {
                        row.createCell(j).setCellValue(rs.getString("PW_ID"));
                    } else if (s.equals("Member ID")) {
                        row.createCell(j).setCellValue(rs.getString("MEM_ID"));
                    } else if (s.equals("Type")) {
                        row.createCell(j).setCellValue(rs.getString("TYPE"));
                    } else if (s.equals("Size")) {
                        row.createCell(j).setCellValue(rs.getString("SIZE"));
                    } else if (s.equals("Dharma Service")) {
                        row.createCell(j).setCellValue(rs.getString("DHARMA_SERVICE"));
                    } else if (s.equals("Sub Event Type")) {
                        row.createCell(j).setCellValue(rs.getString("SUB_EVENT_TYPE"));
                    } else if (s.equals("Date of Donation")) {
                        row.createCell(j).setCellValue(rs.getString("DATE"));
                    } else if (s.equals("Payment Method")) {
                        row.createCell(j).setCellValue(rs.getString("PAY_METHOD"));
                    } else if (s.equals("Amount Paid")) {
                        row.createCell(j).setCellValue(rs.getString("AMT_PAID"));
                    } else if (s.equals("Total Donation")) {
                        row.createCell(j).setCellValue(rs.getString("TOTAL_DON"));
                    } else if (s.equals("Paid On")) {
                        row.createCell(j).setCellValue(rs.getString("PAY_DATE"));
                    } else if (s.equals("Balance")) {
                        row.createCell(j).setCellValue(rs.getString("BAL"));
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
                    } else if (s.equals("消災1")) { //Misfortune
                        row.createCell(j).setCellValue(rs.getString("MIS1"));
                    } else if (s.equals("消災2")) {
                        row.createCell(j).setCellValue(rs.getString("MIS2"));
                    } else if (s.equals("消災3")) {
                        row.createCell(j).setCellValue(rs.getString("MIS3"));
                    } else if (s.equals("消災4")) {
                        row.createCell(j).setCellValue(rs.getString("MIS4"));
                    } else if (s.equals("消災5")) {
                        row.createCell(j).setCellValue(rs.getString("MIS5"));
                    } else if (s.equals("消災6")) {
                        row.createCell(j).setCellValue(rs.getString("MIS6"));
                    } else if (s.equals("消災7")) {
                        row.createCell(j).setCellValue(rs.getString("MIS7"));
                    } else if (s.equals("消災8")) {
                        row.createCell(j).setCellValue(rs.getString("MIS8"));
                    } else if (s.equals("消災9")) {
                        row.createCell(j).setCellValue(rs.getString("MIS9"));
                    } else if (s.equals("消災10")) {
                        row.createCell(j).setCellValue(rs.getString("MIS10"));
                    } else if (s.equals("陽上1")) { //Yang
                        row.createCell(j).setCellValue(rs.getString("YANG1"));
                    } else if (s.equals("陽上2")) {
                        row.createCell(j).setCellValue(rs.getString("YANG2"));
                    } else if (s.equals("陽上3")) {
                        row.createCell(j).setCellValue(rs.getString("YANG3"));
                    } else if (s.equals("陽上4")) {
                        row.createCell(j).setCellValue(rs.getString("YANG4"));
                    } else if (s.equals("陽上5")) {
                        row.createCell(j).setCellValue(rs.getString("YANG5"));
                    } else if (s.equals("陽上6")) {
                        row.createCell(j).setCellValue(rs.getString("YANG6"));
                    } else if (s.equals("陽上7")) {
                        row.createCell(j).setCellValue(rs.getString("YANG7"));
                    } else if (s.equals("陽上8")) {
                        row.createCell(j).setCellValue(rs.getString("YANG8"));
                    } else if (s.equals("陽上9")) {
                        row.createCell(j).setCellValue(rs.getString("YANG9"));
                    } else if (s.equals("陽上10")) {
                        row.createCell(j).setCellValue(rs.getString("YANG10"));
                    } else if (s.equals("超薦類型")) {
                        row.createCell(j).setCellValue(rs.getString("DREN_TYPE"));
                    } else if (s.equals("門氏1")) {
                        row.createCell(j).setCellValue(rs.getString("CLAN1"));
                    } else if (s.equals("門氏2")) {
                        row.createCell(j).setCellValue(rs.getString("CLAN2"));
                    } else if (s.equals("門氏3")) {
                        row.createCell(j).setCellValue(rs.getString("CLAN3"));
                    } else if (s.equals("亡者1")) {
                        row.createCell(j).setCellValue(rs.getString("DECEASED1"));
                    } else if (s.equals("亡者2")) {
                        row.createCell(j).setCellValue(rs.getString("DECEASED2"));
                    } else if (s.equals("亡者3")) {
                        row.createCell(j).setCellValue(rs.getString("DECEASED3"));
                    } else if (s.equals("亡者4")) {
                        row.createCell(j).setCellValue(rs.getString("DECEASED4"));
                    } else if (s.equals("亡者5")) {
                        row.createCell(j).setCellValue(rs.getString("DECEASED5"));
                    } else if (s.equals("亡者6")) {
                        row.createCell(j).setCellValue(rs.getString("DECEASED6"));
                    } else if (s.equals("亡者7")) {
                        row.createCell(j).setCellValue(rs.getString("DECEASED7"));
                    } else if (s.equals("亡者8")) {
                        row.createCell(j).setCellValue(rs.getString("DECEASED8"));
                    } else if (s.equals("亡者9")) {
                        row.createCell(j).setCellValue(rs.getString("DECEASED9"));
                    } else if (s.equals("亡者10")) {
                        row.createCell(j).setCellValue(rs.getString("DECEASED10"));
                    } else if (s.equals("舊業主1")) {
                        row.createCell(j).setCellValue(rs.getString("OLD_OWNERS1"));
                    } else if (s.equals("舊業主2")) {
                        row.createCell(j).setCellValue(rs.getString("OLD_OWNERS2"));
                    }
                    j++;
                }
                rowindex++;
            }

            //Set auto-resize fields
            i = 0;
            for (String s : results) {
                sheet.autoSizeColumn(i);
                i++;
            }

            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
            exportPaiWei.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! exportPaiWei()!");
            //ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            return 0;
        }
        closeConnection();
        return 1;
    }

    //Returns a Member object with fName, lName, and cName
    public Member getMemberName(int memID) {
        Member mem1 = null;
        openConnection();
        try {
            findMember = conn.prepareStatement("select C_NAME, F_NAME, L_NAME from app.MEMBER "
                    + "where (MEM_ID = ?)");

            findMember.setInt(1, memID);
            rs = findMember.executeQuery();

            while (rs.next()) {
                mem1 = new Member(rs.getString("C_NAME"), rs.getString("F_NAME"),
                        rs.getString("L_NAME"));
            }
            rs.close();
            findMember.close();
        } catch (SQLException ex) {
            System.out.println("getMemberName() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return mem1;
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

    private List<Integer> newPaiWeiIndexList = new ArrayList<>();

    public List<Integer> getNewPaiWeiIndexList() {
        return newPaiWeiIndexList;
    }

    public List<Integer> getPaiWeiID(String file) throws FileNotFoundException, IOException {
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
            if (row.getCell(0) != null && !(df.formatCellValue(row.getCell(0)).equals(""))) { //OR contains a number
                memIDlist.add(Integer.parseInt(df.formatCellValue(row.getCell(0))));
            } else { //else has no memID, toInsertNewPaiWeiber() later
                newPaiWeiIndexList.add(i); //Store excel row index for accessing later
            }
        }

        return memIDlist;
    }

    //Imports each row of excel file, no restrictions
    public int importPaiWei(String file, ArrayList<String> results) throws FileNotFoundException, IOException {
        openConnection();
        //Open excel file
        //FileInputStream fileIn = new FileInputStream(new File("MemberImport.xlsx"));
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            importPaiWei = conn.prepareStatement("insert into app.paiWei "
                    + "(MEM_ID, TYPE, SIZE, DHARMA_SERVICE, SUB_EVENT_TYPE, DATE, PAY_METHOD, "
                    + "AMT_PAID, TOTAL_DON, PAY_DATE, BAL, NOTE, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, "
                    + "MIS1, MIS2, MIS3, MIS4, MIS5, MIS6, MIS7, MIS8, MIS9, MIS10, "
                    + "YANG1, YANG2, YANG3, YANG4, YANG5, YANG6, YANG7, YANG8, YANG9, YANG10, "
                    + "DREN_TYPE, CLAN1, CLAN2, CLAN3, "
                    + "DECEASED1, DECEASED2, DECEASED3, DECEASED4, DECEASED5, DECEASED6, DECEASED7, DECEASED8, DECEASED9, DECEASED10, "
                    + "OLD_OWNERS1, OLD_OWNERS2, PW_ID) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " //need 36 more
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            //Put into drop down list ######==>calls initialise method in DbLinkDialogController.java
            //Start reading data from excel, row by row
            java.sql.Date sqlDate;
            java.sql.Date sqlDate2 = java.sql.Date.valueOf("9999-01-01");
            DataFormatter df = new DataFormatter();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);

                if (results.contains("Member ID")) {
                    importPaiWei.setString(1, df.formatCellValue(row.getCell(2)));
                } else {
                    importPaiWei.setString(1, "0");
                    System.out.println("NO MEMBERS");
                }
                if (results.contains("Type")) {
                    importPaiWei.setString(2, df.formatCellValue(row.getCell(3)));
                } else {
                    importPaiWei.setString(2, "");
                }
                if (results.contains("Size")) {
                    importPaiWei.setString(3, df.formatCellValue(row.getCell(4)));
                } else {
                    importPaiWei.setString(3, "");
                }
                if (results.contains("Dharma Service")) {
                    importPaiWei.setString(4, df.formatCellValue(row.getCell(5)));
                } else {
                    importPaiWei.setString(4, "");
                }
                if (results.contains("Sub Event Type")) {
                    importPaiWei.setString(5, df.formatCellValue(row.getCell(6)));
                } else {
                    importPaiWei.setString(5, "");
                }
                if (results.contains("Date of Donation")) {
                    sqlDate = new java.sql.Date(row.getCell(7).getDateCellValue().getTime());
                    importPaiWei.setDate(6, sqlDate);
                } else {
                    importPaiWei.setDate(6, sqlDate2);
                }
                if (results.contains("Payment Method")) {
                    importPaiWei.setString(7, df.formatCellValue(row.getCell(8)));
                } else {
                    importPaiWei.setString(7, "");
                }
                if (results.contains("Amount Paid")) {
                    importPaiWei.setString(8, df.formatCellValue(row.getCell(9)));
                } else {
                    importPaiWei.setString(8, "");
                }
                if (results.contains("Total Donation")) {
                    importPaiWei.setString(9, df.formatCellValue(row.getCell(10)));
                } else {
                    importPaiWei.setString(9, "");
                }
                if (results.contains("Paid On")) {
                    sqlDate = new java.sql.Date(row.getCell(11).getDateCellValue().getTime());
                    importPaiWei.setDate(10, sqlDate);
                } else {
                    importPaiWei.setDate(10, sqlDate2);
                }
                if (results.contains("Balance")) {
                    importPaiWei.setString(11, df.formatCellValue(row.getCell(12)));
                } else {
                    importPaiWei.setString(11, "");
                }
                if (results.contains("Note")) {
                    importPaiWei.setString(12, df.formatCellValue(row.getCell(13)));
                } else {
                    importPaiWei.setString(12, "");
                }
                if (results.contains("Create Date")) {
                    sqlDate = new java.sql.Date(row.getCell(14).getDateCellValue().getTime());
                    importPaiWei.setDate(13, sqlDate);
                } else {
                    importPaiWei.setDate(13, sqlDate2); //SERT TO CURR DATE
                }
                if (results.contains("Update Date")) {
                    sqlDate = new java.sql.Date(row.getCell(15).getDateCellValue().getTime());
                    importPaiWei.setDate(14, sqlDate);
                } else {
                    importPaiWei.setDate(14, sqlDate2);
                }
                if (results.contains("Created By")) {
                    importPaiWei.setString(15, df.formatCellValue(row.getCell(16)));
                } else {
                    importPaiWei.setString(15, ""); //SET TO USER 
                }
                if (results.contains("Updated By")) {
                    importPaiWei.setString(16, df.formatCellValue(row.getCell(17)));
                } else {
                    importPaiWei.setString(16, "");
                }
                if (results.contains("消災1")) {
                    importPaiWei.setString(17, df.formatCellValue(row.getCell(18)));
                } else {
                    importPaiWei.setString(17, "");
                }
                if (results.contains("消災2")) {
                    importPaiWei.setString(18, df.formatCellValue(row.getCell(19)));
                } else {
                    importPaiWei.setString(18, "");
                }
                if (results.contains("消災3")) {
                    importPaiWei.setString(19, df.formatCellValue(row.getCell(20)));
                } else {
                    importPaiWei.setString(19, "");
                }
                if (results.contains("消災4")) {
                    importPaiWei.setString(20, df.formatCellValue(row.getCell(21)));
                } else {
                    importPaiWei.setString(20, "");
                }
                if (results.contains("消災5")) {
                    importPaiWei.setString(21, df.formatCellValue(row.getCell(22)));
                } else {
                    importPaiWei.setString(21, "");
                }
                if (results.contains("消災6")) {
                    importPaiWei.setString(22, df.formatCellValue(row.getCell(23)));
                } else {
                    importPaiWei.setString(22, "");
                }
                if (results.contains("消災7")) {
                    importPaiWei.setString(23, df.formatCellValue(row.getCell(24)));
                } else {
                    importPaiWei.setString(23, "");
                }
                if (results.contains("消災8")) {
                    importPaiWei.setString(24, df.formatCellValue(row.getCell(25)));
                } else {
                    importPaiWei.setString(24, "");
                }
                if (results.contains("消災9")) {
                    importPaiWei.setString(25, df.formatCellValue(row.getCell(26)));
                } else {
                    importPaiWei.setString(25, "");
                }
                if (results.contains("消災10")) {
                    importPaiWei.setString(26, df.formatCellValue(row.getCell(27)));
                } else {
                    importPaiWei.setString(26, "");
                }
                if (results.contains("陽上1")) {
                    importPaiWei.setString(27, df.formatCellValue(row.getCell(28)));
                } else {
                    importPaiWei.setString(27, "");
                }
                if (results.contains("陽上2")) {
                    importPaiWei.setString(28, df.formatCellValue(row.getCell(29)));
                } else {
                    importPaiWei.setString(28, "");
                }
                if (results.contains("陽上3")) {
                    importPaiWei.setString(29, df.formatCellValue(row.getCell(30)));
                } else {
                    importPaiWei.setString(29, "");
                }
                if (results.contains("陽上4")) {
                    importPaiWei.setString(30, df.formatCellValue(row.getCell(31)));
                } else {
                    importPaiWei.setString(30, "");
                }
                if (results.contains("陽上5")) {
                    importPaiWei.setString(31, df.formatCellValue(row.getCell(32)));
                } else {
                    importPaiWei.setString(31, "");
                }
                if (results.contains("陽上6")) {
                    importPaiWei.setString(32, df.formatCellValue(row.getCell(33)));
                } else {
                    importPaiWei.setString(32, "");
                }
                if (results.contains("陽上7")) {
                    importPaiWei.setString(33, df.formatCellValue(row.getCell(34)));
                } else {
                    importPaiWei.setString(33, "");
                }
                if (results.contains("陽上8")) {
                    importPaiWei.setString(34, df.formatCellValue(row.getCell(35)));
                } else {
                    importPaiWei.setString(34, "");
                }
                if (results.contains("陽上9")) {
                    importPaiWei.setString(35, df.formatCellValue(row.getCell(36)));
                } else {
                    importPaiWei.setString(35, "");
                }
                if (results.contains("陽上10")) {
                    importPaiWei.setString(36, df.formatCellValue(row.getCell(37)));
                } else {
                    importPaiWei.setString(36, "");
                }
                if (results.contains("超薦類型")) {
                    importPaiWei.setString(37, df.formatCellValue(row.getCell(38)));
                } else {
                    importPaiWei.setString(37, "");
                }
                if (results.contains("門氏1")) {
                    importPaiWei.setString(38, df.formatCellValue(row.getCell(39)));
                } else {
                    importPaiWei.setString(38, "");
                }
                if (results.contains("門氏2")) {
                    importPaiWei.setString(39, df.formatCellValue(row.getCell(40)));
                } else {
                    importPaiWei.setString(39, "");
                }
                if (results.contains("門氏3")) {
                    importPaiWei.setString(40, df.formatCellValue(row.getCell(41)));
                } else {
                    importPaiWei.setString(40, "");
                }
                if (results.contains("亡者1")) {
                    importPaiWei.setString(41, df.formatCellValue(row.getCell(42)));
                } else {
                    importPaiWei.setString(41, "");
                }
                if (results.contains("亡者2")) {
                    importPaiWei.setString(42, df.formatCellValue(row.getCell(43)));
                } else {
                    importPaiWei.setString(42, "");
                }
                if (results.contains("亡者3")) {
                    importPaiWei.setString(43, df.formatCellValue(row.getCell(44)));
                } else {
                    importPaiWei.setString(43, "");
                }
                if (results.contains("亡者4")) {
                    importPaiWei.setString(44, df.formatCellValue(row.getCell(45)));
                } else {
                    importPaiWei.setString(44, "");
                }
                if (results.contains("亡者5")) {
                    importPaiWei.setString(45, df.formatCellValue(row.getCell(46)));
                } else {
                    importPaiWei.setString(45, "");
                }
                if (results.contains("亡者6")) {
                    importPaiWei.setString(46, df.formatCellValue(row.getCell(47)));
                } else {
                    importPaiWei.setString(46, "");
                }
                if (results.contains("亡者7")) {
                    importPaiWei.setString(47, df.formatCellValue(row.getCell(48)));
                } else {
                    importPaiWei.setString(47, "");
                }
                if (results.contains("亡者8")) {
                    importPaiWei.setString(48, df.formatCellValue(row.getCell(49)));
                } else {
                    importPaiWei.setString(48, "");
                }
                if (results.contains("亡者9")) {
                    importPaiWei.setString(49, df.formatCellValue(row.getCell(50)));
                } else {
                    importPaiWei.setString(49, "");
                }
                if (results.contains("亡者10")) {
                    importPaiWei.setString(50, df.formatCellValue(row.getCell(51)));
                } else {
                    importPaiWei.setString(50, "");
                }
                if (results.contains("舊業主1")) {
                    importPaiWei.setString(51, df.formatCellValue(row.getCell(52)));
                } else {
                    importPaiWei.setString(51, "");
                }
                if (results.contains("舊業主2")) {
                    importPaiWei.setString(52, df.formatCellValue(row.getCell(53)));
                } else {
                    importPaiWei.setString(52, "");
                }
                if (results.contains("Pai Wei ID")) {
                    importPaiWei.setString(53, df.formatCellValue(row.getCell(1)));
                } else {
                    importPaiWei.setString(53, "");
                }

                importPaiWei.execute();
            }

            wb.close();
            fileIn.close();
            importPaiWei.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! importPaiWei() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in MemberQueries.java/importPaiWei from excel... Most likely from extra null rows in excel");
            return 2;
        }
        closeConnection();
        return 1;
    }

    //Imports only rows that contains deleted memID from db OR rows without memID ===> assigns these rows with new memID
    public int importNewMember(List<Integer> newMemList, List<Integer> newMemIndexList, String file, ArrayList<String> results) throws FileNotFoundException, IOException {
        openConnection();
        //Open excel file
        //FileInputStream fileIn = new FileInputStream(new File("MemberImport.xlsx"));
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            importPaiWei = conn.prepareStatement("insert into app.paiWei "
                    + "(MEM_ID, TYPE, SIZE, DHARMA_SERVICE, SUB_EVENT_TYPE, DATE, PAY_METHOD, "
                    + "AMT_PAID, TOTAL_DON, PAY_DATE, BAL, NOTE, CREATE_DATE, UPDATE_DATE, CREATE_BY, UPDATE_BY, "
                    + "MIS1, MIS2, MIS3, MIS4, MIS5, MIS6, MIS7, MIS8, MIS9, MIS10, "
                    + "YANG1, YANG2, YANG3, YANG4, YANG5, YANG6, YANG7, YANG8, YANG9, YANG10, "
                    + "DREN_TYPE, CLAN1, CLAN2, CLAN3, "
                    + "DECEASED1, DECEASED2, DECEASED3, DECEASED4, DECEASED5, DECEASED6, DECEASED7, DECEASED8, DECEASED9, DECEASED10, "
                    + "OLD_OWNERS1, OLD_OWNERS2, PW_ID) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " //need 36 more
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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
                for (int n : newMemIndexList) {
                    if (i == n) {
                        flag = true;
                        break;
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
                        importPaiWei.setString(1, df.formatCellValue(row.getCell(2)));
                    } else {
                        importPaiWei.setString(1, "");
                    }
                    if (results.contains("Type")) {
                        importPaiWei.setString(2, df.formatCellValue(row.getCell(3)));
                    } else {
                        importPaiWei.setString(2, "");
                    }
                    if (results.contains("Size")) {
                        importPaiWei.setString(3, df.formatCellValue(row.getCell(4)));
                    } else {
                        importPaiWei.setString(3, "");
                    }
                    if (results.contains("Dharma Service")) {
                        importPaiWei.setString(4, df.formatCellValue(row.getCell(5)));
                    } else {
                        importPaiWei.setString(4, "");
                    }
                    if (results.contains("Sub Event Type")) {
                        importPaiWei.setString(5, df.formatCellValue(row.getCell(6)));
                    } else {
                        importPaiWei.setString(5, "");
                    }
                    if (results.contains("Date of Donation")) {
                        sqlDate = new java.sql.Date(row.getCell(7).getDateCellValue().getTime());
                        importPaiWei.setDate(6, sqlDate);
                    } else {
                        importPaiWei.setDate(6, sqlDate2);
                    }
                    if (results.contains("Payment Method")) {
                        importPaiWei.setString(7, df.formatCellValue(row.getCell(8)));
                    } else {
                        importPaiWei.setString(7, "");
                    }
                    if (results.contains("Amount Paid")) {
                        importPaiWei.setString(8, df.formatCellValue(row.getCell(9)));
                    } else {
                        importPaiWei.setString(8, "");
                    }
                    if (results.contains("Total Donation")) {
                        importPaiWei.setString(9, df.formatCellValue(row.getCell(10)));
                    } else {
                        importPaiWei.setString(9, "");
                    }
                    if (results.contains("Paid On")) {
                        sqlDate = new java.sql.Date(row.getCell(11).getDateCellValue().getTime());
                        importPaiWei.setDate(10, sqlDate);
                    } else {
                        importPaiWei.setDate(10, sqlDate2);
                    }
                    if (results.contains("Balance")) {
                        importPaiWei.setString(11, df.formatCellValue(row.getCell(12)));
                    } else {
                        importPaiWei.setString(11, "");
                    }
                    if (results.contains("Note")) {
                        importPaiWei.setString(12, df.formatCellValue(row.getCell(13)));
                    } else {
                        importPaiWei.setString(12, "");
                    }
                    if (results.contains("Create Date")) {
                        sqlDate = new java.sql.Date(row.getCell(14).getDateCellValue().getTime());
                        importPaiWei.setDate(13, sqlDate);
                    } else {
                        importPaiWei.setDate(13, sqlDate2); //SERT TO CURR DATE
                    }
                    if (results.contains("Update Date")) {
                        sqlDate = new java.sql.Date(row.getCell(15).getDateCellValue().getTime());
                        importPaiWei.setDate(14, sqlDate);
                    } else {
                        importPaiWei.setDate(14, sqlDate2);
                    }
                    if (results.contains("Created By")) {
                        importPaiWei.setString(15, df.formatCellValue(row.getCell(16)));
                    } else {
                        importPaiWei.setString(15, ""); //SET TO USER 
                    }
                    if (results.contains("Updated By")) {
                        importPaiWei.setString(16, df.formatCellValue(row.getCell(17)));
                    } else {
                        importPaiWei.setString(16, "");
                    }
                    if (results.contains("消災1")) {
                        importPaiWei.setString(17, df.formatCellValue(row.getCell(18)));
                    } else {
                        importPaiWei.setString(17, "");
                    }
                    if (results.contains("消災2")) {
                        importPaiWei.setString(18, df.formatCellValue(row.getCell(19)));
                    } else {
                        importPaiWei.setString(18, "");
                    }
                    if (results.contains("消災3")) {
                        importPaiWei.setString(19, df.formatCellValue(row.getCell(20)));
                    } else {
                        importPaiWei.setString(19, "");
                    }
                    if (results.contains("消災4")) {
                        importPaiWei.setString(20, df.formatCellValue(row.getCell(21)));
                    } else {
                        importPaiWei.setString(20, "");
                    }
                    if (results.contains("消災5")) {
                        importPaiWei.setString(21, df.formatCellValue(row.getCell(22)));
                    } else {
                        importPaiWei.setString(21, "");
                    }
                    if (results.contains("消災6")) {
                        importPaiWei.setString(22, df.formatCellValue(row.getCell(23)));
                    } else {
                        importPaiWei.setString(22, "");
                    }
                    if (results.contains("消災7")) {
                        importPaiWei.setString(23, df.formatCellValue(row.getCell(24)));
                    } else {
                        importPaiWei.setString(23, "");
                    }
                    if (results.contains("消災8")) {
                        importPaiWei.setString(24, df.formatCellValue(row.getCell(25)));
                    } else {
                        importPaiWei.setString(24, "");
                    }
                    if (results.contains("消災9")) {
                        importPaiWei.setString(25, df.formatCellValue(row.getCell(26)));
                    } else {
                        importPaiWei.setString(25, "");
                    }
                    if (results.contains("消災10")) {
                        importPaiWei.setString(26, df.formatCellValue(row.getCell(27)));
                    } else {
                        importPaiWei.setString(26, "");
                    }
                    if (results.contains("陽上1")) {
                        importPaiWei.setString(27, df.formatCellValue(row.getCell(28)));
                    } else {
                        importPaiWei.setString(27, "");
                    }
                    if (results.contains("陽上2")) {
                        importPaiWei.setString(28, df.formatCellValue(row.getCell(29)));
                    } else {
                        importPaiWei.setString(28, "");
                    }
                    if (results.contains("陽上3")) {
                        importPaiWei.setString(29, df.formatCellValue(row.getCell(30)));
                    } else {
                        importPaiWei.setString(29, "");
                    }
                    if (results.contains("陽上4")) {
                        importPaiWei.setString(30, df.formatCellValue(row.getCell(31)));
                    } else {
                        importPaiWei.setString(30, "");
                    }
                    if (results.contains("陽上5")) {
                        importPaiWei.setString(31, df.formatCellValue(row.getCell(32)));
                    } else {
                        importPaiWei.setString(31, "");
                    }
                    if (results.contains("陽上6")) {
                        importPaiWei.setString(32, df.formatCellValue(row.getCell(33)));
                    } else {
                        importPaiWei.setString(32, "");
                    }
                    if (results.contains("陽上7")) {
                        importPaiWei.setString(33, df.formatCellValue(row.getCell(34)));
                    } else {
                        importPaiWei.setString(33, "");
                    }
                    if (results.contains("陽上8")) {
                        importPaiWei.setString(34, df.formatCellValue(row.getCell(35)));
                    } else {
                        importPaiWei.setString(34, "");
                    }
                    if (results.contains("陽上9")) {
                        importPaiWei.setString(35, df.formatCellValue(row.getCell(36)));
                    } else {
                        importPaiWei.setString(35, "");
                    }
                    if (results.contains("陽上10")) {
                        importPaiWei.setString(36, df.formatCellValue(row.getCell(37)));
                    } else {
                        importPaiWei.setString(36, "");
                    }
                    if (results.contains("超薦類型")) {
                        importPaiWei.setString(37, df.formatCellValue(row.getCell(38)));
                    } else {
                        importPaiWei.setString(37, "");
                    }
                    if (results.contains("門氏1")) {
                        importPaiWei.setString(38, df.formatCellValue(row.getCell(39)));
                    } else {
                        importPaiWei.setString(38, "");
                    }
                    if (results.contains("門氏2")) {
                        importPaiWei.setString(39, df.formatCellValue(row.getCell(40)));
                    } else {
                        importPaiWei.setString(39, "");
                    }
                    if (results.contains("門氏3")) {
                        importPaiWei.setString(40, df.formatCellValue(row.getCell(41)));
                    } else {
                        importPaiWei.setString(40, "");
                    }
                    if (results.contains("亡者1")) {
                        importPaiWei.setString(41, df.formatCellValue(row.getCell(42)));
                    } else {
                        importPaiWei.setString(41, "");
                    }
                    if (results.contains("亡者2")) {
                        importPaiWei.setString(42, df.formatCellValue(row.getCell(43)));
                    } else {
                        importPaiWei.setString(42, "");
                    }
                    if (results.contains("亡者3")) {
                        importPaiWei.setString(43, df.formatCellValue(row.getCell(44)));
                    } else {
                        importPaiWei.setString(43, "");
                    }
                    if (results.contains("亡者4")) {
                        importPaiWei.setString(44, df.formatCellValue(row.getCell(45)));
                    } else {
                        importPaiWei.setString(44, "");
                    }
                    if (results.contains("亡者5")) {
                        importPaiWei.setString(45, df.formatCellValue(row.getCell(46)));
                    } else {
                        importPaiWei.setString(45, "");
                    }
                    if (results.contains("亡者6")) {
                        importPaiWei.setString(46, df.formatCellValue(row.getCell(47)));
                    } else {
                        importPaiWei.setString(46, "");
                    }
                    if (results.contains("亡者7")) {
                        importPaiWei.setString(47, df.formatCellValue(row.getCell(48)));
                    } else {
                        importPaiWei.setString(47, "");
                    }
                    if (results.contains("亡者8")) {
                        importPaiWei.setString(48, df.formatCellValue(row.getCell(49)));
                    } else {
                        importPaiWei.setString(48, "");
                    }
                    if (results.contains("亡者9")) {
                        importPaiWei.setString(49, df.formatCellValue(row.getCell(50)));
                    } else {
                        importPaiWei.setString(49, "");
                    }
                    if (results.contains("亡者10")) {
                        importPaiWei.setString(50, df.formatCellValue(row.getCell(51)));
                    } else {
                        importPaiWei.setString(50, "");
                    }
                    if (results.contains("舊業主1")) {
                        importPaiWei.setString(51, df.formatCellValue(row.getCell(52)));
                    } else {
                        importPaiWei.setString(51, "");
                    }
                    if (results.contains("舊業主2")) {
                        importPaiWei.setString(52, df.formatCellValue(row.getCell(53)));
                    } else {
                        importPaiWei.setString(52, "");
                    }
                    if (results.contains("Pai Wei ID")) {
                        importPaiWei.setString(53, df.formatCellValue(row.getCell(1)));
                    } else {
                        importPaiWei.setString(53, "");
                    }

                    importPaiWei.execute();
                }
            }

            wb.close();
            fileIn.close();
            importPaiWei.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("ERROR! importPaiWei() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in MemberQueries.java/importNewMember from excel... Most likely from extra null rows in excel");
            return 2;
        }
        closeConnection();
        return 1;
    }

    //Updates existing members with memID matching in excel
    public int importOldMember(List<Integer> memIDList, String file, ArrayList<String> results) throws FileNotFoundException, IOException {
        openConnection();
        //Open excel file
        FileInputStream fileIn = new FileInputStream(file);

        XSSFWorkbook wb = new XSSFWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        //28 import fields
        try {
            //'REPLACE INTO' Updates existing records and inserts new ones if pkID not found
            importPaiWei = conn.prepareStatement("update app.PAIWEI set "
                    + "MEM_ID = ?, TYPE = ?, SIZE = ?, DHARMA_SERVICE = ?, SUB_EVENT_TYPE = ?, DATE = ?, PAY_METHOD = ?, " //7
                    + "AMT_PAID = ?, TOTAL_DON = ?, PAY_DATE = ?, BAL = ?, NOTE = ?, CREATE_DATE = ?, UPDATE_DATE = ?, CREATE_BY = ?, UPDATE_BY = ?, "//9
                    + "MIS1 = ?, MIS2 = ?, MIS3 = ?, MIS4 = ?, MIS5 = ?, MIS6 = ?, MIS7 = ?, MIS8 = ?, MIS9 = ?, MIS10 = ?, " //10
                    + "YANG1 = ?, YANG2 = ?, YANG3 = ?, YANG4 = ?, YANG5 = ?, YANG6 = ?, YANG7 = ?, YANG8 = ?, YANG9 = ?, YANG10 = ?, " //10
                    + "DREN_TYPE = ?, CLAN1 = ?, CLAN2 = ?, CLAN3 = ?, " //4
                    + "DECEASED1 = ?, DECEASED2 = ?, DECEASED3 = ?, DECEASED4 = ?, DECEASED5 = ?, DECEASED6 = ?, DECEASED7 = ?, DECEASED8 = ?, DECEASED9 = ?, DECEASED10 = ?, "
                    + "OLD_OWNERS1 = ?, OLD_OWNERS2 = ?, PW_ID = ? " //2
                    + "where PAI_WEI_ID=?"); //1
            //Start reading data from excel, row by row
            java.sql.Date sqlDate;
            java.sql.Date sqlDate2 = java.sql.Date.valueOf("9999-01-01");
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
                        importPaiWei.setString(1, df.formatCellValue(row.getCell(2)));
                    } else {
                        importPaiWei.setString(1, "");
                    }
                    if (results.contains("Type")) {
                        importPaiWei.setString(2, df.formatCellValue(row.getCell(3)));
                    } else {
                        importPaiWei.setString(2, "");
                    }
                    if (results.contains("Size")) {
                        importPaiWei.setString(3, df.formatCellValue(row.getCell(4)));
                    } else {
                        importPaiWei.setString(3, "");
                    }
                    if (results.contains("Dharma Service")) {
                        importPaiWei.setString(4, df.formatCellValue(row.getCell(5)));
                    } else {
                        importPaiWei.setString(4, "");
                    }
                    if (results.contains("Sub Event Type")) {
                        importPaiWei.setString(5, df.formatCellValue(row.getCell(6)));
                    } else {
                        importPaiWei.setString(5, "");
                    }
                    if (results.contains("Date of Donation")) {
                        sqlDate = new java.sql.Date(row.getCell(7).getDateCellValue().getTime());
                        importPaiWei.setDate(6, sqlDate);
                    } else {
                        importPaiWei.setDate(6, sqlDate2);
                    }
                    if (results.contains("Payment Method")) {
                        importPaiWei.setString(7, df.formatCellValue(row.getCell(8)));
                    } else {
                        importPaiWei.setString(7, "");
                    }
                    if (results.contains("Amount Paid")) {
                        importPaiWei.setString(8, df.formatCellValue(row.getCell(9)));
                    } else {
                        importPaiWei.setString(8, "");
                    }
                    if (results.contains("Total Donation")) {
                        importPaiWei.setString(9, df.formatCellValue(row.getCell(10)));
                    } else {
                        importPaiWei.setString(9, "");
                    }
                    if (results.contains("Paid On")) {
                        sqlDate = new java.sql.Date(row.getCell(11).getDateCellValue().getTime());
                        importPaiWei.setDate(10, sqlDate);
                    } else {
                        importPaiWei.setDate(10, sqlDate2);
                    }
                    if (results.contains("Balance")) {
                        importPaiWei.setString(11, df.formatCellValue(row.getCell(12)));
                    } else {
                        importPaiWei.setString(11, "");
                    }
                    if (results.contains("Note")) {
                        importPaiWei.setString(12, df.formatCellValue(row.getCell(13)));
                    } else {
                        importPaiWei.setString(12, "");
                    }
                    if (results.contains("Create Date")) {
                        sqlDate = new java.sql.Date(row.getCell(14).getDateCellValue().getTime());
                        importPaiWei.setDate(13, sqlDate);
                    } else {
                        importPaiWei.setDate(13, sqlDate2); //SERT TO CURR DATE
                    }
                    if (results.contains("Update Date")) {
                        sqlDate = new java.sql.Date(row.getCell(15).getDateCellValue().getTime());
                        importPaiWei.setDate(14, sqlDate);
                    } else {
                        importPaiWei.setDate(14, sqlDate2);
                    }
                    if (results.contains("Created By")) {
                        importPaiWei.setString(15, df.formatCellValue(row.getCell(16)));
                    } else {
                        importPaiWei.setString(15, ""); //SET TO USER 
                    }
                    if (results.contains("Updated By")) {
                        importPaiWei.setString(16, df.formatCellValue(row.getCell(17)));
                    } else {
                        importPaiWei.setString(16, "");
                    }
                    if (results.contains("消災1")) {
                        importPaiWei.setString(17, df.formatCellValue(row.getCell(18)));
                    } else {
                        importPaiWei.setString(17, "");
                    }
                    if (results.contains("消災2")) {
                        importPaiWei.setString(18, df.formatCellValue(row.getCell(19)));
                    } else {
                        importPaiWei.setString(18, "");
                    }
                    if (results.contains("消災3")) {
                        importPaiWei.setString(19, df.formatCellValue(row.getCell(20)));
                    } else {
                        importPaiWei.setString(19, "");
                    }
                    if (results.contains("消災4")) {
                        importPaiWei.setString(20, df.formatCellValue(row.getCell(21)));
                    } else {
                        importPaiWei.setString(20, "");
                    }
                    if (results.contains("消災5")) {
                        importPaiWei.setString(21, df.formatCellValue(row.getCell(22)));
                    } else {
                        importPaiWei.setString(21, "");
                    }
                    if (results.contains("消災6")) {
                        importPaiWei.setString(22, df.formatCellValue(row.getCell(23)));
                    } else {
                        importPaiWei.setString(22, "");
                    }
                    if (results.contains("消災7")) {
                        importPaiWei.setString(23, df.formatCellValue(row.getCell(24)));
                    } else {
                        importPaiWei.setString(23, "");
                    }
                    if (results.contains("消災8")) {
                        importPaiWei.setString(24, df.formatCellValue(row.getCell(25)));
                    } else {
                        importPaiWei.setString(24, "");
                    }
                    if (results.contains("消災9")) {
                        importPaiWei.setString(25, df.formatCellValue(row.getCell(26)));
                    } else {
                        importPaiWei.setString(25, "");
                    }
                    if (results.contains("消災10")) {
                        importPaiWei.setString(26, df.formatCellValue(row.getCell(27)));
                    } else {
                        importPaiWei.setString(26, "");
                    }
                    if (results.contains("陽上1")) {
                        importPaiWei.setString(27, df.formatCellValue(row.getCell(28)));
                    } else {
                        importPaiWei.setString(27, "");
                    }
                    if (results.contains("陽上2")) {
                        importPaiWei.setString(28, df.formatCellValue(row.getCell(29)));
                    } else {
                        importPaiWei.setString(28, "");
                    }
                    if (results.contains("陽上3")) {
                        importPaiWei.setString(29, df.formatCellValue(row.getCell(30)));
                    } else {
                        importPaiWei.setString(29, "");
                    }
                    if (results.contains("陽上4")) {
                        importPaiWei.setString(30, df.formatCellValue(row.getCell(31)));
                    } else {
                        importPaiWei.setString(30, "");
                    }
                    if (results.contains("陽上5")) {
                        importPaiWei.setString(31, df.formatCellValue(row.getCell(32)));
                    } else {
                        importPaiWei.setString(31, "");
                    }
                    if (results.contains("陽上6")) {
                        importPaiWei.setString(32, df.formatCellValue(row.getCell(33)));
                    } else {
                        importPaiWei.setString(32, "");
                    }
                    if (results.contains("陽上7")) {
                        importPaiWei.setString(33, df.formatCellValue(row.getCell(34)));
                    } else {
                        importPaiWei.setString(33, "");
                    }
                    if (results.contains("陽上8")) {
                        importPaiWei.setString(34, df.formatCellValue(row.getCell(35)));
                    } else {
                        importPaiWei.setString(34, "");
                    }
                    if (results.contains("陽上9")) {
                        importPaiWei.setString(35, df.formatCellValue(row.getCell(36)));
                    } else {
                        importPaiWei.setString(35, "");
                    }
                    if (results.contains("陽上10")) {
                        importPaiWei.setString(36, df.formatCellValue(row.getCell(37)));
                    } else {
                        importPaiWei.setString(36, "");
                    }
                    if (results.contains("超薦類型")) {
                        importPaiWei.setString(37, df.formatCellValue(row.getCell(38)));
                    } else {
                        importPaiWei.setString(37, "");
                    }
                    if (results.contains("門氏1")) {
                        importPaiWei.setString(38, df.formatCellValue(row.getCell(39)));
                    } else {
                        importPaiWei.setString(38, "");
                    }
                    if (results.contains("門氏2")) {
                        importPaiWei.setString(39, df.formatCellValue(row.getCell(40)));
                    } else {
                        importPaiWei.setString(39, "");
                    }
                    if (results.contains("門氏3")) {
                        importPaiWei.setString(40, df.formatCellValue(row.getCell(41)));
                    } else {
                        importPaiWei.setString(40, "");
                    }
                    if (results.contains("亡者1")) {
                        importPaiWei.setString(41, df.formatCellValue(row.getCell(42)));
                    } else {
                        importPaiWei.setString(41, "");
                    }
                    if (results.contains("亡者2")) {
                        importPaiWei.setString(42, df.formatCellValue(row.getCell(43)));
                    } else {
                        importPaiWei.setString(42, "");
                    }
                    if (results.contains("亡者3")) {
                        importPaiWei.setString(43, df.formatCellValue(row.getCell(44)));
                    } else {
                        importPaiWei.setString(43, "");
                    }
                    if (results.contains("亡者4")) {
                        importPaiWei.setString(44, df.formatCellValue(row.getCell(45)));
                    } else {
                        importPaiWei.setString(44, "");
                    }
                    if (results.contains("亡者5")) {
                        importPaiWei.setString(45, df.formatCellValue(row.getCell(46)));
                    } else {
                        importPaiWei.setString(45, "");
                    }
                    if (results.contains("亡者6")) {
                        importPaiWei.setString(46, df.formatCellValue(row.getCell(47)));
                    } else {
                        importPaiWei.setString(46, "");
                    }
                    if (results.contains("亡者7")) {
                        importPaiWei.setString(47, df.formatCellValue(row.getCell(48)));
                    } else {
                        importPaiWei.setString(47, "");
                    }
                    if (results.contains("亡者8")) {
                        importPaiWei.setString(48, df.formatCellValue(row.getCell(49)));
                    } else {
                        importPaiWei.setString(48, "");
                    }
                    if (results.contains("亡者9")) {
                        importPaiWei.setString(49, df.formatCellValue(row.getCell(50)));
                    } else {
                        importPaiWei.setString(49, "");
                    }
                    if (results.contains("亡者10")) {
                        importPaiWei.setString(50, df.formatCellValue(row.getCell(51)));
                    } else {
                        importPaiWei.setString(50, "");
                    }
                    if (results.contains("舊業主1")) {
                        importPaiWei.setString(51, df.formatCellValue(row.getCell(52)));
                    } else {
                        importPaiWei.setString(51, "");
                    }
                    if (results.contains("舊業主2")) {
                        importPaiWei.setString(52, df.formatCellValue(row.getCell(53)));
                    } else {
                        importPaiWei.setString(52, "");
                    }
                    if (results.contains("Pai Wei ID")) {
                        importPaiWei.setString(53, df.formatCellValue(row.getCell(1)));
                    } else {
                        importPaiWei.setString(53, "");
                    }
                    importPaiWei.setInt(54, Integer.parseInt(df.formatCellValue(row.getCell(0))));

                    importPaiWei.execute();
                }
            }

            wb.close();
            fileIn.close();
            importPaiWei.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("SQL SYNTAX ERROR! importOldPaiWei() using excel file ERROR!");
            ex.printStackTrace();
            return 0;
        } catch (Exception e) {
            System.out.println("Unknown exception in PaiWeiQueries.java/importOldPaiWei() from excel... Most likely from extra null rows in excel");
            e.printStackTrace();
            return 2;
        }
        closeConnection();
        return 1;
    }
}
