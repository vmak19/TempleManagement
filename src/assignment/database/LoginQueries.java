package assignment.database;

import assignment.model.Employee;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginQueries extends DatabaseQuery {

    PreparedStatement getLoginDetails = null;
    PreparedStatement getAdminDetails = null;
    ResultSet rs = null;
    List<Employee> loginDetails;
    List<Employee> adminDetail;

    public List<Employee> getLoginDetails(int userID, String password) {
        loginDetails = new ArrayList<Employee>(); //Will soon store 1 employee
        openConnection();
        try {
            getLoginDetails = conn.prepareStatement("select USERID, PASSWORD from app.EMPLOYEE "
             + "where (USERID = ? AND PASSWORD = ?)");
            
            getLoginDetails.setInt(1, userID);
            getLoginDetails.setString(2, password);
            rs = getLoginDetails.executeQuery();
            while (rs.next()) {
                loginDetails.add(
                        new Employee(rs.getInt("userID"), rs.getString("password")));
            }
            rs.close();
            getLoginDetails.close();
        } catch (SQLException ex) {
            System.out.println("getLoginDetails() error!");
            ex.printStackTrace();
        }
        closeConnection();        
        return loginDetails;
    }    
    
    public List<Employee> getAdminDetails(int userID) {
        adminDetail = new ArrayList<Employee>();
        openConnection();
        try {
            getAdminDetails = conn.prepareStatement("select ISADMINISTRATOR from app.EMPLOYEE "
             + "where (USERID = ?)");
            
            getAdminDetails.setInt(1, userID);
            rs = getAdminDetails.executeQuery();
            while (rs.next()) {
                adminDetail.add(
                         new Employee(rs.getBoolean("isAdministrator")));
            }
            rs.close();
            getAdminDetails.close();
        } catch (SQLException ex) {
            System.out.println("getAdminDetails() error!");
            ex.printStackTrace();
        }
        closeConnection();        
        return adminDetail;
    }  
    
    public String getSessionEmployeeName(int userID) {
        String empName = "";
        openConnection();
        try {
            getAdminDetails = conn.prepareStatement("select empFirstName, empLastName from app.EMPLOYEE "
             + "where (USERID = ?)");
            
            getAdminDetails.setInt(1, userID);
            rs = getAdminDetails.executeQuery();
            while (rs.next()) {
                empName = rs.getString("empFirstName") + " " + rs.getString("empLastName");
            }
            rs.close();
            getAdminDetails.close();
        } catch (SQLException ex) {
            System.out.println("getSessionEmployee() error!");
            ex.printStackTrace();
        }
        closeConnection();        
        return empName;
    }
}
