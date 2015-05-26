/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.Employee;
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
public class LoginQueries extends DatabaseQuery {

    PreparedStatement getLoginDetails = null;
    ResultSet rs = null;
    List<Employee> loginDetails;

    public List<Employee> getLoginDetails(int userID, String password) {
        loginDetails = new ArrayList<Employee>();
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
}
