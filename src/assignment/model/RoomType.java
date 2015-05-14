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
 * @author z5018077
 */
public class RoomType {
    
    private IntegerProperty roomTypeID;
    private StringProperty description;
    private DoubleProperty baseRate;
    private IntegerProperty capacity;

    public RoomType(int roomTypeID) {
        this.roomTypeID = new SimpleIntegerProperty(roomTypeID);        
    }
    
    public RoomType(int roomTypeID, String description, double baseRate, int capacity) {
        this.roomTypeID = new SimpleIntegerProperty(roomTypeID);
        this.description = new SimpleStringProperty(description);
        this.baseRate = new SimpleDoubleProperty(baseRate);
        this.capacity = new SimpleIntegerProperty(capacity);
    }

    public int getRoomTypeID() {
        return roomTypeID.get();
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID.set(roomTypeID);
    }

    public IntegerProperty roomTypeIDProperty() {
        return roomTypeID;        
    }
    
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
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

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }
    
    public IntegerProperty capacityProperty() {
        return capacity;        
    }
    
    
}
