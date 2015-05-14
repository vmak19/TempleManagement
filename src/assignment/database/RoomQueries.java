/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.Room;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author z5018077
 */
public class RoomQueries extends DatabaseQuery {

    PreparedStatement insertRoom = null;
    PreparedStatement getAllRooms = null;
    ResultSet rs = null;
    List<Room> rooms;

    public List<Room> getRooms() {
        rooms = new ArrayList<Room>();
        openConnection();
        try {
            getAllRooms = conn.prepareStatement("select * from app.ROOM inner join app.ROOM_TYPE on app.ROOM.ROOM_ID = app.ROOM_TYPE.ROOM_TYPE_ID");
            rs = getAllRooms.executeQuery();
            while (rs.next()) {
                rooms.add(
                        new Room(rs.getInt("roomID"), rs.getInt("roomTypeID"), rs.getString("description"),
                                rs.getDouble("baseRate"), rs.getInt("capacity")));
            }
            rs.close();
            getAllRooms.close();
        } catch (SQLException ex) {
            System.out.println("getRoom() error!");
        }
        closeConnection();
        return rooms;
    }

    public int insertRoom(Room toInsert) {
        int returnValue = -1;
        openConnection();
        
        try {                
            insertRoom = conn.prepareStatement("insert into app.room "
                    + "(ROOM_ID, ROOM_TYPE_ID)"
                    + "values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertRoom.setInt(1, toInsert.getRoomID());
            insertRoom.setInt(2, toInsert.getRoomTypeID());            
            insertRoom.executeUpdate();
             System.out.println("inserted room");

            rs = insertRoom.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close();
            insertRoom.close();
        } catch (SQLException ex) {
            System.out.println("insertRoom() ERROR!");
        }
        
        closeConnection();
        return returnValue;
    }
}
