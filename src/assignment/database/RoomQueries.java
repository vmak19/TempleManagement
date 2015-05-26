/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.AvailableRoom;
import assignment.model.Room;
import assignment.model.RoomInfo;
import assignment.view.FindRoomDialogController;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.ObservableList;

/**
 *
 * @author z5018077
 */
public class RoomQueries extends DatabaseQuery {

    PreparedStatement insertRoom = null;
    PreparedStatement getAllRooms = null;
    PreparedStatement getAllAvailableRooms = null;
    PreparedStatement getAllSameTypeRooms = null;
    PreparedStatement getCapactityByRoomTypeID = null;
    PreparedStatement getTypeRateByRoomID = null;
    ResultSet rs = null;
    List<RoomInfo> rooms;
    List<RoomInfo> availableRooms;
    int capacity;
    RoomInfo room;
    
    public List<RoomInfo> getRooms() {
        rooms = new ArrayList<RoomInfo>();
        openConnection();
        try {
            getAllRooms = conn.prepareStatement("select app.ROOM.ROOMID, NOOFBEDS,"
                    + "app.ROOMTYPE.ROOMTYPEID, DESCRIPTION, BASERATE, CAPACITY "
                    + "from app.ROOM "
                    + "inner join app.ROOMTYPE "
                    + "on app.ROOM.ROOMTYPEID = app.ROOMTYPE.ROOMTYPEID");
            rs = getAllRooms.executeQuery();
            while (rs.next()) {
                rooms.add(
                        new RoomInfo(rs.getInt("roomID"), rs.getString("roomtypeID"),
                                rs.getString("description"), rs.getDouble("baseRate"),
                                rs.getInt("capacity"), rs.getInt("noOfBeds")));
            }
            rs.close();
            getAllRooms.close();
        } catch (SQLException ex) {
            System.out.println("getRoom() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return rooms;
    }

    public Set<RoomInfo> getAvailableRoomsByType(LocalDate checkIn, 
            LocalDate checkOut, List<String> types, boolean earlyCheckIn, 
            boolean lateCheckOut, ObservableList<RoomInfo> selectedRoomData) {
        Set<RoomInfo> availableRooms = new HashSet<RoomInfo>();
        openConnection();
        for (String type : types) {
            try {
                Date searchCheckIn = Date.valueOf(checkIn);
                Date searchCheckOut = Date.valueOf(checkOut);

                getAllAvailableRooms = conn.prepareStatement(""
                        + "select app.ROOM.ROOMID, app.ROOM.ROOMTYPEID, BASERATE, CAPACITY "
                        + "from app.ROOM "
                        + "inner join app.ROOMTYPE "
                        + "on app.ROOM.ROOMTYPEID = app.ROOMTYPE.ROOMTYPEID "
                        + "left join app.ASSIGNMENT "
                        + "on app.ASSIGNMENT.ROOMID = app.ROOM.ROOMID "
                        + "where app.ROOM.ROOMID not in ("
                                + "select app.ASSIGNMENT.ROOMID "
                                + "from app.ASSIGNMENT) "
                        + "and (app.ROOMTYPE.ROOMTYPEID in (?))"
                        
                        + "union all "
                        
                        + "select app.ROOM.ROOMID, app.ROOM.ROOMTYPEID, BASERATE, CAPACITY "
                        + "from app.ROOM "
                        + "inner join app.ROOMTYPE "
                        + "on app.ROOM.ROOMTYPEID = app.ROOMTYPE.ROOMTYPEID "
                        + "right join app.ASSIGNMENT "
                        + "on app.ASSIGNMENT.ROOMID = app.ROOM.ROOMID "
                        + "inner join app.BOOKING "
                        + "on app.ASSIGNMENT.REFCODE = app.BOOKING.REFCODE "
                        + "where (app.ROOM.ROOMID not in ("
                                + "select app.ASSIGNMENT.ROOMID "
                                + "from app.ASSIGNMENT "
                                + "inner join app.BOOKING "
                                + "on app.ASSIGNMENT.REFCODE = app.BOOKING.REFCODE "
                                + "where ((CHECKIN >= ? and  CHECKIN < ?) "
                                + "or (CHECKOUT > ? and CHECKOUT <= ?) "
                                + "or (? >= CHECKIN and CHECKOUT > ?) "
                                + "or (CHECKIN = ? and EARLYCHECKIN = true and ? = true) "
                                + "or (CHECKOUT = ? and LATECHECKOUT = true and ? = true))) "
                        + "and (app.ROOMTYPE.ROOMTYPEID in (?)))"
                );
                
                getAllAvailableRooms.setString(1, type);
                getAllAvailableRooms.setDate(2, searchCheckIn);
                getAllAvailableRooms.setDate(3, searchCheckOut);
                getAllAvailableRooms.setDate(4, searchCheckIn);
                getAllAvailableRooms.setDate(5, searchCheckOut);
                getAllAvailableRooms.setDate(6, searchCheckIn);
                getAllAvailableRooms.setDate(7, searchCheckIn);
                getAllAvailableRooms.setDate(8, searchCheckOut);
                getAllAvailableRooms.setBoolean(9, lateCheckOut);
                getAllAvailableRooms.setDate(10, searchCheckIn);
                getAllAvailableRooms.setBoolean(11, earlyCheckIn);
                getAllAvailableRooms.setString(12, type);

                getAllSameTypeRooms = conn.prepareStatement("select * from app.ROOMTYPE");
                
                ResultSet rsRoomTypes = getAllSameTypeRooms.executeQuery();
                
                while (rsRoomTypes.next()) {
                    rs = getAllAvailableRooms.executeQuery();
                    List<Integer> roomIDList = new ArrayList<Integer>();
                    
                    // Group roomID with the same room type into one
                    while (rs.next()) {
                        if (rs.getString("roomTypeID").equals(rsRoomTypes.getString("roomTypeID"))) {
                            
                            // Check if current room existed in selected room table
                            boolean isRoomClashed = false;
                            for (RoomInfo room : selectedRoomData) {
                                if (room.getRoomID() == rs.getInt("roomID")) {
                                    isRoomClashed = true;
                                }
                            }
                            
                            if (!isRoomClashed) {
                            roomIDList.add(rs.getInt("roomID"));
                            }
                        }
                    }
                    
                    if (!roomIDList.isEmpty()) {
                        availableRooms.add(
                                new RoomInfo(roomIDList, rsRoomTypes.getString("roomTypeID"),
                                        rsRoomTypes.getDouble("baseRate"), rsRoomTypes.getInt("capacity")));
                    }
                    
                    rs.close();
                }
                rsRoomTypes.close();
                getAllAvailableRooms.close();
            } catch (SQLException ex) {
                System.out.println("getAvailableRoomsByType() error!");
                ex.printStackTrace();
            }
        }
        closeConnection();
        return availableRooms;
    }
    
    public int getCapacityByRoomTypeID(String roomType) {
        capacity = -1;
        openConnection();
        try {
            getCapactityByRoomTypeID = conn.prepareStatement("select CAPACITY "
                    + "from app.ROOMTYPE where ROOMTYPEID=?");
            getCapactityByRoomTypeID.setString(1, roomType);
            rs = getCapactityByRoomTypeID.executeQuery();
            while (rs.next()) {
                capacity = rs.getInt("capacity");
            }
            
            rs.close();
            getCapactityByRoomTypeID.close();
        } catch (SQLException ex) {
            System.out.println("getCapacityByRoomTypeID() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return capacity;
    }
    
    public RoomInfo getRoomInfoByRoomID(int roomID) {
        room = null;
        openConnection();
        try {
            getTypeRateByRoomID = conn.prepareStatement("select roomID, "
                    + "app.ROOM.roomTypeID, baseRate "
                    + "from app.ROOM "
                    + "inner join app.ROOMTYPE "
                    + "on app.ROOM.ROOMTYPEID = app.ROOMTYPE.ROOMTYPEID "
                    + "where ROOMID=?");
            getTypeRateByRoomID.setInt(1, roomID);
            rs = getTypeRateByRoomID.executeQuery();
            while (rs.next()) {
                room = new RoomInfo(rs.getInt("roomID"), 
                        rs.getString("roomTypeID"), rs.getDouble("baseRate"));
            }
            
            rs.close();
            getTypeRateByRoomID.close();
        } catch (SQLException ex) {
            System.out.println("getTypeRateByRoomID() error!");
            ex.printStackTrace();
        }
        closeConnection();
        return room;
    }
            
    public void insertRoom(Room toInsert) {
        openConnection();

        try {
            insertRoom = conn.prepareStatement("insert into app.room "
                    + "(roomid, roomtypeid, noofbeds) "
                    + "values (?, ?, ?)");
            insertRoom.setInt(1, toInsert.getRoomID());
            insertRoom.setString(2, toInsert.getRoomTypeID());
            insertRoom.setInt(3, toInsert.getNoOfBeds());
            insertRoom.executeUpdate();

            insertRoom.close();
        } catch (SQLException ex) {
            System.out.println("insertRoom() ERROR!");
            ex.printStackTrace();
        }
        closeConnection();
    }
}
