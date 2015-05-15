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
    
    private StringProperty roomTypeID;
    private StringProperty description;
    private DoubleProperty baseRate;

    public RoomType(String description, double baseRate) {
        this.description = new SimpleStringProperty(description);
        this.baseRate = new SimpleDoubleProperty(baseRate);
    }
    
    public RoomType(String roomTypeID, String description, double baseRate) {
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
        this.description = new SimpleStringProperty(description);
        this.baseRate = new SimpleDoubleProperty(baseRate);
    }

    // Room type
    public String getRoomTypeID() {
        return roomTypeID.get();
    }

    public void setRoomTypeID(String roomTypeID) {
        this.roomTypeID.set(roomTypeID);
    }

    public StringProperty roomTypeIDProperty() {
        return roomTypeID;        
    }
    
    // Description
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
    
    public StringProperty descriptionProperty() {
        return description;        
    }

    //Base rate
    public double getBaseRate() {
        return baseRate.get();
    }

    public void setBaseRate(double baseRate) {
        this.baseRate.set(baseRate);
    }
    
    public DoubleProperty baseRateProperty() {
        return baseRate;        
    }
    
    @Override
    public String toString() {
        return "RoomType { RoomTypeID=" + roomTypeID + " Description=" + description + " }";
    }
}
