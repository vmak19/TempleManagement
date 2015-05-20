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

//just test stage 1
//minimum 5 test
//test reading in; diffrenet type of cvs in diff circumstance
//test database for rm and inital reading 
/**
 *
 * @author z5018077
 */
public class Room /*extends RoomType*/ {

    private IntegerProperty roomID;
    private StringProperty roomTypeID;
    private IntegerProperty noOfBeds;
    private DoubleProperty extraCharge;
    private DoubleProperty totalCharge;

    public Room(int roomID, String roomTypeID, int noOfBeds, double extraCharge, double totalCharge) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
        this.noOfBeds = new SimpleIntegerProperty(noOfBeds);
        this.extraCharge = new SimpleDoubleProperty(extraCharge);
        this.totalCharge = new SimpleDoubleProperty(totalCharge);
    }

    public Room(int roomID, String roomTypeID, int noOfBeds) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
        this.noOfBeds = new SimpleIntegerProperty(noOfBeds);
    }

    public Room(int roomID, String roomTypeID) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
    }

    public Room(String roomTypeID) {
        this.roomTypeID = new SimpleStringProperty(roomTypeID);
    }

    public int getRoomID() {
        return roomID.get();
    }

    public void setRoomID(int roomID) {
        this.roomID.set(roomID);
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
}
