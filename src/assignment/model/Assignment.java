/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author SONY
 */
public class Assignment {
    public IntegerProperty refCode;
    public IntegerProperty roomID;
    public IntegerProperty numPeople;
    
    public Assignment(int refCode, int roomID) {
        this.refCode = new SimpleIntegerProperty(refCode);
        this.roomID = new SimpleIntegerProperty(roomID);
    }
    
    public Assignment(int refCode, int roomID, int numPeople) {
        this.refCode = new SimpleIntegerProperty(refCode);
        this.roomID = new SimpleIntegerProperty(roomID);
        this.numPeople = new SimpleIntegerProperty(numPeople);
    }
    
    public int getRefCode() {
        return refCode.get();
    }
    
    public void setRefCode(int refCode) {
        this.refCode.set(refCode);
    }
    
    public int getRoomID() {
        return roomID.get();
    }
    
    public void setRoomID(int roomID) {
        this.roomID.set(roomID);
    }
    
    public int getNumPeople() {
        return numPeople.get();
    }
    
    public void setNumPeople(int numPeople) {
        this.numPeople.set(numPeople);
    }
}
