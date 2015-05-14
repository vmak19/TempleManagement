/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

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
    private IntegerProperty roomTypeID;

    public Room(int roomID, int roomTypeID) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomTypeID = new SimpleIntegerProperty(roomTypeID);
    }   
    
    public Room(int roomTypeID) {
        this.roomTypeID = new SimpleIntegerProperty(roomTypeID);
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
    
    public int getRoomTypeID() {
        return roomTypeID.get();
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID.set(roomTypeID);
    }
    
    public IntegerProperty roomTypeIDProperty() {
        return roomTypeID;
    }

}
