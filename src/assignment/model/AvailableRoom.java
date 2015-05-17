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
    private DoubleProperty baseRate;
    private IntegerProperty numPeople;

    public AvailableRoom(int roomID, double baseRate, int numPeople) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.baseRate = new SimpleDoubleProperty(baseRate);
        this.numPeople = new SimpleIntegerProperty(numPeople);
    }   
    
    // Room ID
    public int getRoomID() {
        return roomID.get();
    }
    
    public void setRoomID(int roomID) {
        this.roomID.set(roomID);
    }
    
    public IntegerProperty roomIDProperty() {
        return roomID;
    }
    
    // Base rate
    public double getBaseRate() {
        return baseRate.get();
    }
    
    public void setBaseRate(double baseRate) {
        this.baseRate.set(baseRate);
    }
    
    public DoubleProperty baseRateProperty() {
        return baseRate;
    }
    
    // Number of People
    public int getNumPeople() {
        return numPeople.get();
    }
    
    public void setNumPeople(int numPeople) {
        this.numPeople.set(numPeople);
    }
    
    public IntegerProperty numPeopleProperty() {
        return numPeople;
    }
}
