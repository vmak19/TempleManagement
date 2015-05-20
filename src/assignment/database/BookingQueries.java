/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.Booking;
import assignment.model.BookingInfo;
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
    List<BookingInfo> bookings;
    
    public List<BookingInfo> getBookings() {
        bookings = new ArrayList<BookingInfo>();
        openConnection();
        try {
            getAllBookings = conn.prepareStatement("select app.BOOKING.REFCODE, "
                    + "CUSTFIRSTNAME, CUSTLASTNAME, ROOMID, "
                    + "CREATEDDATE, NUMBREAKFAST, CHECKIN, CHECKOUT, "
                    + "EARLYCHECKIN, LATECHECKOUT, AMOUNTPAID, AMOUNTDUE "
                    + "from app.BOOKING "
                    + "inner join app.ASSIGNMENT "
                    + "on app.BOOKING.REFCODE = app.ASSIGNMENT.REFCODE");
            rs = getAllBookings.executeQuery();
            while (rs.next()) {
                bookings.add(
                    new BookingInfo(rs.getInt("refCode"), rs.getString("custFirstName"), 
                            rs.getString("custLastName"), rs.getInt("roomID"), 
                            rs.getDate("createdDate").toLocalDate(), 
                            rs.getInt("numBreakfast"), rs.getDate("checkIn").toLocalDate(),
                            rs.getDate("checkOut").toLocalDate(), rs.getBoolean("earlyCheckIn"), 
                            rs.getBoolean("lateCheckOut"), rs.getDouble("amountPaid"), 
                            rs.getDouble("amountDue")));
            }
            rs.close();
            getAllBookings.close();
        } catch (SQLException ex) {
            System.out.println("getBookings() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return bookings;
    }
    
    public int insertBooking(Booking toInsert) {
        int returnValue = -1;
        openConnection();
        try {
            
            insertBooking = conn.prepareStatement("insert into app.booking "
                    + "(custFirstName, custLastName, createdDate, numBreakfast, "
                    + "checkIn, checkOut, earlyCheckIn, lateCheckOut, amountPaid, "
                    + "amountDue) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertBooking.setString(1, toInsert.getCustFirstName());
            insertBooking.setString(2, toInsert.getCustLastName());
            insertBooking.setDate(3, toInsert.getCreatedDateToDate());
            insertBooking.setInt(4, toInsert.getNumBreakfast());
            insertBooking.setDate(5, toInsert.getCheckInToDate());
            insertBooking.setDate(6, toInsert.getCheckOutToDate());
            insertBooking.setBoolean(7, toInsert.getEarlyCheckIn());
            insertBooking.setBoolean(8, toInsert.getLateCheckOut());
            insertBooking.setDouble(9, toInsert.getAmountPaid());
            insertBooking.setDouble(10, toInsert.getAmountDue());
            insertBooking.executeUpdate();

            System.out.println("record inserted");
            rs = insertBooking.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close(); 
            insertBooking.close();
        } catch (SQLException ex) {
            System.out.println("ERROR! insertBooking() ERROR!");
            ex.printStackTrace();
        }

        closeConnection();
        return returnValue;
    }
    
    public void deleteBooking(BookingInfo toDelete) {
        openConnection();
        try {
            deleteBooking = conn.prepareStatement("delete from app.booking "
                    + "left join app.assignment "
                    + "on app.booking.refCode = app.assignment.refCode "
                    + "where refcode = ?");
            deleteBooking.setInt(1, toDelete.getRefCode());
            deleteBooking.execute();
            System.out.println("deleted");
        } catch (SQLException ex) {
            System.out.println("ERROR! deleteBooking()!");
            ex.printStackTrace();
        }
        closeConnection();
    }
}
