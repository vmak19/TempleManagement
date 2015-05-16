/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.AvailableRoom;
import assignment.model.RoomInfo;
import assignment.view.FindRoomDialogController;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author z5018077
 */
public class AvailableRoomQueries extends DatabaseQuery {

    PreparedStatement getAllAvailableRooms = null;
    ResultSet rs = null;
    List<RoomInfo> availableRooms;
    FindRoomDialogController findRoomDialogController;
    
    public List<RoomInfo> getAvailableRooms() {
        availableRooms = new ArrayList<RoomInfo>();
        openConnection();
        try {
            getAllAvailableRooms = conn.prepareStatement("select app.ROOM.ROOMID, "
                    + "app.ROOMTYPE.ROOMTYPEID, BASERATE "
                    + "from app.ROOM "
                    + "inner join app.ROOMTYPE "
                    + "on app.ROOM.ROOMTYPEID = app.ROOMTYPE.ROOMTYPEID");
            rs = getAllAvailableRooms.executeQuery();
            while (rs.next()) {
                availableRooms.add(
                        new RoomInfo(rs.getInt("roomID"), rs.getString("roomtypeID"),
                        rs.getDouble("baseRate")));
            }
            rs.close();
            getAllAvailableRooms.close();
        } catch (SQLException ex) {
            System.out.println("getRoom() error!");
        }
        closeConnection();
        return availableRooms;
    }
    
    /*public List<AvailableRoom> getAvailableRooms() {
        availableRooms = new ArrayList<AvailableRoom>();
        openConnection();
        try {
            Date searchedCheckIn = Date.valueOf(findRoomDialogController.checkInField.getValue());
            Date searchedCheckOut = Date.valueOf(findRoomDialogController.checkOutField.getValue());
            getAllAvailableRooms = conn.prepareStatement("select app.ROOM.ROOMID, "
                    + "app.ROOMTYPE.ROOMTYPEID, BASERATE "
                    + "from app.ROOM "
                    + "inner join app.ROOMTYPE "
                    + "on app.ROOM.ROOMTYPEID = app.ROOMTYPE.ROOMTYPEID");
                    /*"select app.BOOKING.ROOMID, app.ROOM.ROOMTYPEID, BASERATE "
                    + "from app.ROOM "
                    + "inner join app.ROOMTYPE "
                    + "on app.ROOM.ROOMTYPEID = app.ROOMTYPE.ROOMTYPEID "
                    + "inner join app.BOOKING "
                    + "on app.BOOKING.ROOMID = app.ROOM.ROOMID "
                    //+ "where not exists "
                    //+ "(select app.BOOKING.ROOMID "
                    //+ "from app.BOOKING "
                    //+ "where ((CHECKIN between 'searchedCheckIn' and 'searchedCheckOut') "
                    //+ "or (CHECKOUT between 'searchedCheckIn' and 'searchedCheckOut') "
                    //+ "or ('searchedCheckIn' between CHECKIN and CHECKOUT)))"
            );*/
            /*rs = getAllAvailableRooms.executeQuery();
            while (rs.next()) {
                availableRooms.add(
                        new AvailableRoom(rs.getInt("roomID"), rs.getString("roomTypeID"), 
                                rs.getDouble("baseRate")));
            }
            rs.close();
            getAllAvailableRooms.close();
        } catch (SQLException ex) {
            System.out.println("getRoom() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return availableRooms;
    }*/
    
    public void setFindRoomDialogController(FindRoomDialogController findRoomDialogController) {
        this.findRoomDialogController = findRoomDialogController;
    }
}
