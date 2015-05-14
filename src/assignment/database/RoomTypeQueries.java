/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.database;

import assignment.model.RoomType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mak
 */
public class RoomTypeQueries extends DatabaseQuery{
    
    PreparedStatement insertRoomType = null;
    PreparedStatement getAllRoomTypes = null;
    ResultSet rs = null;
    List<RoomType> roomTypes;

    public List<RoomType> getRoomTypes() {
        roomTypes = new ArrayList<RoomType>();
        openConnection();
        try {
            getAllRoomTypes = conn.prepareStatement("select * from app.ROOM_TYPE");
            rs = getAllRoomTypes.executeQuery();
            while (rs.next()) {
                roomTypes.add(
                        new RoomType(rs.getInt("roomTypeID"), rs.getString("description"),
                                rs.getDouble("baseRate"), rs.getInt("capacity")));
            }
            rs.close();
            getAllRoomTypes.close();
        } catch (SQLException ex) {
            System.out.println("getRoomType() error!");
        }
        closeConnection();
        return roomTypes;
    }

    public int insertRoomType(RoomType toInsert) {
        int returnValue = -1;
        openConnection();
//System.out.println("1");
        try {
            insertRoomType = conn.prepareStatement("insert into app.ROOM_TYPE " 
                    + "(DESCRIPTION, BASE_RATE, CAPACITY)"
                    + "values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);           
//System.out.println("2");
            insertRoomType.setInt(1, toInsert.getRoomTypeID());
            insertRoomType.setString(2, toInsert.getDescription());
            insertRoomType.setDouble(3, toInsert.getBaseRate());
            insertRoomType.setInt(4, toInsert.getCapacity());
            insertRoomType.executeUpdate();

            rs = insertRoomType.getGeneratedKeys();
            rs.next();
            returnValue = rs.getInt(1);
            rs.close();
            insertRoomType.close();
        } catch (SQLException ex) {
            System.out.println("insertRoomType() ERROR!");
        }

        closeConnection();
        return returnValue;
    }
}
