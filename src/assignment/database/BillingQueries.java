/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.Billing;
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
 * @author Mak
 */
public class BillingQueries extends DatabaseQuery{
    
    PreparedStatement insertBilling = null;
    PreparedStatement getAllBillings = null;
    PreparedStatement deleteBilling = null;
    ResultSet rs = null;
    List<Billing> billings;
    
    public List<Billing> getBillings() {
        billings = new ArrayList<Billing>();
        openConnection();
        try {
            getAllBillings = conn.prepareStatement("select * from app.BOOKING");
            rs = getAllBillings.executeQuery();
            while (rs.next()) {
                billings.add(
                    new Billing(rs.getInt("refCode"), rs.getDouble("amountPaid"), 
                            rs.getDouble("amountDue")));
            }
            rs.close();
            getAllBillings.close();
        } catch (SQLException ex) {
            System.out.println("getBillings() error!");
        }
        closeConnection();
        return billings;
    }
    
    public void deleteBilling(Billing toDelete) {
        openConnection();
        try {
            deleteBilling = conn.prepareStatement("delete from app.BILLING where REFCODE = ?");
            deleteBilling.setInt(1, toDelete.getRefCode());
            deleteBilling.execute();
            System.out.println("deleted");
        } catch (SQLException ex) {
            System.out.println("ERROR! deleteEmployee()!");
            ex.printStackTrace();
        }
        closeConnection();
    }
   
}
