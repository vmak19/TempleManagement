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
public class AvailableRoom {
    private IntegerProperty roomID;
    private StringProperty roomTypeID;
    private DoubleProperty baseRate;
    /*private ObjectProperty<LocalDate> checkIn;
    private ObjectProperty<LocalDate> checkOut;
    private BooleanProperty earlyCheckIn;
    private BooleanProperty lateCheckOut;*/

    public AvailableRoom(int roomID, String roomTypeID, double baseRate/*,
            LocalDate checkIn, LocalDate checkOut, boolean earlyCheckIn,
    boolean lateCheckOut*/) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
        this.baseRate = new SimpleDoubleProperty(baseRate);
        /*this.checkIn = new SimpleObjectProperty(checkIn);
        this.checkOut = new SimpleObjectProperty(checkOut);
        this.earlyCheckIn = new SimpleBooleanProperty(earlyCheckIn);
        this.lateCheckOut = new SimpleBooleanProperty(lateCheckOut);*/
    }   
    
    // Room ID
    public int getRoomID() {
        return roomID.get();
    }
    
    public IntegerProperty roomIDProperty() {
        return roomID;
    }
    
    // Description
    public String getRoomTypeID() {
        return roomTypeID.get();
    }
    
    public StringProperty roomTypeIDProperty() {
        return roomTypeID;        
    }
    
    // Base rate
    public double getBaseRate() {
        return baseRate.get();
    }
    
    public DoubleProperty baseRateProperty() {
        return baseRate;
    }
    
    /*// Check In
    public LocalDate getCheckIn() {
    return checkIn.get();
    }
    
    public ObjectProperty<LocalDate> checkInProperty() {
    return checkIn;
    }
    
    // Covert LocalDate to Date
    public Date getCheckInToDate() {
    LocalDate localDate = getCheckIn();
    //Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    Date date = Date.valueOf(localDate);
    return date;
    }
    
    // Check Out
    public LocalDate getCheckOut() {
    return checkOut.get();
    }
    
    public ObjectProperty<LocalDate> checkOutProperty() {
    return checkOut;
    }
    
    // Covert LocalDate to Date
    public Date getCheckOutToDate() {
    LocalDate localDate = getCheckOut();
    //Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    Date date = Date.valueOf(localDate);
    return date;
    }
    
    // Early check in
    public boolean getEarlyCheckIn() {
    return earlyCheckIn.get();
    }
    
    public BooleanProperty earlyCheckInProperty() {
    return earlyCheckIn;
    }
    
    // Late check out
    public boolean getLateCheckOut() {
    return lateCheckOut.get();
    }
    
    public BooleanProperty lateCheckOutProperty() {
    return lateCheckOut;
    }*/
    
    @Override
    public String toString() {
        return "Room { RoomID=" + roomID + " Room Type=" + roomTypeID + " }";
    }
}
