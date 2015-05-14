/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.Booking;
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
public class BookingQueries extends DatabaseQuery{

    PreparedStatement insertBooking = null;
    PreparedStatement getAllBookings = null;
    PreparedStatement deleteBooking = null;
    ResultSet rs = null;
    List<Booking> bookings;
    
    public List<Booking> getBookings() {
        bookings = new ArrayList<Booking>();
        openConnection();
        try {
            getAllBookings = conn.prepareStatement("select * from app.BOOKING");
            rs = getAllBookings.executeQuery();
            while (rs.next()) {
                bookings.add(
                    new Booking(rs.getInt("refCode"), rs.getString("custFirstName"), 
                            rs.getString("custLastName"), rs.getInt("numPeople"), 
                            rs.getInt("roomNum"), rs.getDate("createdDate").toLocalDate(), 
                            rs.getInt("numBreakfast"), rs.getDate("checkIn").toLocalDate(),
                            rs.getDate("checkOut").toLocalDate(), rs.getBoolean("earlyCheckIn"), 
                            rs.getBoolean("lateCheckOut"), rs.getDouble("amountPaid"), 
                            rs.getDouble("amountDue")));
            }
            rs.close();
            getAllBookings.close();
        } catch (SQLException ex) {
            System.out.println("getBookings() error!");
        }
        closeConnection();
        return bookings;
    }
    
    public int insertBooking(Booking toInsert) {
        int returnValue = -1;
        openConnection();
        try {
            
            insertBooking = conn.prepareStatement("insert into app.booking "
                    + "(custFirstName, custLastName, numPeople, roomNum, "
                    + "createdDate, numBreakfast, checkIn, checkOut, earlyCheckIn, "
                    + "lateCheckOut, amountPaid, amountDue) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertBooking.setString(1, toInsert.getCustFirstName());
            insertBooking.setString(2, toInsert.getCustLastName());
            insertBooking.setInt(3, toInsert.getNumPeople());
            insertBooking.setInt(4, toInsert.getRoomNum());
            insertBooking.setDate(5, toInsert.getCreatedDateToDate());
            insertBooking.setInt(6, toInsert.getNumBreakfast());
            insertBooking.setDate(7, toInsert.getCheckInToDate());
            insertBooking.setDate(8, toInsert.getCheckOutToDate());
            insertBooking.setBoolean(9, toInsert.getEarlyCheckIn());
            insertBooking.setBoolean(10, toInsert.getLateCheckOut());
            insertBooking.setDouble(11, toInsert.getAmountPaid());
            insertBooking.setDouble(12, toInsert.getAmountDue());
            insertBooking.executeUpdate();

            rs = insertBooking.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close(); 
            insertBooking.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! insertBooking() ERROR!");
        }

        closeConnection();
        return returnValue;
    }
    
    public void deleteBooking(Booking toDelete) {
        openConnection();
        try {
            deleteBooking = conn.prepareStatement("delete from app.booking where refcode = ?");
            deleteBooking.setInt(1, toDelete.getRefCode());
            deleteBooking.execute();
            System.out.println("deleted");
        } catch (SQLException ex) {
            System.out.println("ERROR! deleteBooking()!");
        }
        closeConnection();
    }
}
