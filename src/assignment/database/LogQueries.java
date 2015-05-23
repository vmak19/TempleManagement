/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.Employee;
import assignment.model.Log;
import assignment.view.LoginScreenController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Mak
 */
public class LogQueries extends DatabaseQuery{
    
    PreparedStatement insertLog = null;
    PreparedStatement getAllLogs = null;
    PreparedStatement getSessionInfo = null;
    ResultSet rs = null;
    List<Log> logs;
    List<Log> sessionDetail;
    LoginScreenController loginScreenController;
    
    public List<Log> getSessionDetails() {
        sessionDetail = new ArrayList<Log>();
        openConnection();
        try {
            //System.out.println(loginScreenController.getUserID() + loginScreenController.getPassword());
            getSessionInfo = conn.prepareStatement("select USERID, EMPFIRSTNAME, EMPLASTNAME from app.EMPLOYEE "
             + "where (USERID = ? AND PASSWORD = ?)");
            
            getSessionInfo.setString(1, loginScreenController.getUserID());
            getSessionInfo.setString(2, loginScreenController.getPassword());
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
                            rs.getDate("dateMod").toLocalDate(), 
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
    
    public int insertLog(Log toInsert) {
        int returnValue = -1;
        openConnection();
        try {            
            insertLog = conn.prepareStatement("insert into app.LOG "
                    + "(userID, empFirstName, empLastName, dateMod, itemModified)"
                    + "values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertLog.setInt(1, toInsert.getUserID());
            insertLog.setString(2, toInsert.getEmpFirstName());
            insertLog.setString(3, toInsert.getEmpLastName());
            insertLog.setDate(4, toInsert.getDateModToDate());
            insertLog.setString(5, toInsert.getItemModified());
            insertLog.executeUpdate();

            rs = insertLog.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close();
            insertLog.close();
        } catch (SQLException ex) {
            System.out.println("insertLog() ERROR!");
        }

        closeConnection();
        return returnValue;
    }
}
