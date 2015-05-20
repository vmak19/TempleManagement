package assignment.database;

import assignment.model.Assignment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SONY
 */
public class AssignmentQueries extends DatabaseQuery{
    PreparedStatement insertAssignment = null;
    ResultSet rs = null;
    
    public void insertAssignment(Assignment toInsert) {
        openConnection();
        
        try {                
            insertAssignment = conn.prepareStatement("insert into app.assignment "
                    + "(refCode, roomID) "
                    + "values (?, ?)");
            insertAssignment.setInt(1, toInsert.getRefCode());  
            insertAssignment.setInt(2, toInsert.getRoomID());
            insertAssignment.executeUpdate();
            
            insertAssignment.close();
        } catch (SQLException ex) {
            System.out.println("insertAssignment() ERROR!");
            ex.printStackTrace();
        }
        closeConnection();
    }
}
