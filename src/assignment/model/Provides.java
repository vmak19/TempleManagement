/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import java.sql.Date;
import java.time.LocalDate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author SONY
 */
public class Provides {
    
    private IntegerProperty provideID;
    private IntegerProperty refCode;
    private IntegerProperty roomID;
    private IntegerProperty serviceID;
    private ObjectProperty<LocalDate> createdDate;
    
    public Provides() {
        this(0, 0, 0, null);
    }
    
    public Provides(int provideID, int refCode, int roomID, int serviceID, LocalDate createdDate) {
        this.provideID = new SimpleIntegerProperty(provideID);
        this.refCode = new SimpleIntegerProperty(refCode);
        this.roomID = new SimpleIntegerProperty(roomID);
        this.serviceID = new SimpleIntegerProperty(serviceID);
        this.createdDate = new SimpleObjectProperty(createdDate);
    }
    
    public Provides(int refCode, int roomID, int serviceID, LocalDate createdDate) {
        //this.provideID = new SimpleIntegerProperty(-1);
        this.refCode = new SimpleIntegerProperty(refCode);
        this.roomID = new SimpleIntegerProperty(roomID);
        this.serviceID = new SimpleIntegerProperty(serviceID);
        this.createdDate = new SimpleObjectProperty(createdDate);
    }
    
    public int getProvideID() {
        return provideID.get();
    }

    public void setProvideID(int provideID) {
        this.provideID.set(provideID);
    }
    
    public IntegerProperty provideIDProperty() {
        return provideID;        
    }
    
    public int getRefCode() {
        return refCode.get();
    }

    public void setRefCode(int refCode) {
        this.refCode.set(refCode);
    }
    
    public IntegerProperty refCodeProperty() {
        return refCode;        
    }
    
    // Room Number
    public int getRoomID() {
        return roomID.get();
    }
    
    public void setRoomID(int roomID) {
        this.roomID.set(roomID);
    }
    
    public int getServiceID() {
        return serviceID.get();
    }

    public void setServiceID(int serviceID) {
        this.serviceID.set(serviceID);
    }
    
    public IntegerProperty serviceIDProperty() {
        return serviceID;        
    }
    
    // Created Date
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
}
