/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.Assignment;
import assignment.model.Provides;
import assignment.model.Service;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author SONY
 */
public class ProvidesQueries extends DatabaseQuery{
    PreparedStatement insertProvides = null;
    ResultSet rs = null;
    
    public void insertProvides(Provides toInsert) {
        int returnValue = -1;
        openConnection();

        try {
            insertProvides = conn.prepareStatement("insert into app.service "
                    + "(refCode, serviceID) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertProvides.setInt(1, toInsert.getRefCode());
            insertProvides.setInt(2, toInsert.getServiceID());
            insertProvides.executeUpdate();

            rs = insertProvides.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close(); 
            insertProvides.close();
        } catch (SQLException ex) {
            System.out.println("insertProvides() ERROR!");
            ex.printStackTrace();
        }
        closeConnection();
    }

}
