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
    List<RoomInfo> availableRooms;

    public List<RoomInfo> getRooms() {
        rooms = new ArrayList<RoomInfo>();
        openConnection();
        try {
            getAllRooms = conn.prepareStatement("select app.ROOM.ROOMID, NOOFBEDS, EXTRACHARGE, TOTALCHARGE,"
                    + "app.ROOMTYPE.ROOMTYPEID, DESCRIPTION, BASERATE, CAPACITY "
                    + "from app.ROOM "
                    + "inner join app.ROOMTYPE "
                    + "on app.ROOM.ROOMTYPEID = app.ROOMTYPE.ROOMTYPEID");
            rs = getAllRooms.executeQuery();
            while (rs.next()) {
                rooms.add(
                        new RoomInfo(rs.getInt("roomID"), rs.getString("roomtypeID"),
                                rs.getString("description"), rs.getDouble("baseRate"),
                                rs.getInt("capacity"), rs.getInt("noOfBeds"),
                                rs.getDouble("extraCharge"), rs.getDouble("totalCharge")));
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

    public List<RoomInfo> getAvailableRoomsByType(LocalDate checkIn, 
            LocalDate checkOut, List<String> types, boolean earlyCheckIn, 
            boolean lateCheckOut) {
        List<RoomInfo> availableRooms = new ArrayList<RoomInfo>();
        openConnection();
        for (String type : types) {
            try {
                Date searchCheckIn = Date.valueOf(checkIn);
                Date searchCheckOut = Date.valueOf(checkOut);

                //System.out.println(findRoomDialogController.getSearchRoomType());
                getAllAvailableRooms = conn.prepareStatement(
                        "select app.ASSIGNMENT.ROOMID, app.ROOM.ROOMTYPEID, BASERATE, CAPACITY "
                        + "from app.ROOM "
                        + "inner join app.ROOMTYPE "
                        + "on app.ROOM.ROOMTYPEID = app.ROOMTYPE.ROOMTYPEID "
                        + "inner join app.ASSIGNMENT "
                        + "on app.ASSIGNMENT.ROOMID = app.ROOM.ROOMID "
                        + "inner join app.BOOKING "
                        + "on app.ASSIGNMENT.REFCODE = app.BOOKING.REFCODE "
                        + "where ((CHECKIN < ? or CHECKIN >= ?) "
                        + "and (CHECKOUT <= ? or CHECKOUT > ?) "
                        + "and (CHECKIN < ? or CHECKOUT >= ?) "
                        + "and (app.ROOMTYPE.ROOMTYPEID in (?))"
                        + "and not exists ("
                                + "select * from app.BOOKING "
                                + "where (CHECKIN = ? and EARLYCHECKIN = true and ? = true) "
                                + "or (CHECKOUT = ? and LATECHECKOUT = true and ? = true)))"
                );

                getAllAvailableRooms.setDate(1, searchCheckIn);
                getAllAvailableRooms.setDate(2, searchCheckOut);
                getAllAvailableRooms.setDate(3, searchCheckIn);
                getAllAvailableRooms.setDate(4, searchCheckOut);
                getAllAvailableRooms.setDate(5, searchCheckIn);
                getAllAvailableRooms.setDate(6, searchCheckIn);
                getAllAvailableRooms.setString(7, type);
                getAllAvailableRooms.setDate(8, searchCheckOut);
                getAllAvailableRooms.setBoolean(9, lateCheckOut);
                getAllAvailableRooms.setDate(10, searchCheckIn);
                getAllAvailableRooms.setBoolean(11, earlyCheckIn);

                rs = getAllAvailableRooms.executeQuery();
                while (rs.next()) {

                    availableRooms.add(
                            new RoomInfo(rs.getInt("roomID"), rs.getString("roomTypeID"),
                                    rs.getDouble("baseRate"), rs.getInt("capacity")));
                }
                rs.close();
                getAllAvailableRooms.close();
            } catch (SQLException ex) {
                System.out.println("getAvailableRoomsByType() error!");
                ex.printStackTrace();
            }
        }
        closeConnection();
        return availableRooms;
    }

    public void insertRoom(Room toInsert) {
        openConnection();

        try {
            insertRoom = conn.prepareStatement("insert into app.room "
                    + "(roomid, roomtypeid, noofbeds, extraCharge, totalCharge) "
                    + "values (?, ?, ?, ?, ?)");
            insertRoom.setInt(1, toInsert.getRoomID());
            insertRoom.setString(2, toInsert.getRoomTypeID());
            insertRoom.setInt(3, toInsert.getNoOfBeds());
            insertRoom.setDouble(4, toInsert.getExtraCharge());
            insertRoom.setDouble(5, toInsert.getTotalCharge());
            insertRoom.executeUpdate();

            insertRoom.close();
        } catch (SQLException ex) {
            System.out.println("insertRoom() ERROR!");
            ex.printStackTrace();
        }
        closeConnection();
    }
}
