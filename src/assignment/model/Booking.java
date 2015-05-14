/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.util.ArrayList;
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
    
    private IntegerProperty refCode;
    private StringProperty custFirstName;
    private StringProperty custLastName;
    private IntegerProperty numPeople;
    private IntegerProperty roomNum;
    private ObjectProperty<LocalDate> createdDate;
    private IntegerProperty numBreakfast;
    private ObjectProperty<LocalDate> checkIn;
    private ObjectProperty<LocalDate> checkOut;
    private DoubleProperty amountPaid;
    private DoubleProperty amountDue;
    private BooleanProperty earlyCheckIn;
    private BooleanProperty lateCheckOut;

    /*// For later use when room is stored as an array
    private ObjectProperty<ArrayList<Integer>> room;*/
    
    public Booking(int refCode, String custFirstName, String custLastName, 
            int numPeople, int roomNum, LocalDate createdDate, int numBreakfast, 
            LocalDate checkIn, LocalDate checkOut, boolean earlyCheckIn, 
            boolean lateCheckOut, double amountPaid, double amountDue) {
        this.refCode = new SimpleIntegerProperty(refCode);
        this.custFirstName = new SimpleStringProperty(custFirstName);
        this.custLastName = new SimpleStringProperty(custLastName);
        this.numPeople = new SimpleIntegerProperty(numPeople);
        this.roomNum = new SimpleIntegerProperty(roomNum);
        this.createdDate = new SimpleObjectProperty(createdDate);
        this.numBreakfast = new SimpleIntegerProperty(numBreakfast);
        this.checkIn = new SimpleObjectProperty(checkIn);
        this.checkOut = new SimpleObjectProperty(checkOut);
        this.earlyCheckIn = new SimpleBooleanProperty(earlyCheckIn);
        this.lateCheckOut = new SimpleBooleanProperty(lateCheckOut);
        this.amountPaid = new SimpleDoubleProperty(amountPaid);
        this.amountDue = new SimpleDoubleProperty(amountDue);
    }
    
    public Booking(String custFirstName, String custLastName, 
            int numPeople, int roomNum, LocalDate createdDate, int numBreakfast, 
            LocalDate checkIn, LocalDate checkOut, boolean earlyCheckIn, 
            boolean lateCheckOut, double amountPaid, double amountDue) {
        this.custFirstName = new SimpleStringProperty(custFirstName);
        this.custLastName = new SimpleStringProperty(custLastName);
        this.numPeople = new SimpleIntegerProperty(numPeople);
        this.roomNum = new SimpleIntegerProperty(roomNum);
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

    public void setRefCode(int refCode) {
        this.refCode.set(refCode);
    }

    public IntegerProperty refCodeProperty() {
        return refCode;
    }
    
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

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut.set(checkOut);
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
    
    @Override
    public String toString() {
        return "Booking { Ref. Code=" + refCode + "Cust First Name=" + custFirstName + "Cust Last Name=" + custLastName +" }";
    }
}
  