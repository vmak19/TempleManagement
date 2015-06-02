/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import java.util.List;
import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author SONY
 */
public class RoomInfo {

    private IntegerProperty roomID;
    private ObjectProperty<List<Integer>> roomIDList;
    private StringProperty roomTypeID;
    private StringProperty description;
    private DoubleProperty baseRate;
    private IntegerProperty capacity;
    private IntegerProperty noOfBeds;

    /**
     * Is used by selectedRoomData.
     */
    public RoomInfo(int roomID, String roomTypeID, double baseRate, int capacity) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
        this.baseRate = new SimpleDoubleProperty(baseRate);
        this.capacity = new SimpleIntegerProperty(capacity);
    }

    /**
     * Is called by getAvailableRoomsByType() (v2).
     */
    public RoomInfo(List<Integer> roomIDList, String roomTypeID, double baseRate, int capacity) {
        this.roomIDList = new SimpleObjectProperty(roomIDList);
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
        this.baseRate = new SimpleDoubleProperty(baseRate);
        this.capacity = new SimpleIntegerProperty(capacity);
    }

    public RoomInfo(int roomID, String roomTypeID, String description, double baseRate, int capacity, int noOfBeds) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
        this.description = new SimpleStringProperty(description);
        this.baseRate = new SimpleDoubleProperty(baseRate);
        this.capacity = new SimpleIntegerProperty(capacity);
        this.noOfBeds = new SimpleIntegerProperty(noOfBeds);
    }

    public RoomInfo(int roomID, double baseRate, int capacity) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.baseRate = new SimpleDoubleProperty(baseRate);
        this.capacity = new SimpleIntegerProperty(capacity);
    }

    public RoomInfo(int roomID, String roomTypeID, double baseRate) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
        this.baseRate = new SimpleDoubleProperty(baseRate);
    }

    public int hashCode() {
        int hash = 3;
        hash = 39 * hash + Objects.hashCode(this.roomID);
        hash = 39 * hash + Objects.hashCode(this.roomIDList);
        hash = 39 * hash + Objects.hashCode(this.roomTypeID);
        hash = 39 * hash + Objects.hashCode(this.description);
        hash = 39 * hash + Objects.hashCode(this.baseRate);
        hash = 39 * hash + Objects.hashCode(this.capacity);
        hash = 39 * hash + Objects.hashCode(this.noOfBeds);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoomInfo other = (RoomInfo) obj;
        if (this.roomID != other.roomID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Room{" + "roomID=" + roomID + ", roomIDList=" + roomIDList
                + ", " + "roomTypeID=" + roomTypeID + ", description="
                + description + "baseRate=" + baseRate + ", capacity=" 
                + capacity + ", noOfBeds=" + noOfBeds + '}';
    }

    public int getRoomID() {
        return roomID.get();
    }

    public IntegerProperty roomIDProperty() {
        return roomID;
    }

    public List<Integer> getRoomIDList() {
        return roomIDList.get();
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

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }

    public int getNoOfBeds() {
        return noOfBeds.get();
    }

    public void setNoOfBeds(int noOfBeds) {
        this.noOfBeds.set(noOfBeds);
    }

    public IntegerProperty noOfBedsProperty() {
        return noOfBeds;
    }    
}
