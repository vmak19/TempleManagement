package assignment.database;

import assignment.model.Log;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.TimeZone;

public class LogQueries extends DatabaseQuery {

    PreparedStatement insertLog = null;
    PreparedStatement getAllLogs = null;
    PreparedStatement getSessionInfo = null;
    ResultSet rs = null;
    List<Log> logs;
    List<Log> sessionDetail;

    public List<Log> getSessionDetails(int userID) {
        sessionDetail = new ArrayList<Log>();
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

    public List<Log> getLogs() {
        logs = new ArrayList<Log>();
        openConnection();
        try {
            getAllLogs = conn.prepareStatement("select * from app.LOG");
            rs = getAllLogs.executeQuery();
            while (rs.next()) {
                logs.add(
                        new Log(rs.getInt("logID"),
                                rs.getInt("userID"),
                                rs.getString("empFirstName"),
                                rs.getString("empLastName"),
                                rs.getString("dateMod"),
                                rs.getString("itemModified")));
            }
            rs.close();
            getAllLogs.close();
        } catch (SQLException ex) {
            System.out.println("getLogs() error!");
        }
        closeConnection();
        return logs;
    }

    public int insertLog(Log itemModified, int userID) {
        sessionDetail = getSessionDetails(userID);

        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp timestampObject = new java.sql.Timestamp(calendar.getTime().getTime());

        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(stamp.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a");   //dd/MM/yyyy 
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+10"));
        String formattedDate = sdf.format(date);
        
        int returnValue = -1;
        openConnection();
        try {
            insertLog = conn.prepareStatement("insert into app.LOG "
                    + "(userID, empFirstName, empLastName, dateMod, itemModified)"
                    + "values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertLog.setInt(1, userID);
            insertLog.setString(2, sessionDetail.get(0).getEmpFirstName());
            insertLog.setString(3, sessionDetail.get(0).getEmpLastName());
            insertLog.setString(4, formattedDate);
            insertLog.setString(5, itemModified.getItemModified());
            //insertLog.setString(5, formattedDate);
            //insertLog.setString(6, itemModified.getItemModified());
            insertLog.executeUpdate();

            rs = insertLog.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close();
            insertLog.close();
        } catch (SQLException ex) {
            System.out.println("insertLog() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
        return returnValue;
    }

    public int insertLogFromFile(Log toInsert) {
        int returnValue = -1;
        openConnection();
        try {
            insertLog = conn.prepareStatement("insert into app.LOG "
                    + "(userID, empFirstName, empLastName, dateMod, itemModified)"
                    + "values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertLog.setInt(1, toInsert.getUserID());
            insertLog.setString(2, toInsert.getEmpFirstName());
            insertLog.setString(3, toInsert.getEmpLastName());
            insertLog.setString(4, toInsert.getDateMod());
            insertLog.setString(5, toInsert.getItemModified());
            insertLog.executeUpdate();

            rs = insertLog.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close();
            insertLog.close();
        } catch (SQLException ex) {
            System.out.println("insertLogFromFile() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
        return returnValue;
    }
}
