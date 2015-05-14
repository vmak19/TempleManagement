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
    private StringProperty description;
    private DoubleProperty baseRate;

    public RoomInfo(int roomID, String description, double baseRate) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.description = new SimpleStringProperty(description);
        this.baseRate = new SimpleDoubleProperty(baseRate);
    }   
    
    public int getRoomID() {
        return roomID.get();
    }
    
    public IntegerProperty roomIDProperty() {
        return roomID;
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
    
    public DoubleProperty baseRateProperty() {
        return baseRate;
    }
    
    @Override
    public String toString() {
        return "Room { RoomID=" + roomID + " Description=" + description + " }";
    }
}
