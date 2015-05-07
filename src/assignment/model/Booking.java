/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

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
public class Booking {
    
    // private final StringProperty refCode
    private final StringProperty custFirstName;
    private final StringProperty custLastName;
    private final IntegerProperty numPeople;
    private final IntegerProperty roomNum;
    private final ObjectProperty<LocalDate> createdDate;
    private final IntegerProperty numBreakfast;
    private final ObjectProperty<LocalDate> checkIn;
    private final ObjectProperty<LocalDate> checkOut;
    private final DoubleProperty amountPaid;
    private final DoubleProperty amountDue;
    private final BooleanProperty earlyCheckIn;
    private final BooleanProperty lateCheckOut;

    // Just for testing purpose
    public Booking(String custFirstName, String custLastName) {
        this.custFirstName = new SimpleStringProperty(custFirstName);
        this.custLastName = new SimpleStringProperty(custLastName);
        
        this.numPeople = new SimpleIntegerProperty(9);
        this.roomNum = new SimpleIntegerProperty(8);
        this.createdDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
        this.numBreakfast = new SimpleIntegerProperty(7);
        this.checkIn = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 22));
        this.checkOut = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 23));
        this.amountPaid = new SimpleDoubleProperty(6);
        this.amountDue = new SimpleDoubleProperty(5);
        this.earlyCheckIn = new SimpleBooleanProperty(true);
        this.lateCheckOut = new SimpleBooleanProperty(false);
    }
    
    
    public Booking(/** StringProperty refCode , */StringProperty custFirstName, 
            StringProperty custLastName, IntegerProperty numPeople, 
            IntegerProperty roomNum, ObjectProperty<LocalDate> createdDate, 
            IntegerProperty numBreakfast, ObjectProperty<LocalDate> checkIn, 
            ObjectProperty<LocalDate> checkOut, DoubleProperty amountPaid, 
            DoubleProperty amountDue, BooleanProperty earlyCheckIn, 
            BooleanProperty lateCheckOut) {
        // this.refCode = refCode;
        this.custFirstName = custFirstName;
        this.custLastName = custLastName;
        this.numPeople = numPeople;
        this.roomNum = roomNum;
        this.createdDate = createdDate;
        this.numBreakfast = numBreakfast;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.amountPaid = amountPaid;
        this.amountDue = amountDue;
        this.earlyCheckIn = earlyCheckIn;
        this.lateCheckOut = lateCheckOut;
    }
    
    // Ref. Code get() set()
    
    // First name
    public String getCustFirstName() {
        return custFirstName.get();
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName.set(custFirstName);
    }

    public StringProperty custFirstNameProperty() {
        return custFirstName;
    }
    
    // Last name
    public String getCustLastName() {
        return custLastName.get();
    }

    public void setCustLastName(String custLastName) {
        this.custLastName.set(custLastName);
    }

    public StringProperty custLastNameProperty() {
        return custLastName;
    }
    
    // Number of people
    public int getNumPeople() {
        return numPeople.get();
    }

    public void setNumPeople(int numPeople) {
        this.numPeople.set(numPeople);
    }

    public IntegerProperty numPeopleProperty() {
        return numPeople;
    }
    
    // Room Number
    public int getRoomNum() {
        return roomNum.get();
    }

    public void setRoomNum(int roomNum) {
        this.roomNum.set(roomNum);
    }

    public IntegerProperty roomNumProperty() {
        return roomNum;
    }
    
    // Birthday
    public LocalDate getCreatedDate() {
        return createdDate.get();
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate.set(createdDate);
    }

    public ObjectProperty<LocalDate> createdDateProperty() {
        return createdDate;
    }
    
    // Number of Breakfast Dates
    public int getNumBreakfast() {
        return numBreakfast.get();
    }

    public void setNumBreakfast(int numBreakfast) {
        this.numBreakfast.set(numBreakfast);
    }

    public IntegerProperty numBreakfastProperty() {
        return numBreakfast;
    }
    
    // Check In
    public LocalDate getCheckIn() {
        return checkIn.get();
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn.set(checkIn);
    }

    public ObjectProperty<LocalDate> checkInProperty() {
        return checkIn;
    }
    
    // Check Out
    public LocalDate getCheckOut() {
        return checkOut.get();
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut.set(checkOut);
    }

    public ObjectProperty<LocalDate> checkOutProperty() {
        return checkOut;
    }
    
    // Amount Paid
    public double getAmountPaid() {
        return amountPaid.get();
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid.set(amountPaid);
    }

    public DoubleProperty amountPaidProperty() {
        return amountPaid;
    }
    
    // Amount Due
    public double getAmountDue() {
        return amountDue.get();
    }

    public void setAmountDue(double amountDue) {
        this.amountDue.set(amountDue);
    }

    public DoubleProperty amountDueProperty() {
        return amountDue;
    }
    
    // Early check in
    public boolean getEarlyCheckIn() {
        return earlyCheckIn.get();
    }
    
    public void setEarlyCheckIn(boolean earlyCheckIn) {
        this.earlyCheckIn.set(earlyCheckIn);
    }
    
    public BooleanProperty earlyCheckInProperty() {
        return earlyCheckIn;
    }
    
    // Late check out
    public boolean getLateCheckOut() {
        return lateCheckOut.get();
    }
    
    public void setLateCheckOut(boolean lateCheckOut) {
        this.lateCheckOut.set(lateCheckOut);
    }
    
    public BooleanProperty lateCheckOutProperty() {
        return lateCheckOut;
    }
}
  