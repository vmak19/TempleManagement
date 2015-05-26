/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.Billing;
import assignment.model.Provides;
import assignment.model.ServiceInfo;
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
public class BillingQueries extends DatabaseQuery {

    PreparedStatement insertBilling = null;
    PreparedStatement getAllBillings = null;
    PreparedStatement getSpecificBilling = null;
    PreparedStatement addCostToBilling = null;
    PreparedStatement deductCostToBilling = null;
    PreparedStatement getCost = null;
    PreparedStatement getBalance = null;
    PreparedStatement updateBilling = null;
    ResultSet rs = null;
    List<Billing> billings;

    public List<Billing> getBillings() {
        billings = new ArrayList<Billing>();
        openConnection();
        try {
            getAllBillings = conn.prepareStatement("select refcode, amountpaid, amountdue from app.BOOKING");
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
    /*
     public Billing getSpecificBilling(int refCode) {
     //Billing billings = new Billing();
     openConnection();
     try {
     getSpecificBilling = conn.prepareStatement("select amountpaid, amountdue from app.BOOKING where refcode=?");
     getSpecificBilling.setInt(1, refCode);
     rs = getSpecificBilling.executeQuery();
     while (rs.next()) {
     Billing billings = new Billing(rs.getDouble("amountPaid"), rs.getDouble("amountDue"));
     //new Billing(rs.getDouble("amountPaid"), rs.getDouble("amountDue"));
     }
     rs.close();
     getSpecificBilling.close();
     } catch (SQLException ex) {
     System.out.println("getBillings() error!");
     }
     closeConnection();
     return billings;
     }*/

    public List<Billing> getSpecificBilling(int refCode) {
        billings = new ArrayList<Billing>();
        openConnection();
        try {
            getSpecificBilling = conn.prepareStatement("select amountpaid, amountdue from app.BOOKING where refcode=?");
            getSpecificBilling.setInt(1, refCode);
            rs = getSpecificBilling.executeQuery();
            while (rs.next()) {
                billings.add(
                        new Billing(rs.getDouble("amountPaid"), rs.getDouble("amountDue")));
            }
            rs.close();
            getSpecificBilling.close();
        } catch (SQLException ex) {
            System.out.println("getBillings() error!");
        }
        closeConnection();
        return billings;
    }

    public double getAmountDue(Provides toInsert) {
        double amountDue = 0;
        openConnection();
        try {
            getBalance = conn.prepareStatement("select amountdue "
                    + "from app.booking where REFCODE=?", Statement.RETURN_GENERATED_KEYS);
            getBalance.setInt(1, toInsert.getRefCode());
            rs = getBalance.executeQuery();
            while (rs.next()) {
                amountDue = rs.getDouble("amountDue");
            }

            getBalance.close();
        } catch (SQLException ex) {
            System.out.println("getAmountDue() with toInsert param ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
        return amountDue;
    }

    public double getAmountDue(int myRefCode) {
        double amountDue = 0;
        openConnection();
        try {
            getBalance = conn.prepareStatement("select amountdue "
                    + "from app.booking where REFCODE=?", Statement.RETURN_GENERATED_KEYS);
            getBalance.setInt(1, myRefCode);
            rs = getBalance.executeQuery();
            while (rs.next()) {
                amountDue = rs.getDouble("amountDue");
            }

            getBalance.close();
        } catch (SQLException ex) {
            System.out.println("getAmountDue() with myRefCode param ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
        return amountDue;
    }
    
    public double getAmountPaid(int myRefCode) {
        double amountPaid = 0;
        openConnection();
        try {
            getBalance = conn.prepareStatement("select amountpaid "
                    + "from app.booking where REFCODE=?", Statement.RETURN_GENERATED_KEYS);
            getBalance.setInt(1, myRefCode);
            rs = getBalance.executeQuery();
            while (rs.next()) {
                amountPaid = rs.getDouble("amountPaid");
            }

            getBalance.close();
        } catch (SQLException ex) {
            System.out.println("getAmountDue() with myRefCode param ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
        return amountPaid;
    }

    public void addCost(Provides toInsert) {
        double myAmountDue = getAmountDue(toInsert);

        openConnection();
        try {
            addCostToBilling = conn.prepareStatement("update app.booking set amountdue=?"
                    + "where REFCODE=?", Statement.RETURN_GENERATED_KEYS);
            addCostToBilling.setDouble(1, myAmountDue);
            addCostToBilling.setInt(2, toInsert.getRefCode());
            addCostToBilling.executeUpdate();

            addCostToBilling.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! addCost() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
    }

    public void deductAmountDue(ServiceInfo toDeduct) {
        openConnection();
        try {
            deductCostToBilling = conn.prepareStatement("update app.booking "
                    + "set AMOUNTDUE=AMOUNTDUE-? where REFCODE=?");
            deductCostToBilling.setDouble(1, toDeduct.getCost());
            deductCostToBilling.setInt(2, toDeduct.getRefCode());
            deductCostToBilling.executeUpdate();

            deductCostToBilling.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! deductAmountDue() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
    }

    public void updateBilling(int refCode, double paymentAmount) {
        double myAmountPaid = getAmountPaid(refCode);
        double myAmountDue = getAmountDue(refCode);
        
        openConnection();
        try {
            updateBilling = conn.prepareStatement("update app.booking set amountPaid =?, amountdue=?"
                    + "where REFCODE=?", Statement.RETURN_GENERATED_KEYS);
            updateBilling.setDouble(1, myAmountPaid + paymentAmount);
            updateBilling.setDouble(2, myAmountDue - paymentAmount);
            updateBilling.setInt(3, refCode);
            updateBilling.executeUpdate();
            
            updateBilling.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! updateBilling() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
    }
}
