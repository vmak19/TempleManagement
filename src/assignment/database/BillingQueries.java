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
    ResultSet rs = null;
    List<Billing> billings;
    
    public List<Billing> getBillings() {
        billings = new ArrayList<Billing>();
        openConnection();
        try {
            getAllBillings = conn.prepareStatement("select * from app.BILLING");
            rs = getAllBillings.executeQuery();
            while (rs.next()) {
                billings.add(
                    new Billing(rs.getInt("billingID"), 
                            rs.getDouble("amountPaid"), 
                            rs.getDouble("amountDue"),
                            rs.getDate("date").toLocalDate()));
            }
            rs.close();
            getAllBillings.close();
        } catch (SQLException ex) {
            System.out.println("getBillings() error!");
        }
        closeConnection();
        return billings;
    }
    
    public int insertBilling(Billing toInsert) {
        int returnValue = -1;
        openConnection();
        try {
            
            insertBilling = conn.prepareStatement("insert into app.BILLING "
                    + "(billingID, amountPaid, amountDue, date)"
                    + "values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertBilling.setInt(1, toInsert.getBillingID());
            insertBilling.setDouble(2, toInsert.getAmountPaid());
            insertBilling.setDouble(3, toInsert.getAmountDue());
            insertBilling.setDate(4, toInsert.getDateToDate());
            insertBilling.executeUpdate();

            rs = insertBilling.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close();
            insertBilling.close();
        } catch (SQLException ex) {
            System.out.println("insertBilling() ERROR!");
        }

        closeConnection();
        return returnValue;
    }
}
