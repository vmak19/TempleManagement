/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import java.sql.Date;
import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author SONY
 */
public class BookingInfo {
    
    private IntegerProperty refCode;
    private StringProperty custFirstName;
    private StringProperty custLastName;
    private IntegerProperty roomID;
    private ObjectProperty<LocalDate> createdDate;
    private IntegerProperty numBreakfast;
    private ObjectProperty<LocalDate> checkIn;
    private ObjectProperty<LocalDate> checkOut;
    private BooleanProperty earlyCheckIn;
    private BooleanProperty lateCheckOut;
    private DoubleProperty amountPaid;
    private DoubleProperty amountDue;
    
    /**
     * Constructor is called by getBookingsByRoom(). 
     */
    public BookingInfo(int refCode, String custFirstName, String custLastName, 
            int roomID, LocalDate checkIn, LocalDate checkOut) {
        this.refCode = new SimpleIntegerProperty(refCode);
        this.custFirstName = new SimpleStringProperty(custFirstName);
        this.custLastName = new SimpleStringProperty(custLastName);
        this.roomID = new SimpleIntegerProperty(roomID);
        this.checkIn = new SimpleObjectProperty(checkIn);
        this.checkOut = new SimpleObjectProperty(checkOut);
    }
    
    /**
     * Constructor is called by getBookings(). 
     */
    public BookingInfo(int refCode, String custFirstName, String custLastName, 
            int roomID, LocalDate createdDate, int numBreakfast, 
            LocalDate checkIn, LocalDate checkOut, boolean earlyCheckIn, 
            boolean lateCheckOut, double amountPaid, double amountDue) {
        this.refCode = new SimpleIntegerProperty(refCode);
        this.custFirstName = new SimpleStringProperty(custFirstName);
        this.custLastName = new SimpleStringProperty(custLastName);
        this.roomID = new SimpleIntegerProperty(roomID);
        this.createdDate = new SimpleObjectProperty(createdDate);
        this.numBreakfast = new SimpleIntegerProperty(numBreakfast);
        this.checkIn = new SimpleObjectProperty(checkIn);
        this.checkOut = new SimpleObjectProperty(checkOut);
        this.earlyCheckIn = new SimpleBooleanProperty(earlyCheckIn);
        this.lateCheckOut = new SimpleBooleanProperty(lateCheckOut);
        this.amountPaid = new SimpleDoubleProperty(amountPaid);
        this.amountDue = new SimpleDoubleProperty(amountDue);
    }
    
    // Ref. Code get() set()
    public int getRefCode() {
        return refCode.get();
    }

    public IntegerProperty refCodeProperty() {
        return refCode;
    }
    
    // First name
    public String getCustFirstName() {
        return custFirstName.get();
    }

    public StringProperty custFirstNameProperty() {
        return custFirstName;
    }
    
    // Last name
    public String getCustLastName() {
        return custLastName.get();
    }

    public StringProperty custLastNameProperty() {
        return custLastName;
    }
    
    // Room Number
    public int getRoomID() {
        return roomID.get();
    }

    // Created Date
    public LocalDate getCreatedDate() {
        return createdDate.get();
    }

    // Covert LocalDate to Date
    public Date getCreatedDateToDate() {
        LocalDate localDate = getCreatedDate();
        Date date = Date.valueOf(localDate);
        return date;
    }
    
    // Number of Breakfast Dates
    public int getNumBreakfast() {
        return numBreakfast.get();
    }

    // Check In
    public LocalDate getCheckIn() {
        return checkIn.get();
    }
    
    public ObjectProperty<LocalDate> checkInProperty() {
        return checkIn;
    }
    
    // Covert LocalDate to Date
    public Date getCheckInToDate() {
        LocalDate localDate = getCheckIn();
        Date date = Date.valueOf(localDate);
        return date;
    }
    
    // Check Out
    public LocalDate getCheckOut() {
        return checkOut.get();
    }

    public ObjectProperty<LocalDate> checkOutProperty() {
        return checkIn;
    }
    
    // Covert LocalDate to Date
    public Date getCheckOutToDate() {
        LocalDate localDate = getCheckOut();
        //Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date date = Date.valueOf(localDate);
        return date;
    }
    
    // Amount Paid
    public double getAmountPaid() {
        return amountPaid.get();
    }

    // Amount Due
    public double getAmountDue() {
        return amountDue.get();
    }

    // Early check in
    public boolean getEarlyCheckIn() {
        return earlyCheckIn.get();
    }
    
    // Late check out
    public boolean getLateCheckOut() {
        return lateCheckOut.get();
    }
    
}
