/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.Employee;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SONY
 */
public class EmployeeQueries extends DatabaseQuery{

    PreparedStatement insertEmployee = null;
    PreparedStatement getAllEmployees = null;
    PreparedStatement deleteEmployee = null;
    ResultSet rs = null;
    List<Employee> employees;
    
    public List<Employee> getEmployees() {
        employees = new ArrayList<Employee>();
        openConnection();
        try {
            getAllEmployees = conn.prepareStatement("select * from app.EMPLOYEE");
            rs = getAllEmployees.executeQuery();
            while (rs.next()) {
                employees.add(
                    new Employee(rs.getInt("userID"), rs.getString("password"), 
                            rs.getString("empFirstName"), rs.getString("empLastName"), 
                            rs.getBoolean("isAdministrator")));
            }
            rs.close();
            getAllEmployees.close();
        } catch (SQLException ex) {
            System.out.println("getEmployee() error!");
        }
        closeConnection();
        return employees;
    }
    
    public int insertEmployee(Employee toInsert) {
        int returnValue = -1;
        openConnection();
        try {
            
            insertEmployee = conn.prepareStatement("insert into app.employee "
                    + "(password, empFirstName, empLastName, isAdministrator)"
                    + "values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertEmployee.setString(1, toInsert.getPassword());
            insertEmployee.setString(2, toInsert.getEmpFirstName());
            insertEmployee.setString(3, toInsert.getEmpLastName());
            insertEmployee.setBoolean(4, toInsert.getIsAdministrator());
            insertEmployee.executeUpdate();

            rs = insertEmployee.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close();
            insertEmployee.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! insertEmployee() ERROR!");
        }

        closeConnection();
        return returnValue;
    }
    
    public void deleteEmployee(Employee toDelete) {
        openConnection();
        try {
            deleteEmployee = conn.prepareStatement("delete from app.EMPLOYEE where USERID = ?");
            deleteEmployee.setInt(1, toDelete.getUserID());
            deleteEmployee.execute();
            System.out.println("deleted");
        } catch (SQLException ex) {
            System.out.println("ERROR! deleteEmployee()!");
            ex.printStackTrace();
        }
        closeConnection();
    }
}
