/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.Service;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author SONY
 */
public class ServiceQueries extends DatabaseQuery{
    PreparedStatement insertService = null;
    ResultSet rs = null;

    public void insertService(Service toInsert) {
        int returnValue = -1;
        openConnection();

        try {
            insertService = conn.prepareStatement("insert into app.service "
                    + "(servicedesc, cost) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertService.setString(1, toInsert.getServiceDesc());
            insertService.setDouble(2, toInsert.getCost());
            insertService.executeUpdate();

            rs = insertService.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close(); 
            insertService.close();
        } catch (SQLException ex) {
            System.out.println("insertService() ERROR!");
            ex.printStackTrace();
        }
        closeConnection();
    }
    
}
