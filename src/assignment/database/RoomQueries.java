/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.AvailableRoom;
import assignment.model.Room;
import assignment.model.RoomInfo;
import assignment.util.DateUtil;
import assignment.view.FindRoomDialogController;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author z5018077
 */
public class RoomQueries extends DatabaseQuery {

    PreparedStatement insertRoom = null;
    PreparedStatement getAllRooms = null;
    PreparedStatement getAllAvailableRooms = null;
    ResultSet rs = null;
    List<RoomInfo> rooms;
    List<AvailableRoom> availableRooms;
    FindRoomDialogController findRoomDialogController;
    
    public List<RoomInfo> getRooms() {
        rooms = new ArrayList<RoomInfo>();
        openConnection();
        try {
            getAllRooms = conn.prepareStatement("select ROOMID, ROOMTYPEID, BASERATE "
                    + "from app.ROOM "
                    + "inner join app.ROOMTYPE "
                    + "on app.ROOM.ROOMTYPEID = app.ROOMTYPE.ROOMTYPEID");
            rs = getAllRooms.executeQuery();
            while (rs.next()) {
                rooms.add(
                        new RoomInfo(rs.getInt("roomID"), rs.getString("roomtypeID"),
                        rs.getDouble("baseRate")));
            }
            rs.close();
            getAllRooms.close();
        } catch (SQLException ex) {
            System.out.println("getRoom() error!");
        }
        closeConnection();
        return rooms;
    }
    
    public List<AvailableRoom> getAvailableRooms() {
        availableRooms = new ArrayList<AvailableRoom>();
        openConnection();
        try {
            Date searchedCheckIn = Date.valueOf(findRoomDialogController.checkInField.getValue());
            Date searchedCheckOut = Date.valueOf(findRoomDialogController.checkOutField.getValue());
            getAllAvailableRooms = conn.prepareStatement("select app.ROOM.ROOMID, "
                    + "app.ROOM.ROOMTYPEID, BASERATE, CHECKIN, CHECKOUT, EARLYCHECKIN, LATECHECKOUT "
                    + "from app.ROOM "
                    + "inner join app.ROOMTYPE "
                    + "on app.ROOM.ROOMTYPEID = app.ROOMTYPE.ROOMTYPEID "
                    + "inner join app.BOOKING "
                    + "on app.BOOKING.ROOMID = app.ROOM.ROOMID "
                    + "where app.ROOM.ROOMID not in "
                    + "(select app.BOOKING.ROOMID "
                    + "from app.BOOKING "
                    + "where ((CHECKIN between 'searchedCheckIn' and 'searchedCheckOut') "
                    + "or (CHECKOUT between 'searchedCheckIn' and 'searchedCheckOut') "
                    + "or (CHECKIN > 'searchedCheckIn' and CHECKOUT < 'searchedCheckOut')))");
            rs = getAllAvailableRooms.executeQuery();
            while (rs.next()) {
                availableRooms.add(
                        new AvailableRoom(rs.getInt("roomID"), rs.getString("roomTypeID"), 
                                rs.getDouble("baseRate"), rs.getDate("checkIn").toLocalDate(),  
                                rs.getDate("checkOut").toLocalDate(), 
                                rs.getBoolean("earlyCheckIn"), rs.getBoolean("lateCheckOut")));
            }
            rs.close();
            getAllAvailableRooms.close();
        } catch (SQLException ex) {
            System.out.println("getRoom() error!");
        }
        closeConnection();
        return availableRooms;
    }

    
    public int insertRoom(Room toInsert) {
        int returnValue = -1;
        openConnection();
        
        try {                
            insertRoom = conn.prepareStatement("insert into app.room "
                    + "(roomtypeid) "
                    + "values (?)", Statement.RETURN_GENERATED_KEYS);
            insertRoom.setString(1, toInsert.getRoomTypeID());            
            insertRoom.executeUpdate();

            rs = insertRoom.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close();
            insertRoom.close();
        } catch (SQLException ex) {
            System.out.println("insertRoom() ERROR!");
            ex.printStackTrace();
        }
        
        closeConnection();
        return returnValue;
    }
    
    public void setFindRoomDialogController(FindRoomDialogController findRoomDialogController) {
        this.findRoomDialogController = findRoomDialogController;
    }
}
