/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author SONY
 */
public class RoomInfo {
    private IntegerProperty roomID;
    private StringProperty roomTypeID;
    private StringProperty description;
    private DoubleProperty baseRate;
    private IntegerProperty capacity;
    
    public RoomInfo(int roomID, String roomTypeID, double baseRate, int capacity) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
        this.baseRate = new SimpleDoubleProperty(baseRate);
    }   

    public RoomInfo(int roomID, String roomTypeID, String description, double baseRate) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
        this.description = new SimpleStringProperty(description);
        this.baseRate = new SimpleDoubleProperty(baseRate);
    }   
    
    public RoomInfo(int roomID, double baseRate, int capacity) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.baseRate = new SimpleDoubleProperty(baseRate);
        this.capacity = new SimpleIntegerProperty(capacity);
    }  
    
    public int getRoomID() {
        return roomID.get();
    }
    
    public IntegerProperty roomIDProperty() {
        return roomID;
    }

    public String getRoomTypeID() {
        return roomTypeID.get();
    }
    
    public void setRoomTypeID(String roomTypeID) {
        this.roomTypeID.set(roomTypeID);
    }
    
    public StringProperty roomTypeIDProperty() {
        return roomTypeID;        
    }
        
    public String getDescription() {
        return description.get();
    }
    
    public StringProperty descriptionProperty() {
        return description;        
    }
    
    public double getBaseRate() {
        return baseRate.get();
    }

    public void setBaseRate(double baseRate) {
        this.baseRate.set(baseRate);
    }
        
    public DoubleProperty baseRateProperty() {
        return baseRate;
    }
    
    public int getCapacity() {
        return capacity.get();
    }
    
    public IntegerProperty capacityProperty() {
        return capacity;
    }
    
    @Override
    public String toString() {
        return "Room { RoomID=" + roomID + " Description=" + description + " }";
    }
}
