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
import java.sql.Date;
import java.time.LocalDate;
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
    PreparedStatement getAllSameRefCodeRoom = null;
    PreparedStatement getAllBookingsByRoom = null;
    PreparedStatement updateBooking = null;
    PreparedStatement deleteBooking = null;
    ResultSet rs = null;
    List<BookingInfo> bookings;
    List<BookingInfo> bookingsByRoom;
    int latestRefCode;
    
    public List<BookingInfo> getBookings() {
        bookings = new ArrayList<BookingInfo>();
        openConnection();
        try {
            getAllBookings = conn.prepareStatement("select distinct app.BOOKING.REFCODE, "
                    + "CUSTFIRSTNAME, CUSTLASTNAME, ROOMID, "
                    + "CREATEDDATE, NUMBREAKFAST, CHECKIN, CHECKOUT, "
                    + "EARLYCHECKIN, LATECHECKOUT, AMOUNTPAID, AMOUNTDUE "
                    + "from app.BOOKING "
                    + "inner join app.ASSIGNMENT "
                    + "on app.BOOKING.REFCODE = app.ASSIGNMENT.REFCODE");
            rs = getAllBookings.executeQuery();
            
            getAllSameRefCodeRoom = conn.prepareStatement("select * from app.ASSIGNMENT");
            
            
            while (rs.next()) {
                ResultSet rsRooms = getAllSameRefCodeRoom.executeQuery();
                List<Integer> roomIDList = new ArrayList<Integer>();
                
                // Group roomID with the same ref. code into one
                while (rsRooms.next()) {
                    if (rs.getInt("refCode") == rsRooms.getInt("refCode")/* && rs.getInt("roomID") != currentRoomID*/) {
                        roomIDList.add(rsRooms.getInt("roomID"));
                    }
                }
                
                // Add bookings with different ref. code
                if (bookings.isEmpty() || bookings.get(bookings.size()-1).getRefCode() != rs.getInt("refCode")) {
                    bookings.add(
                            new BookingInfo(rs.getInt("refCode"), rs.getString("custFirstName"),
                                    rs.getString("custLastName"), roomIDList,
                                    rs.getDate("createdDate").toLocalDate(),
                                    rs.getInt("numBreakfast"), rs.getDate("checkIn").toLocalDate(),
                                    rs.getDate("checkOut").toLocalDate(), rs.getBoolean("earlyCheckIn"),
                                    rs.getBoolean("lateCheckOut"), rs.getDouble("amountPaid"),
                                    rs.getDouble("amountDue")));
                }
                rsRooms.close();
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
    
    public List<BookingInfo> getBookingsByRoom(int roomID, boolean currentGuest) {
        bookingsByRoom = new ArrayList<BookingInfo>();
        openConnection();
        try {
            if (currentGuest) {
                getAllBookingsByRoom = conn.prepareStatement("select app.BOOKING.REFCODE, "
                        + "CUSTFIRSTNAME, CUSTLASTNAME, ROOMID, CHECKIN, CHECKOUT "
                        + "from app.BOOKING "
                        + "inner join app.ASSIGNMENT "
                        + "on app.BOOKING.REFCODE = app.ASSIGNMENT.REFCODE "
                        + "where ROOMID = ? and CHECKOUT <= ? and CHECKIN >= ?");
                
                getAllBookingsByRoom.setInt(1, roomID);
                getAllBookingsByRoom.setDate(2, Date.valueOf(LocalDate.now()));
                getAllBookingsByRoom.setDate(3, Date.valueOf(LocalDate.now()));
                
            } else {
                getAllBookingsByRoom = conn.prepareStatement("select app.BOOKING.REFCODE, "
                        + "CUSTFIRSTNAME, CUSTLASTNAME, ROOMID, CHECKIN, CHECKOUT "
                        + "from app.BOOKING "
                        + "inner join app.ASSIGNMENT "
                        + "on app.BOOKING.REFCODE = app.ASSIGNMENT.REFCODE "
                        + "where ROOMID = ? and CHECKIN > ?");
            
                getAllBookingsByRoom.setInt(1, roomID);
                getAllBookingsByRoom.setDate(2, Date.valueOf(LocalDate.now()));
            }
            
            rs = getAllBookingsByRoom.executeQuery();
            while (rs.next()) {
                bookingsByRoom.add(
                    new BookingInfo(rs.getInt("refCode"), rs.getString("custFirstName"), 
                            rs.getString("custLastName"), rs.getInt("roomID"), 
                            rs.getDate("checkIn").toLocalDate(), 
                            rs.getDate("checkOut").toLocalDate()));
            }
            rs.close();
            getAllBookingsByRoom.close();
        } catch (SQLException ex) {
            System.out.println("getBookingsByRoom() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return bookingsByRoom;
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
        latestRefCode = returnValue;
        closeConnection();
        return returnValue;
    }
    
    public int getLatestRefCode() {
        return latestRefCode;
    }
    
    public void updateBooking(Booking toUpdate) {
        openConnection();
        try {
            updateBooking = conn.prepareStatement("update app.booking set "
                    + "custFirstName=?, custLastName=?, createdDate=?, "
                    + "numBreakfast=?, checkIn=?, checkOut=?, earlyCheckIn=?, "
                    + "lateCheckOut=?, amountPaid=?, amountDue=? where refCode=?");
            updateBooking.setString(1, toUpdate.getCustFirstName());
            updateBooking.setString(2, toUpdate.getCustLastName());
            updateBooking.setDate(3, toUpdate.getCreatedDateToDate());
            updateBooking.setInt(4, toUpdate.getNumBreakfast());
            updateBooking.setDate(5, toUpdate.getCheckInToDate());
            updateBooking.setDate(6, toUpdate.getCheckOutToDate());
            updateBooking.setBoolean(7, toUpdate.getEarlyCheckIn());
            updateBooking.setBoolean(8, toUpdate.getLateCheckOut());
            updateBooking.setDouble(9, toUpdate.getAmountPaid());
            updateBooking.setDouble(10, toUpdate.getAmountDue());
            updateBooking.setInt(11, toUpdate.getRefCode());
            updateBooking.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ERROR! updateBooking()!");
            ex.printStackTrace();
        }
        closeConnection();
    }
    
    public void updateAmountDue(int refCode, double newCost) {
        openConnection();
        try {
            updateBooking = conn.prepareStatement("update app.booking set "
                    + "amountDue=amountDue+? where refCode=?");
            updateBooking.setDouble(1, newCost);
            updateBooking.setInt(2, refCode);
            updateBooking.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ERROR! updateBooking()!");
            ex.printStackTrace();
        }
        closeConnection();
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
