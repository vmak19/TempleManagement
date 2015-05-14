/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.Room;
import assignment.model.RoomInfo;
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
    PreparedStatement getAllAvailableRooms = null;
    ResultSet rs = null;
    List<RoomInfo> rooms;
    List<AvailableRoom> availableRoom;

    public List<RoomInfo> getRooms() {
        rooms = new ArrayList<RoomInfo>();
        openConnection();
        try {
            getAllRooms = conn.prepareStatement("select ROOMID, DESCRIPTION, BASERATE "
                    + "from app.ROOM "
                    + "inner join app.ROOMTYPE "
                    + "on app.ROOM.ROOMTYPEID = app.ROOMTYPE.ROOMTYPEID");
            rs = getAllRooms.executeQuery();
            while (rs.next()) {
                rooms.add(
                        new RoomInfo(rs.getInt("roomID"), rs.getString("description"),
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
    
    public List<RoomInfo> getRooms() {
        rooms = new ArrayList<RoomInfo>();
        openConnection();
        try {
            getAllRooms = conn.prepareStatement("select ROOMID, DESCRIPTION, BASERATE "
                    + "from app.ROOM "
                    + "inner join app.ROOMTYPE "
                    + "on app.ROOM.ROOMTYPEID = app.ROOMTYPE.ROOMTYPEID");
            rs = getAllRooms.executeQuery();
            while (rs.next()) {
                rooms.add(
                        new RoomInfo(rs.getInt("roomID"), rs.getString("description"),
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
    
    public int insertRoom(Room toInsert) {
        int returnValue = -1;
        openConnection();
        
        try {                
            insertRoom = conn.prepareStatement("insert into app.room "
                    + "(roomtypeid) "
                    + "values (?)", Statement.RETURN_GENERATED_KEYS);
            insertRoom.setInt(1, toInsert.getRoomTypeID());            
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
