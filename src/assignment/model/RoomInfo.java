/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import java.util.Objects;
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
    private IntegerProperty noOfBeds;
    private DoubleProperty extraCharge;
    private DoubleProperty totalCharge;

    public RoomInfo(int roomID, String roomTypeID, double baseRate, int capacity) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
        this.baseRate = new SimpleDoubleProperty(baseRate);
        this.capacity = new SimpleIntegerProperty(capacity);
    }

    public RoomInfo(int roomID, String roomTypeID, String description, double baseRate, int capacity, int noOfBeds,
            double extraCharge, double totalCharge) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
        this.description = new SimpleStringProperty(description);
        this.baseRate = new SimpleDoubleProperty(baseRate);
        this.capacity = new SimpleIntegerProperty(capacity);
        this.noOfBeds = new SimpleIntegerProperty(noOfBeds);
        this.extraCharge = new SimpleDoubleProperty(extraCharge);
        this.totalCharge = new SimpleDoubleProperty(totalCharge);
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

    public int getNoOfBeds() {
        return noOfBeds.get();
    }

    public void setNoOfBeds(int noOfBeds) {
        this.noOfBeds.set(noOfBeds);
    }

    public IntegerProperty noOfBedsProperty() {
        return noOfBeds;
    }

    public double getExtraCharge() {
        return extraCharge.get();
    }

    public void setExtraCharge(double extraCharge) {
        this.extraCharge.set(extraCharge);
    }

    public DoubleProperty extraChargeProperty() {
        return extraCharge;
    }
    
    public double getTotalCharge() {
        return totalCharge.get();
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge.set(totalCharge);
    }

    public DoubleProperty totalChargeProperty() {
        return totalCharge;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.roomID.getValue());
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
        if (!Objects.equals(this.roomID.getValue(), other.roomID.getValue())) {
            return false;
        }
        return true;
    }
    
    
    @Override
    public String toString() {
        return "Room { RoomID=" + roomID + " Description=" + description + " }";
    }
}
